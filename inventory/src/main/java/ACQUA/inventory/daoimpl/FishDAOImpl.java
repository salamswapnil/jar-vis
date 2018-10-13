package ACQUA.inventory.daoimpl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import ACQUA.inventory.dao.FishDAO;
import ACQUA.inventory.model.Fish;
import ACQUA.inventory.utils.UpdateMongo;
import net.sf.cglib.asm.Type;

public class FishDAOImpl implements FishDAO {
	@Autowired
	Fish fish;
	
	@Autowired
	UpdateMongo updateMongo;
	private final String ADD_FISH_CATEGORY="INSERT INTO fish_master(RecordId,Category,Total_Quantity,Date_Added) VALUES(?,?,?,?)";
	//private final String COMMIT="commit";
	private final String GET_ALL_FISH_CATEGORIES="SELECT * FROM fish_master ORDER BY Category";
	private final String ADD_FISH_DETAILS="INSERT INTO FISH_DETAIL(RecordID,Fish_Category,BatchNo,Rate,Quantity,Purchase_Date,Date_Added) "
			+ "VALUES(?,?,?,?,?,?,?);";
	//private final String FISH_STOCK= "SELECT fm.Category,fd.Fish_Category,fd.RecordID, fd.Quantity, fd.BatchNo,fd.Rate FROM fish_master fm INNER JOIN fish_detail fd ON fm.RecordId=fd.Fish_Category";
	private final String FISH_STOCK="SELECT Category,Total_Quantity From Fish_Master;";
	private final String UPDATE_FISH_DETAILS="UPDATE Fish_Detail SET Rate=?,Quantity=?,Last_Modified_Date=? WHERE RecordID=?";
	private final String GET_FISH_FOR_SALE="SELECT DISTINCT(fm.category),fm.recordid FROM fish_master fm, fish_detail fd WHERE fm.recordid=fd.fish_category AND fd.quantity > 0";
	private final String GET_QUANTITY_BY_ID="SELECT Total_Quantity FROM FISH_MASTER WHERE RecordID=?";
	private final String UPDATE_STOCK="UPDATE Fish_Master SET Total_Quantity=?,Last_Modified_Date=? WHERE RecordID=?";
	private final String GET_FISH_NAME_BY_ID="SELECT Category FROM Fish_Master WHERE RecordID=?";
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public int addFishCategory(Fish fish) {
		// TODO Auto-generated method stub
		int n=0;
		jdbcTemplate=new JdbcTemplate(dataSource);
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR,Types.INTEGER,Types.DATE};
		Object[] params = new Object[]{fish.getFishRecordID(),fish.getFishCategory(),0,fish.getDateAdded()};
		n=jdbcTemplate.update(ADD_FISH_CATEGORY,params,types);
		//jdbcTemplate.execute(COMMIT);
		return n;
	}

	@Override
	public int addFishDetails(Fish fish) {
		// TODO Auto-generated method stub
		jdbcTemplate=new JdbcTemplate(dataSource);
		int[] types = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DOUBLE,Types.DOUBLE,Types.DATE,Types.DATE};
		
		Object[] params = new Object[]{fish.getFishRecordID(),fish.getFishCategory(),fish.getBatchNo(),fish.getRate(),
				fish.getQuantity(),fish.getPurchaseDate(),fish.getDateAdded()};
		int n=jdbcTemplate.update(ADD_FISH_DETAILS,params,types);
		int n1;
		int quantity=0;
		if(n>0){
			quantity=getQuantityByID(fish.getFishCategory());
			quantity=quantity+fish.getQuantity();
			n1=updateFishStockByID(quantity, fish.getFishCategory());
			/*New code for updating mogo DB*/
			if(n1>0){
				fish.setQuantity(quantity);
				fish.setLastModifiedDate(new Date());
				//updateMongo.updateFishStock(fish);
			}
			/************/
		}
		return n;
	}

	@Override
	public int updateFishDetails(Fish fish) {
		jdbcTemplate=new JdbcTemplate(dataSource);
		int[] types = new int[]{Types.DOUBLE,Types.DOUBLE,Types.DATE,Types.VARCHAR};
		Object[] params = new Object[]{fish.getRate(),fish.getQuantity(),fish.getLastModifiedDate(),fish.getRecordID()};
		return jdbcTemplate.update(UPDATE_FISH_DETAILS,params,types);
	}

	@Override
	public Map<String,List<String>> getAllFishSpecies() {
		// TODO Auto-generated method stub
		List <String> ids = new ArrayList<String>();
		List <String> species = new ArrayList<String>();
		Map<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		
		jdbcTemplate=new JdbcTemplate(dataSource);
		List <Map<String,Object>> list = jdbcTemplate.queryForList(GET_ALL_FISH_CATEGORIES);
		for(Map<String,Object> row:list){
			ids.add(row.get("RecordId").toString());
			species.add(row.get("Category").toString());
		}
		
		map.put("IDList", ids);
		map.put("SpeciesList", species);
		System.out.println(species);
		return map;
	}

	@Override
	public List<Fish> getFishStock() {
		// TODO Auto-generated method stub
		List<Fish> fishStock=new ArrayList<Fish>(); 
		Fish f;
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(FISH_STOCK);
		for(Map<String,Object> row:list){
			f = new Fish();
			//f.setRecordID(row.get("RecordID").toString());
			//f.setFishRecordID(row.get("Fish_Category").toString());
			f.setFishCategory(row.get("Category").toString());
			f.setQuantity(Integer.parseInt(row.get("Total_Quantity").toString()));
			//f.setRate(Double.valueOf(row.get("Rate").toString()));
			//f.setBatchNo(row.get("BatchNo").toString());
			fishStock.add(f);
		}
		return fishStock;
	}

	@Override
	public Map<String, List<String>> getFishNamesForSale() {
		List <String> fishIdList = new ArrayList<String>();
		List <String> fishNameList = new ArrayList<String>();
		Map<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		
		jdbcTemplate=new JdbcTemplate(dataSource);
		List <Map<String,Object>> list = jdbcTemplate.queryForList(GET_FISH_FOR_SALE);
		for(Map<String,Object> row:list){
			fishIdList.add(row.get("RecordId").toString());
			fishNameList.add(row.get("Category").toString());
		}
		
		map.put("IDList", fishIdList);
		map.put("SpeciesList", fishNameList);
		System.out.println(fishNameList);
		return map;
	}

	@Override
	public int getQuantityByID(String id) {
		int quantity;
		jdbcTemplate=new JdbcTemplate(dataSource);
		quantity=Integer.parseInt(jdbcTemplate.queryForObject(GET_QUANTITY_BY_ID, new Object[]{id},String.class));
		return quantity;
	}

	@Override
	public int updateFishStockByID(int quantity,String id) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		int[] types = new int[]{Types.INTEGER,Types.DATE,Types.VARCHAR};
		Object[] params = new Object[]{quantity,new Date(),id};
		return jdbcTemplate.update(UPDATE_STOCK,params,types);
	}

	@Override
	public String getFishNameById(String id) {
		// TODO Auto-generated method stub
		jdbcTemplate=new JdbcTemplate(dataSource);
		String fName=null;
		try{
			fName=jdbcTemplate.queryForObject(GET_FISH_NAME_BY_ID, new Object[]{id},String.class);
		}
		catch(EmptyResultDataAccessException e){
			System.out.println("No matching product found in Fish table");
			System.out.println(e.getMessage());
		}
		return fName;
	}
	
	
}

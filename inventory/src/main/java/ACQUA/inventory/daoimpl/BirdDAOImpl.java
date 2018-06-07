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

import ACQUA.inventory.dao.BirdDAO;
import ACQUA.inventory.model.Bird;
import ACQUA.inventory.model.Fish;

public class BirdDAOImpl implements BirdDAO {

	@Autowired
	Bird bird;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DataSource dataSource;
	
	private final String GET_ALL_BIRD_NAMES="SELECT * FROM Bird_Master ORDER BY Bird_Name"; 
	private final String ADD_BIRD_CATEGORY="INSERT INTO Bird_Master(RecordID,Bird_Name,Total_Quantity,Date_Added) VALUES(?,?,?,?)";
	private final String ADD_BIRD_DETAILS="INSERT INTO Bird_Detail(RecordID,BirdID,BatchNo,Rate,Quantity,Purchase_Date,Date_Added)"
			+ "VALUES(?,?,?,?,?,?,?)";
	//private final String BIRD_STOCK="SELECT bm.Bird_Name,bd.RecordID,bd.BirdID,bd.BatchNo,bd.Rate,bd.Quantity,bd.Purchase_Date,bd.Date_Added "
			//+ "FROM Bird_Master bm, Bird_Detail bd WHERE bm.RecordId=bd.BirdID";
	private final String BIRD_STOCK="SELECT Bird_Name,Total_Quantity FROM Bird_Master;";
	private final String UPDATE_BIRD_DETAILS="UPDATE Bird_Detail SET Rate=?,Quantity=?,Last_Modified_Date=? WHERE RecordID=?";
	private final String GET_BIRD_FOR_SALE="SELECT DISTINCT(bm.Bird_Name),bm.RecordId FROM bird_master bm, bird_detail bd WHERE bm.recordid=bd.BirdId AND bd.quantity > 0";
	private final String GET_QUANTITY_BY_ID="SELECT Total_Quantity FROM Bird_Master WHERE RecordID=?";
	private final String UPDATE_STOCK="UPDATE Bird_Master SET Total_Quantity=?,Last_Modified_Date=? WHERE RecordID=?";
	private final String GET_BIRD_NAME_BY_ID="SELECT Bird_Name FROM Bird_Master WHERE RecordID=?";
	
	@Override
	public Map<String, List<String>> getAllBirdNames() {
		List <String> ids = new ArrayList<String>();
		List <String> species = new ArrayList<String>();
		Map<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		
		jdbcTemplate=new JdbcTemplate(dataSource);
		List <Map<String,Object>> list = jdbcTemplate.queryForList(GET_ALL_BIRD_NAMES);
		
		for(Map<String,Object> row:list){
			System.out.println(row.get("RecordID").toString());
			ids.add(row.get("RecordID").toString());
			System.out.println(row.get("Bird_Name").toString());
			species.add(row.get("Bird_Name").toString());
		}
		
		map.put("IDList", ids);
		map.put("NameList", species);
		return map;
	}

	@Override
	public int addBirdName(Bird bird) {
		jdbcTemplate=new JdbcTemplate(dataSource);
		int[] types = new int[] { Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.DATE};
		Object[] params = new Object[]{bird.getBirdRecordID(),bird.getBirdName(),0,bird.getDateAdded()};
		return jdbcTemplate.update(ADD_BIRD_CATEGORY,params,types);
	}

	@Override
	public int addBirdDetails(Bird bird) {
		int n=0;
		jdbcTemplate = new JdbcTemplate(dataSource);
		int[] types = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DOUBLE,Types.INTEGER,Types.DATE,Types.DATE};
		Object[] params = new Object[]{bird.getBirdRecordID(),bird.getBirdName(),bird.getBatchNo(),bird.getRate(),bird.getQuantity(),bird.getPurchaseDate(),bird.getDateAdded()};
		n=jdbcTemplate.update(ADD_BIRD_DETAILS, params, types);
		int quantity=0;
		if(n>0){
			quantity=getQuantityByID(bird.getBirdName());
			quantity=quantity+bird.getQuantity();
			n=updateBirdStockByID(quantity, bird.getBirdName());
		}
		return n;
		
	}
	
	@Override
	public int updateBirdDetails(Bird bird) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		int[] types = new int[]{Types.DOUBLE,Types.INTEGER,Types.DATE,Types.VARCHAR};
		Object[] params = new Object[]{bird.getRate(),bird.getQuantity(),bird.getLastModifiedDate(),bird.getRecordID()};
		return jdbcTemplate.update(UPDATE_BIRD_DETAILS, params, types);
	}
	
	@Override
	public List<Bird> getBirdStock() {
		List<Bird> birdStock=new ArrayList<Bird>(); 
		Bird b;
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(BIRD_STOCK);
		for(Map<String,Object> row:list){
			b = new Bird();
			/*b.setRecordID(row.get("RecordID").toString());
			b.setBirdRecordID(row.get("BirdID").toString());*/
			b.setBirdName(row.get("Bird_Name").toString());
			b.setQuantity(Integer.parseInt(row.get("Total_Quantity").toString()));
			/*b.setRate(Double.valueOf(row.get("Rate").toString()));
			b.setBatchNo(row.get("BatchNo").toString());*/
			birdStock.add(b);
		}
		return birdStock;
	}

	@Override
	public Map<String, List<String>> getBirdNamesForSale() {
		List <String> birdIdList = new ArrayList<String>();
		List <String> birdNameList = new ArrayList<String>();
		Map<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		
		jdbcTemplate=new JdbcTemplate(dataSource);
		List <Map<String,Object>> list = jdbcTemplate.queryForList(GET_BIRD_FOR_SALE);
		for(Map<String,Object> row:list){
			birdIdList.add(row.get("RecordId").toString());
			birdNameList.add(row.get("Bird_Name").toString());
		}
		
		map.put("IDList", birdIdList);
		map.put("SpeciesList", birdNameList);
		System.out.println(birdNameList);
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
	public int updateBirdStockByID(int quantity, String id) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		int[] types = new int[]{Types.INTEGER,Types.DATE,Types.VARCHAR};
		Object[] params = new Object[]{quantity,new Date(),id};
		return jdbcTemplate.update(UPDATE_STOCK,params,types);
	}

	@Override
	public String getBirdNameById(String id) {
		// TODO Auto-generated method stub
		jdbcTemplate=new JdbcTemplate(dataSource);
		String bName=null;
		try{
			bName=jdbcTemplate.queryForObject(GET_BIRD_NAME_BY_ID, new Object[]{id},String.class);
		}
		catch(EmptyResultDataAccessException e){
			System.out.println("No matching product found in Bird table");
			System.out.println(e.getMessage());
		}
		return bName;
	}
	
}

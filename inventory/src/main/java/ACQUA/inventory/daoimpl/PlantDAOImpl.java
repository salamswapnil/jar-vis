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

import ACQUA.inventory.dao.PlantDAO;
import ACQUA.inventory.model.Plant;

public class PlantDAOImpl implements PlantDAO {

	@Autowired
	Plant plant;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DataSource dataSource;
	
	private final String ADD_PLANT="INSERT INTO plant_master(RecordId,Plant_Name,Total_Quantity,Date_Added) values(?,?,?,?)";
	private final String ADD_PLANT_DETAILS="INSERT INTO plant_detail (RecordID,PlantID,BatchNo,Rate,Quantity,Purchase_Date,Date_Added)"
			+ "VALUES(?,?,?,?,?,?,?)";
	//private final String PLANT_STOCK="SELECT pm.Plant_Name,pd.PlantID,pd.RecordID, pd.Quantity, pd.BatchNo, pd.Rate FROM plant_master pm INNER JOIN plant_detail pd ON pm.RecordId=pd.PlantId";
	private final String PLANT_STOCK="SELECT Plant_Name,Total_Quantity FROM Plant_Master;";
	
	private final String GET_ALL_PLANT_NAMES="SELECT * FROM plant_master ORDER BY Plant_Name";
	private final String UPDATE_PLANT_DETAILS="UPDATE Plant_Detail SET Rate=?,Quantity=?,Last_Modified_Date=? WHERE RecordID=?";
	private final String GET_PLANT_FOR_SALE="SELECT DISTINCT(pm.Plant_Name),pm.recordid FROM plant_master pm, plant_detail pd WHERE pm.recordid=pd.PlantID AND pd.quantity > 0";
	private final String GET_QUANTITY_BY_ID="SELECT Total_Quantity FROM Plant_MASTER WHERE RecordID=?";
	private final String UPDATE_STOCK="UPDATE Plant_Master SET Total_Quantity=?,Last_Modified_Date=? WHERE RecordID=?";
	private final String GET_PLANT_NAME_BY_ID="SELECT Plant_Name FROM Plant_Master WHERE RecordID=?";
	
	@Override
	public int addPlant(Plant plant) {
		// TODO Auto-generated method stub
		int n=0;
		jdbcTemplate=new JdbcTemplate(dataSource);
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR,Types.INTEGER,Types.DATE};
		Object[] params = new Object[]{plant.getPlantRecordID(),plant.getPlantName(),0,plant.getDateAdded()};
		n=jdbcTemplate.update(ADD_PLANT,params,types);
		return n;
	}

	@Override
	public int addPlantDetails(Plant plant) {
		// TODO Auto-generated method stub
		jdbcTemplate = new JdbcTemplate(dataSource);
		int[] types = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DOUBLE,Types.INTEGER,Types.DATE,Types.DATE};
		Object[] params = {plant.getPlantRecordID(),plant.getPlantId(),plant.getBatchNo(),plant.getRate(),plant.getQuantity(),plant.getPurchaseDate(),plant.getDateAdded()};
		
		int n=jdbcTemplate.update(ADD_PLANT_DETAILS,params,types);
		int quantity=0;
		if(n>0){
			quantity=getQuantityByID(plant.getPlantId());
			quantity=quantity+plant.getQuantity();
			n=updatePlantStockByID(quantity,plant.getPlantId());
		}
		return n;
	}

	@Override
	public int updatePlantDetails(Plant plant) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		int[] types = {Types.DOUBLE,Types.INTEGER,Types.DATE,Types.VARCHAR};
		Object[] params = {plant.getRate(),plant.getQuantity(),plant.getLastModifiedDate(),plant.getRecordId()};
		return jdbcTemplate.update(UPDATE_PLANT_DETAILS,params,types);
	}

	@Override
	public Map<String, List<String>> getAllPlantNames() {
		// TODO Auto-generated method stub
		List <String> ids = new ArrayList<String>();
		List <String> names = new ArrayList<String>();
		Map<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		
		jdbcTemplate=new JdbcTemplate(dataSource);
		List <Map<String,Object>> list = jdbcTemplate.queryForList(GET_ALL_PLANT_NAMES);
		
		for(Map<String,Object> row:list){
			ids.add(row.get("RecordId").toString());
			names.add(row.get("Plant_Name").toString());
		}
		
		map.put("IDList", ids);
		map.put("NameList", names);
		return map;
	}

	@Override
	public List<Plant> getPlantStock() {
		// TODO Auto-generated method stub
		List<Plant> plantstock = new ArrayList<Plant>();
		Plant p;
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(PLANT_STOCK);
		for(Map<String,Object> row:list){
			p=new Plant();
			/*p.setRecordId(row.get("RecordID").toString());
			p.setPlantId(row.get("PlantID").toString());*/
			p.setPlantName(row.get("Plant_Name").toString());
			//p.setBatchNo(row.get("BatchNo").toString());
			p.setQuantity(Integer.parseInt(row.get("Total_Quantity").toString()));
			//p.setRate(Double.valueOf(row.get("Rate").toString()));
			plantstock.add(p);
		}
		return plantstock;
	}

	@Override
	public Map<String, List<String>> getPlantNamesForSale() {
		List <String> plantIdList = new ArrayList<String>();
		List <String> plantNameList = new ArrayList<String>();
		Map<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		
		jdbcTemplate=new JdbcTemplate(dataSource);
		List <Map<String,Object>> list = jdbcTemplate.queryForList(GET_PLANT_FOR_SALE);
		for(Map<String,Object> row:list){
			plantIdList.add(row.get("RecordId").toString());
			plantNameList.add(row.get("Plant_Name").toString());
		}
		
		map.put("IDList", plantIdList);
		map.put("SpeciesList", plantNameList);
		System.out.println(plantNameList);
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
	public int updatePlantStockByID(int quantity, String id) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		int[] types = new int[]{Types.INTEGER,Types.DATE,Types.VARCHAR};
		Object[] params = new Object[]{quantity,new Date(),id};
		return jdbcTemplate.update(UPDATE_STOCK,params,types);
	}

	@Override
	public String getPlantNameById(String id) {
		// TODO Auto-generated method stub
		jdbcTemplate=new JdbcTemplate(dataSource);
		String pName=null;
		try{
			pName=jdbcTemplate.queryForObject(GET_PLANT_NAME_BY_ID, new Object[]{id},String.class);
		}
		catch(EmptyResultDataAccessException e){
			System.out.println("No matching product found in Plants table");
			System.out.println(e.getMessage());
		}
		return pName;
	}

}

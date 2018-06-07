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

import ACQUA.inventory.dao.RawMaterialDAO;
import ACQUA.inventory.model.Fish;
import ACQUA.inventory.model.RawMaterial;

public class RawMaterialDAOImpl implements RawMaterialDAO {

	@Autowired
	RawMaterial rawMaterial;
	
	private final String ADD_MATERIAL_NAME="INSERT INTO raw_material_master(RecordId,Raw_Material_Name,Total_Quantity,Date_Added) VALUES(?,?,?,?)";
	private final String GET_ALL_RM_NAMES="SELECT * FROM raw_material_master ORDER BY Raw_Material_Name";
	private final String ADD_RM_DETAILS="INSERT INTO raw_material_detail(RecordID,MaterialID,BatchNo,Rate,Quantity,Purchase_Date,Date_Added) "
			+ "VALUES(?,?,?,?,?,?,?);";
	private final String GET_QUANTITY_BY_ID="SELECT Total_Quantity FROM raw_material_master WHERE RecordID=?";
	private final String UPDATE_STOCK="UPDATE raw_material_master SET Total_Quantity=?,Last_Modified_Date=? WHERE RecordID=?";
	private final String RM_STOCK="SELECT Raw_Material_Name,Total_Quantity From raw_material_master";
	private final String GET_RM_FOR_SALE="SELECT DISTINCT(rmm.Raw_Material_Name),rmm.recordid FROM raw_material_master rmm, raw_material_detail rmd WHERE rmm.recordid=rmd.MaterialId AND rmd.quantity > 0";
	private final String GET_RM_NAME_BY_ID="SELECT Raw_Material_Name FROM Raw_Material_Master WHERE RecordID=?";
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public int addMaterialName(RawMaterial rawMaterial) {
		int n=0;
		jdbcTemplate=new JdbcTemplate(dataSource);
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR,Types.INTEGER,Types.DATE};
		Object[] params = new Object[]{rawMaterial.getRMRecordID(),rawMaterial.getRMCategory(),0,rawMaterial.getDateAdded()};
		n=jdbcTemplate.update(ADD_MATERIAL_NAME,params,types);
		//jdbcTemplate.execute(COMMIT);
		return n;
	}

	@Override
	public Map<String, List<String>> getAllRMNames() {
		List <String> ids = new ArrayList<String>();
		List <String> species = new ArrayList<String>();
		Map<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		
		jdbcTemplate=new JdbcTemplate(dataSource);
		List <Map<String,Object>> list = jdbcTemplate.queryForList(GET_ALL_RM_NAMES);
		for(Map<String,Object> row:list){
			ids.add(row.get("RecordId").toString());
			species.add(row.get("Raw_Material_Name").toString());
		}
		
		map.put("IDList", ids);
		map.put("NameList", species);
		/*System.out.println(ids);
		System.out.println(species);*/
		return map;
	}

	@Override
	public int addMaterialDetails(RawMaterial rawMaterial) {
		// TODO Auto-generated method stub
				jdbcTemplate=new JdbcTemplate(dataSource);
				int[] types = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DOUBLE,Types.DOUBLE,Types.DATE,Types.DATE};
				
				Object[] params = new Object[]{rawMaterial.getRecordID(),rawMaterial.getRMRecordID(),rawMaterial.getBatchNo(),rawMaterial.getRate(),
						rawMaterial.getQuantity(),rawMaterial.getPurchaseDate(),rawMaterial.getDateAdded()};
				int n=jdbcTemplate.update(ADD_RM_DETAILS,params,types);
				int quantity=0;
				if(n>0){
					quantity=getQuantityById(rawMaterial.getRMRecordID());
					quantity=quantity+rawMaterial.getQuantity();
					n=updateRMStockById(quantity, rawMaterial.getRMRecordID());
				}
				return n;
	}

	@Override
	public int getQuantityById(String id) {
		int quantity;
		jdbcTemplate=new JdbcTemplate(dataSource);
		quantity=Integer.parseInt(jdbcTemplate.queryForObject(GET_QUANTITY_BY_ID, new Object[]{id},String.class));
		return quantity;
	}

	@Override
	public int updateRMStockById(int quantity,String id) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		int[] types = new int[]{Types.INTEGER,Types.DATE,Types.VARCHAR};
		Object[] params = new Object[]{quantity,new Date(),id};
		return jdbcTemplate.update(UPDATE_STOCK,params,types);
	}

	@Override
	public List<RawMaterial> getRMStock() {
		List<RawMaterial> rmStock=new ArrayList<RawMaterial>(); 
		RawMaterial rm;
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(RM_STOCK);
		for(Map<String,Object> row:list){
			rm = new RawMaterial();
			//f.setRecordID(row.get("RecordID").toString());
			//f.setFishRecordID(row.get("Fish_Category").toString());
			rm.setRMCategory(row.get("Raw_Material_Name").toString());
			rm.setQuantity(Integer.parseInt(row.get("Total_Quantity").toString()));
			//f.setRate(Double.valueOf(row.get("Rate").toString()));
			//f.setBatchNo(row.get("BatchNo").toString());
			rmStock.add(rm);
		}
		return rmStock;
	}

	@Override
	public Map<String, List<String>> getRMNamesForSale() {
		List <String> rmIdList = new ArrayList<String>();
		List <String> rmNameList = new ArrayList<String>();
		Map<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		
		jdbcTemplate=new JdbcTemplate(dataSource);
		List <Map<String,Object>> list = jdbcTemplate.queryForList(GET_RM_FOR_SALE);
		for(Map<String,Object> row:list){
			rmIdList.add(row.get("RecordId").toString());
			rmNameList.add(row.get("Raw_Material_Name").toString());
		}
		
		map.put("IDList", rmIdList);
		map.put("SpeciesList", rmNameList);
		System.out.println(rmNameList);
		return map;	}

	@Override
	public String getRMNameById(String id) {
		// TODO Auto-generated method stub
		jdbcTemplate=new JdbcTemplate(dataSource);
		String rmName=null;
		try{
			rmName=jdbcTemplate.queryForObject(GET_RM_NAME_BY_ID, new Object[]{id},String.class);
		}
		catch(EmptyResultDataAccessException e){
			System.out.println("No matching product found in Raw Material table");
			System.out.println(e.getMessage());
		}
		return rmName;
	}

	
}

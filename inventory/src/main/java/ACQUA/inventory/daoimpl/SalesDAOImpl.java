package ACQUA.inventory.daoimpl;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import ACQUA.inventory.dao.BirdDAO;
import ACQUA.inventory.dao.FishDAO;
import ACQUA.inventory.dao.PlantDAO;
import ACQUA.inventory.dao.RawMaterialDAO;
import ACQUA.inventory.dao.SalesDAO;
import ACQUA.inventory.model.CustomerSales;

public class SalesDAOImpl implements SalesDAO {

	@Autowired
	CustomerSales custsales;
	
	@Autowired
	FishDAO fishDAO;
	
	@Autowired
	BirdDAO birdDAO;
	
	@Autowired
	PlantDAO plantDAO;
	
	@Autowired
	RawMaterialDAO rmDAO;
		
	@Autowired
	DataSource datasource;
	private final String INSERT_SALE_RECORD="INSERT INTO Sales VALUES(?,?,?,?,?,?,?,?)";
	private final String INSERT_CUSTOMER_RECORD="INSERT INTO Customer VALUES(?,?,?,?,?,?,?)";
	private final String UPDATE_CUSTOMER_RECORD="UPDATE Customer SET Cust_Name=?,Address=?,Contact=?,Email=?,Last_Modified_Date=? WHERE RecordID=?";
	//private final String GET_CUSTOMER_NAMES="SELECT RecordID,Cust_Name FROM Customer WHERE Cust_Name like '?%' OR Cust_Name like '%?' OR Cust_Name like '%?%'";
	private final String GET_CUSTOMER_DETAILS_BY_ID="SELECT * FROM Customer WHERE RecordId=?";
	private final String GET_ALL_CUSTOMERS="SELECT * FROM Customer";
	private final String GET_SALES_BY_CUST_ID="SELECT RecordID,Product_Purchased,Rate,Quantity,DATE_FORMAT(Sale_Date,'%d/%m/%Y') as Sale_Date FROM Sales WHERE cust_id=?";
	private final String GET_SALES_BY_CUST_ID_DATE="SELECT RecordID,Product_Purchased,Rate,Quantity,DATE_FORMAT(Sale_Date,'%d/%m/%Y') as Sale_Date FROM Sales WHERE cust_id=? AND DATE_FORMAT(Sale_Date,'%d/%m/%Y')=?";
	
	private final String GET_FISH_SALES_REPORT_BETWEEN_DATES="SELECT fm.Category,s.Rate,s.Quantity,DATE_FORMAT(s.SALE_DATE,'%d-%m-%Y') AS Sale_Date FROM "
			+ "Fish_Master fm, Sales s WHERE fm.RecordID=s.Product_Purchased AND (s.Sale_Date >= ? AND s.Sale_Date <= ?)";
	
	private final String GET_BIRD_SALES_REPORT_BETWEEN_DATES="SELECT bm.Bird_Name,s.Rate,s.Quantity,DATE_FORMAT(s.SALE_DATE,'%d-%m-%Y') AS Sale_Date FROM "
			+ "Bird_Master bm, Sales s WHERE bm.RecordID=s.Product_Purchased AND (s.Sale_Date >= ? AND s.Sale_Date <= ?)";
	
	private final String GET_PLANT_SALES_REPORT_BETWEEN_DATES="SELECT pm.Plant_Name,s.Rate,s.Quantity,DATE_FORMAT(s.SALE_DATE,'%d-%m-%Y') AS Sale_Date FROM "
			+ "Plant_Master pm, Sales s WHERE pm.RecordID=s.Product_Purchased AND (s.Sale_Date >= ? AND s.Sale_Date <= ?)";
	
	private final String GET_RM_SALES_REPORT_BETWEEN_DATES="SELECT rmm.Raw_Material_Name,s.Rate,s.Quantity,DATE_FORMAT(s.SALE_DATE,'%d-%m-%Y') AS Sale_Date FROM "
			+ "Raw_Material_Master rmm, Sales s WHERE rmm.RecordID=s.Product_Purchased AND (s.Sale_Date >= ? AND s.Sale_Date <= ?)";
	
	private final String GET_FISH_DAILY_SALES_REPORT="SELECT fm.Category,s.Rate,s.Quantity,DATE_FORMAT(s.SALE_DATE,'%d-%m-%Y') AS Sale_Date FROM "
			+ "Fish_Master fm, Sales s WHERE fm.RecordID=s.Product_Purchased AND s.Sale_Date = ? ";
	
	private final String GET_BIRD_DAILY_SALES_REPORT="SELECT bm.Bird_Name,s.Rate,s.Quantity,DATE_FORMAT(s.SALE_DATE,'%d-%m-%Y') AS Sale_Date FROM "
			+ "Bird_Master bm, Sales s WHERE bm.RecordID=s.Product_Purchased AND s.Sale_Date = ?";
	
	private final String GET_PLANT_DAILY_SALES_REPORT="SELECT pm.Plant_Name,s.Rate,s.Quantity,DATE_FORMAT(s.SALE_DATE,'%d-%m-%Y') AS Sale_Date FROM "
			+ "Plant_Master pm, Sales s WHERE pm.RecordID=s.Product_Purchased AND s.Sale_Date = ?";
	
	private final String GET_RM_DAILY_SALES_REPORT="SELECT rmm.Raw_Material_Name,s.Rate,s.Quantity,DATE_FORMAT(s.SALE_DATE,'%d-%m-%Y') AS Sale_Date FROM "
			+ "Raw_Material_Master rmm, Sales s WHERE rmm.RecordID=s.Product_Purchased AND s.Sale_Date = ?";
	
	
	
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int insertSaleRecord(CustomerSales customerSales,boolean isNewCustomer) {
		jdbcTemplate=new JdbcTemplate(datasource);
		int currQuantity=0;
		/*SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date saleDate = new Date();
		try{
			saleDate= format.parse(customerSales.getSaleDate());
			System.out.println("Sale Date:"+saleDate);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}*/
		
		int[] newcusttypes = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.DATE};
		Object[] newcustparams= new Object[]{customerSales.getCustomerID(),customerSales.getCustomerName(),customerSales.getAddress(),customerSales.getContact(),customerSales.getEmail(),
				customerSales.getDateAdded(),customerSales.getLastModifiedDate()};
		
		int[] custtypes = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.VARCHAR};
		Object[] custparams = new Object[]{customerSales.getCustomerName(),customerSales.getAddress(),customerSales.getContact(),customerSales.getEmail(),customerSales.getLastModifiedDate(),
				customerSales.getCustomerID()};
		
		int[] salestypes = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DOUBLE,Types.INTEGER,Types.DATE,Types.DATE,Types.DATE};
		Object[] salesparams = new Object[]{customerSales.getSalesID(),customerSales.getCustomerID(),customerSales.getProductName(),customerSales.getRate(),customerSales.getQuantity(),
				customerSales.getSaleDate(),customerSales.getDateAdded(),customerSales.getLastModifiedDate()};
		
		int n=0;
		try{
			if(isNewCustomer){
				n=jdbcTemplate.update(INSERT_CUSTOMER_RECORD, newcustparams, newcusttypes);
				System.out.println("Record inserted");
			}
			else{
				n=jdbcTemplate.update(UPDATE_CUSTOMER_RECORD,custparams,custtypes);
				System.out.println("record updated");
			}
			
			if(n > 0){
				n=jdbcTemplate.update(INSERT_SALE_RECORD,salesparams,salestypes);
				System.out.println("sale record inserted");
			}
			if(n > 0){
				if(customerSales.getProductType().equalsIgnoreCase("Fish")){
					currQuantity=fishDAO.getQuantityByID(customerSales.getProductName());
					currQuantity=currQuantity-customerSales.getQuantity();
					n=fishDAO.updateFishStockByID(currQuantity,customerSales.getProductName());
					System.out.println("quantity updated");
				}
				else if(customerSales.getProductType().equalsIgnoreCase("Bird")){
					currQuantity=birdDAO.getQuantityByID(customerSales.getProductName());
					currQuantity=currQuantity-customerSales.getQuantity();
					n=birdDAO.updateBirdStockByID(currQuantity,customerSales.getProductName());
					System.out.println("quantity updated");
				}
				else if(customerSales.getProductType().equalsIgnoreCase("Plant")){
					currQuantity=plantDAO.getQuantityByID(customerSales.getProductName());
					currQuantity=currQuantity-customerSales.getQuantity();
					n=plantDAO.updatePlantStockByID(currQuantity,customerSales.getProductName());
					System.out.println("quantity updated");
				}
				else if(customerSales.getProductType().equalsIgnoreCase("Raw Material")){
					//System.out.println("Product ID"+customerSales.getProductName()+"Quantity :"+customerSales.getQuantity());
					currQuantity=rmDAO.getQuantityById(customerSales.getProductName());
					currQuantity=currQuantity-customerSales.getQuantity();
					n=rmDAO.updateRMStockById(currQuantity, customerSales.getProductName());
					System.out.println("quantity updated");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return n;
	}

	@Override
	public List<Map<String, Object>> getAllCustomerNames(String name) {
		jdbcTemplate=new JdbcTemplate(datasource);
		/*int[] types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		String[] params = new String[]{name,name,name};*/
		String GET_CUSTOMER_NAMES="SELECT RecordID,Cust_Name FROM Customer WHERE Cust_Name like '"+name+"%' OR Cust_Name like '%"+name+"' OR Cust_Name like '%"+name+"%'";
		//System.out.println(GET_CUSTOMER_NAMES);
		return jdbcTemplate.queryForList(GET_CUSTOMER_NAMES);
	}

	@Override
	public CustomerSales getCustomerDetailsById(String id) {
		jdbcTemplate=new JdbcTemplate(datasource);
		int[] types = new int[] {Types.VARCHAR};
		String[] params = new String[]{id};
		List<Map<String,Object>> list=jdbcTemplate.queryForList(GET_CUSTOMER_DETAILS_BY_ID, params, types);
		
		for(Map<String,Object> row:list){
			custsales.setCustomerName(row.get("Cust_Name").toString());
			custsales.setAddress(row.get("Address").toString());
			custsales.setContact(row.get("Contact").toString());
			custsales.setEmail(row.get("Email").toString());
		}
		return custsales;
	}
	
	public List<Map<String,Object>> getAllCustomers(){
		jdbcTemplate=new JdbcTemplate(datasource);
		List<Map<String,Object>> customerList = new ArrayList<>();
		customerList = jdbcTemplate.queryForList(GET_ALL_CUSTOMERS);
		//for(CustomerSales customer:)
		return customerList;
	}

	@Override
	public List<CustomerSales> getPurchasedProductsByCustomer(String custId) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> pList = new ArrayList<>();
		List<CustomerSales> customerProducts = new ArrayList<>();
		CustomerSales csObject;
		jdbcTemplate = new JdbcTemplate(datasource);
		int[] types = new int[] {Types.VARCHAR};
		String[] params = new String[]{custId};
		String pName;
		String pid;
		pList=jdbcTemplate.queryForList(GET_SALES_BY_CUST_ID,params,types);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		for(Map<String,Object> row :pList){
			csObject = new CustomerSales();
			pid=row.get("Product_Purchased").toString();
			csObject.setProductId(pid);
			csObject.setRate(Double.valueOf(row.get("Rate").toString()));
			csObject.setQuantity(Integer.valueOf(row.get("Quantity").toString()));
			csObject.setStrSaleDate(row.get("Sale_Date").toString());
			try{
				System.out.println(row.get("Sale_Date").toString());
				System.out.println("Formated Date "+format.parse(row.get("Sale_Date").toString()));
				csObject.setSaleDate(format.parse(row.get("Sale_Date").toString()));
			}
			catch(Exception e){
				System.out.println("Exception occured: "+e.getMessage());
			}
			pName=plantDAO.getPlantNameById(pid);
			if(pName==null){
				pName=birdDAO.getBirdNameById(pid);
				if(pName==null){
					pName=fishDAO.getFishNameById(pid);
					if(pName==null){
						pName=rmDAO.getRMNameById(pid);
					}
				}
			}
			csObject.setProductName(pName);
			customerProducts.add(csObject);
		}
		return customerProducts;
	}

	@Override
	public List<CustomerSales> getPurchasedProductsByCustomerByDate(String custId, String pdate) {
		List<Map<String,Object>> pList = new ArrayList<>();
		List<CustomerSales> customerProducts = new ArrayList<>();
		CustomerSales csObject;
		
		jdbcTemplate = new JdbcTemplate(datasource);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		int[] types = new int[] {Types.VARCHAR,Types.VARCHAR};
		/*Date purchasedate=null;
		try{
			purchasedate = format.parse(pdate);
			System.out.println("Purchase date:"+purchasedate);
		}
		catch(Exception e){
			System.out.println("exception in date conversion: "+e.getMessage());
		}*/
		String[] params = new String[]{custId,pdate};
		String pName;
		String pid;
		pList=jdbcTemplate.queryForList(GET_SALES_BY_CUST_ID_DATE,params,types);
		
		for(Map<String,Object> row :pList){
			csObject = new CustomerSales();
			pid=row.get("Product_Purchased").toString();
			csObject.setProductId(pid);
			csObject.setRate(Double.valueOf(row.get("Rate").toString()));
			csObject.setQuantity(Integer.valueOf(row.get("Quantity").toString()));
			csObject.setStrSaleDate(row.get("Sale_Date").toString());
			try{
				System.out.println(row.get("Sale_Date").toString());
				System.out.println("Formated Date "+format.parse(row.get("Sale_Date").toString()));
				csObject.setSaleDate(format.parse(row.get("Sale_Date").toString()));
			}
			catch(Exception e){
				System.out.println("Exception occured: "+e.getMessage());
			}
			pName=plantDAO.getPlantNameById(pid);
			if(pName==null){
				pName=birdDAO.getBirdNameById(pid);
				if(pName==null){
					pName=fishDAO.getFishNameById(pid);
					if(pName==null){
						pName=rmDAO.getRMNameById(pid);
					}
				}
			}
			csObject.setProductName(pName);
			customerProducts.add(csObject);
		}
		return customerProducts;
	}

	@Override
	public List<CustomerSales> getSalesReportBetweenDates(String startdate, String enddate,String[] products) {
		//List<CustomerSales> list = new ArrayList<CustomerSales>();
		List<Map<String,Object>> pList = new ArrayList<>();
		List<CustomerSales> customerProducts = new ArrayList<>();
		CustomerSales csObject;
		jdbcTemplate = new JdbcTemplate(datasource);
		int[] types = new int[] {Types.VARCHAR,Types.VARCHAR};
		String[] params = new String[]{startdate,enddate};
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		
		for(String p:products){
			if(p.equalsIgnoreCase("Fish")){
				pList = jdbcTemplate.queryForList(GET_FISH_SALES_REPORT_BETWEEN_DATES,params,types);
				for(Map<String,Object> row :pList){
					csObject = new CustomerSales();
					csObject.setProductType("Fish");
					csObject.setProductName(row.get("Category").toString());
					csObject.setRate(Double.valueOf(row.get("Rate").toString()));
					csObject.setQuantity(Integer.valueOf(row.get("Quantity").toString()));
					csObject.setStrSaleDate(row.get("Sale_Date").toString());
					try{
						csObject.setSaleDate(format.parse(row.get("Sale_Date").toString()));
					}
					catch(Exception e){
						System.out.println("Exception occured: "+e.getMessage());
					}
					customerProducts.add(csObject);
				}
				pList.clear();
			}
			else if(p.equalsIgnoreCase("Bird")){
				pList = jdbcTemplate.queryForList(GET_BIRD_SALES_REPORT_BETWEEN_DATES,params,types);
				for(Map<String,Object> row :pList){
					csObject = new CustomerSales();
					csObject.setProductType("Bird");
					csObject.setProductName(row.get("Bird_Name").toString());
					csObject.setRate(Double.valueOf(row.get("Rate").toString()));
					csObject.setQuantity(Integer.valueOf(row.get("Quantity").toString()));
					csObject.setStrSaleDate(row.get("Sale_Date").toString());
					try{
						csObject.setSaleDate(format.parse(row.get("Sale_Date").toString()));
					}
					catch(Exception e){
						System.out.println("Exception occured: "+e.getMessage());
					}
					customerProducts.add(csObject);
				}
				pList.clear();
			}
			else if(p.equalsIgnoreCase("Plant")){
				pList = jdbcTemplate.queryForList(GET_PLANT_SALES_REPORT_BETWEEN_DATES,params,types);
				for(Map<String,Object> row :pList){
					csObject = new CustomerSales();
					csObject.setProductType("Plant");
					csObject.setProductName(row.get("Plant_Name").toString());
					csObject.setRate(Double.valueOf(row.get("Rate").toString()));
					csObject.setQuantity(Integer.valueOf(row.get("Quantity").toString()));
					csObject.setStrSaleDate(row.get("Sale_Date").toString());
					try{
						csObject.setSaleDate(format.parse(row.get("Sale_Date").toString()));
					}
					catch(Exception e){
						System.out.println("Exception occured: "+e.getMessage());
					}
					customerProducts.add(csObject);
				}
				pList.clear();
			}
			else if(p.equalsIgnoreCase("Raw Material")){
				pList = jdbcTemplate.queryForList(GET_RM_SALES_REPORT_BETWEEN_DATES,params,types);
				for(Map<String,Object> row :pList){
					csObject = new CustomerSales();
					csObject.setProductType("Raw_Material");
					csObject.setProductName(row.get("Raw_Material_Name").toString());
					csObject.setRate(Double.valueOf(row.get("Rate").toString()));
					csObject.setQuantity(Integer.valueOf(row.get("Quantity").toString()));
					csObject.setStrSaleDate(row.get("Sale_Date").toString());
					try{
						csObject.setSaleDate(format.parse(row.get("Sale_Date").toString()));
					}
					catch(Exception e){
						System.out.println("Exception occured: "+e.getMessage());
					}
					customerProducts.add(csObject);
				}
				pList.clear();
			}
		}
		return customerProducts;
	}

	@Override
	public List<CustomerSales> getDailySalesReport(String startdate, String[] products) {
		// List<CustomerSales> list = new ArrayList<CustomerSales>();
		List<Map<String, Object>> pList = new ArrayList<>();
		List<CustomerSales> customerProducts = new ArrayList<>();
		CustomerSales csObject;
		jdbcTemplate = new JdbcTemplate(datasource);
		int[] types = new int[] { Types.VARCHAR };
		String[] params = new String[] { startdate };
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		for (String p : products) {
			if (p.equalsIgnoreCase("Fish")) {
				pList = jdbcTemplate.queryForList(GET_FISH_DAILY_SALES_REPORT, params, types);
				for (Map<String, Object> row : pList) {
					csObject = new CustomerSales();
					csObject.setProductType("Fish");
					csObject.setProductName(row.get("Category").toString());
					csObject.setRate(Double.valueOf(row.get("Rate").toString()));
					csObject.setQuantity(Integer.valueOf(row.get("Quantity").toString()));
					csObject.setStrSaleDate(row.get("Sale_Date").toString());
					try {
						csObject.setSaleDate(format.parse(row.get("Sale_Date").toString()));
					} catch (Exception e) {
						System.out.println("Exception occured: " + e.getMessage());
					}
					customerProducts.add(csObject);
				}
				pList.clear();
			} else if (p.equalsIgnoreCase("Bird")) {
				pList = jdbcTemplate.queryForList(GET_BIRD_DAILY_SALES_REPORT, params, types);
				for (Map<String, Object> row : pList) {
					csObject = new CustomerSales();
					csObject.setProductType("Bird");
					csObject.setProductName(row.get("Bird_Name").toString());
					csObject.setRate(Double.valueOf(row.get("Rate").toString()));
					csObject.setQuantity(Integer.valueOf(row.get("Quantity").toString()));
					csObject.setStrSaleDate(row.get("Sale_Date").toString());
					try {
						csObject.setSaleDate(format.parse(row.get("Sale_Date").toString()));
					} catch (Exception e) {
						System.out.println("Exception occured: " + e.getMessage());
					}
					customerProducts.add(csObject);
				}
				pList.clear();
			} else if (p.equalsIgnoreCase("Plant")) {
				pList = jdbcTemplate.queryForList(GET_PLANT_DAILY_SALES_REPORT, params, types);
				for (Map<String, Object> row : pList) {
					csObject = new CustomerSales();
					csObject.setProductType("Plant");
					csObject.setProductName(row.get("Plant_Name").toString());
					csObject.setRate(Double.valueOf(row.get("Rate").toString()));
					csObject.setQuantity(Integer.valueOf(row.get("Quantity").toString()));
					csObject.setStrSaleDate(row.get("Sale_Date").toString());
					try {
						csObject.setSaleDate(format.parse(row.get("Sale_Date").toString()));
					} catch (Exception e) {
						System.out.println("Exception occured: " + e.getMessage());
					}
					customerProducts.add(csObject);
				}
				pList.clear();
			} else if (p.equalsIgnoreCase("Raw Material")) {
				pList = jdbcTemplate.queryForList(GET_RM_DAILY_SALES_REPORT, params, types);
				for (Map<String, Object> row : pList) {
					csObject = new CustomerSales();
					csObject.setProductType("Raw_Material");
					csObject.setProductName(row.get("Raw_Material_Name").toString());
					csObject.setRate(Double.valueOf(row.get("Rate").toString()));
					csObject.setQuantity(Integer.valueOf(row.get("Quantity").toString()));
					csObject.setStrSaleDate(row.get("Sale_Date").toString());
					try {
						csObject.setSaleDate(format.parse(row.get("Sale_Date").toString()));
					} catch (Exception e) {
						System.out.println("Exception occured: " + e.getMessage());
					}
					customerProducts.add(csObject);
				}
				pList.clear();
			}
		}
		return customerProducts;
	}
}

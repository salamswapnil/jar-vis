package ACQUA.inventory.daoimpl;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	
}

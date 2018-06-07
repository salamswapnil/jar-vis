package ACQUA.inventory.dao;

import java.util.List;
import java.util.Map;

import ACQUA.inventory.model.CustomerSales;

public interface SalesDAO {
	public int insertSaleRecord(CustomerSales customerSales,boolean isNewCustomer);
	public List<Map<String,Object>> getAllCustomerNames(String name);
	public CustomerSales getCustomerDetailsById(String id);
	public List<Map<String,Object>> getAllCustomers();
	public List<CustomerSales> getPurchasedProductsByCustomer(String custId);
	public List<CustomerSales> getPurchasedProductsByCustomerByDate(String custId,String pdate);
}

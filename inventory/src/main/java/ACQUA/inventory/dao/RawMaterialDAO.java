package ACQUA.inventory.dao;

import java.util.List;
import java.util.Map;

import ACQUA.inventory.model.RawMaterial;

public interface RawMaterialDAO {
	public int addMaterialName(RawMaterial rawMaterial);
	public Map<String,List<String>> getAllRMNames();
	public int addMaterialDetails(RawMaterial rawMaterial);
	public int getQuantityById(String id);
	public int updateRMStockById(int quantity,String id);
	public List<RawMaterial> getRMStock();
	public Map<String,List<String>> getRMNamesForSale();
	public String getRMNameById(String id);
}

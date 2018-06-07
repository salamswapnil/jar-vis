package ACQUA.inventory.dao;

import java.util.List;
import java.util.Map;

import ACQUA.inventory.model.Fish;

public interface FishDAO {
	public int addFishCategory(Fish fish);
	public int addFishDetails(Fish fish);
	public int updateFishDetails(Fish fish);
	public Map<String,List<String>> getAllFishSpecies();
	public List<Fish> getFishStock();
	public Map<String,List<String>> getFishNamesForSale();
	public int getQuantityByID(String id);
	public int updateFishStockByID(int quantity,String id);
	public String getFishNameById(String id);
}

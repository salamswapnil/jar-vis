package ACQUA.inventory.dao;

import java.util.List;
import java.util.Map;

import ACQUA.inventory.model.Bird;
import ACQUA.inventory.model.Fish;

public interface BirdDAO {
	public Map<String,List<String>> getAllBirdNames();
	public int addBirdName(Bird bird);
	public int addBirdDetails(Bird bird);
	public int updateBirdDetails(Bird bird);
	public List<Bird> getBirdStock();
	public Map<String,List<String>> getBirdNamesForSale();
	public int getQuantityByID(String id);
	public int updateBirdStockByID(int quantity,String id);
	public String getBirdNameById(String id);
}

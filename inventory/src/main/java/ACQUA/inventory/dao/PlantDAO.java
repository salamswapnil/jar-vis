package ACQUA.inventory.dao;

import java.util.List;
import java.util.Map;

import ACQUA.inventory.model.Plant;

public interface PlantDAO {
	public int addPlant(Plant plant);
	public int addPlantDetails(Plant plant);
	public int updatePlantDetails(Plant plant);
	public Map<String,List<String>> getAllPlantNames();
	public List<Plant> getPlantStock();
	public Map<String,List<String>> getPlantNamesForSale();
	public int getQuantityByID(String id);
	public int updatePlantStockByID(int quantity,String id);
	public String getPlantNameById(String id);
}

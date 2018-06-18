package ACQUA.inventory.controller;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import ACQUA.inventory.dao.BirdDAO;
import ACQUA.inventory.dao.FishDAO;
import ACQUA.inventory.dao.LoginDAO;
import ACQUA.inventory.dao.PlantDAO;
import ACQUA.inventory.dao.RawMaterialDAO;
import ACQUA.inventory.dao.SalesDAO;
import ACQUA.inventory.model.Bird;
import ACQUA.inventory.model.CustomerSales;
import ACQUA.inventory.model.Fish;
import ACQUA.inventory.model.Plant;
import ACQUA.inventory.model.RawMaterial;
import ACQUA.inventory.model.TempModel;
import ACQUA.inventory.model.User;
import ACQUA.inventory.utils.Utils;
	

@Controller
public class MainController {
	
	/*@RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("login", "loginForm", new User());
    }*/
	@Autowired
	LoginDAO loginDAO;
	
	@Autowired
	FishDAO fishDAO;
	
	@Autowired 
	PlantDAO plantDAO;
	
	@Autowired
	BirdDAO birdDAO;
	
	@Autowired
	RawMaterialDAO rmDAO;
	@Autowired
	SalesDAO salesDAO;
	
	@Autowired
	Fish fish;
	@Autowired
	Plant plant;
	@Autowired
	Bird bird;
	@Autowired
	RawMaterial rawMaterial;
	@Autowired
	CustomerSales sales;
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView doLogin(@ModelAttribute("loginForm")User user, BindingResult result, ModelMap model){
		ModelAndView view = null;
		String username=user.getUserName();
		String password=user.getPassword();
		if(loginDAO.login(username, password)){
			view = new ModelAndView("home");
			view.addObject("fishStock",fishDAO.getFishStock());
			view.addObject("plantStock",plantDAO.getPlantStock());
			view.addObject("birdStock",birdDAO.getBirdStock());
			view.addObject("rmStock",rmDAO.getRMStock());
			view.addObject("msg", "Welcome Admin!!");
			return view;
		}
		else{
			view= new ModelAndView("login");
			view.addObject("msg", "Incorrect Credentials!!!Please try again.");
			return view;
		} 
	}
	
	@RequestMapping(value="/home")
	public ModelAndView home(){
		ModelAndView view_home = new ModelAndView();
		view_home.addObject("fishStock",fishDAO.getFishStock());
		view_home.addObject("plantStock",plantDAO.getPlantStock());
		view_home.addObject("birdStock",birdDAO.getBirdStock());
		view_home.addObject("rmStock",rmDAO.getRMStock());
		return view_home;
	}
	
	@RequestMapping(value="/addFishCategory")
	public @ResponseBody String addFishCategory(@RequestParam(required=false, name="name") String category){
		String response="";
		fish.setFishCategory(category);
		fish.setFishRecordID(Utils.generateID());
		fish.setDateAdded(new Date());
		if(fishDAO.addFishCategory(fish)>0)
			response="Fish Category added successfuly...!!!";
		else
			response="Error occured...Please try again!!!";
		return response;
	}
	
	@RequestMapping(value="/getAllFishSpecies")
	public @ResponseBody String getAllFishSpecies(){
		Map<String,List<String>> fishSpecies = new LinkedHashMap<String,List<String>>();
		fishSpecies = fishDAO.getAllFishSpecies();
		Gson gson = new Gson();
		String responseJson = gson.toJson(fishSpecies);
		/*System.out.println(responseJson);*/
		return responseJson;
	}
	
	@RequestMapping(value="/addFishDetails",method=RequestMethod.POST)
	public ModelAndView insertFishDetails(@ModelAttribute("frmFishDetails")Fish fish,BindingResult result,ModelMap model){
		ModelAndView view = new ModelAndView("fish");
		fish.setFishRecordID(Utils.generateID());
		fish.setDateAdded(new Date());
		System.out.println(fish.getFishRecordID()+" "+fish.getQuantity()+" "+fish.getFishCategory()+" "+
		fish.getRate()+" "+fish.getPurchaseDate()+" "+fish.getDateAdded()+" "+fish.getBatchNo());
		if(fishDAO.addFishDetails(fish) > 0){
			view.addObject("msg","Fish details added Successfully...!!!");
		}
		else{
			view.addObject("msg","Error occured while adding fish details...!!!");
		}
		view.addObject("fishStock",fishDAO.getFishStock());
		return view;
	}
	
	@RequestMapping(value="/addPlantNames")
	public @ResponseBody String addPlants(@RequestParam(name="plantname") String plantName){
		String response="";
		plant.setPlantName(plantName);
		plant.setPlantRecordID(Utils.generateID());
		plant.setDateAdded(new Date());
		if(plantDAO.addPlant(plant)>0)
			response="Plant name added successfully...!!!";
		else
			response="Something went wrong...please try again...!!!";
		return response;
	}
	
	@RequestMapping(value="/addPlantDetails")
	public ModelAndView insertPlantDetails(@ModelAttribute("plantObj") Plant plant,BindingResult result, ModelMap model){
		ModelAndView view = new ModelAndView("plant");
		plant.setPlantRecordID(Utils.generateID());
		plant.setDateAdded(new Date());
		if(plantDAO.addPlantDetails(plant)>0){
			view.addObject("msg", "Plant details added Successfully...!!");
		}
		else{
			view.addObject("msg","Error occured while adding Plant details...!!!");
		}
		view.addObject("plantStock",plantDAO.getPlantStock());
		return view;
	}
	
	@RequestMapping(value="/getAllPlantNames")
	public @ResponseBody String getAllPlantNames(){
		Map<String,List<String>> plantNames = new LinkedHashMap<String,List<String>>();
		plantNames = plantDAO.getAllPlantNames();
		Gson gson = new Gson();
		String responseJson = gson.toJson(plantNames);
		/*System.out.println(responseJson);*/
		return responseJson;
	}
	
	@RequestMapping(value="/getAllBirdNames")
	public @ResponseBody String getAllBirdNames(){
		Map<String,List<String>> birdNames = new LinkedHashMap<String, List<String>>();
		birdNames = birdDAO.getAllBirdNames();
		Gson gson = new Gson();
		String responseJson = gson.toJson(birdNames);
		return responseJson;
	} 
	
	@RequestMapping(value="/addBirdName")
	public @ResponseBody String addBirdName(@RequestParam(name="birdname") String birdName ){
		String response="";
		bird.setBirdName(birdName);
		bird.setBirdRecordID(Utils.generateID());
		bird.setDateAdded(new Date());
		if(birdDAO.addBirdName(bird)>0)
			response="Bird name added successfully...!!!";
		else
			response="Something went wrong...please try again...!!!";
		return response;
	}
	
	@RequestMapping(value="/addBirdDetails")
	public ModelAndView addBirdDetails(@ModelAttribute("birdObject") Bird bird, BindingResult result, ModelMap model){
		ModelAndView view = new ModelAndView("bird");
		bird.setBirdRecordID(Utils.generateID());
		bird.setDateAdded(new Date());
		if(birdDAO.addBirdDetails(bird)>0){
			view.addObject("msg", "Bird details added Successfully...!!");
		}
		else{
			view.addObject("msg","Error occured while adding Bird details...!!!");
		}
		view.addObject("birdStock",birdDAO.getBirdStock());
		return view;
	}
	
	@RequestMapping(value="/addMaterial")
	public @ResponseBody String addRawMaterial(@RequestParam(name="materialname") String materialName){
		String response="";
		rawMaterial.setRMCategory(materialName);
		rawMaterial.setRMRecordID(Utils.generateID());
		rawMaterial.setDateAdded(new Date());
		if(rmDAO.addMaterialName(rawMaterial)>0)
			response="Material name added successfully...!!!";
		else
			response="Something went wrong...please try again...!!!";
		return response;
	}
	
	@RequestMapping(value="/getAllMaterialNames")
	public @ResponseBody String getAllRawMaterialNames(){
		Map<String,List<String>> rawMaterials = new LinkedHashMap<String, List<String>>();
		rawMaterials = rmDAO.getAllRMNames();
		Gson gson = new Gson();
		String responseJson = gson.toJson(rawMaterials);
		return responseJson;
	} 
	
	@RequestMapping(value="/addRMDetails")
	public ModelAndView addRawMaterialDetails(@ModelAttribute("rmObject") RawMaterial rawMaterial, BindingResult result, ModelMap model){
		ModelAndView view = new ModelAndView("raw-material");
		rawMaterial.setRecordID(Utils.generateID());
		rawMaterial.setDateAdded(new Date());
		if(rmDAO.addMaterialDetails(rawMaterial)>0){
			view.addObject("msg", "Material details added Successfully...!!");
		}
		else{
			view.addObject("msg","Error occured while adding Material details...!!!");
		}
		view.addObject("rmStock",rmDAO.getRMStock());
		return view;
	}
	
	@RequestMapping(value="/updateDetails")
	public @ResponseBody String updateDetails(@RequestBody TempModel model){
		int n=0;
		if(model.getTblName().equalsIgnoreCase("fish")){
			fish.setRate(model.getRate());
			fish.setQuantity(model.getQuantity());
			fish.setRecordID(model.getRecordID());
			fish.setLastModifiedDate(new Date());
			
			n=fishDAO.updateFishDetails(fish);
				
		}
		else if(model.getTblName().equalsIgnoreCase("plant")){
			plant.setRate(model.getRate());
			plant.setQuantity(model.getQuantity());
			plant.setRecordId(model.getRecordID());
			plant.setLastModifiedDate(new Date());
			
			n=plantDAO.updatePlantDetails(plant);
		}
		else if(model.getTblName().equalsIgnoreCase("bird")){
			bird.setRate(model.getRate());
			bird.setQuantity(model.getQuantity());
			bird.setRecordID(model.getRecordID());
			bird.setLastModifiedDate(new Date());
			
			n=birdDAO.updateBirdDetails(bird);
		}
		else if(model.getTblName().equalsIgnoreCase("rawmaterial")){
			
		}
		
		if(n>0)
			return "Record update successfully...!!!";
		else
			return "Error occured while adding Bird details...!!!";
	}
	
	@RequestMapping(value="/getfishnamesforsale")
	public @ResponseBody String getFishNamesForSale(){
		Map<String,List<String>> fishNames = new LinkedHashMap<String,List<String>>();
		fishNames = fishDAO.getFishNamesForSale();
		Gson gson = new Gson();
		String responseJson = gson.toJson(fishNames);
		/*System.out.println(responseJson);*/
		return responseJson;
	} 
	
	@RequestMapping(value="/getbirdnamesforsale")
	public @ResponseBody String getBirdNamesForSale(){
		Map<String,List<String>> birdNames = new LinkedHashMap<String,List<String>>();
		birdNames = birdDAO.getBirdNamesForSale();
		Gson gson = new Gson();
		String responseJson = gson.toJson(birdNames);
		/*System.out.println(responseJson);*/
		return responseJson;
	}
	
	@RequestMapping(value="/getplantnamesforsale")
	public @ResponseBody String getPlantNamesForSale(){
		Map<String,List<String>> plantNames = new LinkedHashMap<String,List<String>>();
		plantNames = plantDAO.getPlantNamesForSale();
		Gson gson = new Gson();
		String responseJson = gson.toJson(plantNames);
		/*System.out.println(responseJson);*/
		return responseJson;
	}
	
	@RequestMapping(value="/getrmnamesforsale")
	public @ResponseBody String getRMNamesForSale(){
		Map<String,List<String>> rawMaterialNames = new LinkedHashMap<String,List<String>>();
		rawMaterialNames = rmDAO.getRMNamesForSale();
		Gson gson = new Gson();
		String responseJson = gson.toJson(rawMaterialNames);
		/*System.out.println(responseJson);*/
		return responseJson;
	}
	@RequestMapping(value="/saleproduct")
	public @ResponseBody ModelAndView saleProduct(@ModelAttribute("salesobject") CustomerSales customersale, BindingResult result, ModelMap model){
		System.out.println(customersale.getCustomerName()+" "+customersale.getAddress()+" "+customersale.getContact()+" "+customersale.getEmail()+" "+
				customersale.getProductName()+" "+customersale.getQuantity()+" "+customersale.getRate()+" "+customersale.getSaleDate());
		boolean isNewCustomer=false;
		if(customersale.getCustomerID()==null || customersale.getCustomerID().equals("")){
			customersale.setCustomerID(Utils.generateID());
			isNewCustomer=true;
		}
		customersale.setSalesID(Utils.generateID());
		customersale.setDateAdded(new Date());
		customersale.setLastModifiedDate(new Date());
		ModelAndView view = new ModelAndView("sales");
		if(salesDAO.insertSaleRecord(customersale,isNewCustomer) > 0){
			view.addObject("msg", "Record added Successfully...!!");
		}
		else{
			view.addObject("msg", "Error occured while inserting a record...!!");
		}
		return view;
	}
	
	@RequestMapping(value="/generateBatchNo",produces="text/plain")
	public @ResponseBody String generateBatch(@RequestParam(name="name") String speciesname){
		System.out.println("species name "+speciesname);
		return Utils.generateBatch(speciesname);
	}
	
	@RequestMapping(value="/getCurrentQuantity")
	public @ResponseBody String getQuantity(@RequestBody TempModel model){
		int n=0;
		
		System.out.println("model data"+model.getTblName()+" "+model.getProductID());
		
		if(model.getTblName().equalsIgnoreCase("Fish")){
			n=fishDAO.getQuantityByID(model.getProductID());
		}
		else if(model.getTblName().equalsIgnoreCase("Bird")){
			n=birdDAO.getQuantityByID(model.getProductID());
		}
		else if(model.getTblName().equalsIgnoreCase("Plant")){
			n=plantDAO.getQuantityByID(model.getProductID());
		}
		else if(model.getTblName().equalsIgnoreCase("Raw Material")){
			n=rmDAO.getQuantityById(model.getProductID());
		}
		return Integer.toString(n);
	}
	
	@RequestMapping(value="/getAllCustomerNames",method=RequestMethod.GET)
	public @ResponseBody String getAllCustomerNames(@RequestParam String name){
		List<Map<String,Object>> custList=salesDAO.getAllCustomerNames(name);
		Gson gson = new Gson();
		String responseJson=gson.toJson(custList);
		System.out.println(responseJson);
		return responseJson;
	}
	
	@RequestMapping(value="/getCustomerDetailsById")
	public @ResponseBody String getCustomerDetailsById(@RequestParam String id){
		sales=salesDAO.getCustomerDetailsById(id);
		Gson gson = new Gson();
		String responseJson=gson.toJson(sales);
		System.out.println(responseJson);
		return responseJson;
	}
	
	@RequestMapping(value="/getCustomerProductsById")
	public @ResponseBody String getCustomerProductsById(@RequestParam String custId){
		List<CustomerSales> custProducts = salesDAO.getPurchasedProductsByCustomer(custId);
		Gson gson = new Gson();
		String responseJson=gson.toJson(custProducts);
		return responseJson;
	}
	
	@RequestMapping(value="/getCustomerProductsByDate")
	public @ResponseBody String getCustomerProductsByDate(@RequestParam String pdate,@RequestParam String custId){
		List<CustomerSales> custProducts = salesDAO.getPurchasedProductsByCustomerByDate(custId,pdate);
		Gson gson = new Gson();
		String responseJson=gson.toJson(custProducts);
		return responseJson;
	}
	
	@RequestMapping(value="/monthlyReport")
	//public @ResponseBody String getMonthlyReport(@RequestParam String startdate,@RequestParam String enddate,@RequestParam String[] products){
	public @ResponseBody String getMonthlyReport(@RequestBody TempModel obj){
		Gson gson = new Gson();
		List<CustomerSales> custProducts =salesDAO.getSalesReportBetweenDates(obj.getStartDate(), obj.getEndDate(), obj.getProducts());
		String responseString=gson.toJson(custProducts);
		//String responseString=gson.toJson(salesDAO.getSalesReportBetweenDates(startdate,enddate,products));
		return responseString;
	}
	
	@RequestMapping(value="/dailyReport")
	public @ResponseBody String getDailyReport(@RequestBody TempModel obj){
		Gson gson = new Gson();
		List<CustomerSales> custProducts =salesDAO.getDailySalesReport(obj.getStartDate(),obj.getProducts());
		String responseString=gson.toJson(custProducts);
		return responseString;
	}
	
}

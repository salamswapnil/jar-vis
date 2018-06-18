package ACQUA.inventory.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ACQUA.inventory.dao.BirdDAO;
import ACQUA.inventory.dao.FishDAO;
import ACQUA.inventory.dao.PlantDAO;
import ACQUA.inventory.dao.RawMaterialDAO;
import ACQUA.inventory.model.Bird;
import ACQUA.inventory.model.CustomerSales;
import ACQUA.inventory.model.Fish;
import ACQUA.inventory.model.Plant;
import ACQUA.inventory.model.RawMaterial;
import ACQUA.inventory.model.User;

@Controller
public class HomeController {
	
	@Autowired
	User user;
	
	@Autowired
	FishDAO fishDAO;
	
	@Autowired
	PlantDAO plantDAO;
	
	@Autowired
	BirdDAO birdDAO;
	
	@Autowired
	RawMaterialDAO rmDAO;
	@RequestMapping(value={"/"})
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("login", "loginForm", user);
	}
	
	@Autowired
	Fish fish;
	@RequestMapping(value={"/fish"})
	public ModelAndView fish(HttpServletResponse response) throws IOException{
		ModelAndView fishView = new ModelAndView("fish", "frmFishDetails",fish);
		//fishView.addObject("fishStock",fishDAO.getFishStock());
		return fishView;
	}
	
	@Autowired
	Plant plant;
	@RequestMapping(value="/plant")
	public ModelAndView plant(HttpServletResponse response) throws IOException{
		ModelAndView view = new ModelAndView("plant","plantObj",plant);
		//view.addObject("plantStock",plantDAO.getPlantStock());
		return view;
	}
	
	@Autowired
	Bird bird;
	@RequestMapping(value="/bird")
	public ModelAndView bird(HttpServletResponse response) throws IOException{
		ModelAndView view = new ModelAndView("bird","birdObject",bird);
		//view.addObject("birdStock",birdDAO.getBirdStock());
		return view;
	}
	@Autowired
	RawMaterial rawMaterial;
	@RequestMapping(value="/rawmaterial")
	public ModelAndView rawMaterial(HttpServletResponse response) throws IOException{
		ModelAndView view = new ModelAndView("raw-material","rmObject",rawMaterial);
		//view.addObject("rmStock",rmDAO.getRMStock());
		return view;
	}
	
	@Autowired
	CustomerSales sales;
	@RequestMapping(value="/sales")
	public ModelAndView sales(HttpServletResponse response) throws IOException{
		ModelAndView view = new ModelAndView("sales","salesobject",sales);
		return view;
	}
	
	@RequestMapping(value="/bills")
	public ModelAndView bills(HttpServletResponse response) throws IOException{
		ModelAndView view = new ModelAndView("bills");
		return view;
	}
	
	@RequestMapping(value="/salebetweendates")
	public ModelAndView reportbetweendates(HttpServletResponse response) throws IOException{
		ModelAndView view = new ModelAndView("salebetweendates");
		return view;
	}
	
	@RequestMapping(value="/dailySales")
	public ModelAndView dailySales(HttpServletResponse response) throws IOException{
		ModelAndView view = new ModelAndView("dailysales");
		return view;
	}
}

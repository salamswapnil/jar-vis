package ACQUA.inventory.servicecontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ACQUA.inventory.dao.SalesDAO;

@RestController
public class ServiceController {

	@Autowired
	SalesDAO salesDAO;
	@RequestMapping(value="/getAllCustomers",method=RequestMethod.GET)
	public String getAllCustomers(){
		Gson gson = new Gson();
		String responseJson=gson.toJson(salesDAO.getAllCustomers());
		System.out.println(responseJson);
		return responseJson;
	}
}

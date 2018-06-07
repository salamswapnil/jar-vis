package ACQUA.inventory.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import ACQUA.inventory.dao.BirdDAO;
import ACQUA.inventory.dao.FishDAO;
import ACQUA.inventory.dao.LoginDAO;
import ACQUA.inventory.dao.PlantDAO;
import ACQUA.inventory.dao.RawMaterialDAO;
import ACQUA.inventory.dao.SalesDAO;
import ACQUA.inventory.daoimpl.BirdDAOImpl;
import ACQUA.inventory.daoimpl.FishDAOImpl;
import ACQUA.inventory.daoimpl.LoginDAOImpl;
import ACQUA.inventory.daoimpl.PlantDAOImpl;
import ACQUA.inventory.daoimpl.RawMaterialDAOImpl;
import ACQUA.inventory.daoimpl.SalesDAOImpl;
import ACQUA.inventory.model.Bird;
import ACQUA.inventory.model.CustomerSales;
import ACQUA.inventory.model.Fish;
import ACQUA.inventory.model.Plant;
import ACQUA.inventory.model.RawMaterial;
import ACQUA.inventory.model.User;

@Configuration
@ComponentScan(value="ACQUA.inventory.*")
@PropertySource(value="classpath:properties.properties")
public class ApplicationContextConfig {
	@Autowired
	private Environment env;
	@Bean(name="dataSource")
	@Lazy(value=false)
	public DataSource getDataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
		dataSource.setUrl(env.getProperty("ds.url"));
		dataSource.setUsername(env.getProperty("ds.username"));
		dataSource.setPassword(env.getProperty("ds.password"));
		
		System.out.println("Datasource Created: "+dataSource);
		return dataSource;
	}
	
	@Bean(name="user")
	public User getUser(){
		System.out.println("user obj created **************");
		return new User();
	}
	
	@Bean(name="loginDAO")
	public LoginDAO getLoginDAO(){
		System.out.println("logindao obj created **************");
		return new LoginDAOImpl();
	}
	
	@Bean(name="fishDAO")
	public FishDAO getFishDAO(){
		System.out.println("fishdao obj created **************");
		return new FishDAOImpl();
	}
	
	@Bean(name="plantDAO")
	public PlantDAO getPlantDAO(){
		System.out.println("plantdao obj created **************");
		return new PlantDAOImpl();
	} 
	
	@Bean (name="birdDAO")
	public BirdDAO getBirdDAO(){
		System.out.println("birddao obj created **************");
		return new BirdDAOImpl();
	}
	
	@Bean(name="rmDAO")
	public RawMaterialDAO getRawMaterialDAO(){
		return new RawMaterialDAOImpl();
	}
	
	@Bean (name="salesDAO")
	@Lazy(value=false)
	public SalesDAO getSalesDAO(){
		System.out.println("saledao obj created **************");
		return new SalesDAOImpl();
	}
	
	@Bean(name="fish")
	public Fish getFish(){
		System.out.println("Fish obj created");
		return new Fish();
	}
	
	@Bean(name="plant")
	public Plant getPlant(){
		System.out.println("Plant obj created");
		return new Plant();
	}
	
	@Bean(name="bird")
	public Bird getBird(){
		System.out.println("bird obj created **************");
		return new Bird();
	}
	
	@Bean(name="rawMaterial")
	public RawMaterial getRawMaterial(){
		return new RawMaterial();
	}
	
	@Bean(name="sales")
	public CustomerSales getCustomerSales(){
		System.out.println("sale obj created **************");
		return new CustomerSales();
	}
}

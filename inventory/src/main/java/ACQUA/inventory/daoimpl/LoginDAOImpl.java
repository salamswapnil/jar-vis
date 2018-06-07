package ACQUA.inventory.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ACQUA.inventory.dao.LoginDAO;
import ACQUA.inventory.model.User;

public class LoginDAOImpl implements LoginDAO {

	private JdbcTemplate jdbcTemplate;
	//private final 
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	User user;
	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		String query="Select * from user_auth where username='"+username+"' and password='"+password+"'";
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<User> lstUser=jdbcTemplate.query(query,new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				//while(rs.next()){
					user.setUserName(rs.getString(1));
					user.setPassword(rs.getString(2));
				//}
				return user;
			}
		});
		if(lstUser.size() > 0 && lstUser.get(0).getUserName()!=null && lstUser.get(0).getUserName().length()>0 && !lstUser.get(0).getUserName().equals(""))
			return true;
		else
			return false;
	}

	@Override
	public boolean changePassword(String password) {
		// TODO Auto-generated method stub
		return false;
	}

}

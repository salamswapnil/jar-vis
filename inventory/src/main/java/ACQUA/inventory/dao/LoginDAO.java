package ACQUA.inventory.dao;

public interface LoginDAO {
	public boolean login(String username, String password);
	public boolean changePassword(String password);
}

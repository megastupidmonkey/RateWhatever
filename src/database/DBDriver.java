package database;
import java.sql.*;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SuppressWarnings("unused")
public class DBDriver {
	private DataSource ds;
	private Connection c;
	private Statement s;
	public DBDriver(String url, String username, String password){
		DriverManagerDataSource newDs = new DriverManagerDataSource();
		newDs.setUrl(url);
		newDs.setUsername(username);
		newDs.setPassword(password);
		init(newDs);
	}
	public DBDriver(DataSource ds){
		init(ds);
	}
	private void init(DataSource ds){
		try {
			this.ds = ds;
			this.c = ds.getConnection();
			this.s = c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/** @requires ENTITIES table exists
	 */
	public void addEntity(Entity entity){
		
	}
	public void updateEntity(int entityID, String property){
		
	}
	/** @requires RATINGS table exists
	 * @requires rating does not already exist in database
	 */
	public void addRating(Rating rating){
		
	}
	public void updateRating(Rating rating){
		
	}
	public void executeBatch() throws SQLException{
		s.executeBatch();
	}
}

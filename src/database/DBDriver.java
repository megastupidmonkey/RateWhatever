package database;
import javax.sql.DataSource;

public class DBDriver {
	private DataSource ds;
	public DBDriver(DataSource ds){
		this.ds = ds;
	}
	/** @requires ENTITIES table exists
	 */
	public void addEntity(){
		
	}
	/** @requires RATINGS table exists
	 */
	public void addRating(){
		
	}
}

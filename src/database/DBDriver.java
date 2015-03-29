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
	/** @throws SQLException 
	 * @requires ENTITIES table exists and has columns ID and PROPERTIES
	 */
	public void addEntity(Entity entity) throws SQLException{
		s.addBatch("insert into entities (id, properties) values(" + entity.getId() + ", '" +
				JSONConverter.toJson(entity.getProperties()) + "')");
	}
	/** @throws SQLException 
	 * @requires ENTITIES table exists and columns are in order ID, PROPERTIES
	 */
	public void updateEntity(Entity entity) throws SQLException{
		s.addBatch("update entities set properties='" + JSONConverter.toJson(entity.getProperties())
				+ "' where id=" + entity.getId());
	}
	/** @throws SQLException 
	 * @requires RATINGS table exists and columns are in order ID, OWNER, NUMSTARS, ENTITYID, , REPLIES
	 */
	public void addRating(Rating rating) throws SQLException{
		s.addBatch("insert into ratings values(" + rating.getId() + ", '" + rating.getDescription()
				+ "', " + rating.getEntity().getId() + ", '" + rating.getOwner() +
				"', '" + rating.getReplies().toString() + "')");
	}
	public void updateRating(int ratingID, String property, String value) throws SQLException{
		s.addBatch("update table entities set " + property + "=" + value + " where id=" + ratingID);
	}
	public void executeBatch() throws SQLException{
		s.executeBatch();
	}
}

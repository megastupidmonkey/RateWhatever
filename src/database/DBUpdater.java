package database;
import java.sql.*;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import data.Entity;
import data.Rating;

public class DBUpdater {
	private Statement s;
	public DBUpdater(String url, String username, String password){
		DriverManagerDataSource newDs = new DriverManagerDataSource();
		newDs.setUrl(url);
		newDs.setUsername(username);
		newDs.setPassword(password);
		init(newDs);
	}
	public DBUpdater(DataSource ds){
		init(ds);
	}
	private void init(DataSource ds){
		try {
			this.s = ds.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/** @throws SQLException 
	 * @requires ENTITIES table exists and has columns ID and PROPERTIES
	 */
	public void addEntity(String tableName, Entity entity) throws SQLException{
		String mysql = "insert into " + tableName + " (id, totalStars, numRatings, ";
		for(String property : entity.getProperties()){
			mysql += (property + ", ");
		}
		mysql = mysql.substring(0, mysql.length()-2) + ") values('" + entity.getId() + "','"
				+ entity.getTotalStars() + "','" + entity.getNumRatings() + "','";
		for(String property : entity.getProperties()){
			mysql += (entity.getValue(property) + "','");
		}
		mysql = mysql.substring(0, mysql.length() - 2) + ")";
		s.addBatch(mysql);
		s.executeBatch();
	}
	/** @throws SQLException 
	 * @requires ENTITIES table exists and columns are in order ID, PROPERTIES
	 */
	public void updateEntity(String tableName, int entityID, String property, String value) throws SQLException{
		s.addBatch("update " + tableName + " set " + property + 
				"='" + value + "' where ID=" + entityID);
	}
	/** @throws SQLException 
	 * @requires RATINGS table exists and columns are in order ID,
	 *  OWNER, NUMSTARS, DESCRIPTION, ENTITYID, REPLIES
	 */
	public void addRating(Rating rating) throws SQLException{
		String mysql = "insert into ratings (ratingID, owner, numStars, description, entityID, replies) values("
				+ rating.getId() + ",'" + rating.getOwner() + "',"
				+ rating.getNumStars() + ",'" + rating.getDescription() + "',"
				+ rating.getEntity().getId() + ",'"
				+ rating.getReplies().toString() + "')";
		System.out.println(mysql);
		s.addBatch(mysql);
		s.executeBatch();
	}
	public void updateRating(int ratingID, String property, String value) throws SQLException{
		s.addBatch("update ratings set " + property + "='" + 
				value + "' where ratingID='" + ratingID + "'");
		s.executeBatch();
	}
	public int getRowCount(String tableName) throws SQLException{
		ResultSet rs = s.executeQuery("select COUNT(*) from " + tableName);
		rs.next();
		return rs.getInt(1);
	}
	public void executeBatch() throws SQLException{
		s.executeBatch();
	}
	public void finish() throws SQLException{
		s.close();
	}
}

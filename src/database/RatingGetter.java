package database;
import java.sql.*;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class RatingGetter {
	private Statement s;
	public RatingGetter(String url, String username, String password){
		DriverManagerDataSource newDs = new DriverManagerDataSource();
		newDs.setUrl(url);
		newDs.setUsername(username);
		newDs.setPassword(password);
		init(newDs);
	}
	public RatingGetter(DataSource ds){
		init(ds);
	}
	private void init(DataSource ds){
		try {
			this.s = ds.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Entity getEntityWithProperty(String tableName, String property, String value) throws SQLException{
		ResultSet rs = s.executeQuery("select * from " + tableName + " where " + property + "=" + value);
		if(!rs.isBeforeFirst())
			return null; //result set is empty
		rs.next();
		Entity entity = new Entity();
		for(int i = 1;i < rs.getMetaData().getColumnCount();i++){
			String prop = rs.getMetaData().getColumnName(i);
			String val = rs.getString(i);
			entity.addProperty(prop, val);
		}
		return entity;
	}
	/** @throws SQLException 
	 * @requires RATINGS table exists and columns are in order ID,
	 *  OWNER, NUMSTARS, DESCRIPTION, ENTITYID, REPLIES
	 */
	public List<Rating> getRatingsForEntity(String tableName, Entity entity) throws SQLException{
		ResultSet rs = s.executeQuery("select * from " + tableName + " where entityID=" + entity.getId());
		if(!rs.isBeforeFirst())
			return null; //result set is empty
		rs.next();
		List<Rating> list = new java.util.ArrayList<>();
		while(!rs.isAfterLast()){
			Rating rating = new Rating(rs.getInt(3), rs.getString(4), entity, rs.getString(2));
			rating.setId(rs.getInt(1));
			list.add(rating);
		}
		return list;
	}
}

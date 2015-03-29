package database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import data.Entity;
import data.Rating;

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
		ResultSet rs = s.executeQuery("select * from " + tableName + " where " + 
				property + "=" + value);
		if(!rs.isBeforeFirst())
			return null; //result set is empty
		rs.next();
		Entity entity = new Entity();
		entity.setId(rs.getInt(1));
		for(int i = 2;i <= rs.getMetaData().getColumnCount()-2;i++){
			String prop = rs.getMetaData().getColumnName(i);
			String val = rs.getString(i);
			entity.addProperty(prop, val);
		}
		entity.setTotalStars(rs.getInt(rs.getMetaData().getColumnCount()-1));
		entity.setNumRatings(rs.getInt(rs.getMetaData().getColumnCount()));
		return entity;
	}
	/** @throws SQLException 
	 * @requires RATINGS table exists and columns are in order ID,
	 *  OWNER, NUMSTARS, DESCRIPTION, ENTITYID, REPLIES
	 */
	public List<Rating> getRatingsForEntity(String tableName, Entity entity) throws SQLException{
		if(entity == null)
			return new java.util.ArrayList<>(); //result set is empty
		ResultSet rs = s.executeQuery("select * from " + tableName + " where entityID=" + entity.getId());
		if(!rs.isBeforeFirst())
			return new java.util.ArrayList<>(); //result set is empty
		rs.next();
		List<Rating> list = new java.util.ArrayList<>();
		while(!rs.isAfterLast()){
			Rating rating = new Rating(rs.getInt(3), rs.getString(4), entity, rs.getString(2));
			rating.setId(rs.getInt(1));
			rating.setReplies(this.getReplies(rs.getString(6)));
			list.add(rating);
			rs.next();
		}
		return list;
	}
	public Rating getRating(String rateTable, String entityTable, int ratingID) throws SQLException{
		ResultSet rs = s.executeQuery("select * from " + rateTable + " where ratingID=" + ratingID);
		if(!rs.isBeforeFirst())
			return null; //result set is empty
		rs.next();
		int id = rs.getInt(1);
		String owner = rs.getString(2);
		String replies = rs.getString(6);
		Rating rating = new Rating(rs.getInt(3), rs.getString(4),
			this.getEntityWithProperty(entityTable, "ID", "" + rs.getInt(5)), owner);
		rating.setId(id);
		rating.setReplies(this.getReplies(replies));
		return rating;
	}
	public List<String> getReplies(String list) throws SQLException{
		list = list.substring(1, list.length() - 1);
		return new ArrayList<String>(Arrays.asList(list.split("\\s*,\\s*")));
	}
}

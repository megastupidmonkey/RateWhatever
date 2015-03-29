package database;
import java.sql.*;
import java.util.List;
import javax.sql.DataSource;

public class RatingGetter {
	private Statement s;
	public RatingGetter(DataSource ds){
		try {
			this.s = ds.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Entity getEntityWithProperty(String property, String value){
		return null;
	}
	public List<Rating> getRatingsForEntity(Entity entity){
		return null;
	}
	public List<Entity> getTopRatedEntities(){
		return null;
	}
}

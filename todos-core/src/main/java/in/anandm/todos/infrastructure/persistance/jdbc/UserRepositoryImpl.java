package in.anandm.todos.infrastructure.persistance.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import in.anandm.todos.model.user.User;
import in.anandm.todos.model.user.UserRepository;


public class UserRepositoryImpl implements UserRepository {

	private DataSource datasource;
	
	
	public UserRepositoryImpl(DataSource datasource) {
		super();
		this.datasource = datasource;
	}

	@Override
	public void save(User user) {
		try(Connection con = datasource.getConnection()) {
			PreparedStatement insertOrUpdate = con.prepareStatement("insert into user (username, password) values (?, ?) on duplicate key update password=?");
			insertOrUpdate.setString(1, user.getUsername());
			insertOrUpdate.setString(2, user.getPassword());
			insertOrUpdate.setString(3, user.getPassword());
			insertOrUpdate.executeUpdate();
		} catch(SQLException e) {
			throw new JDBCException(e);
		}

	}

	@Override
	public User findByUsername(String username) {
		
		try(Connection con = datasource.getConnection()) {
			PreparedStatement query = con.prepareStatement("select username, password from user where username=?");
			query.setString(1, username);
			ResultSet rs = query.executeQuery();
			User user = null;
			while(rs.next()) {
				user = new User();
				user.setUsername(rs.getString(1));
				user.setPassword(rs.getString(2));
				break;
			}
			return user;
		} catch(SQLException e) {
			throw new JDBCException(e);
		}
	}

}

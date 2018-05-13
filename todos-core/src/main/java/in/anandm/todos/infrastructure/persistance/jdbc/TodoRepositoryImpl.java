package in.anandm.todos.infrastructure.persistance.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import in.anandm.todos.model.todo.Todo;
import in.anandm.todos.model.todo.TodoRepository;
import in.anandm.todos.model.user.User;

public class TodoRepositoryImpl implements TodoRepository {

	private DataSource datasource;
	
	public TodoRepositoryImpl(DataSource datasource) {
		super();
		this.datasource = datasource;
	}

	@Override
	public void save(Todo todo) {
		try(Connection con = datasource.getConnection()) {
			if(todo.getId() <= 0) {
				PreparedStatement insert = con.prepareStatement("insert into todo (task, completed, username) values(?, ?, ?)");
				insert.setString(1, todo.getTask());
				insert.setBoolean(2, todo.isCompleted());
				insert.setString(3, todo.getUser().getUsername());
				
				boolean success = insert.execute();
				
				if(success) {
					ResultSet rs = insert.getGeneratedKeys();
					while(rs.next()) {
						int id = rs.getInt(1);
						todo.setId(id);
					}
				}
			} else {
				PreparedStatement update = con.prepareStatement("update todo set task=?, completed=?, username=? where id=?");
				update.setString(1, todo.getTask());
				update.setBoolean(2, todo.isCompleted());
				update.setString(3, todo.getUser().getUsername());
				update.setInt(4, todo.getId());
				
				update.executeUpdate();
			}
		} catch (SQLException e) {
			throw new JDBCException(e);
		}

	}

	@Override
	public Iterable<Todo> findByUser(User user) {
		try(Connection con = datasource.getConnection()) {
			PreparedStatement query = con.prepareStatement("select id, task, completed from todo where username=?");
			query.setString(1, user.getUsername());
			
			ResultSet rs = query.executeQuery();
			List<Todo> todos = new ArrayList<>();
			while(rs.next()) {
				Todo todo = new Todo();
				todo.setId(rs.getInt(1));
				todo.setTask(rs.getString(2));
				todo.setCompleted(rs.getBoolean(3));
				todo.setUser(user);
				todos.add(todo);
			}
			return todos;
		} catch(SQLException e) {
			throw new JDBCException(e);
		}
	}

	@Override
	public Todo findById(int id) {
		try(Connection con = datasource.getConnection()) {
			PreparedStatement query = con.prepareStatement("select todo.*, user.password from todo, user where todo.username = user.username and todo.id=?");
			query.setInt(1, id);
			
			ResultSet rs = query.executeQuery();
			Todo todo = null;
			while(rs.next()) {
				todo = new Todo();
				todo.setId(rs.getInt(1));
				todo.setTask(rs.getString(2));
				todo.setCompleted(rs.getBoolean(3));
				User user = new User();
				user.setUsername(rs.getString(4));
				user.setPassword(rs.getString(5));
				todo.setUser(user);
				break;
			}
			return todo;
		} catch(SQLException e) {
			throw new JDBCException(e);
		}
	}

	@Override
	public void deleteById(int id) {
		try(Connection con = datasource.getConnection()) {
			PreparedStatement delete = con.prepareStatement("delete from todo where id=?");
			delete.setInt(1, id);
			
			delete.executeUpdate();
		} catch(SQLException e) {
			throw new JDBCException(e);
		}
	}

}

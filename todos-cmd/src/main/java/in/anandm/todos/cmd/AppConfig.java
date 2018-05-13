package in.anandm.todos.cmd;

import in.anandm.todos.model.user.UserRepository;
import in.anandm.todos.model.todo.TodoRepository;
import in.anandm.todos.infrastructure.persistance.jdbc.UserRepositoryImpl;
import in.anandm.todos.infrastructure.persistance.jdbc.TodoRepositoryImpl;
import in.anandm.todos.service.AuthService;


import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.springframework.context.annotation.Bean;

import java.beans.PropertyVetoException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    
	@Bean
	public DataSource datasource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setJdbcUrl("jdbc:mysql://localhost/todos?user=root&password=");
		try {
			cpds.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
		return cpds;
	}
	
    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl(datasource());
    }
    
    
    @Bean
    public TodoRepository todoRepository() {
        return new TodoRepositoryImpl(datasource());
    }
    
    @Bean
    public AuthService authService() {
        return new AuthService(userRepository());
    }
    
    @Bean
    public Context context() {
        return new HashMapContextImpl();
    }
    
    @Bean
    public Action loginAction() {
        return new LoginAction(authService());
    }
    
    @Bean
    public Action regisetrUserAction() {
        return new RegisterUserAction(userRepository(), authService());
    }
    
    @Bean
    public Action logoutAction() {
        return new LogoutAction();
    }
    
    @Bean
    public Action listTodosAction() {
        return new ListTodosAction(todoRepository());
    }
    
    @Bean
    public Action addTodoAction() {
        return new AddTodoAction(todoRepository());
    }
    
    @Bean
    public Action updateTodoAction() {
        return new UpdateTodoAction(todoRepository());
    }
    
    
    @Bean
    public Action exitAction() {
        return (context) -> {
            System.exit(0);
        };
    }
    
    @Bean
    public Menu loginMenu() {
        Menu menu = new Menu();
        menu.setId(1);
        menu.setName("Login");
        menu.setDescription("Login");
        menu.setAction(loginAction());
        return menu;
    }
    
    @Bean
    public Menu regisetrUserMenu() {
        Menu menu = new Menu();
        menu.setId(2);
        menu.setName("Register");
        menu.setDescription("Register");
        menu.setAction(regisetrUserAction());
        return menu;
    }
    
    @Bean
    public Menu logoutMenu() {
        Menu menu = new Menu();
        menu.setId(3);
        menu.setName("Logout");
        menu.setDescription("Logout");
        menu.setAction(logoutAction());
        menu.setLoginRequired(true);
        return menu;
    }
   
    
    @Bean
    public Menu listTodosMenu() {
        Menu menu = new Menu();
        menu.setId(4);
        menu.setName("List Todo");
        menu.setDescription("List Todo");
        menu.setAction(listTodosAction());
        menu.setLoginRequired(true);
        return menu;
    }
    
    @Bean
    public Menu addTodoMenu() {
        Menu menu = new Menu();
        menu.setId(5);
        menu.setName("Add Todo");
        menu.setDescription("Add Todo");
        menu.setAction(addTodoAction());
        menu.setLoginRequired(true);
        return menu;
    }
    
    @Bean
    public Menu updateTodoMenu() {
        Menu menu = new Menu();
        menu.setId(6);
        menu.setName("Modify Todo");
        menu.setDescription("Modify Todo");
        menu.setAction(updateTodoAction());
        menu.setLoginRequired(true);
        return menu;
    }
    
    // @Bean
    // public Menu loginMenu() {
    //     Menu menu = new Menu();
    //     menu.setId(0);
    //     menu.setName("Exit");
    //     menu.setDescription("Exit");
    //     menu.setAction(loginAction());
    // }
    
    @Bean
    public App app() {
        List<Menu> menus = Arrays.asList(new Menu[] {
            loginMenu(),
            regisetrUserMenu(),
            logoutMenu(),
            listTodosMenu(),
            addTodoMenu(),
            updateTodoMenu()
        });
        
        return new App(menus, context());
    }
    
}
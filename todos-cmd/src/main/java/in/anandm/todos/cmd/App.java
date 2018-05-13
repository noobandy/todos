package in.anandm.todos.cmd;

import java.util.Map;
import java.util.TreeMap;
import java.io.Console;
import java.util.List;
import in.anandm.todos.model.user.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {
    private Map<Integer, Menu> menus;
    private Context context;
    
    public App(List<Menu> menus, Context context) {
        this.context = context;
        
        this.menus = new TreeMap<>();
        for(Menu menu : menus) {
            this.menus.put(menu.getId(), menu);
        }
    }
    
    
    public void run() {
        Console console = System.console();
        if(console == null) {
            System.err.println("console not attached. existing...");
            System.exit(1);
        }
        
        context.set(ContextKeys.Console.name(), console);
        
        while(true) {
            User loggedInUser = (User) context.get(ContextKeys.LoggedInUser.name());
            
            for(Map.Entry<Integer, Menu> entry: menus.entrySet()) {
                Menu menu = entry.getValue();
                boolean render = (loggedInUser != null && menu.isLoginRequired()) || (loggedInUser == null && !menu.isLoginRequired());
                
                if(render) {
                    console.format("%d) %s\n", menu.getId(), menu.getName());
                }
            }
            
            String selected = console.readLine("Select a menu: ");
            
            Menu menu = menus.get(Integer.parseInt(selected));
            
            if(menu != null) {
                menu.getAction().perfrom(context);
            }
        }
    }
    
    public static void main( String[] args ) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        
       App app = applicationContext.getBean(App.class);
       app.run();
    }
}

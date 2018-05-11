package in.anandm.todos.cmd;

import in.anandm.todos.model.user.User;
import in.anandm.todos.service.AuthService;
import java.io.Console;

public class LoginAction implements Action {
    private AuthService authService;
    
    public LoginAction(AuthService authService) {
        this.authService = authService;
    }
    
    public void perfrom(Context context) {
        if(context.get(ContextKeys.LoggedInUser.name()) == null) {
            Console console = (Console) context.get(ContextKeys.Console.name());
        
            // read username and password, validate using user repository;
            String username = console.readLine("username: ");
            //TODO: use read password
            String password = console.readLine("password: ");
            
            User authUser = authService.verifyCredentials(username, password);
            
            if(authUser != null) {
                
                context.set(ContextKeys.LoggedInUser.name(), authUser);
            } else {
                console.format("username or password is incorrect\n");
            }
        }
    }
}
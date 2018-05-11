package in.anandm.todos.cmd;

import in.anandm.todos.model.user.User;
import in.anandm.todos.model.user.UserRepository;
import in.anandm.todos.service.AuthService;
import java.io.Console;

public class RegisterUserAction implements Action {
    private UserRepository userRepository;
    private AuthService authService;
    
    public RegisterUserAction(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }
    
    public void perfrom(Context context) {
        if(context.get(ContextKeys.LoggedInUser.name()) == null) {
            Console console = (Console) context.get(ContextKeys.Console.name());
        
            // read username and password, validate using user repository;
            String username = console.readLine("username: ");
            //TODO: use read password
            String password = console.readLine("password: ");
            
            User user = userRepository.findByUsername(username);
            
            if(user != null) {
                console.format("sorry! username '%s' is already taken\n", username);
            } else {
               
               authService.registerUser(username, password);
               
               console.format("Registered successfully\n");
            }
        }
    }
}
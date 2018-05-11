package in.anandm.todos.cmd;

import in.anandm.todos.model.user.User;
import in.anandm.todos.model.user.UserRepository;
import java.io.Console;

public class LogoutAction implements Action {
    
    public LogoutAction() {
      
    }
    
    public void perfrom(Context context) {
        context.unset(ContextKeys.LoggedInUser.name());
        Console console = (Console) context.get(ContextKeys.Console.name());
        console.format("logged out successfully\n");
    }
}
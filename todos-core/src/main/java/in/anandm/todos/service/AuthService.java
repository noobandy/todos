package in.anandm.todos.service;
import in.anandm.todos.model.user.User;
import in.anandm.todos.model.user.UserRepository;

public class AuthService {
    
    private UserRepository userRepository;
    
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;    
    }
    
    public User registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        
        userRepository.save(user);
        return user;
    }
    
    public User verifyCredentials(String username, String password) {
        User found = userRepository.findByUsername(username);
        if(found != null && found.getPassword().equals(password)) {
            return found;
        } else {
            return null;
        }
    }
}
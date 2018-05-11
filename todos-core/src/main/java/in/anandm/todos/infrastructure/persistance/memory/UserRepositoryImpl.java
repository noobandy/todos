package in.anandm.todos.infrastructure.persistance.memory;
import in.anandm.todos.model.user.User;
import in.anandm.todos.model.user.UserRepository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepositoryImpl implements UserRepository {
    
    private Map<String, User> users;
    
    public UserRepositoryImpl() {
        users = new ConcurrentHashMap<>();
    }
    
    public void save(User user) {
        
        users.put(user.getUsername(), user);
    }
  
    public User findByUsername(String username) {
        return users.get(username);
    }
    
    
}
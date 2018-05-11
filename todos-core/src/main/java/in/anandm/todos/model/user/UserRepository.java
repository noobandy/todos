package in.anandm.todos.model.user;

public interface UserRepository {
    
    void save(User user);
    User findByUsername(String username);
}
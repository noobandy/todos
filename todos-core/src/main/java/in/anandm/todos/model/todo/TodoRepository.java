package in.anandm.todos.model.todo;
import in.anandm.todos.model.user.User;

public interface TodoRepository {
    
    void save(Todo todo);
    
    Iterable<Todo> findByUser(User user);
    
    Todo findById(int id);
    
    void deleteById(int id);
    
}
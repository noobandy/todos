package in.anandm.todos.infrastructure.persistance.memory;
import in.anandm.todos.model.todo.Todo;
import in.anandm.todos.model.todo.TodoRepository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import in.anandm.todos.model.user.User;

public class TodoRepositoryImpl implements TodoRepository {
    
    private Map<Integer, Todo> todos;
    private AtomicInteger nextId;
    
    public TodoRepositoryImpl() {
        todos = new ConcurrentHashMap<>();
        nextId = new AtomicInteger(1);
    }
    
    public void save(Todo todo) {
        if(todo.getId() <= 0) {
            todo.setId(nextId.getAndIncrement());
        }
        
        todos.put(todo.getId(), todo);
    }
    
    public Iterable<Todo> findByUser(User user) {
        List<Todo> result = new ArrayList<>(todos.values());
        Iterator<Todo> itr = result.iterator();
        while(itr.hasNext()) {
            if(!itr.next().getUser().equals(user)) {
                itr.remove();
            }
        }
        
         return result;
    }
    
    public Todo findById(int id) {
        return todos.get(id);
    }
    
    public void deleteById(int id) {
        todos.remove(id);
    }
    
}
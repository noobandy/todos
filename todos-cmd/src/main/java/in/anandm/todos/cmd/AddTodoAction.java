package in.anandm.todos.cmd;

import in.anandm.todos.model.user.User;
import in.anandm.todos.model.todo.TodoRepository;
import java.io.Console;
import in.anandm.todos.model.todo.Todo;


public class AddTodoAction implements Action {
    private TodoRepository todoRepository;
    
    public AddTodoAction(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    
    public void perfrom(Context context) {
        User loggedInUser = (User) context.get(ContextKeys.LoggedInUser.name());
        
        if(loggedInUser != null) {
            
            Console console = (Console) context.get(ContextKeys.Console.name());
            Todo todo = new Todo();
            todo.setUser(loggedInUser);
            
            String task = console.readLine("Enter new task details: ");
            
            todo.setTask(task);
            
            todoRepository.save(todo);
            
            console.format("a new todo with id %d added.\n", todo.getId());
        }
    }
}
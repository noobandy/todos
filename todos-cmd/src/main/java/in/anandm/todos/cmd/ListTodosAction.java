package in.anandm.todos.cmd;

import in.anandm.todos.model.user.User;
import in.anandm.todos.model.todo.TodoRepository;
import in.anandm.todos.model.todo.Todo;
import java.io.Console;

public class ListTodosAction implements Action {
    private TodoRepository todoRepository;
    
    public ListTodosAction(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    
    public void perfrom(Context context) {
        User loggedInUser = (User) context.get(ContextKeys.LoggedInUser.name());
        if(loggedInUser != null) {
            
            Iterable<Todo> todos = todoRepository.findByUser(loggedInUser);
            
            
            Console console = (Console) context.get(ContextKeys.Console.name());
            console.format("Todos\n");
            
            for(Todo todo : todos) {
                String symbol = "\u2610";
                if(todo.isCompleted()) {
                    symbol = "\u2611";
                }
                console.format("%d) %s %s\n", todo.getId(), symbol, todo.getTask());
            }
        }
    }
}
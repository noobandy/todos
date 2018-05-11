package in.anandm.todos.cmd;

import in.anandm.todos.model.user.User;
import in.anandm.todos.model.todo.TodoRepository;
import java.io.Console;
import in.anandm.todos.model.todo.Todo;

public class UpdateTodoAction implements Action {
    private TodoRepository todoRepository;
    
    public UpdateTodoAction(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    
    public void perfrom(Context context) {
        User loggedInUser = (User) context.get(ContextKeys.LoggedInUser.name());
        
        if(loggedInUser != null) {
            
            Console console = (Console) context.get(ContextKeys.Console.name());
            
            String id = console.readLine("Enter todo id that you want to modify: ");
            
            Todo todo = todoRepository.findById(Integer.parseInt(id));
            
            if(todo != null) {
                String action = console.readLine("Pick an action M(Modify task details)/D(Delete)/T(Toggle completed status): ");
                switch(action.toUpperCase()) {
                    case "M":
                        String task = console.readLine("Enter new task details: ");
                        todo.setTask(task);
                        todoRepository.save(todo);
                        console.format("task %s updated.\n", id);
                        break;
                    case "D": 
                        todoRepository.deleteById(todo.getId());
                        console.format("task %s deleted\n", id);
                        break;
                    case "T": 
                        String statusBefore = todo.isCompleted() ? "Completed" : "Pending";
                        todo.setCompleted(!todo.isCompleted());
                        String statusAfter = todo.isCompleted() ? "Completed" : "Pending";
                        todoRepository.save(todo);
                        console.format("todo %s changed from %s to %s\n", id, statusBefore, statusAfter);
                        break;
                    default:
                    console.format("Action not defined\n");
                }
            } else {
                console.format("Todo with id %s doesn't exists\n", id);
            }
        }
    }
}
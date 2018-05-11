package in.anandm.todos.cmd;

public class Menu {
    private int id;
    private String name;
    private String description;
    private Action action;
    private boolean loginRequired;
    
    public Menu() {
        
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setAction(Action action) {
        this.action = action;
    }
    
    public Action getAction() {
        return action;
    }
    
    public boolean isLoginRequired() {
        return loginRequired;
    }
    
    public void setLoginRequired(boolean loginRequired) {
        this.loginRequired = loginRequired;
    }
    
}
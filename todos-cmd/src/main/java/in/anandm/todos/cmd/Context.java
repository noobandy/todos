package in.anandm.todos.cmd;

public interface Context {
    void set(String key, Object value);
    
    Object get(String key);
    
    void unset(String key);
}
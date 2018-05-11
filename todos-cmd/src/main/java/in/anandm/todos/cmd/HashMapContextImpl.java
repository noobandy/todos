package in.anandm.todos.cmd;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class HashMapContextImpl implements Context {
    private Map<String, Object> map; 
    
    public HashMapContextImpl() {
        map = new ConcurrentHashMap<>();
    }
    
    public void set(String key, Object value) {
        if(value != null) {
            map.put(key, value);    
        }
    }
    
    public void unset(String key) {
        map.remove(key);
    }
    public Object get(String key) {
        return map.get(key);
    }
}
package in.anandm.todos.model.user;

public class User {
    private String username;
    private String password;
    
    public User() {
        
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
    
    public int hashCode() {
        return username.hashCode();
    }
    
    public boolean equals(Object other) {
        if(other instanceof User) {
            User otherUser = (User) other;
            return username.equals(otherUser.username);
        } else {
            return false;
        }
    }
}
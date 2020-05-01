
public class User{
    public String name;
    public String password;
    public String salt;

    public User(String name, String password){
        this.name = name.toLowerCase();
        this.password = password;
        this.salt = password.substring(0,2); 
    }
    
}
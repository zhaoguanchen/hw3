package hw3.data.model;

 /**
  * @Description: User Entity
  * @Author: Guanchen Zhao
  * @Date: 2021/2/27  
  */
public class User {
    String account;
    String password;
    String name;
    
    public User(String account, String password, String name) {
        this.account = account;
        this.password = password;
        this.name = name;
      
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

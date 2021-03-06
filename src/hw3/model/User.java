package hw3.model;

/**
 * @Description: User Entity
 * Users(user_id, user_name, lab, PI_name, email, phone, crypt_passwd_hash, lab_training)
 * @Author: Guanchen Zhao
 * @Date: 2021/2/27
 */
public class User {
    private Integer userId;

    /**
     * account to login
     */
    private String account;

    /**
     * name
     */
    private String name;

    /**
     * crypt_passwd_hash
     */
    private String password;

    /**
     * lab name
     */
    private String PI_name;

    /**
     * email address
     */
    private String email;

    /**
     * mobile phone
     */
    private String phone;

    /**
     * is trained? Y or N
     */
    private String lab_training;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPI_name() {
        return PI_name;
    }

    public void setPI_name(String PI_name) {
        this.PI_name = PI_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLab_training() {
        return lab_training;
    }

    public void setLab_training(String lab_training) {
        this.lab_training = lab_training;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", PI_name='" + PI_name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", lab_training='" + lab_training + '\'' +
                '}';
    }
}

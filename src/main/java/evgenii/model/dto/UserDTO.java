package evgenii.model.dto;

/**
 * Created by zhenia on 16.06.17.
 */
public class UserDTO {
    public String username;
    public String phone;
    public String address;
    public String password;

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

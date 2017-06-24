package evgenii.model;

import evgenii.model.dto.UserDTO;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;


@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String username;
    private String password;
//    @Pattern(regexp = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})", message = "invalid phone number")
    private String phone;
//    @Pattern(regexp = "[\\w',-\\\\/.\\s]", message = "invalid address format")
    private String address;


    public User() {
    }

    public User(UserDTO dto) {
        this.username = dto.username;
        this.phone = dto.phone;
        this.address = dto.address;
        this.password = dto.password;
    }

    @Deprecated
    public User(int id, String username, String phone, String address, String password) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    @Deprecated
    public User(String username, String phone, String address) {
        this.username = username;
        this.phone = phone;
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!username.equals(user.username)) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        return address != null ? address.equals(user.address) : user.address == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

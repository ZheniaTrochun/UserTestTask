package evgenii.dao;

import evgenii.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhenia on 15.06.17.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String name);

    User findUserById(Integer id);
}

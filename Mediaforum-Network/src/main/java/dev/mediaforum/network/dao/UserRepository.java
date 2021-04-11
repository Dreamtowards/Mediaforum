package dev.mediaforum.network.dao;


import dev.mediaforum.network.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByUsernameIgnoreCase(String username);

}

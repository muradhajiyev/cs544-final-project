package edu.miu.cs544.medappointment.repository;

import edu.miu.cs544.medappointment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailOrUsername(String username, String email);
    User findByUsername(String username);

}

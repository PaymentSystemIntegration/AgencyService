package com.sep.psp.repo;


import com.sep.psp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Integer> {
    User findOneByEmail(String email);
    User findByEmailAndPassword(String email, String password);

}

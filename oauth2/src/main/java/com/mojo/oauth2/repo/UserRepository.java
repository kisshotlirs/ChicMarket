package com.mojo.oauth2.repo;

import com.mojo.oauth2.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mojo
 * @description: TODO
 * @date 2023/7/19 0019 22:11
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsername(String username);
}

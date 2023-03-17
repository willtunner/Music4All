package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findByName(String name);

    @Query(value = "select * from users where name ilike :name% ", nativeQuery = true)
    List<User> findByName(@Param("name") String name);
}

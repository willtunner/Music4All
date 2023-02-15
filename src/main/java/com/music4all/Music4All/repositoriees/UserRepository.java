package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

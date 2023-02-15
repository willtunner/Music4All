package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.Followers;
import com.music4all.Music4All.model.Music;import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowersRepository extends JpaRepository<Followers, Long> {
}

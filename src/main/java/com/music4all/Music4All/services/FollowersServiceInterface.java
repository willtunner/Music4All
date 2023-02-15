package com.music4all.Music4All.services;

import com.music4all.Music4All.model.Disc;
import com.music4all.Music4All.model.Followers;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Optional;

public interface FollowersServiceInterface {
    Followers saveFollowers(Followers followers) throws MessagingException;
    Followers getFollowers(String followersName);
    Boolean deleteFollowers(Long idFollowers);
    List<Followers> getFollowers();
    Optional<Followers> getFollowersById(Long idFollowers);
    Followers updateFollowers(Followers followers);
}

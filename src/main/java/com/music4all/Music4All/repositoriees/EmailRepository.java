package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    @Query(nativeQuery = true, value = "select * from tb_email e where e.status_email != 2  and e.deleted = false and count < 5 order by email_id desc limit :limitError")
    List<Email> findByStatusEmail(Integer limitError);

}

package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    @Query(nativeQuery = true, value = "select * from tb_email  where status_email !=2 AND status_email !=1  AND deleted = false AND count <= 5 ORDER BY email_id DESC LIMIT :limitError")
    List<Email> findByStatusEmail(Integer limitError);

}

package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BandRepository extends JpaRepository<Band, Long> {

    @Query(value = "select * from band where name ilike :name% ", nativeQuery = true)
    List<Band> findByName(@Param("name") String name);

    List<Band> findBandByState(String state);

    //List<Employee> findFirst2ByDeptOrderBySalaryDesc(String state);
    List<Band> findTop5ByStateOrderByLikeAsc(String state);
}

package com.example.slgm.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.slgm.entity.Unitkousei;

@Repository
public interface SlgmRepositoryUnitkousei extends JpaRepository<Unitkousei, Integer> {
	//@Query("Select a.kid from unitkousei a inner join sendanm b on a.sid = b.id"  )

	//public List<Unitkousei> findAll();


	
	
}

package com.example.slgm.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.slgm.entity.Unitrekka;

@Repository
public interface SlgmRepositoryUnitrekka  extends JpaRepository<Unitrekka, Integer> {
	
//	@Query("SELECT id FROM unitrekka ORDER BY id")
//	Integer getRandomId();	
	
//	public List<Unitrekka>  findAll();
}

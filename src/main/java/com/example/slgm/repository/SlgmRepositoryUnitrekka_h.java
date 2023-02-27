package com.example.slgm.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.slgm.entity.rekka;

@Repository
public interface SlgmRepositoryUnitrekka_h  extends JpaRepository<rekka, Integer> {
	
//	@Query("SELECT id FROM rekka ORDER BY id,kuunz DESC")
//	Integer getRandomId();	
	
//	public List<rekka>  findAll();
}

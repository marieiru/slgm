package com.example.slgm.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.slgm.entity.Unitbunruim;

@Repository
public interface SlgmRepositoryUnitbunrui extends JpaRepository<Unitbunruim, Integer> {
	
//	@Query("SELECT id FROM unitbunruim ORDER BY id")
//	Integer getRandomId();	
	
//	public List<Unitbunruim>  findAll();
}

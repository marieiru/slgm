package com.example.slgm.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.slgm.entity.Bukim;

@Repository
public interface SlgmRepositoryBuki extends JpaRepository<Bukim, Integer> {

//	@Query("SELECT wno FROM bukim ORDER BY wno")
//	Integer getRandomId();	
	
//	public List<Bukim>  findAll();	
	
}

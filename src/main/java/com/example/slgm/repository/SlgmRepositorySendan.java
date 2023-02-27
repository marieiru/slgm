package com.example.slgm.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.example.slgm.entity.Sendanm;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SlgmRepositorySendan extends JpaRepository<Sendanm, Integer> {

//	@Query("SELECT id FROM sendanm ORDER BY id")
//	Integer getRandomId();	
	
//	public List<Sendanm>  findAll();
	
}


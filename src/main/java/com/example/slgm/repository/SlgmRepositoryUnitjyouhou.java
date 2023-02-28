package com.example.slgm.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.slgm.entity.Unitjyouhou;

@Repository
public interface SlgmRepositoryUnitjyouhou  extends JpaRepository<Unitjyouhou, Integer> {

//	@Query("SELECT zid FROM unitjyouhou ORDER BY zid")
//	Integer getRandomId();	
	
//	public List<Unitjyouhou>  findAll();		
}

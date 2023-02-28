package com.example.slgm.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.slgm.entity.Unityakuwarim;

@Repository
public interface SlgmRepositoryUnityakuwari extends JpaRepository<Unityakuwarim, Integer> {
	
//	@Query("SELECT id FROM unityakuwarim ORDER BY id")
//	Integer getRandomId();	
	
//	public List<Unityakuwarim>  findAll();
}

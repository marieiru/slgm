package com.example.slgm.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.slgm.entity.Chapter_unit2;

@Repository
public interface SlgmRepositoryChapter_unit2 extends JpaRepository<Chapter_unit2, Integer> {
	
}


package com.example.slgm.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.slgm.entity.Chapter_kousei1;

@Repository
public interface SlgmRepositoryChapter_kousei1 extends JpaRepository<Chapter_kousei1, Integer> {
	
}
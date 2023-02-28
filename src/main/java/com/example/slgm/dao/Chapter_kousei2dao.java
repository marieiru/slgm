package com.example.slgm.dao;

import java.io.Serializable;
import java.util.List;

import com.example.slgm.entity.Chapter_kousei2;

public interface Chapter_kousei2dao extends Serializable {
	public List<Chapter_kousei2> search(Integer did);
}
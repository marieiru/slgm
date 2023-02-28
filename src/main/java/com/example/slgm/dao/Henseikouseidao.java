package com.example.slgm.dao;

import java.io.Serializable;
import java.util.List;

import com.example.slgm.entity.Henseit;

public interface Henseikouseidao extends Serializable {
	public List<Henseit> search(Integer sid, Integer bid);

}

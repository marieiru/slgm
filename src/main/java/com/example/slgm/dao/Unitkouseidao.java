package com.example.slgm.dao;

import java.io.Serializable;
import java.util.List;

import com.example.slgm.entity.Unitkousei;

public interface Unitkouseidao  extends Serializable {
	public List<Unitkousei> search(Integer zid);
}

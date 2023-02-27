package com.example.slgm.dao;

import java.io.Serializable;
import java.util.List;

import com.example.slgm.entity.Bukim;

public interface Bukimdao extends Serializable {
	public List<Bukim> search(Integer sid,Integer h);
}

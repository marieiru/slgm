package com.example.slgm.dao;

import java.io.Serializable;
import java.util.List;

import com.example.slgm.entity.Unitrekka;

public interface Unitrekkadao  extends Serializable {
	public List<Unitrekka> search(Integer sid, Integer yid);	
}
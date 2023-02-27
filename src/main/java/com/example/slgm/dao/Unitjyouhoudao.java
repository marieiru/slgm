package com.example.slgm.dao;

import java.io.Serializable;
import java.util.List;

import com.example.slgm.entity.Unitjyouhou;

public interface Unitjyouhoudao   extends Serializable {
	public List<Unitjyouhou> search(Integer sid, Integer yid);
	public List<Unitjyouhou> search_sid(Integer zid);
}

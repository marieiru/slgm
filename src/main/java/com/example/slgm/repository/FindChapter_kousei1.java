package com.example.slgm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.slgm.dao.Chapter_kousei1dao;
import com.example.slgm.entity.Chapter_kousei1;


@SuppressWarnings("serial")
@Repository
public class FindChapter_kousei1 implements Chapter_kousei1dao {	
	
    @Autowired
    private EntityManager entityManager;

    public FindChapter_kousei1() {
        super();
    }

    public FindChapter_kousei1(EntityManager manager) {
        this();
        entityManager = manager;
    }		
	
    //Daoクラスで用意したsearchメソッドをオーバーライドする
    @SuppressWarnings("unchecked")
    @Override
    public List<Chapter_kousei1> search(Integer did) {

        //StringBuilderでSQL文を連結する
        StringBuilder sql = new StringBuilder();
        //sql.append("Select a.kid from unitkousei a inner join sendanm b on a.sid = b.id WHERE ");
      
        sql.append("Select a from Chapter_kousei1 a WHERE ");
 
        //genreがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if((did)==0) {
        } else {
            sql.append("a.did =" + did);
            
        }
         
        sql.append(" order by id ");
 
        Query query = entityManager.createQuery(sql.toString());

        return query.getResultList();
    }	    
    
    
	
}

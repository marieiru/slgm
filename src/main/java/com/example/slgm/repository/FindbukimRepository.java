package com.example.slgm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.slgm.dao.Bukimdao;
import com.example.slgm.entity.Bukim;


@SuppressWarnings("serial")
@Repository
public class FindbukimRepository implements Bukimdao {	
	
    @Autowired
    private EntityManager entityManager;

    public FindbukimRepository() {
        super();
    }

    public FindbukimRepository(EntityManager manager) {
        this();
        entityManager = manager;
    }		
	
    //Daoクラスで用意したsearchメソッドをオーバーライドする
    @SuppressWarnings("unchecked")
    @Override
    public List<Bukim> search(Integer sid,Integer h) {

        //StringBuilderでSQL文を連結する
        StringBuilder sql = new StringBuilder();
        //sql.append("Select a.kid from unitkousei a inner join sendanm b on a.sid = b.id WHERE ");
      
        sql.append("Select a from Bukim a WHERE ");
 
        //genreがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if((sid)==0) {
        } else {
            sql.append("a.sid =" + sid);
            
        }

        if((h)==0) {
        	sql.append(" AND ");
            sql.append("a.syubetu is not '白兵'");      
        } else if((h)==2) {
        
        } else {
        	sql.append(" AND ");
            sql.append("a.syubetu ='白兵'");

        }        
        
        
        sql.append(" order by wno");
 
        Query query = entityManager.createQuery(sql.toString());

        return query.getResultList();
    }	    
    
    
	
}

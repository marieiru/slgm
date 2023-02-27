package com.example.slgm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.slgm.dao.Unitrekkadao;
import com.example.slgm.entity.Unitrekka;

@SuppressWarnings("serial")
@Repository
public class FindUnitrekkaRepository implements Unitrekkadao {
	
    @Autowired
    private EntityManager entityManager;

    public FindUnitrekkaRepository() {
        super();
    }

    public FindUnitrekkaRepository(EntityManager manager) {
        this();
        entityManager = manager;
    }	
	
	
    //Daoクラスで用意したsearchメソッドをオーバーライドする
    @SuppressWarnings("unchecked")
    @Override
    public List<Unitrekka> search(Integer sid, Integer yid) {

        //StringBuilderでSQL文を連結する
        StringBuilder sql = new StringBuilder();
        //sql.append("Select a.kid from unitkousei a inner join sendanm b on a.sid = b.id WHERE ");
      
        sql.append("Select a from Unitrekka a WHERE ");
      
        boolean andFlg    = false;

        //genreがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if((sid)==0) {
        } else {
            sql.append("a.sid =" + sid);

            andFlg   = true;
        }


        //authorがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく

        if((yid)==0) {
        } else {
            if (andFlg) sql.append(" AND ");
            sql.append("a.yid =" + yid);
 
            andFlg    = true;
        }
        
        sql.append(" order by zid");
        /*
        QueryはSQLでデータを問い合わせるためのクエリ文に相当する機能を持つ
        entityManagerのcreateQueryメソッドを使用する
        sql変数を引数に渡す
        */
        Query query = entityManager.createQuery(sql.toString());

        return query.getResultList();	
    }
	
}	

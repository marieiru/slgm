package com.example.slgm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.slgm.dao.Unitkouseidao;
import com.example.slgm.entity.Unitkousei;

@SuppressWarnings("serial")
@Repository
public class FindkouseiimpRepository implements Unitkouseidao {

	
    @Autowired
    private EntityManager entityManager;

    public FindkouseiimpRepository() {
        super();
    }

    public FindkouseiimpRepository(EntityManager manager) {
        this();
        entityManager = manager;
    }	
	
	
    //Daoクラスで用意したsearchメソッドをオーバーライドする
    @SuppressWarnings("unchecked")
    @Override
    public List<Unitkousei> search(Integer zid) {

        //StringBuilderでSQL文を連結する
        StringBuilder sql = new StringBuilder();
        //sql.append("Select a.kid from unitkousei a inner join sendanm b on a.sid = b.id WHERE ");
      
        sql.append("Select a from Unitkousei a WHERE ");
      
        boolean sidFlg  = false;
        boolean bidFlg = false;
        boolean yidFlg  = false;
        boolean andFlg    = false;

        //genreがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if((zid)==0) {
        } else {
            sql.append("a.zid =" + zid);
            sidFlg = true;
            andFlg   = true;
        }


        //authorがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        /**
        if((bid)==0) {
        } else {
            if (andFlg) sql.append(" AND ");
            sql.append("a.bid =" + bid);
            bidFlg = true;
            andFlg    = true;
        }
        
 
        //titleがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if(!"".equals(yid)) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.title LIKE :title");
            yidFlg = true;
            andFlg   = true;
        }
**/
        
        sql.append(" order by zid,kid");
        /*
        QueryはSQLでデータを問い合わせるためのクエリ文に相当する機能を持つ
        entityManagerのcreateQueryメソッドを使用する
        sql変数を引数に渡す
        */
        Query query = entityManager.createQuery(sql.toString());

        //上記のif文でtrueになった場合は、各変数に値をセットする
        //今回、あいまい検索したいのでlike句を使用する
//        if (sidFlg) query.setParameter("sid", sid );
//        if (bidFlg) query.setParameter("bid", "%" + bid + "%");
//        if (yidFlg) query.setParameter("yid", "%" + yid + "%");
        return query.getResultList();
    }	
		
	
	
}

package com.example.slgm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="bukim")
@Getter
@Setter
public class Bukim {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//追加
	@Column
	private Integer wno;
	@Column
	private String wname;	
	@Column
	private String syubetu;	
	@Column
	private Integer syatei;	
	@Column
	private Integer at;	
	@Column
	private Integer ap;	
	@Column
	private String ks;	
	@Column
	private String dmg;	
	@Column
	private String abl;		
	
	@Column
	private Integer sid;	
	
	@Column
	private Integer hit;		
	
    @ManyToOne
    @JoinColumn(name="sid",insertable=false, updatable=false )
    private Sendanm sendanm;	
	
}

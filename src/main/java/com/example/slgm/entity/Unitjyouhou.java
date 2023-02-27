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
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="unitjyouhou")
public class Unitjyouhou {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//追加
	@Column
	private Integer zid;
	@Column
	private String yuname;	

/**	
	@Column
	private String fname;	
	@Column
	private String brname;	
	@Column
	private String ywname;	
**/	
	
	@Column
	private Integer sid;	
	
	@Column
	private Integer yid;	
	
	@Column
	private Integer pck;	
	@Column
	private Integer mck;	
	@Column
	private Integer ninnzuu;	
	@Column
	private Integer zouin;	
	@Column
	private Integer sousuu;	
	@Column
	private Integer kbzouin;		
	@Column
	private String img;	
	
    @ManyToOne
    @JoinColumn(name="sid",insertable=false, updatable=false )
    private Sendanm sendanm;
  
    @ManyToOne
    @JoinColumn(name="yid",insertable=false, updatable=false )
    private Unityakuwarim unityakuwarim;		
	
	
}

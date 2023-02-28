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
@Table(name = "chapter_kousei2")
@Getter
@Setter
public class Chapter_kousei2 {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//追加
	@Column
	private Integer id;	
	@Column
	private Integer kid;
//	@Column
//	private Integer sid;	
	@Column
	private Integer bid;	
	@Column
	private String kname;	
	@Column
	private Integer at;	
	@Column
	private Integer taff;	
	@Column
	private Integer uunz;	
	@Column
	private Integer ak;	
	@Column
	private Integer ld;	
	@Column
	private Integer asa;		
	@Column
	private Integer assp;		
	@Column
	private String abl;		
	@Column
	private Integer zid;
	

	@Column
	private Integer wno;
	@Column
	private Integer wno2;	
	@Column
	private Integer wno3;	
	@Column
	private Integer wno4;	
	@Column
	private Integer wno5;	
	@Column
	private Integer wno6;	
	@Column
	private Integer wno7;		
	
	@Column
	private Integer mvg;		

	@Column
	private Integer did;		
	
 
  
    @ManyToOne
    @JoinColumn(name="bid",insertable=false, updatable=false )
    private Unitbunruim unitbunruim;	
    
    @ManyToOne
    @JoinColumn(name="wno",insertable=false, updatable=false )
    private Bukim bukim;	    
  
    @ManyToOne
    @JoinColumn(name="wno2",insertable=false, updatable=false )
    private Bukim bukim2;	
    
    @ManyToOne
    @JoinColumn(name="wno3",insertable=false, updatable=false )
    private Bukim bukim3;	
    
    @ManyToOne
    @JoinColumn(name="wno4",insertable=false, updatable=false )
    private Bukim bukim4;	    
  
    @ManyToOne
    @JoinColumn(name="wno5",insertable=false, updatable=false )
    private Bukim bukim5;	
    
    @ManyToOne
    @JoinColumn(name="wno6",insertable=false, updatable=false )
    private Bukim bukim6;	
    
    @ManyToOne
    @JoinColumn(name="wno7",insertable=false, updatable=false )
    private Bukim bukim7;	  
	
}

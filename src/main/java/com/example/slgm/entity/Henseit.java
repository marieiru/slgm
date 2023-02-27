package com.example.slgm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


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
@Table(name="henseit")
public class Henseit {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//追加
	@Column
	private Integer id;
	@Column
	private Integer sid;	
	@Column
	private Integer bid;	
	@Column
	private Integer yid;	
	
}

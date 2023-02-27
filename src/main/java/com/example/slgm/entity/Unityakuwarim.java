package com.example.slgm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import javax.persistence.Id;

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
@Table(name="unityakuwarim")
public class Unityakuwarim {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//追加
	@Column
	private Integer id;
	@Column
	private String ywname;
}

package com.example.slgm.form;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormChapter_kousei2 {

	private Integer id;	
	@NotNull
	private Integer kid;
//	@NotNull
//	private Integer sid;	
	@NotNull
	private Integer bid;	
	@NotBlank
	private String kname;	
	@NotNull
	private Integer at;	
	@NotNull
	private Integer taff;	
	@NotNull
	private Integer uunz;	
	@NotNull
	private Integer ak;	
	@NotNull
	private Integer ld;	
	@NotNull
	private Integer asa;		
	@NotNull
	private Integer assp;		
	
	private String abl;		
	@NotNull
	private Integer zid;
	

	@NotNull
	private Integer wno;
	@NotNull
	private Integer wno2;	
	@NotNull
	private Integer wno3;	
	@NotNull
	private Integer wno4;	
	@NotNull
	private Integer wno5;	
	@NotNull
	private Integer wno6;	
	@NotNull
	private Integer wno7;			

	private Integer mvg;		
	
	private Integer did;		
	
}

package com.example.slgm.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormUnitkousei {


	//@Id
	private Integer kid;
	
	//@NotNull
	private Integer sid;
	
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
	
	private Integer zid;	
	
	@NotNull
	private Integer mv;		
	
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
	
	@NotNull
	private Integer kazu;	
	
	private Boolean newUnitkousei;
	
}

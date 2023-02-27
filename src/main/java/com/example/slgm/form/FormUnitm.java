package com.example.slgm.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormUnitm {

	private Integer id;
	@NotNull
	private Integer zid;	
	@NotNull
	private Integer kid;	
	
	private Integer kazu;	
	
//	@NotNull
	private Integer kbzouin;		
	
	private Boolean newUnitm;
	
}

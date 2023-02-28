package com.example.slgm.form;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormUnitbunruim {
	private Integer id;
	@NotBlank
	private String brname;	
	
	private Boolean newUnitbunruim;
}

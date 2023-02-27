package com.example.slgm.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormHenseit {

	private Integer id;
	
	
	private Integer sid;
	
	private Integer bid;
	
	private Integer yid;	
	
	private Boolean setNewHenseit;
	
}

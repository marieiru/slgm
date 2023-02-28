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
public class FormSoubi {
//	@Id
	private Integer id;
	@NotNull
	private Integer kid;
	@NotNull
	private Integer wno;
	@NotNull
	private Integer wgno;
	
	private Boolean newSoubi;
}

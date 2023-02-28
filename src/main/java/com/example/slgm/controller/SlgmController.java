package com.example.slgm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SlgmController {

	@GetMapping("/menu")
	public String menu() {
		
		return "menu";
	}
	


	@GetMapping("/function/{no}")
	public String SelectFunctiom(@PathVariable Integer no) {
		String view = null;
		
		switch (no) {
		case 1:
			view = "function1";
			break;		
			
		case 2:
			view = "function2";
			break;		
			
		case 3:
			view = "function3";
			break;					
		
		}
		
		return view;
		
	}	

	
	
	@PostMapping(value = "send", params = "a")
	public String showAView() {
		return "sendanh";
	}
	
	@PostMapping(value = "send", params = "b")
	public String showBView() {
		return "b";
	}	
	
	@PostMapping(value = "send", params = "c")
	public String showCView() {
		return "c";
	}		
	
}

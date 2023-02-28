package com.example.slgm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.slgm.entity.Unitjyouhou;
import com.example.slgm.entity.Unitkousei;
import com.example.slgm.form.FormBukim;
import com.example.slgm.form.FormUnitjyouhou;
import com.example.slgm.form.FormUnitkousei;

import com.example.slgm.repository.FindkouseiimpRepository;
import com.example.slgm.repository.FindunitjyouhouRepository;
import com.example.slgm.repository.SlgmRepositorySendan;
import com.example.slgm.repository.SlgmRepositoryUnitbunrui;
import com.example.slgm.repository.SlgmRepositoryUnitjyouhou;
import com.example.slgm.repository.SlgmRepositoryUnitkousei;
import com.example.slgm.repository.SlgmRepositoryUnityakuwari;



@Controller
@RequestMapping("/battle")
public class BattleController {

	@GetMapping("/index")
	public String bukim(FormBukim formBukim,Model model) {


		return "battle";
	}	
	

	@ModelAttribute
	public FormUnitjyouhou setForm() {
		return new FormUnitjyouhou();
	}

	@ModelAttribute
	public FormUnitkousei setFormkousei() {
		return new FormUnitkousei();
	}	
	
	
	
}

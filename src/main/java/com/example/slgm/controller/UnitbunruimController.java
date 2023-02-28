package com.example.slgm.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.example.slgm.entity.Unitbunruim;

import com.example.slgm.form.FormUnitbunruim;
import com.example.slgm.repository.SlgmRepositoryUnitbunrui;

@Controller
@RequestMapping("/unitbunruim")
public class UnitbunruimController {

	@Autowired
	SlgmRepositoryUnitbunrui repository;
	
	
    @ModelAttribute
    public FormUnitbunruim setForm() {
        return new FormUnitbunruim();
    }
    
    
	@GetMapping("/unitbunruim")
	public String unitbunruim() {
		return "unitbunruim";
	}   	


	@GetMapping
	public String showList(FormUnitbunruim formUnitbunruim, Model model) {

		formUnitbunruim.setNewUnitbunruim(true);
		Iterable<Unitbunruim> list = selectAll();
	
		model.addAttribute("list",list);
		model.addAttribute("title","登録用フォーム");
		return "unitbunruim";
		
	}

	
	@PostMapping("/insert")
	public String insert( @Validated FormUnitbunruim formUnitbunruim, 
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		Unitbunruim unitbunruim = new Unitbunruim();
		unitbunruim.setBrname(formUnitbunruim.getBrname());

		if (!bindingResult.hasErrors()) {
			
			insertUnitbunruim(unitbunruim);
			redirectAttributes.addFlashAttribute("complete", "登録が完了しました。");
			return "redirect:/unitbunruim";
		} else {
			return showList(formUnitbunruim, model);			
		}		
		
	
	}
	

	@GetMapping("/{id}")
	public String showUpdatet(FormUnitbunruim formUnitbunruim, @PathVariable Integer id, Model model) {

		Optional<Unitbunruim> unitbunruimOpt= selectOneById(id);
		
		Optional<FormUnitbunruim> unitbunruimFormOpt= unitbunruimOpt.map(t -> makeFormUnitbunruim(t));
		
		if (unitbunruimFormOpt.isPresent()) {
			formUnitbunruim = unitbunruimFormOpt.get();
		}
		
		makeUpdateModel(formUnitbunruim, model);
		return "unitbunruim";
			
		
	}	
	
	private void makeUpdateModel(FormUnitbunruim formUnitbunruim, Model model) {

		model.addAttribute("id", formUnitbunruim.getId());
		formUnitbunruim.setNewUnitbunruim(false);
		model.addAttribute("formUnitbunruim", formUnitbunruim);
		model.addAttribute("title", "更新用フォーム");
	
	}
	
	
	@SuppressWarnings("deprecation")
	@PostMapping("/update")
	public String update(@Validated FormUnitbunruim formUnitbunruim, 
			BindingResult Result, 
			Model model, 
			RedirectAttributes redirectAttributes) {

		Unitbunruim unitbunruim = makeUnitbunruim(formUnitbunruim);
		
		if (!Result.hasErrors()) {
			updateUnitbunruim(unitbunruim);
			redirectAttributes.addFlashAttribute("complete", "更新が完了しました。");
			return "redirect:/unitbunruim/" + unitbunruim.getId();
		} else {
			makeUpdateModel(formUnitbunruim, model);
			return "unitbunruim";		
		}		
		
	}

	
	@PostMapping("/delete")
	public String delete(@RequestParam("id") String id,			
			Model model, 			
			RedirectAttributes redirectAttributes) {

		deleteUnitbunruimById(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました。");	
		return "redirect:/unitbunruim";
		
	}	

	

	
	private Unitbunruim makeUnitbunruim(FormUnitbunruim formUnitbunruim) {

		Unitbunruim unitbunruim = new Unitbunruim();
		unitbunruim.setId(formUnitbunruim.getId());
		unitbunruim.setBrname(formUnitbunruim.getBrname());
		
		return unitbunruim;
		
	}
	
	private FormUnitbunruim makeFormUnitbunruim(Unitbunruim unitbunruim) {

		FormUnitbunruim form = new FormUnitbunruim();
		form.setId(unitbunruim.getId());
		form.setBrname(unitbunruim.getBrname());
		
		form.setNewUnitbunruim(false);
		return form;
	
	}

	
	public void insertUnitbunruim(Unitbunruim unitbunruim) {
		repository.saveAndFlush(unitbunruim);
	}
	

	public void updateUnitbunruim(Unitbunruim unitbunruim) {
		repository.saveAndFlush(unitbunruim);
	}
	

	public void deleteUnitbunruimById(Integer id) {
		repository.deleteById(id);
	}
	
//	public void selectAll(Unitbunruim unitbunruim) {
	public Iterable<Unitbunruim> selectAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}	
	
	public Optional<Unitbunruim> selectOneById(Integer id) {
		return repository.findById(id);
	}		
	
}

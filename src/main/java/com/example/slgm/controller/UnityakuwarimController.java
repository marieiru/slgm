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


import com.example.slgm.entity.Unityakuwarim;

import com.example.slgm.form.FormUnityakuwarim;

import com.example.slgm.repository.SlgmRepositoryUnityakuwari;



@Controller
@RequestMapping("/unityakuwarim")
public class UnityakuwarimController {

	@Autowired
	SlgmRepositoryUnityakuwari repository;
	
	
    @ModelAttribute
    public FormUnityakuwarim setForm() {
        return new FormUnityakuwarim();
    }
    
    
	@GetMapping("/unityakuwarim")
	public String unityakuwarim() {
		return "unityakuwarim";
	}   	
	
	
	

	@GetMapping
	public String showList(FormUnityakuwarim formUnityakuwarim, Model model) {

		formUnityakuwarim.setNewUnityakuwarim(true);
		Iterable<Unityakuwarim> list = selectAll();
		
		model.addAttribute("list",list);
		model.addAttribute("title","登録用フォーム");
		return "unityakuwarim";
		
	}

	
	@PostMapping("/insert")
	public String insert( @Validated FormUnityakuwarim formUnityakuwarim, 
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		Unityakuwarim unityakuwarim = new Unityakuwarim();
		unityakuwarim.setYwname(formUnityakuwarim.getYwname());

		if (!bindingResult.hasErrors()) {
			
			insertUnityakuwarim(unityakuwarim);
			redirectAttributes.addFlashAttribute("complete", "登録が完了しました。");
			return "redirect:/unityakuwarim";
		} else {
			return showList(formUnityakuwarim, model);			
		}		
		
	
	}
	

	@GetMapping("/{id}")
	public String showUpdatet(FormUnityakuwarim formUnityakuwarim, @PathVariable Integer id, Model model) {

		Optional<Unityakuwarim> unityakuwarimOpt= selectOneById(id);
		
		Optional<FormUnityakuwarim> unityakuwarimFormOpt= unityakuwarimOpt.map(t -> makeFormUnityakuwarim(t));
		
		if (unityakuwarimFormOpt.isPresent()) {
			formUnityakuwarim = unityakuwarimFormOpt.get();
		}
		
		makeUpdateModel(formUnityakuwarim, model);
		return "unityakuwarim";
			
		
	}	
	
	private void makeUpdateModel(FormUnityakuwarim formUnityakuwarim, Model model) {

		model.addAttribute("id", formUnityakuwarim.getId());
		formUnityakuwarim.setNewUnityakuwarim(false);
		model.addAttribute("formUnityakuwarim", formUnityakuwarim);
		model.addAttribute("title", "更新用フォーム");
	
	}
	
	
	@SuppressWarnings("deprecation")
	@PostMapping("/update")
	public String update(@Validated FormUnityakuwarim formUnityakuwarim, 
			BindingResult Result, 
			Model model, 
			RedirectAttributes redirectAttributes) {

		Unityakuwarim unityakuwarim = makeUnityakuwarim(formUnityakuwarim);
		
		if (!Result.hasErrors()) {
			updateUnityakuwarim(unityakuwarim);
			redirectAttributes.addFlashAttribute("complete", "更新が完了しました。");
			return "redirect:/unityakuwarim/" + unityakuwarim.getId();
		} else {
			makeUpdateModel(formUnityakuwarim, model);
			return "unityakuwarim";		
		}		
		
	}

	
	@PostMapping("/delete")
	public String delete(@RequestParam("id") String id,			
			Model model, 			
			RedirectAttributes redirectAttributes) {

		deleteUnityakuwarimById(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました。");	
		return "redirect:/unityakuwarim";
		
	}	

	

	
	private Unityakuwarim makeUnityakuwarim(FormUnityakuwarim formUnityakuwarim) {

		Unityakuwarim unityakuwarim = new Unityakuwarim();
		unityakuwarim.setId(formUnityakuwarim.getId());
		unityakuwarim.setYwname(formUnityakuwarim.getYwname());
		
		return unityakuwarim;
		
	}
	
	private FormUnityakuwarim makeFormUnityakuwarim(Unityakuwarim unityakuwarim) {

		FormUnityakuwarim form = new FormUnityakuwarim();
		form.setId(unityakuwarim.getId());
		form.setYwname(unityakuwarim.getYwname());
		
		form.setNewUnityakuwarim(false);
		return form;
	
	}

	
	public void insertUnityakuwarim(Unityakuwarim unityakuwarim) {
		repository.saveAndFlush(unityakuwarim);
	}
	

	public void updateUnityakuwarim(Unityakuwarim unityakuwarim) {
		repository.saveAndFlush(unityakuwarim);
	}
	

	public void deleteUnityakuwarimById(Integer id) {
		repository.deleteById(id);
	}
	
	public Iterable<Unityakuwarim> selectAll() {

		return	repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}	
	
	public Optional<Unityakuwarim> selectOneById(Integer id) {
		return repository.findById(id);
	}		
	
}

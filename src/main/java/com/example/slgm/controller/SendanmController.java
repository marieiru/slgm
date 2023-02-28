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


import com.example.slgm.entity.Sendanm;
import com.example.slgm.form.FormSendanm;

import com.example.slgm.repository.SlgmRepositorySendan;

@Controller
@RequestMapping("/sendanm")
public class SendanmController {

	@Autowired
	SlgmRepositorySendan repository;
	
	
    @ModelAttribute
    public FormSendanm setForm() {
        return new FormSendanm();
    }
    
    
	@GetMapping("/sendanm")
	public String sendanm() {
		return "sendanm";
	}   	
	
	
	

	@GetMapping
	public String showList(FormSendanm formSendanm, Model model) {

		formSendanm.setNewSendanm(true);
		Iterable<Sendanm> list = selectAll();
		
		model.addAttribute("list",list);
		model.addAttribute("title","登録用フォーム");
		return "sendanm";
		
	}

	
	@PostMapping("/insert")
	public String insert( @Validated FormSendanm formSendanm, 
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		Sendanm sendanm = new Sendanm();
		sendanm.setFname(formSendanm.getFname());

		if (!bindingResult.hasErrors()) {
			
		//	repository.saveAndFlush(sendanm);
			insertSendanm(sendanm);
			redirectAttributes.addFlashAttribute("complete", "登録が完了しました。");
			return "redirect:/sendanm";
		} else {
			return showList(formSendanm, model);			
		}		
		
	
	}
	 

	@GetMapping("/{id}")
	public String showUpdatet(FormSendanm formSendanm, @PathVariable Integer id, Model model) {

		Optional<Sendanm> sendanmOpt= selectOneById(id);
		
		Optional<FormSendanm> sendanmFormOpt= sendanmOpt.map(t -> makeFormSendanm(t));
		
		if (sendanmFormOpt.isPresent()) {
			formSendanm = sendanmFormOpt.get();
		}
		
		makeUpdateModel(formSendanm, model);
		return "sendanm";
			
		
	}	
	
	private void makeUpdateModel(FormSendanm formSendanm, Model model) {

		model.addAttribute("id", formSendanm.getId());
		formSendanm.setNewSendanm(false);
		model.addAttribute("formSendanm", formSendanm);
		model.addAttribute("title", "更新用フォーム");
	
	}
	
	
	@SuppressWarnings("deprecation")
	@PostMapping("/update")
	public String update(@Validated FormSendanm formSendanm, 
			BindingResult Result, 
			Model model, 
			RedirectAttributes redirectAttributes) {

		Sendanm sendanm = makeSendanm(formSendanm);
		
		if (!Result.hasErrors()) {
			updateSendanm(sendanm);
			redirectAttributes.addFlashAttribute("complete", "更新が完了しました。");
			return "redirect:/sendanm/" + sendanm.getId();
		} else {
			makeUpdateModel(formSendanm, model);
			return "sendanm";		
		}		
		
	}

	
	@PostMapping("/delete")
	public String delete(@RequestParam("id") String id,			
			Model model, 			
			RedirectAttributes redirectAttributes) {

		deleteSendanmById(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました。");	
		return "redirect:/sendanm";
		
	}	

	

	
	private Sendanm makeSendanm(FormSendanm formSendanm) {

		Sendanm sendanm = new Sendanm();
		sendanm.setId(formSendanm.getId());
		sendanm.setFname(formSendanm.getFname());
		
		return sendanm;
		
	}
	
	private FormSendanm makeFormSendanm(Sendanm sendanm) {

		FormSendanm form = new FormSendanm();
		form.setId(sendanm.getId());
		form.setFname(sendanm.getFname());
		
		form.setNewSendanm(false);
		return form;
	
	}

    //保存
    public void insertSendanm(Sendanm sendanm) {
         repository.saveAndFlush(sendanm);
    }
	
	/**
	public void insertSendanm(Sendanm sendanm) {
		repository.save(sendanm);
	}
	

    //保存
    public BookData save(BookData bookData) {
        return bookDataRepository.saveAndFlush(bookData);
    }
	**/
    
	public void updateSendanm(Sendanm sendanm) {
		repository.saveAndFlush(sendanm);
	}
	

	public void deleteSendanmById(Integer id) {
		repository.deleteById(id);
	}
	
	public Iterable<Sendanm> selectAll() {
		return	repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}	
	
	public Optional<Sendanm> selectOneById(Integer id) {
		return repository.findById(id);
	}	
	
}

package com.example.slgm.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.slgm.entity.Bukim;

import com.example.slgm.form.FormBukim;

import com.example.slgm.repository.SlgmRepositoryBuki;
import com.example.slgm.repository.SlgmRepositorySendan;
import com.example.slgm.entity.Unitbunruim;
import com.example.slgm.form.FormUnitbunruim;
import com.example.slgm.repository.SlgmRepositoryUnitbunrui;


@Controller
@RequestMapping("/bukim")
public class BukimController {

	@Autowired
	SlgmRepositoryBuki repository;
	
	@Autowired
	SlgmRepositorySendan categoryRepository;	
	
	@ModelAttribute
	public FormBukim setForm() {
		return new FormBukim();
	}

	@GetMapping("/index")
	public String bukim(FormBukim formBukim,Model model) {

		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		model.addAttribute("bukiList", repository.findAll(Sort.by(Sort.Direction.ASC, "wno")));
		
		formBukim.setNewBukim(true);
		return "bukim";
	}

	@GetMapping
	public String showList(FormBukim formBukim, Model model) {

		formBukim.setNewBukim(true);
		Iterable<Bukim> list = selectAll();

		model.addAttribute("list", list);
		model.addAttribute("title", "登録用フォーム");
		return "bukim";

	}

	@PostMapping("/insert")
	public String insert(@Validated FormBukim formBukim, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		Bukim bukim = new Bukim();
		bukim.setWname(formBukim.getWname());
		bukim.setSyubetu(formBukim.getSyubetu());
		bukim.setSyatei(formBukim.getSyatei());		
		bukim.setAt(formBukim.getAt());		
		bukim.setAp(formBukim.getAp());		
		bukim.setDmg(formBukim.getDmg());
		
		bukim.setKs(formBukim.getKs());
		
		bukim.setAbl(formBukim.getAbl());		
		
		bukim.setSid(formBukim.getSid());		
		
		bukim.setHit(formBukim.getHit());
		
		if (!bindingResult.hasErrors()) {

			insertBukim(bukim);
			redirectAttributes.addFlashAttribute("complete", "登録が完了しました。");
			return "redirect:/bukim/index";
		} else {
			return showList(formBukim, model);
		}

	}

	@GetMapping("/{wno}")
	public String showUpdatet(FormBukim formBukim, @PathVariable Integer wno, Model model) {

		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		Optional<Bukim> bukimOpt = selectOneById(wno);

		Optional<FormBukim> bukimFormOpt = bukimOpt.map(t -> makeFormBukim(t));

		if (bukimFormOpt.isPresent()) {
			formBukim = bukimFormOpt.get();
		}

		makeUpdateModel(formBukim, model);
		return "bukim";

	}

	private void makeUpdateModel(FormBukim formBukim, Model model) {

		model.addAttribute("wno", formBukim.getWno());
		formBukim.setNewBukim(false);
		model.addAttribute("formBukim", formBukim);
		model.addAttribute("title", "更新用フォーム");

	}

	@SuppressWarnings("deprecation")
	@PostMapping("/update")
	public String update(@Validated FormBukim formBukim, BindingResult Result, Model model,
			RedirectAttributes redirectAttributes) {

		Bukim bukim = makeBukim(formBukim);

		if (!Result.hasErrors()) {
			updateBukim(bukim);
			redirectAttributes.addFlashAttribute("complete", "更新が完了しました。");
			return "redirect:/bukim/" + bukim.getWno();
		} else {
			makeUpdateModel(formBukim, model);
			return "bukim";
		}

	}

	@PostMapping("/delete")
	public String delete(@RequestParam("wno") String wno, Model model, RedirectAttributes redirectAttributes) {

		deleteBukimById(Integer.parseInt(wno));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました。");
		return "redirect:/bukim/index";

	}

	private Bukim makeBukim(FormBukim formBukim) {

		Bukim bukim = new Bukim();
		bukim.setWno(formBukim.getWno());
		
		bukim.setWname(formBukim.getWname());
		bukim.setSyubetu(formBukim.getSyubetu());
		bukim.setSyatei(formBukim.getSyatei());		
		bukim.setAt(formBukim.getAt());		
		bukim.setAp(formBukim.getAp());		
		bukim.setDmg(formBukim.getDmg());
		
		bukim.setKs(formBukim.getKs());
		
		bukim.setAbl(formBukim.getAbl());	
		
		bukim.setSid(formBukim.getSid());	
		
		bukim.setHit(formBukim.getHit());

		return bukim;

	}

	private FormBukim makeFormBukim(Bukim bukim) {

		FormBukim form = new FormBukim();
		form.setWno(bukim.getWno());
		form.setWname(bukim.getWname());

		form.setSyubetu(bukim.getSyubetu());
		form.setSyatei(bukim.getSyatei());		
		form.setAt(bukim.getAt());		
		form.setAp(bukim.getAp());		
		form.setDmg(bukim.getDmg());
		
		form.setKs(bukim.getKs());
		
		form.setAbl(bukim.getAbl());	
		
		form.setSid(bukim.getSid());
		
		form.setHit(bukim.getHit());		
		
		form.setNewBukim(false);
		return form;

	}

	public void insertBukim(Bukim bukim) {
		repository.save(bukim);
	}

	public void updateBukim(Bukim bukim) {
		repository.save(bukim);
	}

	public void deleteBukimById(Integer wno) {
		repository.deleteById(wno);
	}

	public Iterable<Bukim> selectAll() {

		return repository.findAll(Sort.by(Sort.Direction.ASC, "wno"));
	}

	public Optional<Bukim> selectOneById(Integer wno) {
		return repository.findById(wno);
	}

}

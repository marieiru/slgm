package com.example.slgm.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.slgm.entity.Unitrekka;
import com.example.slgm.form.FormUnitrekka;
import com.example.slgm.repository.SlgmRepositoryUnitrekka;

import com.example.slgm.repository.SlgmRepositoryUnitrekka_h;
import com.example.slgm.entity.rekka;
import com.example.slgm.repository.FindunitjyouhouRepository;
import com.example.slgm.repository.SlgmRepositoryUnitjyouhou;
import com.example.slgm.entity.Bukim;
import com.example.slgm.entity.Unitjyouhou;


@Controller
@RequestMapping("/unitrekka")
public class UnitrekkaController {

	@Autowired
	SlgmRepositoryUnitrekka repository;

	
	@Autowired
	SlgmRepositoryUnitjyouhou jyouhourepository;	

	@Autowired
	FindunitjyouhouRepository findunitjyouhou;			
	
	
	@ModelAttribute
	public FormUnitrekka setForm() {
		return new FormUnitrekka();
	}

	@GetMapping("/index")
	public String Unitrekka(FormUnitrekka formUnitrekka, Model model) {
		model.addAttribute("unitList", jyouhourepository.findAll());
		
		model.addAttribute("rekkaList", repository.findAll());
		formUnitrekka.setNewUnitrekka(true);
		return "unitrekka";
	}

	@GetMapping
	public String showList(FormUnitrekka formUnitrekka, Model model) {

		formUnitrekka.setNewUnitrekka(true);
		Iterable<Unitrekka> list = selectAll();

		model.addAttribute("list", list);
		model.addAttribute("title", "登録用フォーム");
		return "unitrekka";

	}

	@PostMapping("/insert")
	public String insert(@Validated FormUnitrekka formUnitrekka, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		
		Unitrekka Unitrekka = new Unitrekka();
		Unitrekka.setZid(formUnitrekka.getZid());
		Unitrekka.setKuunz(formUnitrekka.getKuunz());
		Unitrekka.setMv(formUnitrekka.getMv());		
		Unitrekka.setAss(formUnitrekka.getAss());		
		Unitrekka.setScs(formUnitrekka.getScs());		
		
		//sid 取得
        List<Unitjyouhou> result_sid = search_sid(formUnitrekka.getZid());
        Unitrekka.setSid(result_sid.get(0).getSid());	
		
        //yid 取得
        Unitrekka.setYid(result_sid.get(0).getYid());	
        
		
		if (!bindingResult.hasErrors()) {

			insertUnitrekka(Unitrekka);
			redirectAttributes.addFlashAttribute("complete", "登録が完了しました。");
			return "redirect:/unitrekka/index";
		} else {
			return showList(formUnitrekka, model);
		}

	}

	public List<Unitjyouhou> search_sid(Integer zid){

        List<Unitjyouhou> result = new ArrayList<Unitjyouhou>();

        //すべてブランクだった場合は全件検索する
     //   if ("".equals(genre) && "".equals(author) && "".equals(title)){
        if ((zid)==0){
        	result = jyouhourepository.findAll(Sort.by(Sort.Direction.ASC, "zid"));
        }
        else {
            //上記以外の場合、BookDataDaoImplのメソッドを呼び出す
            result = findunitjyouhou.search_sid(zid);
        }
        return result;
    }		
	
	
	@GetMapping("/{id}")
	public String showUpdatet(FormUnitrekka formUnitrekka, @PathVariable Integer id, Model model) {
	
		model.addAttribute("unitList", jyouhourepository.findAll());

		Optional<Unitrekka> UnitrekkaOpt = selectOneById(id);

		Optional<FormUnitrekka> UnitrekkaFormOpt = UnitrekkaOpt.map(t -> makeFormUnitrekka(t));

		if (UnitrekkaFormOpt.isPresent()) {
			formUnitrekka = UnitrekkaFormOpt.get();
		}

		makeUpdateModel(formUnitrekka, model);
		return "unitrekka";

	}

	
	private void makeUpdateModel(FormUnitrekka formUnitrekka, Model model) {

		model.addAttribute("id", formUnitrekka.getId());
		formUnitrekka.setNewUnitrekka(false);
		model.addAttribute("formUnitrekka", formUnitrekka);
		model.addAttribute("title", "更新用フォーム");

	}

	@SuppressWarnings("deprecation")
	@PostMapping("/update")
	public String update(@Validated FormUnitrekka formUnitrekka, BindingResult Result, Model model,
			RedirectAttributes redirectAttributes) {
		
		Unitrekka Unitrekka = makeUnitrekka(formUnitrekka);

		if (!Result.hasErrors()) {
			updateUnitrekka(Unitrekka);
			redirectAttributes.addFlashAttribute("complete", "更新が完了しました。");
			return "redirect:/unitrekka/" + Unitrekka.getId();
		} else {
			makeUpdateModel(formUnitrekka, model);
			return "unitrekka";
		}

	}

	@PostMapping("/delete")
	public String delete(@RequestParam("id") String id, Model model, RedirectAttributes redirectAttributes) {

		deleteUnitrekkaById(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました。");
		return "redirect:/unitrekka/index";

	}

	private Unitrekka makeUnitrekka(FormUnitrekka formUnitrekka) {

		Unitrekka Unitrekka = new Unitrekka();
		
		Unitrekka.setId(formUnitrekka.getId());
		Unitrekka.setZid(formUnitrekka.getZid());
		Unitrekka.setKuunz(formUnitrekka.getKuunz());
		Unitrekka.setMv(formUnitrekka.getMv());		
		Unitrekka.setAss(formUnitrekka.getAss());		
		Unitrekka.setScs(formUnitrekka.getScs());				
		

        List<Unitjyouhou> result_sid = search_sid(formUnitrekka.getZid());
        Unitrekka.setSid(result_sid.get(0).getSid());	
		
        //yid 取得
        Unitrekka.setYid(result_sid.get(0).getYid());			
		
		return Unitrekka;

	}

	private FormUnitrekka makeFormUnitrekka(Unitrekka Unitrekka) {

		FormUnitrekka form = new FormUnitrekka();

		form.setId(Unitrekka.getId());
		form.setZid(Unitrekka.getZid());
		form.setKuunz(Unitrekka.getKuunz());
		form.setMv(Unitrekka.getMv());		
		form.setAss(Unitrekka.getAss());		
		form.setScs(Unitrekka.getScs());						
		
		form.setNewUnitrekka(false);
		return form;

	}

	public void insertUnitrekka(Unitrekka Unitrekka) {
		repository.saveAndFlush(Unitrekka);
	}

	public void updateUnitrekka(Unitrekka Unitrekka) {
		repository.saveAndFlush(Unitrekka);
	}

	public void deleteUnitrekkaById(Integer id) {
		repository.deleteById(id);
	}

	public Iterable<Unitrekka> selectAll() {

		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	public Optional<Unitrekka> selectOneById(Integer id) {
		return repository.findById(id);
	}	
		
	
		
	
	
	
}

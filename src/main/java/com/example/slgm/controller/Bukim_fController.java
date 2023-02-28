package com.example.slgm.controller;

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

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.slgm.entity.Bukim;

import com.example.slgm.form.FormBukim;
import com.example.slgm.repository.FindbukimRepository;
import com.example.slgm.repository.SlgmRepositoryBuki;
import com.example.slgm.repository.SlgmRepositorySendan;



@Controller
@RequestMapping("/bukim_f")
public class Bukim_fController {

	@Autowired
	SlgmRepositoryBuki repository;
	
	@Autowired
	SlgmRepositorySendan categoryRepository;	
	
	@Autowired
	SlgmRepositoryBuki bukirepository;	    
  
	@Autowired
    FindbukimRepository Findbuki;	
	
	 @GetMapping("/select")
    public String select(@ModelAttribute("formModel") Bukim bukim, Model model) {

        model.addAttribute("msg", "検索結果");
		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));

       
        List<Bukim> result_b = search_buki(bukim.getSid(),2);
		// result_sid.get("sid")
        model.addAttribute("bukiList", result_b);       
        
        
        return "bukim_f";
    }	
	

    public List<Bukim> search_buki(Integer sid,Integer h){

        List<Bukim> result = new ArrayList<Bukim>();

        //すべてブランクだった場合は全件検索する
     //   if ("".equals(genre) && "".equals(author) && "".equals(title)){
        if ((sid)==0){
        	result = bukirepository.findAll(Sort.by(Sort.Direction.ASC, "wno"));
        }
        else {
            //上記以外の場合、BookDataDaoImplのメソッドを呼び出す
            result = Findbuki.search(sid,h);
        }
        return result;
    }		

	
	@ModelAttribute
	public FormBukim setForm() {
		return new FormBukim();
	}

	@GetMapping("/index")
	public String bukim(FormBukim formBukim,Model model) {

		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		model.addAttribute("bukiList", repository.findAll(Sort.by(Sort.Direction.ASC, "wno")));
		
		formBukim.setNewBukim(true);
		return "bukim_f";
	}

	@GetMapping
	public String showList(FormBukim formBukim, Model model) {

		formBukim.setNewBukim(true);
		Iterable<Bukim> list = selectAll();

		model.addAttribute("list", list);
		model.addAttribute("title", "登録用フォーム");
		return "bukim_f";
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
			return "redirect:/bukim_f/index";
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
		return "bukim_f";

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
			return "redirect:/bukim_f/" + bukim.getWno();
		} else {
			makeUpdateModel(formBukim, model);
			return "bukim_f";
		}

	}

	@PostMapping("/delete")
	public String delete(@RequestParam("wno") String wno, Model model, RedirectAttributes redirectAttributes) {

		deleteBukimById(Integer.parseInt(wno));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました。");
		return "redirect:/bukim_f/index";

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

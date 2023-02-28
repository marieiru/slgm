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


import com.example.slgm.entity.Unitjyouhou;
import com.example.slgm.entity.Unitkousei;
import com.example.slgm.form.FormUnitjyouhou;
import com.example.slgm.repository.SlgmRepositoryUnitjyouhou;

import com.example.slgm.AppConfig;

import com.example.slgm.repository.SlgmRepositorySendan;
import com.example.slgm.repository.SlgmRepositoryUnitbunrui;
import com.example.slgm.repository.SlgmRepositoryUnityakuwari;
import com.example.slgm.repository.FindunitjyouhouRepository;

@Controller
@RequestMapping("/unitjyouhou")
public class UnitjyouhouController {
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	SlgmRepositoryUnitjyouhou repository;
	
	@Autowired
	SlgmRepositorySendan categoryRepository;	
	

	@Autowired
	SlgmRepositoryUnitbunrui unitbunruiRepository;		
	
	@Autowired
	SlgmRepositoryUnityakuwari unityakuwariRepository;		

	
	
	
	
	@PostMapping(value="/upload")
	public String upload(@RequestParam() MultipartFile file, Model model, FormUnitjyouhou formUnitjyouhou) {
		if(file.isEmpty()) {
			model.addAttribute("error", "ファイルを指定してください");
			return "redirect:/unitjyouhou";
		} else {
		}
	
		File dest = new File(appConfig.getImageDir(), file.getOriginalFilename());
		try {
			file.transferTo(dest); //表示される修正候補の「try/catchで囲む」を選択
//			quizForm.setImg(dest.getPath()); //IMGのパスをquizFormに写す
		} catch (IllegalStateException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return "redirect:/unitjyouhou/index";
		
	}		
	
	
	@ModelAttribute
	public FormUnitjyouhou setForm() {
		return new FormUnitjyouhou();
	}

	@GetMapping("/index")
	public String unitjyouhou(FormUnitjyouhou formUnitjyouhou,Model model) {
		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
//		model.addAttribute("unitbunrui", unitbunruiRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unityakuwari", unityakuwariRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		model.addAttribute("jyouhouList", repository.findAll(Sort.by(Sort.Direction.ASC, "zid")));
		formUnitjyouhou.setNewUnitjyouhou(true);
		return "unitjyouhou";
	}

	@GetMapping
	public String showList(FormUnitjyouhou formUnitjyouhou, Model model) {

		formUnitjyouhou.setNewUnitjyouhou(true);
		Iterable<Unitjyouhou> list = selectAll();

		model.addAttribute("list", list);
		model.addAttribute("title", "登録用フォーム");
		return "unitjyouhou";

	}

	@PostMapping("/insert")
	public String insert(@RequestParam() MultipartFile file, 
			@Validated FormUnitjyouhou formUnitjyouhou, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unityakuwari", unityakuwariRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));		
		
		
		Unitjyouhou unitjyouhou = new Unitjyouhou();
		unitjyouhou.setYuname(formUnitjyouhou.getYuname());
		
		unitjyouhou.setSid(formUnitjyouhou.getSid());

		unitjyouhou.setYid(formUnitjyouhou.getYid());	
		
		unitjyouhou.setPck(formUnitjyouhou.getPck());
		unitjyouhou.setMck(formUnitjyouhou.getMck());	
		unitjyouhou.setNinnzuu(formUnitjyouhou.getNinnzuu());		
		unitjyouhou.setZouin(formUnitjyouhou.getZouin());
		unitjyouhou.setSousuu(formUnitjyouhou.getSousuu());
		unitjyouhou.setKbzouin(formUnitjyouhou.getKbzouin());
//		unitjyouhou.setImg(formUnitjyouhou.getImg());

		unitjyouhou.setImg(file.getOriginalFilename()); //IMGのパスを写す		
		
		upload(file,model,formUnitjyouhou);		
		
		if (!bindingResult.hasErrors()) {

			insertUnitjyouhou(unitjyouhou);
			redirectAttributes.addFlashAttribute("complete", "登録が完了しました。");
			return "redirect:/unitjyouhou/index";
		} else {
			return showList(formUnitjyouhou, model);
		}

	}

	@GetMapping("/{zid}")
	public String showUpdatet(FormUnitjyouhou formUnitjyouhou, @PathVariable Integer zid, Model model) {

		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
//		model.addAttribute("unitbunrui", unitbunruiRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unityakuwari", unityakuwariRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));		
		
		
		Optional<Unitjyouhou> unitjyouhouOpt = selectOneById(zid);

		Optional<FormUnitjyouhou> unitjyouhouFormOpt = unitjyouhouOpt.map(t -> makeFormUnitjyouhou(t));

		if (unitjyouhouFormOpt.isPresent()) {
			formUnitjyouhou = unitjyouhouFormOpt.get();
		}

		makeUpdateModel(formUnitjyouhou, model);
		return "unitjyouhou";

	}

	private void makeUpdateModel(FormUnitjyouhou formUnitjyouhou, Model model) {

		model.addAttribute("zid", formUnitjyouhou.getZid());
		formUnitjyouhou.setNewUnitjyouhou(false);
		model.addAttribute("formUnitjyouhou", formUnitjyouhou);
		model.addAttribute("title", "更新用フォーム");

	}

	@SuppressWarnings("deprecation")
	@PostMapping("/update")
	public String update(@RequestParam() MultipartFile file,
			@Validated FormUnitjyouhou formUnitjyouhou, BindingResult Result, Model model,
			RedirectAttributes redirectAttributes) {

		if ((file.getOriginalFilename()!= null) && (file.getSize()!= 0)) {
			formUnitjyouhou.setImg(file.getOriginalFilename());
			upload(file,model,formUnitjyouhou);	
		} else {
			
		};		
		
		
		Unitjyouhou unitjyouhou = makeUnitjyouhou(formUnitjyouhou);

		if (!Result.hasErrors()) {
			updateUnitjyouhou(unitjyouhou);
			redirectAttributes.addFlashAttribute("complete", "更新が完了しました。");
			return "redirect:/unitjyouhou/" + unitjyouhou.getZid();
		} else {
			makeUpdateModel(formUnitjyouhou, model);
			return "unitjyouhou";
		}

	}

	@PostMapping("/delete")
	public String delete(@RequestParam("zid") String zid, Model model, RedirectAttributes redirectAttributes) {

		deleteUnitjyouhouById(Integer.parseInt(zid));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました。");
		return "redirect:/unitjyouhou/index";

	}

	private Unitjyouhou makeUnitjyouhou(FormUnitjyouhou formUnitjyouhou) {

		Unitjyouhou unitjyouhou = new Unitjyouhou();
		
		unitjyouhou.setZid(formUnitjyouhou.getZid());

		unitjyouhou.setYuname(formUnitjyouhou.getYuname());
		
		unitjyouhou.setSid(formUnitjyouhou.getSid());
		
		unitjyouhou.setYid(formUnitjyouhou.getYid());		
		
		unitjyouhou.setPck(formUnitjyouhou.getPck());		
		unitjyouhou.setMck(formUnitjyouhou.getMck());	
		unitjyouhou.setNinnzuu(formUnitjyouhou.getNinnzuu());		
		unitjyouhou.setZouin(formUnitjyouhou.getZouin());
		unitjyouhou.setSousuu(formUnitjyouhou.getSousuu());
		unitjyouhou.setKbzouin(formUnitjyouhou.getKbzouin());
		unitjyouhou.setImg(formUnitjyouhou.getImg());		
		
		return unitjyouhou;

	}

	private FormUnitjyouhou makeFormUnitjyouhou(Unitjyouhou unitjyouhou) {

		FormUnitjyouhou form = new FormUnitjyouhou();

		form.setZid(unitjyouhou.getZid());
	
		form.setYuname(unitjyouhou.getYuname());
		
		form.setSid(unitjyouhou.getSid());
		
		form.setYid(unitjyouhou.getYid());	
		
		form.setPck(unitjyouhou.getPck());		
		form.setMck(unitjyouhou.getMck());	
		form.setNinnzuu(unitjyouhou.getNinnzuu());		
		form.setZouin(unitjyouhou.getZouin());
		form.setSousuu(unitjyouhou.getSousuu());
		form.setKbzouin(unitjyouhou.getKbzouin());
		form.setImg(unitjyouhou.getImg());				
		
		form.setNewUnitjyouhou(false);
		return form;

	}

	public void insertUnitjyouhou(Unitjyouhou unitjyouhou) {
		repository.saveAndFlush(unitjyouhou);
	}

	public void updateUnitjyouhou(Unitjyouhou unitjyouhou) {
		repository.saveAndFlush(unitjyouhou);
	}

	public void deleteUnitjyouhouById(Integer zid) {
		repository.deleteById(zid);
	}

	public Iterable<Unitjyouhou> selectAll() {

		return repository.findAll(Sort.by(Sort.Direction.ASC, "zid"));
	}

	public Optional<Unitjyouhou> selectOneById(Integer zid) {
		return repository.findById(zid);
	}	
		
	
	
	
}

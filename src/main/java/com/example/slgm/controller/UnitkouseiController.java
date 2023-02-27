package com.example.slgm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import com.example.slgm.entity.Unitjyouhou;
import com.example.slgm.entity.Unitkousei;
import com.example.slgm.form.FormUnitkousei;
import com.example.slgm.repository.SlgmRepositoryUnitkousei;
import com.example.slgm.repository.FindkouseiimpRepository;
import com.example.slgm.repository.FindunitjyouhouRepository;
//import com.example.slgm.repository.SlgmRepositorySendan;
import com.example.slgm.repository.SlgmRepositoryUnitbunrui;
import com.example.slgm.repository.SlgmRepositoryUnitjyouhou;
import com.example.slgm.repository.SlgmRepositoryBuki;
import com.example.slgm.repository.FindbukimRepository;

@Controller
@RequestMapping("/unitkousei")
public class UnitkouseiController {

	@Autowired
	SlgmRepositoryUnitkousei kouseirepository;
	
//	@Autowired
//	SlgmRepositorySendan categoryRepository;		
	
	@Autowired
	SlgmRepositoryUnitbunrui unitbunruiRepository;			

	@Autowired
	SlgmRepositoryBuki bukiRepository;			
	
	@Autowired
	FindbukimRepository findbukiRepository;	
	
	//検索関連		
	@PersistenceContext
	EntityManager entityManager;	

	@Autowired
	FindunitjyouhouRepository findunitjyouhou;			
	
    @Autowired
    FindkouseiimpRepository findkouseiimpRepository;		
	
	@Autowired
	SlgmRepositoryBuki bukirepository;	    
  
	@Autowired
    FindbukimRepository Findbuki;		   	

	@Autowired
	SlgmRepositoryUnitjyouhou repository;	
	

//検索関連	
	
	
	@ModelAttribute
	public FormUnitkousei setForm() {
		return new FormUnitkousei();
	}

	@GetMapping("/index")
	public String unitkousei(FormUnitkousei formUnitkousei, Model model) {
/**
		model.addAttribute("unitbunrui", unitbunruiRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		
//ここから武器検索		
		model.addAttribute("bukiRepository", bukiRepository.findAll(Sort.by(Sort.Direction.ASC, "wno")));
		
		model.addAttribute("kouseiList", kouseirepository.findAll());

		
		formUnitkousei.setNewUnitkousei(true);
		
		model.addAttribute("unitbunrui", unitbunruiRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		formUnitkousei.setZid(zid);
		model.addAttribute("formUnitkousei", formUnitkousei);		
		
        List<Unitkousei> result = search_kousei(zid);
		
        model.addAttribute("kouseiList", result);

        //武器で戦団をもとに抽出	
        List<Unitjyouhou> result_sid = search_sid(zid);
        
        
        List<Bukim> result_b = search_buki(result_sid.get(0).getSid(),0);
		// result_sid.get("sid")
        model.addAttribute("bukiList", result_b);       

        List<Bukim> result_h = search_buki(result_sid.get(0).getSid(),1);
		// result_sid.get("sid")
        model.addAttribute("bukiList3", result_h);        
        formUnitkousei.setNewUnitkousei(true);		
		
		
		
		
		
		
		**/
		
		return "unitkousei";
	}

	@GetMapping
	public String showList(FormUnitkousei formUnitkousei, Model model) {

		formUnitkousei.setNewUnitkousei(true);
		Iterable<Unitkousei> list = selectAll();

		model.addAttribute("list", list);
		model.addAttribute("title", "登録用フォーム");
		
		model.addAttribute("unitbunrui", unitbunruiRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));

        //武器で戦団をもとに抽出	
        List<Unitjyouhou> result_sid = search_sid(formUnitkousei.getZid());
        List<Bukim> result_b = search_buki(result_sid.get(0).getSid(),0);
        model.addAttribute("bukiList", result_b);       
        List<Bukim> result_h = search_buki(result_sid.get(0).getSid(),1);
        model.addAttribute("bukiList3", result_h);        
        formUnitkousei.setNewUnitkousei(true);		
		
		return "unitkousei";
		
	}

	@PostMapping("/insert")
	public String insert(@Validated FormUnitkousei formUnitkousei, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		
		if (!bindingResult.hasErrors()) {
			
//			insertUnitkousei(unitkousei);
		    for (int i = 1; i <= formUnitkousei.getKazu(); i++){
		
				Unitkousei unitkousei = new Unitkousei();
				unitkousei.setKname(formUnitkousei.getKname());
				unitkousei.setAt(formUnitkousei.getAt());
				unitkousei.setTaff(formUnitkousei.getTaff());		
				unitkousei.setUunz(formUnitkousei.getUunz());		
				unitkousei.setAk(formUnitkousei.getAk());		
				unitkousei.setLd(formUnitkousei.getLd());	
				unitkousei.setAsa(formUnitkousei.getAsa());		
				unitkousei.setAssp(formUnitkousei.getAssp());
				unitkousei.setAbl(formUnitkousei.getAbl());
				
				unitkousei.setBid(formUnitkousei.getBid());		
				
				unitkousei.setZid(formUnitkousei.getZid());		
				
				unitkousei.setWno(formUnitkousei.getWno());				
				unitkousei.setWno2(formUnitkousei.getWno2());		
				unitkousei.setWno3(formUnitkousei.getWno3());				    	
		    	
				unitkousei.setWno4(formUnitkousei.getWno4());				
				unitkousei.setWno5(formUnitkousei.getWno5());	
				
				unitkousei.setWno6(formUnitkousei.getWno6());				
				unitkousei.setWno7(formUnitkousei.getWno7());				
			
				unitkousei.setMv(formUnitkousei.getMv());		
				
		    	insertUnitkousei(unitkousei);
		      }			
			
			redirectAttributes.addFlashAttribute("complete", "登録が完了しました。");
		//	return "redirect:/unitkousei/index";
			return "redirect:/unitkousei_kai/" + formUnitkousei.getZid();
		} else {
			return showList(formUnitkousei, model);
		}

	}

	@GetMapping("/{kid}")
	public String showUpdatet(FormUnitkousei formUnitkousei, @PathVariable Integer kid, Model model) {

//		model.addAttribute("prefecturesList", categoryRepository.findAll());
		model.addAttribute("unitbunrui", unitbunruiRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
	
	
		
		
		Optional<Unitkousei> unitkouseiOpt = selectOneById(kid);

		Optional<FormUnitkousei> unitkouseiFormOpt = unitkouseiOpt.map(t -> makeFormUnitkousei(t));

		if (unitkouseiFormOpt.isPresent()) {

	        //武器で戦団をもとに抽出	
	        List<Unitjyouhou> result_sid = search_sid(unitkouseiFormOpt.get().getZid());
 	        
	        List<Bukim> result_b = search_buki(result_sid.get(0).getSid(),0);
			// result_sid.get("sid")
	        model.addAttribute("bukiList", result_b);      				

	        List<Bukim> result_h = search_buki(result_sid.get(0).getSid(),1);
			// result_sid.get("sid")
	        model.addAttribute("bukiList3", result_h);        	        
	        
			formUnitkousei = unitkouseiFormOpt.get();
			
		}

		makeUpdateModel(formUnitkousei, model);
		return "unitkousei";

	}

	
    //検索  武器
	public List<Unitjyouhou> search_sid(Integer zid){

        List<Unitjyouhou> result = new ArrayList<Unitjyouhou>();

        //すべてブランクだった場合は全件検索する
     //   if ("".equals(genre) && "".equals(author) && "".equals(title)){
        if ((zid)==0){
        	result = repository.findAll(Sort.by(Sort.Direction.ASC, "zid"));
        }
        else {
            //上記以外の場合、BookDataDaoImplのメソッドを呼び出す
            result = findunitjyouhou.search_sid(zid);
        }
        return result;
    }	
	
	
	
	public List<Bukim> search_buki(Integer sid, Integer h){

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
	
	private void makeUpdateModel(FormUnitkousei formUnitkousei, Model model) {

		model.addAttribute("kid", formUnitkousei.getKid());
		formUnitkousei.setNewUnitkousei(false);
		model.addAttribute("formUnitkousei", formUnitkousei);
		model.addAttribute("title", "更新用フォーム");

	}

	@SuppressWarnings("deprecation")
	@PostMapping("/update")
	public String update(@Validated FormUnitkousei formUnitkousei, BindingResult Result, Model model,
			RedirectAttributes redirectAttributes) {

		Unitkousei unitkousei = makeUnitkousei(formUnitkousei);

		if (!Result.hasErrors()) {
			updateUnitkousei(unitkousei);
			redirectAttributes.addFlashAttribute("complete", "更新が完了しました。");
			return "redirect:/unitkousei/" + unitkousei.getKid();
		} else {
			makeUpdateModel(formUnitkousei, model);
			return "unitkousei/index";
		}

	}

	@PostMapping("/delete")
	public String delete(@RequestParam("kid") String kid,@RequestParam("zid") Integer zid,
			Model model, RedirectAttributes redirectAttributes) {

		deleteUnitkouseiById(Integer.parseInt(kid));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました。");
	//	return "redirect:/unitkousei_kai/index";
		return "redirect:/unitkousei_kai/" + zid;
	}

	private Unitkousei makeUnitkousei(FormUnitkousei formUnitkousei) {

		Unitkousei unitkousei = new Unitkousei();
		unitkousei.setKid(formUnitkousei.getKid());
		unitkousei.setKname(formUnitkousei.getKname());
		unitkousei.setAt(formUnitkousei.getAt());
		unitkousei.setTaff(formUnitkousei.getTaff());		
		unitkousei.setUunz(formUnitkousei.getUunz());		
		unitkousei.setAk(formUnitkousei.getAk());		
		unitkousei.setLd(formUnitkousei.getLd());	
		unitkousei.setAsa(formUnitkousei.getAsa());		
		unitkousei.setAssp(formUnitkousei.getAssp());
		unitkousei.setAbl(formUnitkousei.getAbl());		
		
//		unitkousei.setSid(formUnitkousei.getSid());		
		
		unitkousei.setBid(formUnitkousei.getBid());		
	
		unitkousei.setZid(formUnitkousei.getZid());		
		
		unitkousei.setWno(formUnitkousei.getWno());
		unitkousei.setWno2(formUnitkousei.getWno2());	
		unitkousei.setWno3(formUnitkousei.getWno3());	
		
		unitkousei.setWno4(formUnitkousei.getWno4());				
		unitkousei.setWno5(formUnitkousei.getWno5());	
		
		unitkousei.setWno6(formUnitkousei.getWno6());				
		unitkousei.setWno7(formUnitkousei.getWno7());				
	
		unitkousei.setMv(formUnitkousei.getMv());				
		
		
		return unitkousei;

	}

	private FormUnitkousei makeFormUnitkousei(Unitkousei unitkousei) {

		FormUnitkousei form = new FormUnitkousei();

		form.setKid(unitkousei.getKid());
		form.setKname(unitkousei.getKname());
		form.setAt(unitkousei.getAt());
		form.setTaff(unitkousei.getTaff());		
		form.setUunz(unitkousei.getUunz());		
		form.setAk(unitkousei.getAk());		
		form.setLd(unitkousei.getLd());	
		form.setAsa(unitkousei.getAsa());		
		form.setAssp(unitkousei.getAssp());		
		
		form.setAbl(unitkousei.getAbl());	
		
//		form.setSid(unitkousei.getSid());	
		
		form.setBid(unitkousei.getBid());	
		
		form.setZid(unitkousei.getZid());	
		
		form.setWno(unitkousei.getWno());	
		form.setWno2(unitkousei.getWno2());	
		form.setWno3(unitkousei.getWno3());	
		
		form.setWno4(unitkousei.getWno4());	
		form.setWno5(unitkousei.getWno5());	
		form.setWno6(unitkousei.getWno6());	
		
		form.setWno7(unitkousei.getWno7());	
		
		form.setMv(unitkousei.getMv());		
		
		form.setNewUnitkousei(false);
		return form;

	}

	public void insertUnitkousei(Unitkousei unitkousei) {
		kouseirepository.saveAndFlush(unitkousei);
	}

	public void updateUnitkousei(Unitkousei unitkousei) {
		kouseirepository.saveAndFlush(unitkousei);
	}

	public void deleteUnitkouseiById(Integer kid) {
		kouseirepository.deleteById(kid);
	}

	public Iterable<Unitkousei> selectAll() {

		return kouseirepository.findAll(Sort.by(Sort.Direction.ASC, "kid"));
	}

	public Optional<Unitkousei> selectOneById(Integer kid) {
		return kouseirepository.findById(kid);
	}	
	
	
	
	
	
	
}


package com.example.slgm.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import com.example.slgm.entity.Bukim;

import com.example.slgm.form.FormUnitjyouhou;
import com.example.slgm.repository.SlgmRepositoryUnitjyouhou;

import com.example.slgm.form.FormUnitkousei;
import com.example.slgm.repository.SlgmRepositoryUnitkousei;

import com.example.slgm.form.FormBukim;
import com.example.slgm.repository.SlgmRepositoryBuki;


import com.example.slgm.AppConfig;

import com.example.slgm.repository.SlgmRepositorySendan;
import com.example.slgm.repository.SlgmRepositoryUnitbunrui;
import com.example.slgm.repository.SlgmRepositoryUnityakuwari;
import com.example.slgm.repository.FindkouseiimpRepository;
import com.example.slgm.repository.FindunitjyouhouRepository;

import com.example.slgm.repository.FindbukimRepository;

@Controller
@RequestMapping("/unitkousei_kai")
public class Unitkousei_kaiController {
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	SlgmRepositoryUnitkousei kouseirepository;	
	
	@Autowired
	SlgmRepositoryUnitjyouhou repository;
	
	@Autowired
	SlgmRepositorySendan categoryRepository;	
	

	@Autowired
	SlgmRepositoryUnitbunrui unitbunruiRepository;		
	
	@Autowired
	SlgmRepositoryUnityakuwari unityakuwariRepository;		

	@Autowired
	FindunitjyouhouRepository findunitjyouhou;			
	
    @Autowired
    FindkouseiimpRepository findkouseiimpRepository;		
	
	@Autowired
	SlgmRepositoryBuki bukirepository;	    
  
	@Autowired
    FindbukimRepository Findbuki;		   
	
	
    //?????????????????????????????????
    //@ModelAttribute???form??????formModel??????????????????
    //?????????(BookData)?????????(bookdata)???????????????
    @GetMapping("/select")
    public String select(@ModelAttribute("formModel") Unitjyouhou unitjyouhou, Model model) {

        model.addAttribute("msg", "????????????");
        //bookdata???????????????????????????????????????
        List<Unitjyouhou> result = search(unitjyouhou.getSid(),unitjyouhou.getYid());

		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));   
//		model.addAttribute("unitbunrui", unitbunruiRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unityakuwari", unityakuwariRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
      
		model.addAttribute("jyouhouList", result);

        return "unitkousei_kai";
    }	
    
	
    //??????
    public List<Unitjyouhou> search(Integer sid, Integer yid){

        List<Unitjyouhou> result = new ArrayList<Unitjyouhou>();

        //?????????????????????????????????????????????????????????
     //   if ("".equals(genre) && "".equals(author) && "".equals(title)){
        if ((sid)==0 && (yid)==0){
        	result = repository.findAll(Sort.by(Sort.Direction.ASC, "zid"));
        }
        else {
            //????????????????????????BookDataDaoImpl??????????????????????????????
            result = findunitjyouhou.search(sid,yid);
        }
        return result;
    }    
    
    
//????????????			
	
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
		return "unitkousei_kai";
	}

	@GetMapping
	public String showList(FormUnitjyouhou formUnitjyouhou, Model model) {

		formUnitjyouhou.setNewUnitjyouhou(true);
		Iterable<Unitjyouhou> list = selectAll();

		model.addAttribute("list", list);
		model.addAttribute("title", "?????????????????????");
		return "unitkousei_kai";

	}



	@GetMapping("/{zid}")
	public String kouseihyouji(FormUnitkousei formUnitkousei, @PathVariable Integer zid, Model model) {

		model.addAttribute("unitbunrui", unitbunruiRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		formUnitkousei.setZid(zid);
		model.addAttribute("formUnitkousei", formUnitkousei);		
		
        List<Unitkousei> result = search_kousei(zid);
		
        model.addAttribute("kouseiList", result);

        //?????????????????????????????????	
        List<Unitjyouhou> result_sid = search_sid(zid);
        
        
        List<Bukim> result_b = search_buki(result_sid.get(0).getSid(),0);
		// result_sid.get("sid")
        model.addAttribute("bukiList", result_b);       

        List<Bukim> result_h = search_buki(result_sid.get(0).getSid(),1);
		// result_sid.get("sid")
        model.addAttribute("bukiList3", result_h);        
        formUnitkousei.setNewUnitkousei(true);
		return "unitkousei";

	}
	
    //??????  ??????
	public List<Unitjyouhou> search_sid(Integer zid){

        List<Unitjyouhou> result = new ArrayList<Unitjyouhou>();

        //?????????????????????????????????????????????????????????
     //   if ("".equals(genre) && "".equals(author) && "".equals(title)){
        if ((zid)==0){
        	result = repository.findAll(Sort.by(Sort.Direction.ASC, "zid"));
        }
        else {
            //????????????????????????BookDataDaoImpl??????????????????????????????
            result = findunitjyouhou.search_sid(zid);
        }
        return result;
    }	
	
	
	
	public List<Bukim> search_buki(Integer sid,Integer h){

        List<Bukim> result = new ArrayList<Bukim>();

        //?????????????????????????????????????????????????????????
     //   if ("".equals(genre) && "".equals(author) && "".equals(title)){
        if ((sid)==0){
        	result = bukirepository.findAll(Sort.by(Sort.Direction.ASC, "wno"));
        }
        else {
            //????????????????????????BookDataDaoImpl??????????????????????????????
            result = Findbuki.search(sid,h);
        }
        return result;
    }	
	
	
	
    //??????
    public List<Unitkousei> search_kousei(Integer zid){

        List<Unitkousei> result = new ArrayList<Unitkousei>();

        //?????????????????????????????????????????????????????????
     //   if ("".equals(genre) && "".equals(author) && "".equals(title)){
        if ((zid)==0){
        	result = kouseirepository.findAll(Sort.by(Sort.Direction.ASC, "kid"));
        }
        else {
            //????????????????????????BookDataDaoImpl??????????????????????????????
            result = findkouseiimpRepository.search(zid);
        }
        return result;
    }	

	private void makeUpdateModel(FormUnitjyouhou formUnitjyouhou, Model model) {

		model.addAttribute("zid", formUnitjyouhou.getZid());
		formUnitjyouhou.setNewUnitjyouhou(false);
		model.addAttribute("formUnitjyouhou", formUnitjyouhou);
		model.addAttribute("title", "?????????????????????");

	}







	public Iterable<Unitjyouhou> selectAll() {

		return repository.findAll(Sort.by(Sort.Direction.ASC, "zid"));
	}

	public Optional<Unitjyouhou> selectOneById(Integer zid) {
		return repository.findById(zid);
	}	
	
}

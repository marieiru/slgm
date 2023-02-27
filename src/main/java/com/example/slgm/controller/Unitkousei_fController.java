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

import com.example.slgm.entity.Unitkousei;
import com.example.slgm.form.FormUnitkousei;
import com.example.slgm.repository.SlgmRepositoryUnitkousei;
import com.example.slgm.repository.FindkouseiimpRepository;
import com.example.slgm.repository.SlgmRepositorySendan;
import com.example.slgm.repository.SlgmRepositoryUnitbunrui;

@Controller
@RequestMapping("/unitkousei_f")
public class Unitkousei_fController {

	@Autowired
	SlgmRepositoryUnitkousei kouseirepository;
	
	@Autowired
	SlgmRepositorySendan categoryRepository;		
	
	@Autowired
	SlgmRepositoryUnitbunrui unitbunruiRepository;		
	
	
	//検索関連		
	@PersistenceContext
	EntityManager entityManager;	
	
    @Autowired
    FindkouseiimpRepository findkouseiimpRepository;	
	
    //検索結果の受け取り処理
    //@ModelAttributeでformからformModelを受け取り、
    //その型(BookData)と変数(bookdata)を指定する
    @GetMapping("/select")
    public String select(@ModelAttribute("formModel") Unitkousei unitkousei, Model model) {

        model.addAttribute("msg", "検索結果");
        //bookdataのゲッターで各値を取得する
        List<Unitkousei> result = search(unitkousei.getSid(),unitkousei.getBid());

		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));   
		
		model.addAttribute("unitbunrui", unitbunruiRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
        model.addAttribute("kouseiList", result);

        return "unitkousei_f";
    }	
	
	
    //検索
    public List<Unitkousei> search(Integer sid, Integer bid){

        List<Unitkousei> result = new ArrayList<Unitkousei>();

        //すべてブランクだった場合は全件検索する
     //   if ("".equals(genre) && "".equals(author) && "".equals(title)){
        if ((sid)==0 && (bid)==0){
        	result = kouseirepository.findAll(Sort.by(Sort.Direction.ASC, "kid"));
        }
        else {
            //上記以外の場合、BookDataDaoImplのメソッドを呼び出す
//            result = findkouseiimpRepository.search(sid,bid);
        }
        return result;
    }
    
    
//検索関連	
	
	
	@ModelAttribute
	public FormUnitkousei setForm() {
		return new FormUnitkousei();
	}

	@GetMapping("/index")
	public String unitkousei(FormUnitkousei formUnitkousei, Model model) {

	
		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		model.addAttribute("unitbunrui", unitbunruiRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		model.addAttribute("kouseiList", kouseirepository.findAll(Sort.by(Sort.Direction.ASC, "kid")));		
		formUnitkousei.setNewUnitkousei(true);
		return "unitkousei_f";
	}

	@GetMapping
	public String showList(FormUnitkousei formUnitkousei, Model model) {

		formUnitkousei.setNewUnitkousei(true);
		Iterable<Unitkousei> list = selectAll();

		model.addAttribute("list", list);
		model.addAttribute("title", "登録用フォーム");
		return "unitkousei_f";

	}

	@PostMapping("/insert")
	public String insert(@Validated FormUnitkousei formUnitkousei, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

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
		
		unitkousei.setSid(formUnitkousei.getSid());
		
		if (!bindingResult.hasErrors()) {

			insertUnitkousei(unitkousei);
			redirectAttributes.addFlashAttribute("complete", "登録が完了しました。");
			return "redirect:/unitkousei/index";
		} else {
			return showList(formUnitkousei, model);
		}

	}

	@GetMapping("/{kid}")
	public String showUpdatet(FormUnitkousei formUnitkousei, @PathVariable Integer kid, Model model) {
		
		model.addAttribute("prefecturesList", categoryRepository.findAll());
		model.addAttribute("unitbunrui", unitbunruiRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));		
		
		Optional<Unitkousei> unitkouseiOpt = selectOneById(kid);

		Optional<FormUnitkousei> unitkouseiFormOpt = unitkouseiOpt.map(t -> makeFormUnitkousei(t));

		if (unitkouseiFormOpt.isPresent()) {
			formUnitkousei = unitkouseiFormOpt.get();
		}

		makeUpdateModel(formUnitkousei, model);
		return "unitkousei_f";

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
			return "unitkousei_f";
		}

	}

	@PostMapping("/delete")
	public String delete(@RequestParam("kid") String kid, Model model, RedirectAttributes redirectAttributes) {

		deleteUnitkouseiById(Integer.parseInt(kid));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました。");
		return "redirect:/unitkousei/index";

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
		
		unitkousei.setSid(formUnitkousei.getSid());		
	
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
		
		form.setSid(unitkousei.getSid());	
		
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

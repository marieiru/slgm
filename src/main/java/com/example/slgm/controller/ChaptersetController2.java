
package com.example.slgm.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;

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

import com.example.slgm.form.FormUnitjyouhou;
import com.example.slgm.form.FormUnitkousei;
import com.example.slgm.repository.SlgmRepositoryUnityakuwari;

import com.example.slgm.repository.SlgmRepositorySendan;
import com.example.slgm.repository.SlgmRepositoryUnitbunrui;
import com.example.slgm.repository.SlgmRepositoryUnitjyouhou;
import com.example.slgm.repository.SlgmRepositoryUnitkousei;
import com.example.slgm.entity.Bukim;
import com.example.slgm.entity.Unitjyouhou;
import com.example.slgm.entity.Unitkousei;

import com.example.slgm.entity.Chapter_kousei2;
import com.example.slgm.entity.Chapter_unit2;

import com.example.slgm.repository.FindkouseiimpRepository;
import com.example.slgm.repository.FindunitjyouhouRepository;
import com.example.slgm.repository.FindChapter_kousei2;

import com.example.slgm.repository.SlgmRepositoryChapter_kousei2;
import com.example.slgm.repository.SlgmRepositoryChapter_unit2;

@Controller
@RequestMapping("/chapter_set2")
public class ChaptersetController2 {

	@Autowired
	SlgmRepositoryUnitjyouhou repository;

	@Autowired
	SlgmRepositoryUnitjyouhou jyouhourepository;

	@Autowired
	SlgmRepositorySendan categoryRepository;

	@Autowired
	SlgmRepositoryUnityakuwari unityakuwariRepository;

	@Autowired
	FindunitjyouhouRepository findunitjyouhou;

	@Autowired
	SlgmRepositoryUnitkousei kouseirepository;

	@Autowired
	FindkouseiimpRepository findkouseiimpRepository;

	@Autowired
	SlgmRepositoryChapter_kousei2 repoChapter_kousei2;

	@Autowired
	SlgmRepositoryChapter_unit2 repoChapter_unit2;

	@Autowired
	FindChapter_kousei2 repofindChapter_kousei2;

	@ModelAttribute
	public FormUnitjyouhou setForm() {
		return new FormUnitjyouhou();
	}

	@ModelAttribute
	public FormUnitkousei setFormkousei() {
		return new FormUnitkousei();
	}

	@GetMapping("/index")
	public String Unitjyouhou(FormUnitjyouhou formUnitjyouhou, Model model) {

		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unityakuwari", unityakuwariRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));


		model.addAttribute("chapter1List", repoChapter_unit2.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("kouseiList", repoChapter_kousei2.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		formUnitjyouhou.setNewUnitjyouhou(true);
		return "chapter_set2";
	}

	@GetMapping("/select")
	public String select(@ModelAttribute("formModel") Unitjyouhou unitjyouhou, Model model) {

		model.addAttribute("msg", "検索結果");
		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unityakuwari", unityakuwariRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));

		List<Unitjyouhou> result = search_t(unitjyouhou.getSid(), unitjyouhou.getYid());

		model.addAttribute("chapter1List", repoChapter_unit2.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("kouseiList", repoChapter_kousei2.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		model.addAttribute("jyouhouList", result);

		return "chapter_set2";
	}

	public List<Unitjyouhou> search_t(Integer sid, Integer yid) {

		List<Unitjyouhou> result_t = new ArrayList<Unitjyouhou>();

		// すべてブランクだった場合は全件検索する
		// if ("".equals(genre) && "".equals(author) && "".equals(title)){
		if ((sid) == 0 && (yid) == 0) {
			result_t = jyouhourepository.findAll(Sort.by(Sort.Direction.ASC, "zid"));
			// repository.findAll(Sort.by(Sort.Direction.ASC, "zid")));
		} else {
			// 上記以外の場合、BookDataDaoImplのメソッドを呼び出す
			result_t = findunitjyouhou.search(sid, yid);
		}
		return result_t;
	}

	@GetMapping("/{zid}")
	public String showUpdatet(FormUnitjyouhou formUnitjyouhou, @RequestParam("yuname") String yuname,
			@PathVariable Integer zid, Model model) {
//対戦用のユニット情報をテーブルにインサート

		// jyouhou

		Optional<Unitjyouhou> unitjyouhouOpt = selectOneById(zid);

		Optional<FormUnitjyouhou> unitjyouhouFormOpt = unitjyouhouOpt.map(t -> makeFormUnitjyouhou(t));

		if (unitjyouhouFormOpt.isPresent()) {
			formUnitjyouhou = unitjyouhouFormOpt.get();

			Chapter_unit2 chapter_unit2 = new Chapter_unit2();

			chapter_unit2.setZid(formUnitjyouhou.getZid());
			chapter_unit2.setYuname(formUnitjyouhou.getYuname());
			chapter_unit2.setSid(formUnitjyouhou.getSid());
			chapter_unit2.setYid(formUnitjyouhou.getYid());
			chapter_unit2.setPck(formUnitjyouhou.getPck());
			chapter_unit2.setMck(formUnitjyouhou.getMck());

			chapter_unit2.setImg(formUnitjyouhou.getImg());

			updateChapter_unit2(chapter_unit2);
		}

		List<Chapter_unit2> result_u = repoChapter_unit2.findAll(Sort.by(Sort.Direction.DESC, "id"));

		// kousei
		List<Unitkousei> result = search_kousei(zid);

		// for (int i = 1; i <= formUnitkousei.getKazu(); i++){
		// rekkaList != null && rekkaList.size() >= 1
		if (result != null && result.size() >= 1) {
			for (int i = 0; i <= result.size() - 1; i++) {

				Chapter_kousei2 chapter_kousei2 = new Chapter_kousei2();

				chapter_kousei2.setDid(result_u.get(0).getId());

				chapter_kousei2.setKid(result.get(i).getKid());
				chapter_kousei2.setKname(result.get(i).getKname());
				chapter_kousei2.setAt(result.get(i).getAt());
				chapter_kousei2.setTaff(result.get(i).getTaff());
				chapter_kousei2.setUunz(result.get(i).getUunz());
				chapter_kousei2.setAk(result.get(i).getAk());
				chapter_kousei2.setLd(result.get(i).getLd());
				chapter_kousei2.setAsa(result.get(i).getAsa());
				chapter_kousei2.setAssp(result.get(i).getAssp());
				chapter_kousei2.setAbl(result.get(i).getAbl());

				chapter_kousei2.setBid(result.get(i).getBid());
				chapter_kousei2.setZid(result.get(i).getZid());
				chapter_kousei2.setWno(result.get(i).getWno());
				chapter_kousei2.setWno2(result.get(i).getWno2());
				chapter_kousei2.setWno3(result.get(i).getWno3());
				chapter_kousei2.setWno4(result.get(i).getWno4());
				chapter_kousei2.setWno5(result.get(i).getWno5());
				chapter_kousei2.setWno6(result.get(i).getWno6());
				chapter_kousei2.setWno7(result.get(i).getWno7());
				chapter_kousei2.setMvg(36);

				// レコードをINSERT処理 現状 手段をしらないのが残念
				if (i < result.size() - 1) {
					updateChapter_kousei2(chapter_kousei2);
				} else {
					updateChapter_kousei2(chapter_kousei2);
					repoChapter_kousei2.flush();
				}

			}
		}

		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unityakuwari", unityakuwariRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));

		model.addAttribute("jyouhouList", jyouhourepository.findAll(Sort.by(Sort.Direction.ASC, "zid")));
		model.addAttribute("chapter1List", repoChapter_unit2.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("kouseiList", repoChapter_kousei2.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		model.addAttribute("title", yuname + "を戦力として登録しました。");

		return "redirect:/chapter_set2/index";
	}

	public List<Unitkousei> search_kousei(Integer zid) {

		List<Unitkousei> result = new ArrayList<Unitkousei>();

		// すべてブランクだった場合は全件検索する
		// if ("".equals(genre) && "".equals(author) && "".equals(title)){
		if ((zid) == 0) {
			result = kouseirepository.findAll(Sort.by(Sort.Direction.ASC, "kid"));
		} else {
			// 上記以外の場合、BookDataDaoImplのメソッドを呼び出す
			result = findkouseiimpRepository.search(zid);
		}
		return result;
	}

	public void updateChapter_kousei2(Chapter_kousei2 chapter_kousei2) {
		repoChapter_kousei2.save(chapter_kousei2);

	}

	public Optional<Unitjyouhou> selectOneById(Integer zid) {
		return repository.findById(zid);
	}

	private FormUnitjyouhou makeFormUnitjyouhou(Unitjyouhou unitjyouhou) {

		FormUnitjyouhou form = new FormUnitjyouhou();

		form.setZid(unitjyouhou.getZid());

		form.setYuname(unitjyouhou.getYuname());

		form.setSid(unitjyouhou.getSid());

		form.setYid(unitjyouhou.getYid());

		form.setPck(unitjyouhou.getPck());
		form.setMck(unitjyouhou.getMck());

		form.setImg(unitjyouhou.getImg());

		form.setNewUnitjyouhou(false);
		return form;

	}

	public void updateChapter_unit2(Chapter_unit2 chapter_unit2) {
		repoChapter_unit2.saveAndFlush(chapter_unit2);
	}

	@PostMapping("/delete")
	public String delete(@RequestParam("id") Integer id, Model model, RedirectAttributes redirectAttributes) {

		List<Chapter_kousei2> result = repofindChapter_kousei2.search(id);

		if (result != null && result.size() >= 1) {
			for (int i = 0; i <= result.size() - 1; i++) {
				deleteChapter_kousei2(result.get(i).getId());
			}
		}

		deleteChapter_unit2(id);

		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました。");
		return "redirect:/chapter_set2/index";

	}

	public void deleteChapter_unit2(Integer id) {
		repoChapter_unit2.deleteById(id);
	}

	public void deleteChapter_kousei2(Integer id) {

		repoChapter_kousei2.deleteById(id);
	}

}

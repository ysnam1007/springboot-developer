package sku.lesson.springboot.controller;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sku.lesson.springboot.utils.DateService;
import sku.lesson.springboot.dto.MemberDTO;
import sku.lesson.springboot.service.MemberService3;

@Controller
@RequestMapping(value = "/member3")
public class MemberController3 {

	@Autowired
	private MemberService3 ms;
	
	@RequestMapping(value = "/ajax", method = RequestMethod.POST)
	@ResponseBody
	public String checkId(@RequestParam("userId") String id) {
		boolean check = ms.checkId(id);
		return "{\"result\":"+check+"}";
	}
	
	@RequestMapping(value = "/registView", method = RequestMethod.GET)
	public String registView(Model model) {
		model.addAttribute("registDate",DateService.getDateTime(1));
		return "./regist";
	}
	
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regist(@ModelAttribute MemberDTO dto, BindingResult br) {
		if(br.hasErrors()) {
			Timestamp date = new Timestamp(System.currentTimeMillis());
			dto.setRegistDate(date);
			System.out.println(" error handling >>> "+dto);
		}
		//service 에게 전달
		boolean flag = ms.registMember(dto);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public String list(Model model) {
		ArrayList<MemberDTO> list = ms.getMemberData();
		model.addAttribute("list", list);
		System.out.println(" list3 >> "+list.size());
		return "./list";
	}
	
	//search
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam("userId") String id, Model model) {
		MemberDTO dto = ms.findById(id);
		model.addAttribute("dto", dto);
		System.out.println("search3 >>> "+id);
		return "./detail";
	}
	//updateview
	@RequestMapping(value = "/updateView", method = RequestMethod.GET)
	public String updateView(@RequestParam("userId") String id, Model model) {
		MemberDTO dto = ms.findById(id);
		model.addAttribute("dto", dto);
		System.out.println("update view3 >>> "+id);
		return "./update";
	}
	//update
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute MemberDTO dto) {
		
		//서비츠 요청
		ms.modifyMember(dto);

		System.out.println("update3 >>> "+dto.getUserId());
		//request.setAttribute("userId", id);
		return "redirect:search?userId="+dto.getUserId();
	}
	//delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("userId") String id) {
		ms.remove(id);
		System.out.println("delete3 >>> "+id);
		return "redirect:list";
	}
}

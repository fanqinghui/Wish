package com.foundation.controller.habit;

import com.foundation.common.BaseController;
import com.foundation.service.app.IAppVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/habit")
public class HabitController extends BaseController {

	Logger logger = LoggerFactory.getLogger(HabitController.class);

	@Autowired
	IAppVersionService IAppVersionService;


	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello world!");
		return "hello";
	}

	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public String restfulMethod(@PathVariable("id") Long id) {
		System.out.println(id);
		return "view";
	}
}
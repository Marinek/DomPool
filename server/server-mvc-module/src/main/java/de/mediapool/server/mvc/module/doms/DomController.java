package de.mediapool.server.mvc.module.doms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.mediapool.server.entities.doms.domain.Dom;
import de.mediapool.server.entities.doms.repository.DomColorRespository;
import de.mediapool.server.entities.doms.repository.DomRepository;
import de.mediapool.server.entities.doms.repository.DomTypeRespository;
import de.mediapool.server.entities.users.domain.User;
import de.mediapool.server.security.domain.PreAuthorization;

@Controller
public class DomController {

	@Autowired
	private DomRepository domRepository;

	@Autowired
	private DomTypeRespository domTypeRepository;

	@Autowired
	private DomColorRespository domColorRepository;


	@RequestMapping(value="/editDom", method=RequestMethod.GET) 
	@PreAuthorize(PreAuthorization.ROLE_USER)
	public String editDom(@RequestParam(name="id", required=false)Long domID, Model model) {
		if(domID != null) {
			model.addAttribute("dom", domRepository.findOne(domID));
		} else {
			model.addAttribute("dom", new Dom());
		}
		
		model.addAttribute("domTypes", domTypeRepository.findAll());
		model.addAttribute("domColors", domColorRepository.findAll());

		return "views/doms/editDom";
	}

	@RequestMapping(value="/editDom", method=RequestMethod.POST )
	public String saveDom(@ModelAttribute Dom dom, @AuthenticationPrincipal User user, Model model) {
		dom.setSpotter(user);
		
		model.addAttribute("dom", domRepository.save(dom));

		return "redirect:/listDoms";
	}
	
	@RequestMapping(value="/listDoms", method=RequestMethod.GET)
	public String listDom(Model model) {
		
		model.addAttribute("doms", domRepository.findAll());

		return "views/doms/listDoms";
	}
	
	@RequestMapping(value="/dom", method=RequestMethod.GET)
	public String showDom(@RequestParam(name="id", required=false)Long domID, Model model) {
		
		model.addAttribute("dom", domRepository.findOne(domID));

		return "views/doms/fragmentDom :: domDetail";
	}
	
}

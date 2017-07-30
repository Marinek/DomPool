package de.mediapool.server.mvc.module.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.mediapool.server.entities.users.domain.User;
import de.mediapool.server.entities.users.repository.UserRepository;
import de.mediapool.server.security.domain.PreAuthorization;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;


	@RequestMapping(value="/editUser", method=RequestMethod.GET)
	@PreAuthorize(PreAuthorization.ROLE_ADMIN)
	public String editProductList(@RequestParam(name="id", required=false)Long productListId, Model model) {
		if(productListId != null) {
			model.addAttribute("user", userRepository.findOne(productListId));
		} else {
			model.addAttribute("user", new User());
		}

		return "views/users/editUser";
	}

	@RequestMapping(value="/editUser", method=RequestMethod.POST )
	public String getProductList(@ModelAttribute User user, Model model) {
		model.addAttribute("user", userRepository.save(user));

		return "redirect:/listUser";
	}
	
	@RequestMapping(value="/listUser", method=RequestMethod.GET)
	public String editProductList(Model model) {
		
		model.addAttribute("users", userRepository.findAll());

		return "views/users/listUsers";
	}
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public String showUser(@RequestParam(name="id", required=false)Long userID, Model model) {
		
		model.addAttribute("user", userRepository.findOne(userID));

		return "views/users/fragmentUser :: userDetail";
	}
	
}

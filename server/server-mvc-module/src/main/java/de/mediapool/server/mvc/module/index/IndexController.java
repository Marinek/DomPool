package de.mediapool.server.mvc.module.index;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {
    	
    	Map<String, Object> fillIndexModel = fillIndexModel(null);
    	
    	model.addAllAttributes(fillIndexModel);
    	
        return "views/index";
    }

    public Map<String, Object> fillIndexModel(Map<String, Object> model) {
    	if(model == null) {
    		model = new HashMap<>();
    	}
    	
    	return model;
    }
    
}
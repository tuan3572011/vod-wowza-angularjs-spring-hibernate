package vn.edu.hcmuaf.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import vn.edu.hcmuaf.util.LinkService;

import com.vod.model.Director;

@Controller
@RequestMapping(value = "/ThemDaoDienController")
public class ThemDaoDienController {

	@RequestMapping(value = "/layout")
	public String getIndexPage(Model model) {
		model.addAttribute("director", new Director());
		System.out.println("Them dao dien controller");
		return "ThemDaoDien";

	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveIndexPage(@ModelAttribute Director director, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Void> entity = restTemplate.postForEntity(
				LinkService.DIRECTOR_ADD, director, Void.class);

		boolean isSaveOk = true;
		if (entity.getStatusCode().equals(HttpStatus.OK)) {
			isSaveOk = true;
		} else {
			isSaveOk = false;
		}
		if (isSaveOk) {
			model.addAttribute("message", isSaveOk);
		}
		return "ThemDaoDien";
	}
}

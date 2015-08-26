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

import com.vod.model.Starring;

@Controller
@RequestMapping(value = "/DienVienController")
public class DienVienController {

	@RequestMapping(value = "/layout")
	public String addStarringLayout(Model model) {
		model.addAttribute("starring", new Starring());
		System.out.println("Them dien vien controller");
		return "ThemDienVien";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveStarring(@ModelAttribute Starring starring, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Void> entity = restTemplate.postForEntity(
				LinkService.STAR_ADD, starring, Void.class);
		boolean isSaveOk = true;
		if (entity.getStatusCode().equals(HttpStatus.OK)) {
			isSaveOk = true;
		} else {
			isSaveOk = false;
		}
		if (isSaveOk) {
			model.addAttribute("message", isSaveOk);
		}
		return "ThemDienVien";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void detailStarring() {

	}
}

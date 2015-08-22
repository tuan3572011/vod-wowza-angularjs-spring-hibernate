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
@RequestMapping(value = "/ThemDienVienController")
public class ThemDienVienController {

	@RequestMapping(value = "/layout")
	public String getIndexPage(Model model) {
		model.addAttribute("starring", new Starring());
		System.out.println("Them dien vien controller");
		return "ThemDienVien";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveIndexPage(@ModelAttribute Starring starring, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Void> entity = restTemplate.postForEntity(
				LinkService.STAR_ADD, starring, Void.class);
		boolean isSaveOk = true;
		if (entity.getStatusCode().equals(HttpStatus.OK)) {
			isSaveOk = true;
			// InputStream starringInputStream =
			// CreateInputStream.starringJson();
			// String folder = "Json";
			// String fileName = "starring.json";
			// String contentType = "application/json";
			// long fileLength = 1;
			// byte[] buffer = new byte[500];
			// int count = -1;
			// try {
			// while ((count = starringInputStream.read(buffer)) != -1) {
			// fileLength += count;
			// }
			// } catch (IOException e1) {
			// e1.printStackTrace();
			// }
			// try {
			// isSaveOk = UploadController.upS3(starringInputStream, folder,
			// fileName, fileLength, contentType);
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
		} else {
			isSaveOk = false;
		}
		if (isSaveOk) {
			model.addAttribute("message", isSaveOk);
		}
		return "ThemDienVien";
	}
}

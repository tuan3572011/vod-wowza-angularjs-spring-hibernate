package vn.edu.hcmuaf.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import vn.edu.hcmuaf.util.LinkService;

@Controller
@RequestMapping("/RegisFilmController")
public class RegisFilmController {

	@RequestMapping(value = "/addRegis/{email}/{idFilm}/{type}", method = RequestMethod.GET)
	public @ResponseBody
	String doRegisSerie(@PathVariable("email") String email,
			@PathVariable("idFilm") String idFilm,
			@PathVariable("type") String type) {
		String check = "FAIL";
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("idFilm", idFilm);
		params.put("type", type);

		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.getForObject(LinkService.REGIS_ADD, String.class,
					params);
			check = "OK";
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
		}

		return check;
	}

	@RequestMapping(value = "/checkRegis/{email}/{idFilm}", method = RequestMethod.GET)
	public @ResponseBody
	String doCheckRegis(@PathVariable("email") String email,
			@PathVariable("idFilm") String idFilm) {
		String check = "FAIL";
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("idFilm", idFilm);

		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.getForObject(LinkService.REGIS_GETBY_EMAIL_IDFILM,
					Void.class, params);
			System.out.println("OK");
			check = "OK";
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				System.out.println("NOT FOUND");
			}
		}
		return check;
	}
}

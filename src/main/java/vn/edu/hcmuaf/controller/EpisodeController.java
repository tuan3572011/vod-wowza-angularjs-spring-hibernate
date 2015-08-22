package vn.edu.hcmuaf.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import vn.edu.hcmuaf.util.LinkService;

@Controller
@RequestMapping("/EpisodeController")
public class EpisodeController {

	@RequestMapping(value = "/episodes/{serieId}", method = RequestMethod.GET)
	public @ResponseBody
	String doGetEpisodes(@PathVariable("serieId") String serieId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("serieId", serieId);
		RestTemplate restTemplate = new RestTemplate();

		String str = restTemplate.getForObject(
				LinkService.EPISODE_GET_LIST_BY_SERIE, String.class, params);
		System.out.println(str);
		return str;
	}
}

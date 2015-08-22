package vn.edu.hcmuaf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import vn.edu.hcmuaf.util.LinkService;

import com.vod.model.Starring;

/**
 * Created with IntelliJ IDEA. User: xvitcoder Date: 12/21/12 Time: 12:22 AM
 */
@Controller
@RequestMapping("/ChiTietController")
public class ChiTietController {

	@RequestMapping("/layout")
	public String getTrainPartialPage() {
		return "ChiTiet";
	}

	@RequestMapping("/seriedetail")
	public String getPageSeriDetail(Map<String, Object> map) {
		return "seriedetail";
	}

	/*private List<MovieSearch> topIMDB() {
		List<MovieSearch> movies = null;
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<MovieSearch>> responseType = new ParameterizedTypeReference<List<MovieSearch>>() {
		};
		ResponseEntity<List<MovieSearch>> result = restTemplate.exchange(
				LinkService.MOVIE_GETALL, HttpMethod.GET, null, responseType);
		if (result.getStatusCode().equals(HttpStatus.OK)) {
			movies = result.getBody();
			movies.addAll(movies);
			movies.addAll(movies);
			movies.addAll(movies);
		}
		return movies;
	}*/

	@ResponseBody
	@RequestMapping(value = "/LoadStarring", method = RequestMethod.GET)
	public List<Starring> slideShowDienVien(
			@RequestParam(value = "movieId") Integer movieId) {
		List<Starring> starrings = null;
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<Starring>> responseType = new ParameterizedTypeReference<List<Starring>>() {
		};
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("movieId", movieId);
		ResponseEntity<List<Starring>> result = restTemplate.exchange(
				LinkService.STAR_GETBY_ID, HttpMethod.GET, null, responseType,
				params);
		if (result.getStatusCode().equals(HttpStatus.OK)) {
			starrings = result.getBody();
			starrings.addAll(starrings);
			starrings.addAll(starrings);
			starrings.addAll(starrings);
		}
		return starrings;
	}

}

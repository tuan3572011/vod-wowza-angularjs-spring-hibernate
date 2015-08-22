package vn.edu.hcmuaf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import vn.edu.hcmuaf.util.LinkService;

import com.vod.model.MovieSearch;
import com.vod.model.MovieSerie;

/**
 * Created with IntelliJ IDEA. User: xvitcoder Date: 12/21/12 Time: 12:22 AM
 */
@Controller
@RequestMapping("/PhimBoController")
public class PhimBoController {

	@RequestMapping("/layout")
	public String getTrainPartialPage(Map<String, Object> map) {
		map.put("movies", getListMovie());
		return "PhimBo";
	}

	@RequestMapping(value = "/serie/{movieId}", method = RequestMethod.GET)
	public @ResponseBody
	MovieSerie doShowSerieDetail(@PathVariable("movieId") String movieId) {
		MovieSerie movie = getById(movieId);
		return movie;
	}

	@RequestMapping(value = "/jsonsearch", method = RequestMethod.GET)
	public @ResponseBody
	String doGetJsonSearch() {
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(LinkService.SEARCH_FILM_JSON,
				String.class);
		return result;
	}

	private List<MovieSearch> getListMovie() {
		List<MovieSearch> movies = null;
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<MovieSearch>> responseType = new ParameterizedTypeReference<List<MovieSearch>>() {
		};
		ResponseEntity<List<MovieSearch>> result = restTemplate.exchange(
				LinkService.SERIE_GETALL, HttpMethod.GET, null, responseType);
		if (result.getStatusCode().equals(HttpStatus.OK)) {
			movies = result.getBody();
		}

		return movies;
	}

	private MovieSerie getById(String movieId) {
		MovieSerie movie = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("serieId", movieId);
		RestTemplate restTemplate = new RestTemplate();

		movie = restTemplate.getForObject(LinkService.SERIE_GETBY_ID,
				MovieSerie.class, params);
		return movie;
	}

}
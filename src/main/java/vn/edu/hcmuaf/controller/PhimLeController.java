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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import vn.edu.hcmuaf.util.LinkService;

import com.vod.model.Movie;
import com.vod.model.MovieSearch;

/**
 * Created with IntelliJ IDEA. User: xvitcoder Date: 12/21/12 Time: 12:22 AM
 */
@Controller
@RequestMapping("/PhimLeController")
public class PhimLeController {

	@RequestMapping("/layout")
	public String getTrainPartialPage(Map<String, Object> map) {
		System.out.println("begin phim le layout");
		map.put("movies", getListMovie());
		return "PhimLe";
	}

	@RequestMapping(value = "/movie/{movieId}", method = RequestMethod.GET)
	public @ResponseBody Movie doShowABC(@PathVariable("movieId") String movieId) {
		Movie movie = getById(movieId);
		return movie;
	}

	@RequestMapping(value = "/abc", method = RequestMethod.GET)
	public String kahsdfkj(@RequestParam("abc") String abc) {
		System.out.println(abc);
		return "NewFile";
	}

	private List<MovieSearch> getListMovie() {
		List<MovieSearch> movies = null;
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<MovieSearch>> responseType = new ParameterizedTypeReference<List<MovieSearch>>() {
		};
		ResponseEntity<List<MovieSearch>> result = restTemplate.exchange(
				LinkService.MOVIE_GETALL, HttpMethod.GET, null, responseType);
		if (result.getStatusCode().equals(HttpStatus.OK)) {
			movies = result.getBody();
		}
		return movies;
	}

	private Movie getById(String movieId) {
		Movie movie = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("movieId", movieId);
		RestTemplate restTemplate = new RestTemplate();

		movie = restTemplate.getForObject(LinkService.MOVIE_GETBY_ID,
				Movie.class, params);
		return movie;
	}
}

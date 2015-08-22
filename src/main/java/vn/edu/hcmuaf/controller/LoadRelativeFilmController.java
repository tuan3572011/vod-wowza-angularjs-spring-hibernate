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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import vn.edu.hcmuaf.util.LinkService;

import com.vod.model.MovieSearch;

@Controller
@RequestMapping("/LoadRelativeFilmController")
public class LoadRelativeFilmController {

	@RequestMapping(value = "/LoadRelativeMovies")
	public @ResponseBody List<MovieSearch> loadRelativeMovies(
			@RequestParam(value = "movieId") Integer movieId) {
		List<MovieSearch> movies = null;
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<MovieSearch>> responseType = new ParameterizedTypeReference<List<MovieSearch>>() {
		};
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("filmId", movieId);
		ResponseEntity<List<MovieSearch>> result = restTemplate.exchange(
				LinkService.RELATED_MOVIES, HttpMethod.GET, null, responseType,
				params);
		if (result.getStatusCode().equals(HttpStatus.OK)) {
			movies = result.getBody();
		}
		movies.addAll(movies);
		movies.addAll(movies);
		return movies;
	}

	@RequestMapping(value = "/LoadHotMovies")
	public @ResponseBody List<MovieSearch> loadHotMovies() {
		System.out.println("Load hot movies");
		List<MovieSearch> movies = null;
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<MovieSearch>> responseType = new ParameterizedTypeReference<List<MovieSearch>>() {
		};
		ResponseEntity<List<MovieSearch>> result = restTemplate.exchange(
				LinkService.TOPIMDB, HttpMethod.GET, null, responseType);
		if (result.getStatusCode().equals(HttpStatus.OK)) {
			movies = result.getBody();
		}
		movies.addAll(movies);
		movies.addAll(movies);
		return movies;
	}

}

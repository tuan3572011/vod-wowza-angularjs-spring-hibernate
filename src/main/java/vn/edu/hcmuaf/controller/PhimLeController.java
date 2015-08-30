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
		// map.put("movies", getListMovie());
		return "PhimLe";
	}

	@RequestMapping(value = "/movie/{movieId}", method = RequestMethod.GET)
	public @ResponseBody
	Movie doShowABC(@PathVariable("movieId") String movieId) {
		Movie movie = getById(movieId);
		return movie;
	}

	@RequestMapping(value = "/abc", method = RequestMethod.GET)
	public String kahsdfkj(@RequestParam("abc") String abc) {
		System.out.println(abc);
		return "NewFile";
	}

	@RequestMapping("/GetListPhimLe")
	public @ResponseBody
	List<MovieSearch> getListMovie() {
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

	@RequestMapping(value = "/movie/getByCategory/{cateId}", method = RequestMethod.GET)
	public @ResponseBody
	String getMoviesByCategory(@PathVariable("cateId") String cateId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("cateId", cateId);
		RestTemplate restTemplate = new RestTemplate();

		String str = restTemplate.getForObject(
				LinkService.MOVIE_GETBY_CATEGORY, String.class, params);

		return str;
	}

	@RequestMapping(value = "/movie/getByCountry/{countryId}", method = RequestMethod.GET)
	public @ResponseBody
	String getMoviesByCountry(@PathVariable("countryId") String countryId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("countryId", countryId);
		RestTemplate restTemplate = new RestTemplate();

		String str = restTemplate.getForObject(LinkService.MOVIE_GETBY_COUNTRY,
				String.class, params);

		return str;
	}

	@RequestMapping(value = "/movie/getByYear/{year}", method = RequestMethod.GET)
	public @ResponseBody
	String getMoviesByYear(@PathVariable("year") String year) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("year", year);
		RestTemplate restTemplate = new RestTemplate();

		String str = restTemplate.getForObject(LinkService.MOVIE_GETBY_YEAR,
				String.class, params);

		return str;
	}

	@RequestMapping(value = "/movie/getByView", method = RequestMethod.GET)
	public @ResponseBody
	String getMoviesByView() {

		RestTemplate restTemplate = new RestTemplate();

		String str = restTemplate.getForObject(LinkService.MOVIE_GETBY_VIEW,
				String.class);

		return str;
	}

	@RequestMapping(value = "/movie/getByRate", method = RequestMethod.GET)
	public @ResponseBody
	String getMoviesByRate() {

		RestTemplate restTemplate = new RestTemplate();

		String str = restTemplate.getForObject(LinkService.MOVIE_GETBY_RATE,
				String.class);

		return str;
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

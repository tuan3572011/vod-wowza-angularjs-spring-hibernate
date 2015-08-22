package vn.edu.hcmuaf.controller.admin;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import vn.edu.hcmuaf.util.LinkService;

import com.vod.model.Country;
import com.vod.model.Episodes;
import com.vod.model.Movie;
import com.vod.model.MovieSerie;
import com.vod.model.Starring;

@Controller
@RequestMapping(value = "/ThemPhimController")
public class ThemPhimController {

	@RequestMapping(value = "/layout")
	public String getIndexPage(Model model) {
		Set<Country> countries = new HashSet<Country>();
		Country c1 = new Country();
		c1.setName("country1");
		Country c2 = new Country();
		c2.setName("country2");

		countries.add(c1);
		countries.add(c2);

		model.addAttribute("countries", countries);

		Starring star1 = new Starring();
		star1.setName("star1");
		star1.setId(1);

		Starring star2 = new Starring();
		star2.setName("star2");
		star2.setId(2);

		Set<Starring> starrings = new HashSet<Starring>();
		starrings.add(star1);
		starrings.add(star2);
		model.addAttribute("starrings", starrings);
		model.addAttribute("message", "nasdfnsfda");

		System.out.println("Them phim layout");
		return "ThemPhim";
	}

	@RequestMapping(value = "/Movie/Save", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody void saveMovie(@RequestBody Movie movie) {
		System.out.println("image " + movie.getImage());
		System.out.println("name " + movie.getName());
		System.out.println("key " + movie.getMovie_key());
		System.out.println("trailer " + movie.getTrailer());

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<Void> entity = restTemplate.postForEntity(
				LinkService.MOVIE_ADD, movie, Void.class);
		boolean isSaveOk = true;
		if (entity.getStatusCode().equals(HttpStatus.OK)) {
			isSaveOk = true;
		} else {
			isSaveOk = false;
		}
		System.out.println(isSaveOk);
	}

	@RequestMapping(value = "/Episode/Save", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody void saveEpisode(@RequestBody Episodes episode) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<Void> entity = restTemplate.postForEntity(
				LinkService.EPISODE_ADD, episode, Void.class);
		boolean isSaveOk = true;
		if (entity.getStatusCode().equals(HttpStatus.OK)) {
			isSaveOk = true;
		} else {
			isSaveOk = false;
		}
		System.out.println(isSaveOk);
	}

	@ResponseBody
	@RequestMapping(value = "/MovieSerie/Save", method = RequestMethod.POST)
	public void saveMovieSerie(@RequestBody MovieSerie serie) {
		System.out.println("image " + serie.getImage());
		for (Country ct : serie.getCountries()) {
			System.out.println(ct.getName() + "--" + ct.getId());
		}
		System.out.println(serie.getCountries().size());
		System.out.println(serie.getInfo());
		System.out.println("THEM PHIM ");

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		ResponseEntity<Void> entity = restTemplate.postForEntity(
				LinkService.SERIE_ADD, serie, Void.class);
		boolean isSaveOk = true;
		if (entity.getStatusCode().equals(HttpStatus.OK)) {
			isSaveOk = true;
		} else {
			isSaveOk = false;
		}
		System.out.println(isSaveOk);

	}

	@RequestMapping(value = "/ThemMovieSerie/Layout", method = RequestMethod.GET)
	public String movieSerieLayout() {
		return "ThemMovieSerie";
	}

	@RequestMapping(value = "/ThemMovie/Layout", method = RequestMethod.GET)
	public String movieLayout() {
		return "ThemMovie";
	}

	@RequestMapping(value = "/ThemEpisode/Layout", method = RequestMethod.GET)
	public String episodeLayout() {
		return "ThemEpisode";
	}
}

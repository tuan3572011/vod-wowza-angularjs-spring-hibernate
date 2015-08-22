package vn.edu.hcmuaf.util;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import vn.edu.hcmuaf.controller.admin.UploadController;

import com.google.gson.Gson;
import com.vod.model.Category;
import com.vod.model.Country;
import com.vod.model.Director;
import com.vod.model.Film;
import com.vod.model.MovieSearch;
import com.vod.model.Starring;

public class WriteJsonData {
	public static void writeDirector() {
		List<Director> series = null;
		final String url = "http://localhost:8080/vodService/director/all";
		RestTemplate restTemplate = new RestTemplate();

		ParameterizedTypeReference<List<Director>> responseType = new ParameterizedTypeReference<List<Director>>() {
		};
		ResponseEntity<List<Director>> result = restTemplate.exchange(url,
				HttpMethod.GET, null, responseType);

		if (result.getStatusCode().equals(HttpStatus.OK)) {
			series = result.getBody();
		}
		writeJson("director", new Gson().toJson(series));
	}

	public static void writeStarring() {
		List<Starring> series = null;
		final String url = "http://localhost:8080/vodService/starring/all";
		RestTemplate restTemplate = new RestTemplate();

		ParameterizedTypeReference<List<Starring>> responseType = new ParameterizedTypeReference<List<Starring>>() {
		};
		ResponseEntity<List<Starring>> result = restTemplate.exchange(url,
				HttpMethod.GET, null, responseType);

		if (result.getStatusCode().equals(HttpStatus.OK)) {
			series = result.getBody();
		}
		writeJson("starring", new Gson().toJson(series));
	}

	public static void writeCategory() {
		List<Category> series = null;
		final String url = "http://localhost:8080/vodService/category/all";
		RestTemplate restTemplate = new RestTemplate();

		ParameterizedTypeReference<List<Category>> responseType = new ParameterizedTypeReference<List<Category>>() {
		};
		ResponseEntity<List<Category>> result = restTemplate.exchange(url,
				HttpMethod.GET, null, responseType);

		if (result.getStatusCode().equals(HttpStatus.OK)) {
			series = result.getBody();
		}
		writeJson("category", new Gson().toJson(series));
	}

	public static void writeCountry() {
		List<Country> series = null;
		final String url = "http://localhost:8080/vodService/country/all";
		RestTemplate restTemplate = new RestTemplate();

		ParameterizedTypeReference<List<Country>> responseType = new ParameterizedTypeReference<List<Country>>() {
		};
		ResponseEntity<List<Country>> result = restTemplate.exchange(url,
				HttpMethod.GET, null, responseType);

		if (result.getStatusCode().equals(HttpStatus.OK)) {
			series = result.getBody();
		}
		writeJson("country", new Gson().toJson(series));
	}

	public static void writeMovieSearch() {
		List<MovieSearch> series = null;
		final String url = "http://localhost:8080/vodService/movieSearch/all";
		RestTemplate restTemplate = new RestTemplate();

		ParameterizedTypeReference<List<MovieSearch>> responseType = new ParameterizedTypeReference<List<MovieSearch>>() {
		};
		ResponseEntity<List<MovieSearch>> result = restTemplate.exchange(url,
				HttpMethod.GET, null, responseType);

		if (result.getStatusCode().equals(HttpStatus.OK)) {
			series = result.getBody();
		}
		writeJson("movieSearch", new Gson().toJson(series));
	}

	private static void writeJson(String fileName, String json) {
		URL resourcesUrl = UploadController.class.getClassLoader().getResource(
				fileName + ".json");
		try {
			File file = new File(resourcesUrl.toURI());
			if (!file.exists()) {
				file.createNewFile();
			}
			PrintWriter writer = new PrintWriter(file);
			writer.print(json);
			writer.flush();
			writer.close();
			System.out.println(file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

}

package vn.edu.hcmuaf.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import vn.edu.hcmuaf.controller.admin.UploadController;
import vn.edu.hcmuaf.util.LinkService;
import vn.edu.hcmuaf.util.ResourcesFolderUtility;

import com.vod.model.Director;
import com.vod.model.MovieSearch;
import com.vod.model.Starring;

@Controller
@RequestMapping("/GetJsonController")
public class GetJsonDataController {
    private static final Logger logger = LoggerFactory
            .getLogger(GetJsonDataController.class);

    @ResponseBody
    @RequestMapping(value = "/Director", method = RequestMethod.POST)
    public List<Director> getAllDirector() {
        List<Director> directors = null;
        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<List<Director>> responseType = new ParameterizedTypeReference<List<Director>>() {
        };
        ResponseEntity<List<Director>> result = restTemplate
                .exchange(LinkService.DIRECTOR_GETALL, HttpMethod.GET, null,
                        responseType);

        if (result.getStatusCode().equals(HttpStatus.OK)) {
            directors = result.getBody();
        }
        return directors;

    }

    @ResponseBody
    @RequestMapping(value = "/Starring", method = RequestMethod.POST)
    public List<Starring> getAllStarring() {

        List<Starring> starrings = null;
        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<List<Starring>> responseType = new ParameterizedTypeReference<List<Starring>>() {
        };
        ResponseEntity<List<Starring>> result = restTemplate.exchange(
                LinkService.STAR_GETALL, HttpMethod.GET, null, responseType);

        if (result.getStatusCode().equals(HttpStatus.OK)) {
            starrings = result.getBody();
        }
        return starrings;
    }

    @ResponseBody
    @RequestMapping(value = "/Country", method = RequestMethod.POST)
    public String getAllCountry() {
        return doRead("countryJson");
    }

    @ResponseBody
    @RequestMapping(value = "/Category", method = RequestMethod.POST)
    public String getAllCategory() {
        return doRead("categoryJson");
    }
    @ResponseBody
    @RequestMapping(value = "/Sort", method = RequestMethod.GET)
    public String getAllSort() {
        return doRead("sortJson");
    }

    @ResponseBody
    @RequestMapping(value = "/PublishedYear", method = RequestMethod.POST)
    public String getAllPublishedYear() {
        return doRead("publishedYearJson");
    }

    @ResponseBody
    @RequestMapping(value = "/MovieSerie", method = RequestMethod.POST)
    public List<MovieSearch> getAllMovieSerie() {
        logger.info("begin get movie serie");
        List<MovieSearch> movieSerie = null;
        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<List<MovieSearch>> responseType = new ParameterizedTypeReference<List<MovieSearch>>() {
        };
        ResponseEntity<List<MovieSearch>> result = restTemplate.exchange(
                LinkService.SERIE_GETALL, HttpMethod.GET, null, responseType);

        if (result.getStatusCode().equals(HttpStatus.OK)) {
            movieSerie = result.getBody();
        }
        return movieSerie;
    }

    @RequestMapping(value = "/movieSearch", method = RequestMethod.POST)
    public String getAllFilm() {
        return doRead("movieSearch");
    }

    private String doRead(String fileName) {
        logger.info(fileName + ".json");
        String path = ResourcesFolderUtility.getPathFromResourceFolder(
                UploadController.class, fileName + ".json");
        StringBuffer stringBuffer = null;
        BufferedReader reader = null;
        try {
            File file = new File(path);
            reader = new BufferedReader(new FileReader(file));
            stringBuffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
                line = reader.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        logger.info(stringBuffer.toString());
        return stringBuffer.toString().trim();
    }

}

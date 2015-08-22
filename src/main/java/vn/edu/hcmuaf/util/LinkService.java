package vn.edu.hcmuaf.util;

public class LinkService {

	// Search Film
	public static final String TOPIMDB = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/film/topIMDB";
	public static final String TOPNEW = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/film/topNew";
	public static final String RELATED_MOVIES = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/film/relatedMovies/{filmId}";
	public static final String SEARCH_FILM = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/search/{searchData}/all";
	public static final String SEARCH_FILM_BY_STARRING = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/search/{searchData}/starring";
	public static final String SEARCH_FILM_BY_DIRECTOR = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/search/{searchData}/director";
	public static final String SEARCH_ALL_FILM = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/search/all/film";
	public static final String SEARCH_FILM_JSON = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/film/all.json";
	
	// Movie
	public static final String MOVIE_GETBY_ID = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/movie/get/{movieId}";
	public static final String MOVIE_GETALL = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/movie/all";
	public static final String MOVIE_ADD = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/movie/add";
	public static final String MOVIE_UPDATE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/movie/update";

	// Serie
	public static final String SERIE_GETBY_ID = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/serie/get/{serieId}";
	public static final String SERIE_GETALL = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/serie/all";
	public static final String SERIE_ADD = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/serie/add";
	public static final String DERIE_DELETE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/serie/delete/{serieId}";
	public static final String SERIE_UPDATE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/serie/update";

	// Episode
	public static final String EPISODE_GETBY_ID = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/episode/get/{epId}";
	public static final String EPISODE_GETALL = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/episode/all";
	public static final String EPISODE_ADD = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/episode/add";
	public static final String EPISODE_GETBY_SERIE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/episode/getBySerie/{serieId}/{noEpisode}";
	public static final String EPISODE_UPDATE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/episode/update";
	public static final String EPISODE_GET_LIST_BY_SERIE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/episode/getListBySerie/{serieId}";
	
	// User
	public static final String USER_GETBY_EMAIL = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/user/get/{email}/login";
	public static final String USER_GETALL = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/user/all";
	public static final String USER_ADD = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/user/add";
	public static final String USER_UPDATE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/user/update";

	// Register Film
	public static final String REGIS_ADD = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/regis/addregis/{email}/{idFilm}/{type}";
	public static final String REGIS_GETBY_ID = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/regis/getById/{regisId}";
	public static final String REGIS_GETBY_EMAIL = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/regis/getListByUser/{email}/user";
	public static final String REGIS_GETBY_EMAIL_IDFILM = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/regis/getRegis/{email}/{idFilm}";
	
	// Re charge
	public static final String RECHARGE_ADD = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/recharge/add/{email}/{seri}";
	public static final String RECHARGE_UPDATE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/recharge/update";
	public static final String RECHARGE_GETBY_ID = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/recharge/getById/{rechargeId}";
	public static final String RECHARGE_GETBY_USER = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/recharge/getByUser/{email}/user";

	// Comment
	public static final String COMMENT_ADD = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/comment/add";
	public static final String COMMENT_GETBY_ID = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/comment/getById/{commentId}";
	public static final String COMMENT_GETBY_USER = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/comment/getByUser/{email}/user";
	public static final String COMMENT_GETBY_MOVIE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/comment/getByFilm/{filmId}";

	// Starring
	public static final String STAR_GETBY_ID = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/starring/get/{starId}";
	public static final String STAR_ADD = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/starring/add";
	public static final String STAR_UPDATE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/starring/update";
	public static final String STAR_GETALL = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/starring/all";

	// Director
	public static final String DIRECTOR_ADD = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/director/add";
	public static final String DIRECTOR_GETBY_ID = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/director/get/{directorId}";
	public static final String DIRECTOR_GETALL = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/director/all";
	public static final String DIRECTOR_UPDATE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/director/update";

	// Country
	public static final String COUNTRY_ADD = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/country/add";
	public static final String COUNTRY_UPDATE = "";
	public static final String COUNTRY_GETBY_ID = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/country/get/{countryId}";
	public static final String COUNTRY_GETALL = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/country/all";

	// Category
	public static final String CATE_ADD = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/category/add";
	public static final String CATE_UPDATE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/category/update";
	public static final String CATE_GETBY_ID = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/category/get/{cateId}";
	public static final String CATE_GETALL = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/category/all";

	// MovieOdd
	public static final String MOVIEODD_GETALL = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/movieodd/all";
	public static final String MOVIEODD_GETBY_VIEW = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/movieodd/getByView";
	public static final String MOVIEODD_GETBY_RATE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/movieodd/getByRate";
	public static final String MOVIEODD_GETBY_ID = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/movieodd/get/{movieId}";

	// http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/

	// Card
	public static final String CARD_ALL = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/card/all";
	public static final String CARD_UNUSED = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/card/all/unused";
	public static final String CARD_UPDATE = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/card/update";
	public static final String CARD_GETBY_ID = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/card/getById/{cardId}";
	public static final String CARD_GETBY_SERI = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/card/getBySeri/{seri}";
	public static final String CARD_ADD = "http://vodservice-leetrong.rhcloud.com/VOD-ServiceProject/card/add/{value}";

}
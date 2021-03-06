'use strict';

/**
 * CarController
 * 
 * @constructor
 */
var ThemPhimController = function($scope, $http) {

	$scope.selection = 'movieSerie';

	$scope.loadPage = function(page) {
		$scope.selection = page;
	};

	// get starrings
	var updateStarringsJson = function() {
		$http.post('GetJsonController/Starring').success(function(data) {
			$scope.starringsS = data;
		});
	};
	updateStarringsJson();
	$scope.shouldUpdateStarringsJson = function() {
		if (localStorage.getItem('shouldUpdateStarringsJson')) {
			console.log('update starring');
			updateStarringsJson();
			localStorage.removeItem('shouldUpdateStarringsJson');
		}
	};

	// get director
	var updateDirectorsJson = function() {
		$http.post('GetJsonController/Director').success(function(data) {
			$scope.directorsS = data;

		});
	};
	updateDirectorsJson();
	$scope.shouldUpdateDirectorsJson = function() {
		if (localStorage.getItem('shouldUpdateDirectorsJson')) {
			console.log('update director');
			updateDirectorsJson();
			localStorage.removeItem('shouldUpdateDirectorsJson');
		}
	};

	// save movie serie
	$scope.addSerieMovie = function(serie) {
		var serieInfo = sessionStorage.getItem("info");
		serie.serie.info = serieInfo;
		serie.serie.image = sessionStorage.getItem("image");
		serie.serie.trailer = sessionStorage.getItem("trailer");
		var req = {
			method : 'POST',
			url : 'ThemPhimController/MovieSerie/Save',
			headers : {
				"Content-Type" : "application/json"
			},
			data : serie
		};
		// alert(serie.image + serie.engName + serie.vnName + serie.imdb
		// + serie.trailer + serie.price + serie.publishedYear
		// + serie.starrings[0].name + serie.director.name
		// + serie.countries[0].name + serie.categories[0].name
		// + serie.numEpisodes + serie.info);

		$http(req).success(function() {
			alert("OK");
			// if save movieSerie Success, update movie serie list in add
			// episode
			$scope.updateMovieSeriesJson();
		}).error(function(response) {
			alert(response);
		});
	};

	// save movie
	$scope.addMovie = function(movie) {
		movie.movie.info = sessionStorage.getItem("info");
		movie.movie.movie_key = sessionStorage.getItem("key");
		movie.movie.image = sessionStorage.getItem("image");
		movie.movie.trailer = sessionStorage.getItem("trailer");
		movie.movie.name = sessionStorage.getItem("name");

		console.log("bg");
		console.log("name " + movie.movie.name);
		console.log("EngName " + movie.movie.engName);
		console.log("vnName " + movie.movie.vnName);
		console.log("IMDB " + movie.movie.imdb);
		console.log("publishedYear " + movie.movie.publishedYear);
		console.log("price " + movie.movie.price);
		console.log("key " + movie.movie.movie_key);
		console.log("image " + movie.movie.image);
		console.log("info" + movie.movie.info);
		console.log("trailer" + movie.movie.trailer);
		console.log("starrings " + movie.movie.starrings[0].name);
		console.log("director " + movie.movie.director.name);
		console.log("countries " + movie.movie.countries[0].name);
		console.log("categories " + movie.movie.categories[0].name);
		var req = {
			method : 'POST',
			url : 'ThemPhimController/Movie/Save',
			headers : {
				"Content-Type" : "application/json"
			},
			data : movie
		};
		$http(req).success(function() {
			alert("OK");
		}).error(function(error) {
			alert(error);
		});
	};

	// save episode
	$scope.addEpisode = function(episode) {
		episode.episode.movie_key = sessionStorage.getItem("key");
		episode.episode.name = sessionStorage.getItem("name");
		console.log("ep key " + episode.episode.movie_key);
		console.log("ep name " + episode.episode.name);
		console.log("ep num " + episode.episode.noEpisode);
		console.log("movieSerie name " + episode.episode.movieSerie.name);
		var req = {
			method : 'POST',
			url : 'ThemPhimController/Episode/Save',
			headers : {
				"Content-Type" : "application/json"
			},
			data : episode
		};
		$http(req).success(function() {
			alert("OK");
		}).error(function(error) {
			alert(error);
		});
	};

	// update movie serie
	$scope.updateMovieSeriesJson = function() {
		$http.get('GetJsonController/MovieSerie').success(function(data) {
			$scope.movieSeriesS = data;
		});
	};
	$scope.updateMovieSeriesJson();
};
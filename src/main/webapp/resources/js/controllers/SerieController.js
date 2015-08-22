/**
 * 
 */
var SerieController = function($scope, $rootScope, $http, $location, $modal,
		RegisService, FilmUtilityService, SerieService) {
	var firstEpisode = null;
	var allStarring = [];

	$scope.showMovieDetail = function(movieId) {
		var req3 = {
			method : 'GET',
			url : 'EpisodeController/episodes/' + movieId,
			headers : {
				"Content-Type" : "application/json"
			}
		};

		$http(req3).success(function(data3) {
			$scope.episodes = data3;
			if (data3.length > 0) {
				firstEpisode = data3[0];
				alert(firstEpisode);
			}
			sessionStorage.setItem("episodes", data3);
		}).error(function() {
			alert("ERROR");
		});

		FilmUtilityService.getComments(movieId).success(function(data2) {
			$rootScope.comments = data2;
			sessionStorage.setItem("comments", data2);
		}).error(function() {
			alert("ERROR");
		});

		SerieService.getSerie(movieId).success(function(data) {
			$rootScope.movie = data.serie;
			$rootScope.movieInfo = data.serie.info;
			allStarring = data.serie.starrings;
			$scope.nextStarring();

			// Check regisFilm
			RegisService.checkRegis(data.serie).success(function(dataRegis) {
				$scope.hasBought = dataRegis;
				sessionStorage.setItem("hasBought", dataRegis);
				if (dataRegis == "OK") {

				}
			}).error(function() {
				alert("ERROR");
			});

		}).error(function() {
			alert("ERROR");
		});
	};

	var movieId = sessionStorage.getItem("phimBoId");

	$scope.showMovieDetail(movieId);

	$scope.showSerie = function(movieId) {
		sessionStorage.removeItem("phimBoId");
		sessionStorage.setItem("phimBoId", movieId);
		$scope.showMovieDetail(movieId);
	};

	// create function to show starrings when click next btn

	var starring = [];
	var currentStarringIndex = 0;
	$scope.nextStarring = function() {
		starring = [];
		if (currentStarringIndex >= allStarring.length) {
			currentStarringIndex = 0;
		}
		var i = 0;
		for (i = currentStarringIndex; i < currentStarringIndex + 5; i++) {
			starring.push(allStarring[i]);
		}
		currentStarringIndex += 5;
		$scope.starrings = starring;
	};
	
	// load relative movies
	 $rootScope.allRelativeMovies = [];
	var loadRelativeMovie = function(movieId) {
		FilmUtilityService.loadRelativeMovie(movieId).success(function(data) {
			$rootScope.allRelativeMovies = data;
			// show relative movie
			$scope.nextRelativeMovies();
		}).error(function() {
			alert("ERROR");
		});

	};
	loadRelativeMovie(movieId);

	// create function to show relative movie when click next btn
	var relativeMovies = [];
	var currentRelativeMoviesIndex = 0;
	$scope.nextRelativeMovies = function() {
		relativeMovies = [];
		if (currentRelativeMoviesIndex >= $rootScope.allRelativeMovies.length) {
			currentRelativeMoviesIndex = 0;
		}
		var i = 0;
		for (i = currentRelativeMoviesIndex; i < currentRelativeMoviesIndex + 5; i++) {
			relativeMovies.push($rootScope.allRelativeMovies[i]);
		}
		currentRelativeMoviesIndex += 5;
		$scope.relativeMovies = relativeMovies;
	};

	// load hot movies
	// $rootScope.allHotMovies = [];
	var loadHotMovie = function(movie) {
		FilmUtilityService.loadHotMovie().success(function(data) {
			$rootScope.allHotMovies = data;

			// show Hot movie
			$scope.nextHotMovies();
		}).error(function() {
			alert("ERROR");
		});

	};
	loadHotMovie();

	// create function to show hot movie when click next btn
	var hotMovies = [];
	var currentHotMoviesIndex = 0;
	$scope.nextHotMovies = function() {
		hotMovies = [];
		if (currentHotMoviesIndex >= $rootScope.allHotMovies.length) {
			currentHotMoviesIndex = 0;
		}
		var i = 0;
		for (i = currentHotMoviesIndex; i < currentHotMoviesIndex + 5; i++) {
			hotMovies.push($rootScope.allHotMovies[i]);
		}
		currentHotMoviesIndex += 5;
		$scope.hotMovies = hotMovies;
	};
	

	// go the the player page
	$scope.playMovie = function(episode) {
		FilmUtilityService.playMovie(episode).success(function() {
			sessionStorage.setItem("source", "CamOnViTatCa.mp4");
//			sessionStorage.setItem("source", episode.name);
			// Distinguish between movie and episode in player controller
			// if this is movie, movieType will be movie
			// and remove serieId in sessionStorage
			sessionStorage.setItem("movieId", episode.id);
			sessionStorage.setItem("movieType", "episode");
			sessionStorage.setItem("serieId", movieId);
			$location.path('/PlayVideo');
		}).error(function() {
			alert("ERROR");
		});
	};

	// Check user have bought episode
	$scope.CheckRegisterEpisode = function(episode) {
		if ($scope.hasBought == "OK") {
			alert("Da dang ki ca bo. Xem Phim");
			$scope.playMovie(episode);
		} else {
			RegisService.checkRegis(episode).success(function(data) {
				if (data == "OK") {
					$scope.playMovie(episode);
				} else {
					alert("Khong the xem phim");
					$scope.openRegisterDialog(episode);
				}
			}).error(function() {
				alert("ERROR");
			});
		}
	};

	$scope.openRegisterDialog = function(film) {
		var email = $rootScope.user.email;
		if (email != null) {
			var modalInstance = $modal.open({
				templateUrl : 'myModalContent.html',
				controller : ModalInstanceCtrl,
				resolve : {
					film : function() {
						return film;
					}
				}
			});

			modalInstance.result.then(function(film) {
				RegisService.RegisFilm(film).success(
						function(data) {
							if (data == "OK") {
								alert("Giao dich thanh cong");
								if (firstEpisode != null
										|| angular.isDefined(firstEpisode)) {
									$scope.playMovie(firstEpisode);
								}
							} else {
								alert("Giao dich khong thanh cong");
							}
						}).error(function() {
					alert("ERROR");
				});
			}, function() {

			});
		} else {
			alert("Please login");
			$location.path("/Login");
		}
		;
	};
};

var ModalInstanceCtrl = function($scope, $modalInstance, film) {

	$scope.film = film;

	$scope.ok = function() {
		alert("OK");
		$modalInstance.close(film);
	};

	$scope.cancel = function() {
		alert("Cancel");
		$modalInstance.dismiss('cancel');
	};
};
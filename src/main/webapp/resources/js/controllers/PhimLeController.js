/**
 * 
 */
var PhimLeController = function($scope, $http, $location, $rootScope) {

	$scope.PhimLe = function() {
		var req = {
			method : 'GET',
			url : 'PhimLeController/GetListPhimLe',
			headers : {
				"Content-Type" : "application/json"
			},
		};
		$http(req).success(function(data) {
			$rootScope.movies = data;
		}).error(function(error) {
			console.error(error);
		});
	};

	$scope.PhimLe();

	$scope.categoryFilm = function() {
		var cateId = $scope.category.id;
		var req = {
			method : 'GET',
			url : 'PhimLeController/movie/getByCategory/' + cateId,
			headers : {
				"Content-Type" : "application/json"
			},
		};
		$http(req).success(function(data) {
			$rootScope.movies = data;
		});
	};

	$scope.countryFilm = function() {
		var countryId = $scope.country.id;
		var req = {
			method : 'GET',
			url : 'PhimLeController/movie/getByCountry/' + countryId,
			headers : {
				"Content-Type" : "application/json"
			},
		};
		$http(req).success(function(data) {
			$rootScope.movies = data;
		});
	};

	$scope.sortFilm = function() {

		var sortId = $scope.sort.id;
		var urlStr;
		if (sortId == 1) {
			urlStr = "PhimLeController/GetListPhimLe";
		} else if (sortId == 2) {
			urlStr = "PhimLeController/movie/getByView";
		} else {
			urlStr = "PhimLeController/movie/getByRate";
		}
		var req = {
			method : 'GET',
			url : urlStr,
			headers : {
				"Content-Type" : "application/json"
			},
		};
		$http(req).success(function(data) {
			$rootScope.movies = data;
		});
	};

	$scope.publishFilm = function() {
		var year = $scope.publishYear.id;
		var urlStr;
		if (year == 1) {
			urlStr = "PhimLeController/GetListPhimLe";
		} else {
			urlStr = "PhimLeController/movie/getByYear/" + year;
		}
		var req = {
			method : 'GET',
			url : urlStr,
			headers : {
				"Content-Type" : "application/json"
			},
		};
		$http(req).success(function(data) {
			$rootScope.movies = data;
		});
	};

	$scope.ChiTiet = function(movieId) {
		sessionStorage.setItem("movieId", movieId);
		$location.path('/ChiTiet');
	};

};
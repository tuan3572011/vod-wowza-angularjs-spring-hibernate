/**
 * 
 */
var PhimBoController = function($scope, $location, $rootScope, $http) {

	$scope.PhimBo = function() {
		var req = {
			method : 'GET',
			url : 'PhimBoController/GetListSerie',
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

	$scope.PhimBo();

	$scope.categoryFilm = function() {
		var cateId = $scope.category.id;
		var req = {
			method : 'GET',
			url : 'PhimBoController/serie/getByCategory/' + cateId,
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
			url : 'PhimBoController/serie/getByCountry/' + countryId,
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
			urlStr = "PhimBoController/GetListSerie";
		} else {
			urlStr = "PhimBoController/serie/getByYear/" + year;
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

	$scope.SerieDetail = function(movieId) {
		$rootScope.phimBoId = movieId;
		sessionStorage.setItem("phimBoId", movieId);
		$location.path('/SerieDetail');
	};

	$scope.readJsonSearch = function() {
		var req = {
			method : 'GET',
			url : 'PhimBoController/jsonsearch',
			headers : {
				"Content-Type" : "application/json"
			}
		};

		$http(req).success(function(data) {
			$rootScope.jsonSearch = data;
		}).error(function(response) {
			alert(response);
		});
	};

	$scope.readJsonSearch();

};
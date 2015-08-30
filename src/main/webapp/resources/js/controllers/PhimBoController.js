/**
 * 
 */
var PhimBoController = function($scope, $location, $rootScope, $http) {

	$scope.sorts = [ {
		id : 1,
		name : 'Tất cả'
	}, {
		id : 2,
		name : 'Lượt xem'
	}, {
		id : 3,
		name : 'Đánh giá'
	} ];
	$scope.publishYears = [ {
		id : 1,
		name : 'Tất cả'
	}, {
		id : 2015,
		name : '2015'
	}, {
		id : 2014,
		name : '2014'
	}, {
		id : 2013,
		name : '2013'
	}, {
		id : 2012,
		name : '2012'
	}, {
		id : 2011,
		name : '2011'
	}, {
		id : 2010,
		name : 'Trước 2011'
	} ];

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

	// get category
	$http.post('GetJsonController/Category').success(function(data) {
		$scope.categoriesS = data;
	});
	// get country
	$http.post('GetJsonController/Country').success(function(data) {
		$scope.countriesS = data;
	});

};
/**
 * 
 */
var PhimLeController = function($scope, $http, $location, $rootScope) {
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
		alert(sortId);
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
			urlStr = "PhimLeController//movie/getByYear/" + year;
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

	// get category
	$http.post('GetJsonController/Category').success(function(data) {
		$scope.categoriesS = data;
	});
	// get country
	$http.post('GetJsonController/Country').success(function(data) {
		$scope.countriesS = data;
	});

	$scope.ChiTiet = function(movieId) {
		sessionStorage.setItem("movieId", movieId);
		$location.path('/ChiTiet');
	};

};
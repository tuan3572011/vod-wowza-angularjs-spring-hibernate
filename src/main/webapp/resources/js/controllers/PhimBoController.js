/**
 * 
 */
var PhimBoController = function($scope, $location, $rootScope, $http) {

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
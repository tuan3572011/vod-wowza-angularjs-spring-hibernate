/**
 * 
 */
var PhimLeController = function($scope, $http, $location) {

	$scope.ChiTiet = function(movieId) {
		sessionStorage.setItem("movieId", movieId);
		$location.path('/ChiTiet');
	};

};
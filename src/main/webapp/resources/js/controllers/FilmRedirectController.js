/**
 * 
 */
var FilmRedirectController = function($scope, $location, $rootScope) {
	$scope.Redirect = function(movieId, movieType) {
		if (movieType == "movie") {
			sessionStorage.setItem("phimLeId", movieId);
			$location.path('/ChiTiet');
		} else {
			sessionStorage.setItem("phimBoId", movieId);
			$location.path('/SerieDetail');
		}
	};
};
/**
 * 
 */
var RegisFilmController = function($scope, $http) {
	$scope.RegisSerie = function(film) {
		var email = "admin@gmail.com";
		var idFilm = film.id;
		var type = film.type;
		var req = {
			method : 'GET',
			url : 'RegisFilmController/addRegis/' + email + '/' + idFilm + '/'
					+ type,
			headers : {
				"Content-Type" : "application/json"
			},
		};

		$http(req).success(function(data) {
			if (data == "OK") {
				alert("Giao dich thanh cong");
			} else {
				alert("Lỗi giao dịch");
			}
		}).error(function(response) {
			alert(response);
		});
	};

	$scope.checkRegis = function(episode) {
		var email = "admin@gmail.com";
		var idFilm = episode.id;
//		alert(episode.serie.id);
		var req = {
			method : 'GET',
			url : 'RegisFilmController/checkRegis/' + email + '/' + idFilm,
			headers : {
				"Content-Type" : "application/json"
			}
		};
		$http(req).success(function(data) {
			if (data == "OK") {
				alert("Xem phim");
			} else {
				alert("Khong the xem phim");
			}
		}).error(function(response) {
			alert(response);
		});
	};

};
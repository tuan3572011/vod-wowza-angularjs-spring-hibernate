/**
 * 
 */
var RegisterUserController = function($rootScope, $scope, $http, $location) {

	$scope.RegisterUser = function(user) {
		var req = {
				method : 'POST',
				url : 'RegisterUserController/RegisterUser',
				headers : {
					"Content-Type" : "application/json"
				},
				data : user
			};
			$http(req).success(function(data) {
				if(data == "OK"){
					alert("Đăng ký thành công!");
					$location.path("/Login");
				}else{
					alert("Lỗi!");
				}
			}).error(function() {
				alert("ERROR");
			});
	};

};
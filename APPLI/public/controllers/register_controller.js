'use strict'

var mod = angular.module("register", ["ngRoute"]);

mod.config( ["$routeProvider", function($routeProvider) {

    $routeProvider.when('/register', {
      templateUrl: 'pages/register.html',
      controller: 'registerCtrl'
    });
}]);

mod.controller('registerCtrl', function($scope, $http, $location) {
  $scope.register = function() {
    $http({
      method: 'POST',
      url: '/register',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      transformRequest: function(obj) {
          var str = [];
          for(var p in obj)
          str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
          return str.join("&");
      },
      data: {name: $scope.name, email: $scope.email, password: $scope.password}
  }).then(function (response) {
      if(response.data == 200)
        $location.path("/profile");
      else {
        alert(response.data);
      }
  });
  }
});

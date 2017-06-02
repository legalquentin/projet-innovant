'use strict'

var mod = angular.module("login", ["ngRoute"]);

mod.config( ["$routeProvider", function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: 'pages/login.html',
    controller: 'loginCtrl'
  });
}]);

mod.controller('loginCtrl', function($scope, $http, $location) {
  $scope.login = function() {
    $http({
      method: 'POST',
      url: '/connection',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      transformRequest: function(obj) {
          var str = [];
          for(var p in obj)
          str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
          return str.join("&");
      },
      data: {email: $scope.email, password: $scope.password}
  }).then(function (response) {
      if(response.data == 200)
        $location.path("/profile");
      else {
        alert("WRONG CREDENTIALS !")
      }
  });
  }
});

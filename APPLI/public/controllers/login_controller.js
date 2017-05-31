'use strict'

var mod = angular.module("login", ["ngRoute"]);

mod.config( ["$routeProvider", function($routeProvider) {
    $routeProvider.when('/login', {
      templateUrl: 'pages/login.html',
      controller: 'loginCtrl'
    });
}]);

mod.controller('loginCtrl', function() {

})

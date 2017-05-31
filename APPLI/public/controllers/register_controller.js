'use strict'

var mod = angular.module("register", ["ngRoute"]);

mod.config( ["$routeProvider", function($routeProvider) {
    
    $routeProvider.when('/register', {
      templateUrl: 'pages/register.html',
      controller: 'registerCtrl'
    });
}]);

mod.controller('register', function() {

});

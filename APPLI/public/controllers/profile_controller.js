'use strict'

var mod = angular.module("profile", ["ngRoute"]);

mod.config( ["$routeProvider", function($routeProvider) {
    $routeProvider.when('/profile', {
      templateUrl: 'pages/profile.html',
      controller: 'profileCtrl'
    });
}]);

mod.controller('profileCtrl', function() {

})

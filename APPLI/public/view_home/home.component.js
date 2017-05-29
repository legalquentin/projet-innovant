'use strict';

var mod = angular.module('home');

mod.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {
    templateUrl: '/view_home/home.template.html',
    controller: 'homeCtrl'
  });
}]);

mod.controller('homeCtrl', function($scope, $http) {
  $http.get('http://127.0.0.1:8080/greeting').
      then(function(response) {
          $scope.greeting = response;
      });
});

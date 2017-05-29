'use strict';

var mod = angular.module('map');

mod.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/map', {
    templateUrl: '/view_map/map.template.html',
    controller: 'mapCtrl'
  });
}]);

mod.controller('mapCtrl', function($scope, $http) {
  $http.get('http://rest-service.guides.spring.io/greeting').
      then(function(response) {
          $scope.greeting = response.data;
      });
});

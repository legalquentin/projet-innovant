'use strict';

var mod = angular.module('connect');

mod.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/connect', {
    templateUrl: '/view_connect/connect.template.html',
    controller: 'connectCtrl'
  });
}]);

mod.controller('connectCtrl', function($scope) {
  // $('#noHap').modal('show');
});

var mod = angular.module('navbar');

mod.component('navbar', {
  templateUrl: 'view_navbar/navbar.template.html',
  controller: 'navbarCtrl'
});

mod.factory('navbarService', function() {
  var navbarService = {
    searchQuery: ''
  };
  return navbarService;
});

mod.controller('navbarCtrl', function($scope, $interval, $route, $location, navbarService) {
});

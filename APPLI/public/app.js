'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'ngMap',
  'navbar',
  'home',
  'chat',
  'connect',
  'map',
  'settings'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  // $locationProvider.hashPrefix('!');
  $routeProvider.otherwise({redirectTo: '/connect'});
}]);

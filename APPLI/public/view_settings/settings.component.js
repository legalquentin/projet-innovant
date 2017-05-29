'use strict';

var mod = angular.module('settings');

mod.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/settings', {
    templateUrl: '/view_settings/settings.template.html',
    controller: 'settingsCtrl'
  });
}]);

mod.controller('settingsCtrl', function($scope) {
});

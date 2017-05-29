'use strict';

var mod = angular.module('chat');

mod.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/chat', {
    templateUrl: '/chat/chat.template.html',
    controller: 'chatCtrl'
  });
}]);

mod.controller('chatCtrl', function($scope) {
});

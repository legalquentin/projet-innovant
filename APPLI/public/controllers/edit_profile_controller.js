'use strict'

var mod = angular.module("editProfile", ["ngRoute"]);

mod.config( ["$routeProvider", function($routeProvider) {
    $routeProvider.when('/edit-profile', {
      templateUrl: 'pages/edit_profile.html',
      controller: 'editProfileCtrl'
    });
}]);

mod.controller('editProfileCtrl', function() {

})

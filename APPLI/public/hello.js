angular.module('demo', [])
.controller('Hello', function($scope, $http) {
    $http.get('/greetings').
        then(function(response) {
            $scope.greeting = response.data;
        });
});

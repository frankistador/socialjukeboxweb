'use strict';

angular.module('socialjukeboxwebApp')
    .controller('MainController', function ($scope, Principal, $state, $timeout, $location, Auth) {
    
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        
        $scope.go = function (path) {
		  $location.path(path);
		};

        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        
        $scope.login = function () {
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                if ($rootScope.previousStateName === 'register') {
                    $state.go('home');
                } else {
                    $rootScope.back();
                }
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
        
        
        
    });

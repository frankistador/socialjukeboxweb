'use strict';

angular.module('socialjukeboxwebApp')
    .controller('addSongController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    });

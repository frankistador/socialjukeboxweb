'use strict';

angular.module('socialjukeboxwebApp')
    .controller('PlaylistDetailController', function ($scope, $stateParams, Playlist, Song) {
        $scope.playlist = {};
        $scope.load = function (id) {
            Playlist.get({id: id}, function(result) {
              $scope.playlist = result;
            });
        };
        $scope.load($stateParams.id);
    });

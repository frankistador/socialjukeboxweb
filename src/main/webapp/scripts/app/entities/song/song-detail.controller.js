'use strict';

angular.module('socialjukeboxwebApp')
    .controller('SongDetailController', function ($scope, $stateParams, Song, Playlist) {
        $scope.song = {};
        $scope.load = function (id) {
            Song.get({id: id}, function(result) {
              $scope.song = result;
            });
        };
        $scope.load($stateParams.id);
    });

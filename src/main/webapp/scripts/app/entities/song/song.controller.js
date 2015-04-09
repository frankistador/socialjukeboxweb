'use strict';

angular.module('socialjukeboxwebApp')
    .controller('SongController', function ($scope, Song, Playlist) {
        $scope.songs = [];
        $scope.playlists = Playlist.query();
        $scope.loadAll = function() {
            Song.query(function(result) {
               $scope.songs = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Song.update($scope.song,
                function () {
                    $scope.loadAll();
                    $('#saveSongModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Song.get({id: id}, function(result) {
                $scope.song = result;
                $('#saveSongModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Song.get({id: id}, function(result) {
                $scope.song = result;
                $('#deleteSongConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Song.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSongConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.song = {name: null, url: null, artist: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });

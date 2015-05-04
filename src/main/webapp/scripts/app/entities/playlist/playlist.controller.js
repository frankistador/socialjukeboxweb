'use strict';

angular.module('socialjukeboxwebApp')
    .controller('PlaylistController', function ($scope, Playlist, Song, ParseLinks, Principal) {
    	
    	 Principal.identity().then(function(account) {
    		 $scope.account = account;
    		 $scope.isAuthenticated = Principal.isAuthenticated;
    	 });
        $scope.playlists = [];
        $scope.songs = Song.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Playlist.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.playlists.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.playlists = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
        	$scope.playlist.host = $scope.account;
            Playlist.update($scope.playlist,
                function () {
                    $scope.reset();
                    $('#savePlaylistModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Playlist.get({id: id}, function(result) {
                $scope.playlist = result;
                $('#savePlaylistModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Playlist.get({id: id}, function(result) {
                $scope.playlist = result;
                $('#deletePlaylistConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Playlist.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deletePlaylistConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.playlist = {name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });

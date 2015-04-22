'use strict';

angular.module('socialjukeboxwebApp')
    .controller('playlistsController', function ($scope, Playlist, ParseLinks) {
    
    	 $scope.playlists = [];
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
 

    });

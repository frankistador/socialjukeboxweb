'use strict';

angular.module('socialjukeboxwebApp').controller('PlaylistCreationController', function ($scope, Playlist, Principal,$http) {
   
    $scope.playlist = {
        id : null,
        name : "",
        songs : [],
        host : null
    };
    
    Principal.identity().then(function(account) {
        $scope.account = account;
        $scope.playlist.host = account;
        $scope.isAuthenticated = Principal.isAuthenticated;
    });
    
    $scope.submitNewPlaylist = function(){
    
    	$scope.success=false;
        $scope.error=false; 
        
        Playlist.update($scope.playlist, function () {
            $scope.playlist = {
                id : null,
                name : "",
                songs : [],
                host : null
            };
            $scope.success = true;
        });
    }

});

'use strict';

angular.module('socialjukeboxwebApp').
    controller('userPlaylistController', function ($scope, Principal, ngTableParams,$stateParams,$http) {
	$scope.songs = [];
	$scope.urlParam=$stateParams.id;
    		
    
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        function getPlaylist() {
            $http.get(window.location.pathname+"api/playlists/"+$scope.urlParam).success(function(data) {
                $scope.record = data;
                $scope.songs = data.songs;
                $scope.playlistName = data.name;
            })
		};
		
		getPlaylist();	
		     
		      
		$scope.postSong = function() {
 
    		$scope.success=false;
        	$scope.error=false;   		
        	$http.post(window.location.pathname+'api/submission/submit?name='+$scope.title+'&idPlaylist='+$scope.urlParam).
    		  success(function(data, status, headers, config) {
    			  $scope.success=true;
    			  $scope.submittedSong=$scope.title;
    			  $scope.title="";
    		  }).
    		  
    		  error(function(data, status, headers, config) {
    			  $scope.error=true;
    			  $scope.title=""; 
    		  }); 
    	}
    	
    });

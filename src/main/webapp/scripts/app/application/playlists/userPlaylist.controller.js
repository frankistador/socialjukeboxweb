'use strict';

angular.module('socialjukeboxwebApp').
    controller('userPlaylistController', function ($scope, Principal, ngTableParams,$stateParams,$http) {
	
	$scope.songs = "";
	$scope.urlParam=$stateParams.id;
    
     $scope.tableParams = new ngTableParams({
          page: 1,            
          count: 10           
      }, {
          total: $scope.songs.length, 
          getData: function($defer, params) {
          	$defer.resolve($scope.songs.slice((params.page() - 1) * params.count(), params.page() * params.count()));
             
          }
      });



        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        function getPlaylist() {
            $http.get("/api/playlists/"+$scope.urlParam).success(function(data) {
                $scope.record = data;
                $scope.songs = data.songs;
                $scope.playlistName = data.name;

              })
		};
		getPlaylist();	
		
		$scope.postSong = function() {
 
    		$scope.success=false;
        	$scope.error=false;   		

    		$http.post('/api/submission/submit?name='+$scope.title).
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

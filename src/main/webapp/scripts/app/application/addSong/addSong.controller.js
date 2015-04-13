'use strict';

angular.module('socialjukeboxwebApp')
    .controller('addSongController', function ($scope, $http) {
    	
    
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

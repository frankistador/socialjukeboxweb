'use strict';

angular.module('socialjukeboxwebApp')
    .controller('playlistsController', function ($scope) {
    	
    
    	 $scope.playlists = [{id : 1, name : "play1"}, {id : 2, name : "play2"}, {id : 3, name : "play3"}];
         $scope.page = 1;
 

    });

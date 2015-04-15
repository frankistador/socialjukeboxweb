'use strict';

angular.module('socialjukeboxwebApp').
    controller('userPlaylistController', function ($scope, Principal, ngTableParams,$stateParams,$http) {

     $scope.songs = [{title: "coucou c'est les sauterelles aaaaaaaaaaaaaa aaaaaaaaaaaaaaa", artist: "à que c'est patoche aaaaaaaaaaaaaaa aaaaaaaaa"},{title: "test1", artist:"jacques"},{title: "test2", artist: "jean"}];
$scope.urlParam=$stateParams.id;
   $scope.tableParams = new ngTableParams({
          page: 1,            // show first page
          count: 10           // count per page
      }, {
          total: $scope.songs.length, // length of data
          getData: function($defer, params) {
              $defer.resolve($scope.songs.slice((params.page() - 1) * params.count(), params.page() * params.count()));
          }
      });

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        function test($scope,$routeParams,$http) {
            $http.get("/get/"+$routeParams.productId).success(function(data) {
                $scope.record = data;

            });
}
    });

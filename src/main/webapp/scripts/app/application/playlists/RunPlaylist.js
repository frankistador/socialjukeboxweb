'use strict';

angular.module('socialjukeboxwebApp')
    .config(function ($stateProvider) {
        $stateProvider
           .state('RunPlaylist', {
            parent: 'application',
            url: '/RunPlaylist/:id',
            data: {
                roles: []

            },
            views: {
                'content@': {
                    templateUrl: 'scripts/app/application/playlists/RunPlaylist.html',
                    controller: 'RunPlaylistController'
                }
            },
            resolve: {
            
            }
        })
        
    });

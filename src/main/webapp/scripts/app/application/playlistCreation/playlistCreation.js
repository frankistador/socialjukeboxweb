'use strict';

angular.module('socialjukeboxwebApp')
    .config(function ($stateProvider) {
    $stateProvider
        .state('playlistCreation', {
        parent: 'application',
        url: '/playlistCreation',
        data: {
            roles: ['ROLE_USER'],
            pageTitle : 'Create a playlist'
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/application/playlistCreation/playlistCreation.html',
                controller: 'PlaylistCreationController'
            }
        },
        resolve: {
        }
    })

});

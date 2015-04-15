'use strict';

angular.module('socialjukeboxwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('playlists', {
                parent: 'application',
                url: '/playlists',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle : 'playlists.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/application/playlists/playlists.html',
                        controller: 'playlistsController'
                    }
                },
                resolve: {
                    /*mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('playlists');
                        return $translate.refresh();
                    }]*/
                }
            });
    });

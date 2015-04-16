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
            })
        .state('userPlaylist', {
            parent: 'application',
            url: '/playlists/:id',
            data: {
                roles: [],
                pageTitle : 'userPlaylist.title'

            },
            views: {
                'content@': {
                    templateUrl: 'scripts/app/application/playlists/userPlaylist.html',
                    controller: 'userPlaylistController'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('userPlaylist');
                    return $translate.refresh();
                }]
            }
        });
    });

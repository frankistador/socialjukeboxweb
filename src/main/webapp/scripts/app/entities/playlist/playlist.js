'use strict';

angular.module('socialjukeboxwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('playlist', {
                parent: 'entity',
                url: '/playlist',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'socialjukeboxwebApp.playlist.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/playlist/playlists.html',
                        controller: 'PlaylistController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('playlist');
                        return $translate.refresh();
                    }]
                }
            })
            .state('playlistDetail', {
                parent: 'entity',
                url: '/playlist/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'socialjukeboxwebApp.playlist.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/playlist/playlist-detail.html',
                        controller: 'PlaylistDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('playlist');
                        return $translate.refresh();
                    }]
                }
            });
    });

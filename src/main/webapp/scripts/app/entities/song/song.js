'use strict';

angular.module('socialjukeboxwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('song', {
                parent: 'entity',
                url: '/song',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'socialjukeboxwebApp.song.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/song/songs.html',
                        controller: 'SongController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('song');
                        return $translate.refresh();
                    }]
                }
            })
            .state('songDetail', {
                parent: 'entity',
                url: '/song/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'socialjukeboxwebApp.song.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/song/song-detail.html',
                        controller: 'SongDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('song');
                        return $translate.refresh();
                    }]
                }
            });
    });

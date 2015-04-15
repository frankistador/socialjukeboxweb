'use strict';

angular.module('socialjukeboxwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userPlaylist', {
                parent: 'application',
                url: '/userPlaylist/:id',
                data: {
                    roles: [],
                    pageTitle : 'userPlaylist.title'

                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/application/userPlaylist/userPlaylist.html',
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

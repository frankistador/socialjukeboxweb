'use strict';

angular.module('socialjukeboxwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('addSong', {
                parent: 'application',
                url: '/addSong',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle : 'addSong.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/application/addSong/addSong.html',
                        controller: 'addSongController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('addSong');
                        return $translate.refresh();
                    }]
                }
            });
    });

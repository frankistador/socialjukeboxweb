'use strict';

angular.module('socialjukeboxwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('application', {
                abstract: true,
                parent: 'site'
            });
    });

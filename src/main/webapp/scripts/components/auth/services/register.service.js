'use strict';

angular.module('socialjukeboxwebApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });



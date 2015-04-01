'use strict';

angular.module('socialjukeboxwebApp')
    .factory('Password', function ($resource) {
        return $resource('api/account/change_password', {}, {
        });
    });

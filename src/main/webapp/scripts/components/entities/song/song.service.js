'use strict';

angular.module('socialjukeboxwebApp')
    .factory('Song', function ($resource) {
        return $resource('api/songs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

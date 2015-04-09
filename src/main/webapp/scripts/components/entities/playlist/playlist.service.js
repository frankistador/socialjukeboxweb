'use strict';

angular.module('socialjukeboxwebApp')
    .factory('Playlist', function ($resource) {
        return $resource('api/playlists/:id', {}, {
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

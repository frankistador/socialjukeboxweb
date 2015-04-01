'use strict';

angular.module('socialjukeboxwebApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });

'use strict';

angular.module('customercontactApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });

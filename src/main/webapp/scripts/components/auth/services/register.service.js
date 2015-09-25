'use strict';

angular.module('customercontactApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });



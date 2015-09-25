'use strict';

angular.module('customercontactApp')
    .factory('ContactDetails', function ($resource, DateUtils) {
        return $resource('api/contactDetailss/:id', {}, {
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

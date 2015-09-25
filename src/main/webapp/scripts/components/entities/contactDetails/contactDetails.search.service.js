'use strict';

angular.module('customercontactApp')
    .factory('ContactDetailsSearch', function ($resource) {
        return $resource('api/_search/contactDetailss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

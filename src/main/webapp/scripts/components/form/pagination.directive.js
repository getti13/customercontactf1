/* globals $ */
'use strict';

angular.module('customercontactApp')
    .directive('customercontactAppPagination', function() {
        return {
            templateUrl: 'scripts/components/form/pagination.html'
        };
    });

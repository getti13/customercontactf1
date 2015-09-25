 'use strict';

angular.module('customercontactApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-customercontactApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-customercontactApp-params')});
                }
                return response;
            },
        };
    });
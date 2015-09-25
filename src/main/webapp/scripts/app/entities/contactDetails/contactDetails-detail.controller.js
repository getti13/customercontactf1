'use strict';

angular.module('customercontactApp')
    .controller('ContactDetailsDetailController', function ($scope, $rootScope, $stateParams, entity, ContactDetails) {
        $scope.contactDetails = entity;
        $scope.load = function (id) {
            ContactDetails.get({id: id}, function(result) {
                $scope.contactDetails = result;
            });
        };
        $rootScope.$on('customercontactApp:contactDetailsUpdate', function(event, result) {
            $scope.contactDetails = result;
        });
    });

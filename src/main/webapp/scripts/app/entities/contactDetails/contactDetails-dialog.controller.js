'use strict';

angular.module('customercontactApp').controller('ContactDetailsDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ContactDetails',
        function($scope, $stateParams, $modalInstance, entity, ContactDetails) {

        $scope.contactDetails = entity;
        $scope.load = function(id) {
            ContactDetails.get({id : id}, function(result) {
                $scope.contactDetails = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('customercontactApp:contactDetailsUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.contactDetails.id != null) {
                ContactDetails.update($scope.contactDetails, onSaveFinished);
            } else {
                ContactDetails.save($scope.contactDetails, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);

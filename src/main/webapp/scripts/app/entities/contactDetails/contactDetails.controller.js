'use strict';

angular.module('customercontactApp')
    .controller('ContactDetailsController', function ($scope, ContactDetails, ContactDetailsSearch, ParseLinks) {
        $scope.contactDetailss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            ContactDetails.query({page: $scope.page, size: 30}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.contactDetailss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            ContactDetails.get({id: id}, function(result) {
                $scope.contactDetails = result;
                $('#deleteContactDetailsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ContactDetails.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteContactDetailsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ContactDetailsSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.contactDetailss = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.contactDetails = {firstName: null, middleName: null, lastName: null, telephone: null, shebaMiles: null, email: null, fax: null, preferedContact: null, preferedLanguage: null, country: null, address: null, city: null, zipcode: null, comment: null, id: null};
        };
    });

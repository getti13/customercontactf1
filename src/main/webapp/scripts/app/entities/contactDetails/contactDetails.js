'use strict';

angular.module('customercontactApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contactDetails', {
                parent: 'entity',
                url: '/contactDetailss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'customercontactApp.contactDetails.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contactDetails/contactDetailss.html',
                        controller: 'ContactDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contactDetails');
                        $translatePartialLoader.addPart('preferedContact');
                        $translatePartialLoader.addPart('preferedLanguage');
                        $translatePartialLoader.addPart('country');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('contactDetails.detail', {
                parent: 'entity',
                url: '/contactDetails/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'customercontactApp.contactDetails.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contactDetails/contactDetails-detail.html',
                        controller: 'ContactDetailsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contactDetails');
                        $translatePartialLoader.addPart('preferedContact');
                        $translatePartialLoader.addPart('preferedLanguage');
                        $translatePartialLoader.addPart('country');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ContactDetails', function($stateParams, ContactDetails) {
                        return ContactDetails.get({id : $stateParams.id});
                    }]
                }
            })
            .state('contactDetails.new', {
                parent: 'contactDetails',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/contactDetails/contactDetails-dialog.html',
                        controller: 'ContactDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {firstName: null, middleName: null, lastName: null, telephone: null, shebaMiles: null, email: null, fax: null, preferedContact: null, preferedLanguage: null, country: null, address: null, city: null, zipcode: null, comment: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('contactDetails', null, { reload: true });
                    }, function() {
                        $state.go('contactDetails');
                    })
                }]
            })
            .state('contactDetails.edit', {
                parent: 'contactDetails',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/contactDetails/contactDetails-dialog.html',
                        controller: 'ContactDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ContactDetails', function(ContactDetails) {
                                return ContactDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contactDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

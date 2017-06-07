(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('invitee', {
            parent: 'entity',
            url: '/invitee',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'schedulyApp.invitee.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/invitee/invitees.html',
                    controller: 'InviteeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('invitee');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('invitee-detail', {
            parent: 'entity',
            url: '/invitee/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'schedulyApp.invitee.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/invitee/invitee-detail.html',
                    controller: 'InviteeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('invitee');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Invitee', function($stateParams, Invitee) {
                    return Invitee.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'invitee',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('invitee-detail.edit', {
            parent: 'invitee-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invitee/invitee-dialog.html',
                    controller: 'InviteeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Invitee', function(Invitee) {
                            return Invitee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('invitee.new', {
            parent: 'invitee',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invitee/invitee-dialog.html',
                    controller: 'InviteeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                email: null,
                                contact: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('invitee', null, { reload: 'invitee' });
                }, function() {
                    $state.go('invitee');
                });
            }]
        })
        .state('invitee.edit', {
            parent: 'invitee',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invitee/invitee-dialog.html',
                    controller: 'InviteeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Invitee', function(Invitee) {
                            return Invitee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('invitee', null, { reload: 'invitee' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('invitee.delete', {
            parent: 'invitee',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invitee/invitee-delete-dialog.html',
                    controller: 'InviteeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Invitee', function(Invitee) {
                            return Invitee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('invitee', null, { reload: 'invitee' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

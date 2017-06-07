(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('week-day', {
            parent: 'entity',
            url: '/week-day',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'schedulyApp.weekDay.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/week-day/week-days.html',
                    controller: 'WeekDayController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('weekDay');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('week-day-detail', {
            parent: 'entity',
            url: '/week-day/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'schedulyApp.weekDay.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/week-day/week-day-detail.html',
                    controller: 'WeekDayDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('weekDay');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WeekDay', function($stateParams, WeekDay) {
                    return WeekDay.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'week-day',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('week-day-detail.edit', {
            parent: 'week-day-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/week-day/week-day-dialog.html',
                    controller: 'WeekDayDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WeekDay', function(WeekDay) {
                            return WeekDay.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('week-day.new', {
            parent: 'week-day',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/week-day/week-day-dialog.html',
                    controller: 'WeekDayDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                available: null,
                                startLimit: null,
                                endLimit: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('week-day', null, { reload: 'week-day' });
                }, function() {
                    $state.go('week-day');
                });
            }]
        })
        .state('week-day.edit', {
            parent: 'week-day',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/week-day/week-day-dialog.html',
                    controller: 'WeekDayDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WeekDay', function(WeekDay) {
                            return WeekDay.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('week-day', null, { reload: 'week-day' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('week-day.delete', {
            parent: 'week-day',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/week-day/week-day-delete-dialog.html',
                    controller: 'WeekDayDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WeekDay', function(WeekDay) {
                            return WeekDay.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('week-day', null, { reload: 'week-day' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

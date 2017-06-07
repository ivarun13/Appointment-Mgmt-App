(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('EventDetailController', EventDetailController);

    EventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Event', 'Appointment', 'WeekDay', 'User'];

    function EventDetailController($scope, $rootScope, $stateParams, previousState, entity, Event, Appointment, WeekDay, User) {
        var vm = this;

        vm.event = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('schedulyApp:eventUpdate', function(event, result) {
            vm.event = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

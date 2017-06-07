(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('AppointmentDetailController', AppointmentDetailController);

    AppointmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Appointment', 'Invitee', 'User', 'Event', 'Day'];

    function AppointmentDetailController($scope, $rootScope, $stateParams, previousState, entity, Appointment, Invitee, User, Event, Day) {
        var vm = this;

        vm.appointment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('schedulyApp:appointmentUpdate', function(event, result) {
            vm.appointment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

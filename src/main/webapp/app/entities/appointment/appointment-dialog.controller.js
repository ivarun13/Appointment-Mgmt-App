(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('AppointmentDialogController', AppointmentDialogController);

    AppointmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Appointment', 'Invitee', 'User', 'Event', 'Day'];

    function AppointmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Appointment, Invitee, User, Event, Day) {
        var vm = this;

        vm.appointment = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.invitees = Invitee.query({filter: 'appointment-is-null'});
        $q.all([vm.appointment.$promise, vm.invitees.$promise]).then(function() {
            if (!vm.appointment.invitee || !vm.appointment.invitee.id) {
                return $q.reject();
            }
            return Invitee.get({id : vm.appointment.invitee.id}).$promise;
        }).then(function(invitee) {
            vm.invitees.push(invitee);
        });
        vm.users = User.query();
        vm.events = Event.query();
        vm.days = Day.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.appointment.id !== null) {
                Appointment.update(vm.appointment, onSaveSuccess, onSaveError);
            } else {
                Appointment.save(vm.appointment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('schedulyApp:appointmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startTime = false;
        vm.datePickerOpenStatus.endTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

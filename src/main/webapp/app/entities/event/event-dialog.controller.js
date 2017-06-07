(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('EventDialogController', EventDialogController);

    EventDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Event', 'Appointment', 'WeekDay', 'User'];

    function EventDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Event, Appointment, WeekDay, User) {
        var vm = this;

        vm.event = entity;
        vm.clear = clear;
        vm.save = save;
        vm.appointments = Appointment.query();
        vm.weekdays = WeekDay.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.event.id !== null) {
                Event.update(vm.event, onSaveSuccess, onSaveError);
            } else {
                Event.save(vm.event, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('schedulyApp:eventUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

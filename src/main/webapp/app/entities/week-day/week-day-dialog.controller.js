(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('WeekDayDialogController', WeekDayDialogController);

    WeekDayDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WeekDay', 'Event'];

    function WeekDayDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WeekDay, Event) {
        var vm = this;

        vm.weekDay = entity;
        vm.clear = clear;
        vm.save = save;
        vm.events = Event.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.weekDay.id !== null) {
                WeekDay.update(vm.weekDay, onSaveSuccess, onSaveError);
            } else {
                WeekDay.save(vm.weekDay, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('schedulyApp:weekDayUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

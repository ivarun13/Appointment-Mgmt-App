(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('WeekDayDeleteController',WeekDayDeleteController);

    WeekDayDeleteController.$inject = ['$uibModalInstance', 'entity', 'WeekDay'];

    function WeekDayDeleteController($uibModalInstance, entity, WeekDay) {
        var vm = this;

        vm.weekDay = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WeekDay.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

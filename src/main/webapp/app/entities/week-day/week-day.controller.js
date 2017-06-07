(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('WeekDayController', WeekDayController);

    WeekDayController.$inject = ['$scope', '$state', 'WeekDay'];

    function WeekDayController ($scope, $state, WeekDay) {
        var vm = this;
        
        vm.weekDays = [];

        loadAll();

        function loadAll() {
            WeekDay.query(function(result) {
                vm.weekDays = result;
            });
        }
    }
})();

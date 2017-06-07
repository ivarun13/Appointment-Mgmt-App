(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('WeekDayDetailController', WeekDayDetailController);

    WeekDayDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WeekDay', 'Event'];

    function WeekDayDetailController($scope, $rootScope, $stateParams, previousState, entity, WeekDay, Event) {
        var vm = this;

        vm.weekDay = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('schedulyApp:weekDayUpdate', function(event, result) {
            vm.weekDay = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

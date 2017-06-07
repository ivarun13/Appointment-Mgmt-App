(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('DayDetailController', DayDetailController);

    DayDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Day', 'Appointment'];

    function DayDetailController($scope, $rootScope, $stateParams, previousState, entity, Day, Appointment) {
        var vm = this;

        vm.day = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('schedulyApp:dayUpdate', function(event, result) {
            vm.day = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

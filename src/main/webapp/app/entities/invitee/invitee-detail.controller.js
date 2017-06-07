(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('InviteeDetailController', InviteeDetailController);

    InviteeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Invitee'];

    function InviteeDetailController($scope, $rootScope, $stateParams, previousState, entity, Invitee) {
        var vm = this;

        vm.invitee = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('schedulyApp:inviteeUpdate', function(event, result) {
            vm.invitee = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

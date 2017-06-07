(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('InviteeController', InviteeController);

    InviteeController.$inject = ['$scope', '$state', 'Invitee'];

    function InviteeController ($scope, $state, Invitee) {
        var vm = this;
        
        vm.invitees = [];

        loadAll();

        function loadAll() {
            Invitee.query(function(result) {
                vm.invitees = result;
            });
        }
    }
})();

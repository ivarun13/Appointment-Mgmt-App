(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('InviteeDeleteController',InviteeDeleteController);

    InviteeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Invitee'];

    function InviteeDeleteController($uibModalInstance, entity, Invitee) {
        var vm = this;

        vm.invitee = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Invitee.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

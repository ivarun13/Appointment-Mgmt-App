(function() {
    'use strict';

    angular
        .module('schedulyApp')
        .controller('InviteeDialogController', InviteeDialogController);

    InviteeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Invitee'];

    function InviteeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Invitee) {
        var vm = this;

        vm.invitee = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.invitee.id !== null) {
                Invitee.update(vm.invitee, onSaveSuccess, onSaveError);
            } else {
                Invitee.save(vm.invitee, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('schedulyApp:inviteeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

'use strict';

describe('Controller Tests', function() {

    describe('Appointment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAppointment, MockInvitee, MockUser, MockEvent, MockDay;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAppointment = jasmine.createSpy('MockAppointment');
            MockInvitee = jasmine.createSpy('MockInvitee');
            MockUser = jasmine.createSpy('MockUser');
            MockEvent = jasmine.createSpy('MockEvent');
            MockDay = jasmine.createSpy('MockDay');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Appointment': MockAppointment,
                'Invitee': MockInvitee,
                'User': MockUser,
                'Event': MockEvent,
                'Day': MockDay
            };
            createController = function() {
                $injector.get('$controller')("AppointmentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'schedulyApp:appointmentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

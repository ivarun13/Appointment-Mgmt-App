(function() {
    'use strict';
    angular
        .module('schedulyApp')
        .factory('WeekDay', WeekDay);

    WeekDay.$inject = ['$resource'];

    function WeekDay ($resource) {
        var resourceUrl =  'api/week-days/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

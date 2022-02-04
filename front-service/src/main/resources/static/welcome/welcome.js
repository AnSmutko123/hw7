angular.module('market-front').controller('welcomeController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadTheMostAddedProductsPerDay = function () {
        $http({
            url: 'http://localhost:5555/analytics/api/v1/products_analytics/added',
            method: 'GET'
        }).then(function (response) {
            $scope.productsAddedPerDay = response.data;
        });
    };

    $scope.loadTheMostBoughtProductsPerMonth = function () {
        $http({
            url: 'http://localhost:5555/analytics/api/v1/products_analytics/bought',
            method: 'GET'
        }).then(function (response) {
            $scope.productsBoughtPerMonth = response.data;
        });
    };

    $scope.loadTheMostAddedProductsPerDay();
    $scope.loadTheMostBoughtProductsPerMonth();

});
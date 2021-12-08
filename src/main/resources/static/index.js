angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app';   // адрес запроса

    $scope.changePage = function (min, max) {
        $http({
            url: contextPath + '/products/change_page',
            method: 'GET',
            params: {
                min: min,
                max: max
            }
        }).then(function (response) {
            $scope.ProductsList = response.data;
        });
    }


    $scope.loadProducts = function () {     // создание функции
        $http.get(contextPath + '/products')  // запрос по адресу
            .then(function (response) {
                $scope.ProductsList = response.data;
            });
    };

    $scope.deleteProduct = function (productId) {
        $http.get(contextPath + '/products/delete/' + productId)  // запрос по адресу
            .then(function (response) {
                $scope.changePage();
            });
    }

    $scope.changeCost = function (productId, delta) {
        $http({
            url: contextPath + '/products/change_cost/',
            method: 'GET',
            params: {
                productId: productId,
                delta : delta
            }
        }).then(function (response) {
            $scope.changePage();
        });
    }

    $scope.filterProductsByCost = function () {
        $http({
            url: contextPath + '/products/between_cost',
            method: 'GET',
            params: {
                min: $scope.filter.min,
                max : $scope.filter.max
            }
        }).then(function (response) {
            $scope.ProductsList = response.data;
        });
    }

    $scope.changePage();

});
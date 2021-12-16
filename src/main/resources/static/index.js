angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1';   // адрес запроса

    $scope.loadProducts = function (pageIndex = 1) {     // создание функции
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_cost: $scope.filter ? $scope.filter.min_cost : null,
                max_cost: $scope.filter ? $scope.filter.max_cost : null,
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
            // console.log(response.data.content);
        });
    };

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + '/products/' + productId)  // запрос по адресу
            .then(function (response) {
                $scope.loadProducts();
            });
    }

    $scope.createProduct = function () {
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.changeCost = function (productId, delta) {
        $http({
            url: contextPath + '/products/change_cost/',
            method: 'PATCH',
            params: {
                productId: productId,
                delta : delta
            }
        }).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + '/products/' + productId)  // запрос по адресу
            .then(function (response) {
                $scope.loadProducts();
            });
    }

    $scope.addProductToCart = function (productId) {
        $http.get(contextPath + '/carts/' + productId)  // запрос по адресу
            .then(function (response) {
                // console.log(response);
                $scope.loadCart();
            });
    }

    $scope.deleteFromCart = function (productId) {
        $http.delete(contextPath + '/carts/' + productId)  // запрос по адресу
            .then(function (response) {
                $scope.loadCart();
                // console.log(response);
            });
    }

    $scope.loadCart = function () {     // создание функции
        $http({
            url: contextPath + '/carts',
            method: 'GET',
        }).then(function (response) {
            $scope.CartMap = response.data;
            console.log(response.data);
        });
    };


    $scope.loadProducts();
    $scope.loadCart();

});
angular.module('market-front').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/app/';

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + 'api/v1/products',
            method: 'GET',
            params: {
                p: pageIndex,
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                title_part_category: $scope.filter ? $scope.filter.title_part_category : null
            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.productsPage.totalPages);
            console.log(response.data);
        });
    };

    $scope.addToCart = function (productId) {
        $http.get(contextPath + 'api/v1/cart/' + $localStorage.springWebGuestCartId + '/add/' + productId)
            .then(function (response) {
            });
    }

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 0; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.loadProducts();

});
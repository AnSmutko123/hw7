angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $rootScope, $localStorage) {
    const contextPath = 'http://localhost:8189/app/api/v1';   // адрес запроса

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

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
                delta: delta
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

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/app/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {

            });
    }

    $scope.tryToRegister = function () {
        $http.post('http://localhost:8189/app/register', $scope.newUser)
            .then(function (response) {
                console.log(response);
                alert('Пользователь успешно зарегистрирован');
                if ($scope.newUser.username) {
                    $scope.newUser.username = null;
                }
                if ($scope.newUser.password) {
                    $scope.newUser.password = null;
                }
                if ($scope.newUser.name) {
                    $scope.newUser.name = null;
                }
                if ($scope.newUser.email) {
                    $scope.newUser.email = null;
                }
            });
    }

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    }

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    }

    $scope.showCurrentUserInfo = function () {
        $http.get('http://localhost:8189/app/api/v1/profile', $scope.user)
            .then(function successCallback(response) {
                alert('MY NAME IS: ' + response.data.username);
            }, function errorCallback(response) {
                alert('UNAUTHORIZED')
            });
    }


    $scope.loadProducts();
    $scope.loadCart();

});

function MinesweeperCtrl($scope) {
    $scope.newGame = function (width, height, mines) {
        $scope.game = new minesweeper.MinesweeperGame(width, height, mines);
        $scope.rows = $scope.game.getRows();
    };

    $scope.openSquare = function (square) {
        var result = $scope.game.openSquare(square);
    };

    $scope.toggleFlag = function (square) {
        $scope.game.toggleFlag(square);
    };

    $scope.isGameOver = function() {
      return !$scope.isGameOn();
    };

    $scope.isGameOn = function() {
      return $scope.game && !$scope.isGameWon() && !$scope.isGameLost();
    };

    $scope.isGameWon = function() {
      return $scope.game && $scope.game.isGameWon();
    };

    $scope.isGameLost = function() {
      return $scope.game && $scope.game.isGameLost();
    };
}

/* Angular app initialization */
var minesweeperApp = angular.module('MinesweeperApp', []);

/* Binding right click to HTML elements' ng-right-click attribute: http://stackoverflow.com/a/15732476 */
minesweeperApp.directive('ngRightClick', function($parse) {
    return function(scope, element, attrs) {
        var fn = $parse(attrs.ngRightClick);
        element.bind('contextmenu', function(event) {
            scope.$apply(function() {
                event.preventDefault();
                fn(scope, {$event:event});
            });
        });
    };
});

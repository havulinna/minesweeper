/*
 * This source file contains the business logic for a minesweeper game demo.
 *
 * As JavaScript applications often mix the business logic with the user interface logic,
 * making the applications hard to extend and test, the key design goal here was
 * to separate the UI from the logic as much as possible.
 */
var minesweeper = {
    Square : function(row, col) {
        this._row = row;
        this._col = col;
        this._isMine = false;
        this._isOpen = false;
        this._isFlagged = false;
        this._nearbyMines = 0;

        this.isOpen = function() {
            return this._isOpen;
        };
        this.setOpen = function() {
            this._isOpen = true;
        };
        this.isMine = function() {
            return this._isMine;
        };
        this.setMine = function() {
            this._isMine = true;
        };
        this.isFlagged = function() {
            return this._isFlagged;
        };
        this.toggleFlag = function() {
            this._isFlagged = !this._isFlagged;
        };

        /* Returns the number of mines placed in the squares next to this one */
        this.getNearbyMines = function() {
            return this._nearbyMines;
        };
        this.incrementNearbyMines = function() {
            this._nearbyMines++;
        };
        this.isNeighbor = function(other) {
            if (other === this) {
                return false;
            } else {
                return Math.abs(other._col - this._col) <= 1 && Math.abs(other._row - this._row) <= 1;
            }
        };
    },

    Minefield : function(width, height, mineCount) {
        this.width = width;
        this.height = height;

        this.rows = [];

        // Creates a two-dimensional array and fills it with new Squares
        for (var row = 0; row < height; row++) {
            this.rows[row] = [];
            for (var col = 0; col < width; col++) {
                this.rows[row][col] = new minesweeper.Square(row, col);
            }
        }

        // Returns a new array containing all squares on this minefield
        this.getSquares = function() {
            return _.flatten(this.rows);
        };

        this.getNeighbors = function(square) {
            return _.filter(this.getSquares(), function(candidate) {
                return square.isNeighbor(candidate);
            });
        };

        /* Sets the given squares as 'mines' and updates the mine counters of their neighbors */
        this._setMines = function(squaresWithMines) {
            _.each(squaresWithMines, function(currentSquare) {
                // Set mine in current square
                currentSquare.setMine();

                // Increment the square counters on neighbors
                _.each(this.getNeighbors(currentSquare), function(neighbor) {
                    neighbor.incrementNearbyMines();
                }, this);
            }, this);
        };

        // Take the needed number of random squares and set mines to them
        this._setMines(_.sample(this.getSquares(), mineCount));
    },

    MinesweeperGame : function(width, height, mineCount) {
        this.width = width;
        this.height = height;
        this.moves = 0;

        this._minefield = new minesweeper.Minefield(width, height, mineCount);

        this._gameWon = false;
        this._gameLost = false;

        this.getRows = function() {
            return this._minefield.rows;
        };

        this.isGameWon = function() {
            return this._gameWon;
        };

        this.isGameLost = function() {
            return this._gameLost;
        };

        this.isCompleted = function() {
            /* Find any square that should still be opened. If there are no
             * more squares left to open, the game is completed. */
            var closedNonMine = _.find(
                this._minefield.getSquares(),
                function(square) {
                    return !square.isMine() && !square.isOpen();
                }
            );
            return !closedNonMine;
        };

        this.toggleFlag = function(square) {
            if (!this.isGameLost() && !this.isGameWon() && !square.isOpen()) {
                square.toggleFlag();
            }
        };

        /* Opens the given square if the game is on and the square is not open nor flagged.
         * 
         * If the square itself nor its neighbors contain any mines, also the neighbors are
         * opened recursively.
         */
        this.openSquare = function(square) {
            if (this.isGameWon() || this.isGameLost() || square.isOpen() || square.isFlagged()) {
                return;
            }
            square.setOpen();

            if (square.isMine()) {
                this._gameLost = true;
            } else {
                // If there are no mines nearby, also the neighbors are opened recurively
                if (square.getNearbyMines() === 0) {
                    var neighbors = this._minefield.getNeighbors(square);
                    _.each(neighbors, function(currentNeighbor) {
                        if (!currentNeighbor.isOpen()) {
                            this.openSquare(currentNeighbor);
                        }
                    }, this);
                }

                if (this.isCompleted()) {
                    this._gameWon = true;
                }
            }
        };
    }
};

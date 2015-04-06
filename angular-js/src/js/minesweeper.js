/* Game engine, plain JavaScript with no dependencies to Angular */
var minesweeper = {
  Square: function(row, col) {
    this._row = row;
    this._col = col;
    this._isMine = false;
    this._isOpen = false;
    this._isFlagged = false;
    this._nearbyMines = 0;

    this.isOpen = function() { 
      return this._isOpen;
    };
    this.isMine = function() {
      return this._isMine;
    };
    this.isFlagged = function() {
      return this._isFlagged;
    };
    this.getNearbyMines = function() {
      return this._nearbyMines;
    };
  },

  Minefield: function(width, height, mineCount) {
    this.width = width;
    this.height = height;

    this.rows = [];
    for (var row = 0; row < height; row++) {
      this.rows[row] = [];
      for (var col = 0; col < width; col++) {
        this.rows[row][col] = new minesweeper.Square(row, col);
      }
    }


    this.getSquares = function() {
      return _.flatten(this.rows);
    };

    this.setMines = function(squaresWithMines) {
      _.each(squaresWithMines, function(currentSquare) {
        currentSquare._isMine = true;
        _.each(this.getNeighbors(currentSquare), function(neighbor) {
          neighbor._nearbyMines++;
        }, this);
      }, this);
    };

    this.getNeighbors = function(square) {
      var col = square._col;
      var row = square._row;
      return _.filter(this.getSquares(), function(candidate) {
        var isNeighbor = Math.abs(candidate._col - col) <= 1 && Math.abs(candidate._row - row) <= 1;
        return isNeighbor && candidate !== square;
      });
    };

    this.mines = _.sample(this.getSquares(), mineCount);
    this.setMines(this.mines);
  },

  MinesweeperGame: function(width, height, mineCount) {
    this.width = width;
    this.height = height;
    this.moves = 0;

    this.minefield = new minesweeper.Minefield(width, height, mineCount);
    this._gameWon = false;
    this._gameLost = false;

    this.getRows = function() {
      return this.minefield.rows;  
    };
    this.isGameWon = function() {
      return this._gameWon;
    };
    this.isGameLost = function() {
      return this._gameLost;
    };

    this.toggleFlag = function(square) {
      if (!this._gameLost && !this._gameWon && !square.isOpen()) {
        square._isFlagged = !square._isFlagged;
      }
    };

    this.openSquare = function(square) {
      if (this._gameWon || this._gameLost || square._isOpen || square._isFlagged) {
        return;
      }
      square._isOpen = true;

      if (square.isMine()) {
        this._gameLost = true;
      } else {
        if (square._nearbyMines === 0) {
          var neighbors = this.minefield.getNeighbors(square);
          _.each(neighbors, function(currentNeighbor) {
            if (!currentNeighbor.isOpen()) {
              this.openSquare(currentNeighbor);
            }
          }, this);
        }

        var closedNonMine = _.find(this.minefield.getSquares(), function(square) {
          return !square.isMine() && !square.isOpen();
        });

        if (!closedNonMine) {
          this._gameWon = true;
        }
      }
    };
  }
};

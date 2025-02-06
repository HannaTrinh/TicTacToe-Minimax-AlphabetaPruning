<h1 align="center">Welcome to TicTacToe-Minimax-AlphaBetaPruning 👋</h1>
<p>
</p>

> A tictactoe game which uses the minimax algorithm and alpa beta pruning to produce optimized moves. The game waits to be assigned a colour (&#34;blue&#34; or &#34;orange) in order to start. Blue goes first, if the program is assigned blue, it produces a move immediately and waits for orange to respond. If the program is assigned orange, it waits for the orange player to input a move using the Scanner.in. 

## Prerequisites
- Java ([Java installation guide](https://dev.java/download/))
- Git

## Install

```sh
gh repo clone HannaTrinh/TicTacToe-Minimax-AlphabetaPruning
```


## Compiling

```sh
javac HannaTicTacToe.java
```

## Usage

```sh
java HannaTicTacToe.java
```

## Additional Logging

```sh
cd /TicTacToe-Minimax-AlphabetaPruning
tail -f debug.log
```
> In a separate terminal, calling the following debug log will show the live running program


## Results and Testing
After implementing the minimax algorithm with alpha beta pruning, I began playing against the algorithm using some basic edge cases, such as producing obvious 3 in a row moves to see if the algorithm will block it. For example, 'o' moves to a1 to block player 'b' 3-in-a-row:
```
[b][ ][ ]    [b][ ][ ]
[b][o][ ] -> [b][o][ ]
[ ][ ][ ]    [o][ ][ ]  
```
I also tested the algorithm by playing incredibly terrible moves such as ignoring obvious 3 in a row moves. For example 'o' moves to b1 to win instead of blocking 'b' at a1:
```sh
[b][o][ ]    [b][o][ ]
[b][o][ ] -> [b][o][ ]
[ ][ ][b]    [ ][o][b]  
```

Finally I decided to test the program against another optimized player/program provided by my TA from this class. The game always ends in a draw which indicates a successful algorithm that is somewhat efficient as it can produce a move within 2 seconds.

### Strength
1. This program includes a logger that writes game states and events to a debug.log file, which helped a lot with debugging and understanding game flow during development.
2. AI implementation using minimax algorithm with alpha-bbeta pruning for hte AI's decision-making process helps optimize the search for the best move by reducing the number of nodes evaluated in the decision tree.
3. The game board updates dynamically based on player input and AI decisions, which keeps the gameplay interactive and engaging.
4. It contains End Game Detection to handle game termination scenarios such as win or draw.

### Weakness
1. Board reprentation is made using a 2D array which makes handling coordinates unecessarily complex and posed as an issue several times during developemnt.
2. Although using a 2D array is perfectly fine for a simple 3x3 tictactoe game, it is not flexible if th euser prefers a larger board. Larger board will consume more memory usage and performance, which could be more suitable with a different datastructure.
3. It lacks Unit Testing which would've helped catch bugs early in the developement process


## Author

👤 **Hanna Trinh**

* Github: [@HannaTrinh](https://github.com/HannaTrinh)
* LinkedIn: [@maianh-trinh](https://linkedin.com/in/maianh-trinh)

## Show your support

Give a ⭐️ if this project helped you!

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_
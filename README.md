This is a copy of the coding game competition, you can find the original [here](https://www.codingame.com/ide/1568342dc103267f7770ecf72ae2311f306c8b8)

You will need a sample server in java can be found [here](clients/java/src/main/java/Example). But you can use any other language to play. 

To play you need to create a server that will be registered with the Game Engine that will call your server for the responses.

Endpoints you will need to create (as seen on the example):

```

     get("/solve/:magicword", ...);

     post("/stats", ...);
```

Game Instructions

Your program receives a string of uppercase characters – the magic phrase Bilbo needs to spell out – and must output a sequence of characters, each representing an action for Bilbo to perform. These actions will have Bilbo move around in the forest, interacting with the different stones.
The forest Bilbo is trapped works as follows:
The forest contains 30 zones.
Bilbo can move left and right through the zones which are all aligned. He does this when he receives a less-than < or greater-than > sign.
The last zone is connected to the first zone, effectively creating a looping area.
Each zone contains a magic stone upon which is inscribed a rune with which Bilbo can interact.
Runes work as follows:
Every rune is represented by a letter of the alphabet (A-Z) or an empty space.
All runes start out as a space.
Bilbo can change the value of the letter on the rune by rolling back and forth through the possibilities. He does this when he receives a plus + or minus - character.
The letter after Z is space. The letter after space is A.
Bilbo can trigger a rune. This will add the displayed letter to the phrase he is spelling out. He does this when he receives a dot . character.
One rune can be triggered several times.
YOU WIN IF THE MAGIC PHRASE IS SPELLED OUT CORRECTLY AT THE END OF BILBO'S MOVE SEQUENCE.
You lose if:
At the end of Bilbo's move sequence, the wrong message is displayed.
Bilbo performs 4000 moves or more in the forest.
You do not supply Bilbo with a valid sequence of actions.
Example:
If Bilbo needs to spell out the word AB, you could give him the instructions +.+.. He will:
Make the first rune go from space to A.
Trigger the A.
Make rune go from A to B.
Trigger the B.
Alternatively, you could also give him the instructions +.>++. to achieve the same result. Experiment with different tactics!

## Texas Hold 'Em Outcome Generator

### Overview
The purpose of this application is to generate a CSV file containing information about the cards held by a player during a hand of a Texas Hold 'Em game. Each row represents a hand of Texas Hold 'Em, and the columns contain information about the cards held by a single player (referred to as the `self` player) as well as the outcome of the game for the self player (0 being a loss, and 1 being a win).

### How it Works
This is achieved by simulating games of Texas Hold 'Em. The following is a high level summary of the logic:

1. Create players that are participating in the game (one of these players representing yourself).
2. Create a shuffled deck of cards.
3. Distribute two cards to each player from the top of the deck.
4. Distribute 5 cards to the community pile (representing the flop and river) from the top of the deck.
5. Compare the selfs hand with every other players hand to determine if the self won.
6. Add information about the cards in the self players hand as well as the outcome of the hand for the self player as a row in the CSV.
7. Write the relevant data out to a CSV file.

### Future Development
In the future, I plan on using this data to train a machine learning model that can predict the probability of winning a hand of Texas Hold 'Em, given only information about the cards that a single player holds (i.e. someone could use the model during a game to help make a decision on whether they should continue playing in the round or fold).

### Out of Scope
In real life, Texas Hold 'Em games can be very complex. Players begin with a set amount of money (most likely in the form of chips). The money is used to make bets during each round. A players cards, remaining amount of money, and level of aversion to risk all play a major influence on whether or not the player chooses to stay in the game or fold after each round. To simplify the logic for this application, I have ignored modeling anything involving decisions made by a player during a hand, including betting, checking, and folding.

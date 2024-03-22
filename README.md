* COMP2522 - Object-Oriented Programming - Assignment 2

The goal of this assignment was to apply object-oriented theory to the design of the "Game of Life", simulating plants and herbivores in a world and having them procreate, move, eat, and die.

* PART 1 OF ASSIGNMENT
Apply OOP principles and concepts to create a base version of the game. The only lifeforms are Plants and Herbivores.
- Herbivores move around and eat plants they encounter, and die if they haven't eaten in some number of turns
- Plants create new Plants around them if certain conditions are met

* PART 2
- Omnivore and Carnivore animal types are introduced
- All animals can also reproduce like plants if conditions are correct

////////////////////////////////////////////////////////////////////////
* PREVIOUS ITERATION
Only includes plants and herbivores. Design choices were made so that classes could be expanded upon if the instructor introduces additional requirements such as:
- More, different types of animals (carnivores, avians)
- Different food types
- Different terrain types
- Lifeforms that take more than one action per turn

* CURRENT ITERATION
Additional animals introduced, all animals can now reproduce in addition to their previous actions.
Refactoring was done to increase readability and maintability, and to make expansion or future changes
  easier to implement.

////////////////////////////////////////////////////////////////////////
* NOTES:
- RandomGenerator class provided by instructor, for testing purposes. For better randomness, use Java's built-in Math class instead.
- Additional refactoring could be done, such as extracting functionality from a method for enhanced readability, or removing useless private variables that don't contribute much.
- Additional improvements could be made to readability or maintainability. (Predicates?)

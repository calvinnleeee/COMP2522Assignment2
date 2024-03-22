COMP2522 - Object-Oriented Programming - Assignment 2

The goal of this assignment was to apply object-oriented theory to the design of the "Game of Life", simulating plants and herbivores in a world and having them procreate, move, eat, and die.

Current iteration only includes plants and herbivores. Design choices were made so that classes could be expanded upon if the instructor introduces additional requirements such as:
- More, different types of animals (carnivores, avians)
- Different food types
- Different terrain types
- Lifeforms that take more than one action per turn

PART 1
Apply OOP principles and concepts to create a base version of the game. The only lifeforms are Plants and Herbivores.
- Herbivores move around and eat plants they encounter, and die if they haven't eaten in some number of turns
- Plants create new Plants around them if certain conditions are met

PART 2
- Omnivore and Carnivore animal types are introduced
- All animals can also reproduce like plants if conditions are correct

////////////////////////////////////////////////////////////////////////
Notes:
- RandomGenerator class provided by instructor, for testing purposes. For better randomness, use Java's built-in Math class instead.
- Some class methods are empty, could be deleted later once finalized (eg. die method of Lifeforms)
- Possible refactoring could be done, such as extracting functionality from a method for enhanced readability

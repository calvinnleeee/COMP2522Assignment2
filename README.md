COMP2522 - Object-Oriented Programming - Assignment 2

The goal of this assignment was to apply object-oriented theory to the design of the "Game of Life", simulating plants and herbivores in a world and having them procreate, move, eat, and die.

Current iteration only includes plants and herbivores. Design choices were made so that classes could be expanded upon if the instructor introduces additional requirements such as:
- More, different types of animals (carnivores, avians)
- Different food types
- Different terrain types
- Lifeforms that take more than one action per turn

Notes:
- RandomGenerator class provided by instructor. For better randomness, could use Java's built-in Math class instead.
- Some class methods are empty, could be deleted later once finalized (eg. die method of Lifeforms)
- Possible refactoring could be done, such as extracting functionality from a method for enhanced readability

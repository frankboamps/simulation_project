Design
====

This considers the current design for the cellular automata simulator project.

Names:

1. Franklin Boampong
2. Lucy Gu
3. Michelle Tai

### Design changes considered

The findings from the static 

1. Making the grid more encapsulated by creating a grid class.

2. Identify the longest methods and try to refactor them as soon as possible.

3. Change public variables in classes into private.

4. Have all magic numbers static final to be used elsewhere in file.

5. Have all strings in the resources file and use the resource bundle to obtain string.

6. Consider the naming of classes, methods, and variables to actual representations.

7. Reduce the several if-else conditions in current design implementation by creating more classes for different 
conditions which are checked. 

8. Refactor positions of data files into outer resources folder to enable users load them when needed.

9. Greatly refactor individual simulation classes to ensure that their implementations are of good design as possible.

10. Have the initial grid display within the grid class and hence reduce the great dependencies observed in the 
SimulationViewSubscene class.

11. Check error conditions and have TO DOs to create like pop ups.
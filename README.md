simulation
==== 

This project implements a cellular automata simulator.

Names:
1. Franklin Boampong
2. Lucy Gu
3. Michelle Tai

### Timeline

Start Date: 01/27/2020

Finish Date: 02/10/2020

Hours Spent: 20+ hours each, around 80+ hours total

### Primary Roles

* Team Member #1 (Franklin Boampong)
	- Primary Role: Game view Class
	- Secondary Role: Fire, Sugar Scape, Rock Paper Scissors, XML Exceptions

* Team Member #2 (Michelle Tai)
   -  Primary Role: XML class
   -  Secondary Role: Percolation, XML Exceptions, Game of Life

* Team Member #3 (Lucy Gu)
   - Primary Role: Cell, Simulation
   - Secondary Role: Game of Life, Segregation, Sugar Scape, Rock Paper Scissors

Team Altogether: Work on WaTor simulation, refactoring

### Resources Used

1. Stack Overflow
2. Youtube
3. Oracle Java Documentations

### Running the Program
Main class: SimulationMain is the main class

Data files needed: 

1. Back32.gif
2. Background.jpg
3. blue_background_for_popup.png
4. deep_yellow.png
5. default.css
6. English.properties
7. kenvector_future.ttf

Features implemented:

**Simulation**
1. A grid of cells that is used to represent the simulation.
2. Cells on the edges of the grid should have smaller, partial, neighborhoods than those in the middle, with full neighborhoods.
3. Simulation algorithm where the state information of each cell is updated each step based on rules applied to cell 
state and neighbor states.
4. Simulations run indefinitely.
5. Implemented Java code for the rules of five different simulations: game of life (for testing), percolation (the simplest one - rules are given on page 3), segregation, predator-prey, and fire.
6. We had different arrangements of neighbors.
7. We have rectangle and triangle cells.
8. We have finite and toroidal grid edges.
9. Implemented Rock, paper, Scissors and SugarScape simulations.

**Configuration**

1. Read in an XML formatted file that contains the initial settings for a simulation. The file contains three parts:
  1. kind of simulation it represents, as well as a title and the author of this data file
  2. settings for global configuration parameters specific to the simulation
  3. width and height of the grid and the initial configuration of the states for the cells in the grid
2. Checking for no simulation given or simulation type that we don't have.
3. Initial configurations are set either randomly or through an initial configuration tag.
4. Implemented a saving current configuration of cells feature, but was not able to add it into a button


**Visualization**
1. Display the current states of the 2D grid and animate the simulation from its initial state indefinitely until the user stops it.
2. Allow users to load a new configuration file, which stops the current simulation and starts the new one.
3. The display size of an individual cell should be calculated each time by the grid's total size, but the size of the visualization window should not change size.
4. Allow users to pause and resume the simulation, as well as step forward through it.
5. Allow users to speed up or slow down the simulation's animation rate.
6. Any text displayed in the user interface should be set using resource files, not hard-coded.
7. Display a line graph for the simulations.
8. Implemented a slider to change the speed.
9. Can run multiple simulations at a time.


### Notes/Assumptions

Assumptions or Simplifications:
1. Percolation occurs when there is a percolated cells on the left edge of the grid AND on the right 
edge of the grid–we didn't take into account top-bottom percolation.
2. Assumed that time will be saved as an integer
3. We assumed the reproduction time for sharks is 10 chronons and the time for fishe is 2 chronons in 
 WaTor. We also assumed each fish was worth 10 energy. 


Interesting data files:
1. FireRandom.xml
2. FireTriangleRandom.xml
3. SugarscapeRandom.xml
4. RockPaperScissorRandom.xml


Known Bugs:

1. For some reason, when the step button is being pressed, the graph does not update with the step button being pressed.
2. Some of the simulations when run on the last slider level speed tend to run very slowly. 
3. Some of the print outs for the error handling are not as wanted but could not change string because of time and 
team priorities.

Extra credit:
1. Styled gui, took UI/UX into consideration



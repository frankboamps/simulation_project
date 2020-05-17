#DESIGN_EXERCISE
###Names/NetID:  
Lucy Gu (wg74)  
Michelle Tai (mrt36)  
Franklin Boampong (fsb10)

###Code Review:
We looked at each other's code and commented on overly-long classes, 
clear organization, and extending & implementing

###Design for Simulation:
We started off thinking about using 2D arrays, but since we will be 
dynamically adding, removing, passing data of entries around, an array 
of fixed size is likely not a good idea. We are currently thinking about 
using an ArrayList of ArrayLists, since it will be easy to dynamically 
manipulate/modify the size of ArrayList without breaking the program.
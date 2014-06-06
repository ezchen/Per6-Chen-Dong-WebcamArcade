Webcam-Arcade
=============

Games that involve image processing through the webcam

Instructions

\- Compile using javac -cp .:libs/\* \*.java
\- Get a color that you wish to track, preferably a light source and cover the entire square in the center. Press any button to begin tracking this color
\- Press any button to enter draw Screen (update this junhao)

Drawing

\- once you enter the draw screen, press any button besides z and to draw a rectangle
\- draw screen supports undo and redo. Press **z** for undo and **r** for redo


Algorithms, DataStructures, etc...

**Overall hierarchy**

Driver - Entrance to the program
	   - Creates The WebcamPanel, SetupScreen, etc...
	   - Has a stack of screens
			- peek() the top of the stack and pass down the method paintImage(), which is basically like a while loop (more like a thread									that is constantly updated in the WebcamPanel).

Screen - Interface
			- Describes general methods that screens should have. Look into code for more info

SetupScreen - Records the color, allows the user to test if the color is good enough before selecting a different screen

DrawScreen - Allows the user to draw
		   - Contains a LinkedList of history of commands and a Stack of optional redo commands

PongScreen - Pong!

Mean Shift Algorithm

\- Separate colors into RGB (red green blue)
\- Find the weighted mean of all the colors in a Rectangle, then move to that mean
	\- The weight is given by w = exp(-k*Distance(colorTracked, colorPixel)^2)
	\- Credits to cmsoft.com for this weighted pixel equation

Undo/Redo

\- All commands are added to the end of the linkedlist (like a stack, but we need to iterate through the list to draw the rectangles)
\- Any undo commands are pushed onto a redo stack



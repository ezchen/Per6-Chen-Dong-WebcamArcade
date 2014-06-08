Webcam-Arcade
=============

Games that involve image processing through the webcam

Instructions
\- Compile using "javac -cp .:libs/\* \*.java" or "javac -cp .;libs/\* \*.java" on Windows machines

\- Run the program with "java -cp .:libs/\* Driver" or "java -cp .;libs/\* Driver"

\- Get a distinct color that you wish to track, preferably a light source and cover the entire square in the center. Press any button to begin tracking this color

\- Press **D** to enter the Draw Screen

\- Press **P** to enter the Pong Screen

Drawing
\- Once you enter the draw screen, press any key besides 'z' and 'r' to draw a rectangle

\- Draw screen supports undo and redo. Press **z** to undo and **r** to redo

Pong
\- 

\-


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

	\- The weight is given by w = e^(-k*Distance(colorTracked, colorPixel)^2)

	\- Credits to cmsoft.com for this weighted pixel equation

Undo/Redo in DrawScreen
\- All commands are added to the end of the linkedlist (like a stack, but we need to iterate through the list to draw the rectangles)

\- Any undo commands are pushed onto a redo stack

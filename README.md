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

Image Processing

We will be tracking using the RGB values of the pixels from the images
streamed through the webcam to determine what the "mouse" will be.

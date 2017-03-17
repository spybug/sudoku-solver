# Overview
Sudoku Solver is an app for Android built in Android Studio. This is a personal project made by myself to solve any given sudoku puzzle. 
It uses a brute force algorithm to test values in each column and continually go down row by row until the puzzle is filled with correct values.

## Screenshots

Empty grid  |  Entering values
:-------------------------:|:-------------------------:
![Empty grid](http://i.imgur.com/f238o7d.jpg)  |  ![Entering values](http://i.imgur.com/Lrxub2v.jpg)

Solved puzzle |  Errors when entering duplicates
:-------------------------:|:-------------------------:
![Solved puzzle](http://i.imgur.com/SvRW8SF.jpg)  |  ![Errors when entering duplicates](http://i.imgur.com/R2Z0yrc.jpg)


# Features
* Solves a puzzle relatively quickly given valid numbers
  * Only allows the user to enter in numbers, so there is no way to accidentally enter in a wrong character 
* If invalid numbers are entered into the puzzle, it will notify you right away with a red background
* Can clear the board to reset what is there
* The board can be saved and reloaded from a database

## Planned updates
* Ability to solve the puzzle one number at a time, so as to not spoil the solution
* Be able to customize the theming and colors for the app to the user's liking
* Read in a puzzle from the camera by taking a picture and parsing the board
* Support board sizes other then just 9x9 boards
* Add a hints page to help users learn better strategies for solving puzzles
* Add a contact form in the app to submit any feature requests or bugs

# Worm Game - README

## How to run the Game

1. Open command prompt/terminal
2. Navigate to the installed game location:
```
cd path\to\game
```
3. then run the game with:
```
java src/Game.java
```

The game will launch immediately with the main menu.

## Features to test

### Main Menu
- Custom buttons with different hover/press sprites.
- Specific menu background music plays.
- Quit button to exit.

###  Controls
- **Movement**: WASD or Arrow Keys to move the worm
- **Restart**: In order to restart, press 'R' to restart the current level.
- **Return to Menu**: The player may access the menu at any time by pressing 'ESC'.


### Game Mechanics
- **Worm size**: Each level, the worm has a different maximum length specific to the puzzle.
- **Buttons**: Move over buttons to press them, playing a button press sound.
- **Gates**: All buttons in a level must be pressed for a gate to open, after which an opening sound plays and the player may pass through.
- **Puddle/Victory**: To sucessfully pass a level the player must go over the puddle after all buttons were pressed.
- **Collision**: Upon hitting your own body or a wall the game plays a collision sound and blocks movement.

### Win Screen
- Displays when level is completed
- Options to: proceed to next level, return to menu, or quit
- Victory music plays

### Audio
- Menu background music
- Gameplay background music
- Victory background music
- Sound effects for: button press, gate unlock and collision


2. Buttons: The player must go over the button to trigger it.
3. Gates: All buttons in a level must be pressed for a gate to open, after which the player may pass through.
4. Restart: In order to restart, press 'R', you may only restart twice in a second.
5. Victory/Puddle: To sucessfully pass a level the player must go over the puddle after all buttons were pressed.
6. Win screen: Upon winning, the player is presented with a win screen where you may go back to the menu, next level or quit.
7. Main menu: the player may access the main menu at any time by pressing 'ESC'.
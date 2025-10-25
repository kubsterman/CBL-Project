# Worm Game - README

## How to run the Game

1. Open command prompt/terminal
2. Navigate to the installed game location:
```
cd path\to\game
```
3. then run the game with:
```
java -cp bin Game
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

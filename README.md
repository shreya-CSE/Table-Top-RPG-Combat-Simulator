# TableTop RPG Combat Simulator

## Project Description

Welcome to the final phase of the TableTop RPG Combat Simulator project! The primary goal is to create a Java-based application that simulates combat scenarios between players and monsters. Each phase introduces new features and improvements to the application.

### Phase I: Basic Character Creation and Combat

In the first phase, the focus is on implementing fundamental features such as player creation, a combat system, and file I/O capabilities.

#### Features Implemented:
- **Player Creation:**
  - Players can create custom characters with a name, basic stats (Hit Points, Armor Class, Strength, Dexterity, Constitution), and choose a weapon.
  - Players have 10 points to allocate to their stats.
  - Weapon options include Greataxe, Longsword, Warhammer, Shortsword, and Dagger.

- **Combat System:**
  - Basic combat system involves rolling a d20 and adding Dexterity modifier to determine initiative.
  - Players can perform actions like attacking and disarming during their turn.
  - Players can move up to 5 grid units during their turn.
  - Actions like attacking and disarming involve rolling a d20, adding modifiers, and determining success based on the opponent's AC.

- **File I/O:**
  - Players can save and load characters to and from CSV files.
  - File operations are implemented using multi-threading to enhance responsiveness.


### Phase II: Introduction of Monsters and Enhanced Combat

The second phase expands the combat simulator to include monsters. Players can now face off against computer-controlled monsters.

#### Additional Features:
- **Monster Class:**
  - Introduces a new `Monster` class that extends the `Creature` superclass.
  - Monsters have types (Humanoid, Fiend, Dragon) and simplified attack mechanics.

- **Load/Save Characters:**
  - Players can save and load characters with additional features for handling monsters.

- **Starting a Game:**
  - Players can choose to play against random monsters.
  - The game initializes monster locations and rolls initiative for both players and monsters.

- **Combat View:**
  - A grid displays all creatures, and players can interact with avatars for actions.

- **Rolling Initiative:**
  - Initiative rolls are now more complex with multiple players and monsters.

### Phase III: GUI Implementation

The final phase focuses on building a graphical user interface (GUI) for the game. Players can interact with the game elements through a visual interface.

#### GUI Features:
- **Main Menu:**
  - The GUI includes a main menu with options like Start Game, Create Character, Load Character, Save Character, and Exit.

- **Center Panel:**
  - The center panel displays buttons for menu options and the player party.
  - Players can view and interact with the party, selecting characters for the game.

- **Status Bar:**
  - A status bar at the bottom provides information about background operations, player status, and more.

- **Character Creation:**
  - GUI-based character creation with inputs for name, stats, weapon, and avatar selection.

- **Saving/Loading Characters:**
  - GUI components for saving and loading characters with file selection options.

- **Starting a Game:**
  - Players can start a game, selecting monsters to face in combat through the GUI.

- **Combat View:**
  - A graphical grid presents creatures with avatars, and players can interact visually.

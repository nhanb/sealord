# Intro

A battleship-style game written in Java swing and standard 2D graphics. Here in
this game, battleships are replaced with sea monsters.

Requires Oracle JDK 7. To build this game, simply use `ant` to build using
`build.xml`. The file is actually generated from netbeans, and there's
nothing stopping you from importing it into a netbeans project.

# How to play

1. Choose the difficulty:
    + Easy: monsters can only be laid horizontally. PC AI shoots randomly
    for every turn.
    + Hard: monsters can be laid either horizontally or vertically. PC AI
    tries shooting neighboring squares after a hit.
2. Put monsters on your map (the left one). If you're on Hard difficulty, press
space to switch between horizontal and vertical.
3. Profit.

# Kinks

- Sound is disabled by default. Turn it on by setting `ENABLE_SOUND` to `true`
in `Main.java`. The reason is because after a few turns, the sound code will
crash on Ubuntu (haven't tried it on Windows). No idea why.

- After building the jar file, remember to copy `img/` and `sound/` to its
same folder or things won't work.

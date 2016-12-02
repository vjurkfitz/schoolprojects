/* Kovaliov game: the goal is to push mobile boxes to holes in random positions.
Developed by Victoria Thibes as the final project
for the course Algorithms and Programming, in 2013/1, held by professor Mara Abel.
Informatics Institute - UFRGS

Translated in Nov/2016 */

/* CONSTANTS ======================================*/

/* Screen dimensions */
#define WIDTH 50
#define HEIGHT 20

/* Colors from conio.h*/
#define BLACK 0
#define GREEN 2
#define CYAN 3
#define RED 4
#define PINK 5
#define GREY 8
#define WHITE 15


/* LIBRARIES ======================================*/

#include <stdlib.h>
#include <stdio.h>
#include <conio.h>
#include <time.h>

#include "structures.h"
#include "initialization.h"
#include "prints.h"
#include "tests.h"
#include "movement.h"
#include "files.h"

/* FUNCTIONS ======================================*/

void ranking (USER user1)
/* Opens ranking, inserts current player, sorts and prints again
    Parameters: user1: USER (current player) */
{

    FILE* highscore;
    int maxranking=10; // Number of positions
    USER scores[maxranking];
    USER aux, usercopy;
    int i, j;

    usercopy = user1;
    initscores(scores,maxranking);


    if (readranking(scores,maxranking) == 1)
        return;

    else{

        readranking(scores,maxranking);

        for (i=0; i<maxranking; i++){
            if (usercopy.total_score > scores[i].total_score){
                    aux=scores[i];
                    scores[i]=usercopy;
                    usercopy = aux;
            }

            for (j=0; j<maxranking; j++){
                if (scores[j].total_score>scores[i].total_score){
                    aux=scores[i];
                    scores[i]=scores[j];
                    scores[j]=aux;
                }
            }
        }

    writeranking(scores,maxranking);
    }
    textbackground(BLACK);
    clrscr();
    textcolor(GREY);
    textbackground(GREY);
    cputsxy(12,4, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

    for(i=0; i<HEIGHT-2;i++){
        cputsxy(12,i+5, "X");
        cputsxy(66,i+5, "X");}

    cputsxy(12,22, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

    textbackground(BLACK);
    textcolor(CYAN);

    gotoxy(15,5);
    printf("Your points: %.2f",user1.total_score);
    cputsxy(20,7, "** Ranking with the best players **");

    int y=9;
    int position=10;

    textcolor(WHITE);

    gotoxy(17,7);
    printf("Position     Name                 Score");

    for (i=0; i<maxranking; i++){
        gotoxy(17,y);
        printf("   %d:", position);
        gotoxy(51,y);
        printf("%.2f",scores[i].total_score);
        cputsxy(29,y,scores[i].name);
        position--;
        y++;
    }
    gotoxy(23,24);
    system("pause");
}

void pause (char scene[HEIGHT+3][WIDTH+2], HOLE holes[], BOX boxes[], USER user1, int numberofboxes)
/* Saves the current game status in a .bin file. Only gets back when the user enters P.
    Parameters: scene: char (matrix with the scene map)
                holes: HOLE (vector holes)
                boxes: BOX (vector of boxes)
                user1: USER (current player)
                level: int (level that is being played)
                numberofboxes: int (number of boxes in the level) */
{
    FILE* pause;
    int key;

    pause = fopen("pause.bin","w+b");

    fwrite(scene,sizeof(int),(HEIGHT+3)*(WIDTH+2),pause);
    fwrite(holes,sizeof(HOLE),numberofboxes,pause);
    fwrite(boxes,sizeof(BOX),numberofboxes,pause);
    fwrite(&user1,sizeof(USER),1,pause);
    fwrite(&numberofboxes,sizeof(int),1,pause);

    fclose(pause);

    do {
        key = getch();
        if (kbhit())
            key = getch();
    }
    while (key != 80 && key != 112); // p or P

    return;
}

int movement (PLAYER player1, char scene [HEIGHT+3][WIDTH+2], BOX boxes[], int numberofboxes, char name[25], HOLE holes[], USER *user1)
/* Function to move a player
    Parameters: player1: PLAYER (current player)
                scene: char (matrix with the scene map)
                boxes: BOX (vector of boxes)
                numberofboxes: int (number of boxes in the level)
                name: string (player name)
                holes: HOLE (vector holes)
                user1: USER (current player) */
{
    int key;
    int moves=0;
    int i, j; // Indexes
    int seconds; // Counts the match time
    int option;
    int placed; // Number of boxes that have been placed in a hole
    PLAYER noone;
    BOX  nothing1;
    USER useraux;

    useraux = *user1;

    while (key != 99) // End
    {
        option = 0; // User as auxiliary variable

        leftpanel(name, moves); // Lateral panel
        cronometer(&seconds);

        key = getch();
        if(kbhit())
            key = getch();

        switch (key){

        case 72: // Up
        case 80: // Down
        case 75: // Left
        case 77: // Right
            if(testwall(player1.position,scene,key) != 1){ // Player did not hit a wall
                for (i=0; i<numberofboxes; i++){

                    if (testbox(player1.position,boxes[i],key) == 0){  // Player can move
                        if (boxes[i].placed == 0){                     // Box can move
                            if (testwall(boxes[i].position,scene,key) != 1){    // Box doesn't hit a wall
                                for (j=0; j<numberofboxes; j++){
                                    if (boxbox(boxes[i],boxes[j],key) == 1 && j!=i) // This box does not hit another
                                        option = 1;
                                    }

                                if (option != 1){
                                    movebox(key,&player1,&boxes[i]);
                                    moves++;
                                    option = 1; // Move is done
                                }
                            }
                            else
                                option = 1; // Can't move
                    }
                    else
                        option = 1;  // Can't move
                    }
                }

                for (i=0; i<numberofboxes; i++){
                    if (testbox(player1.position,boxes[i],key) == 1) // Not in position to move a box
                        option=1;
                }
                if (option!=1){  // There is nothing stopping the player
                    moveplayer(key,&player1);
                    moves++;
                }
            }
            textbackground(BLACK);

            break;


        case 9:  // TAB - Open upper menu
            initmenu(GREY);
            int menukey;
            menukey = getch();
            if(kbhit())
                menukey = getch();

            switch(menukey){

            case 78: // N
            case 110: // n -> new game (restarts at level 1)
                return 1;
                break;

            case 81: // Q
            case 113: // q -> Quit
                gotoxy(1, HEIGHT+3);
                textbackground(BLACK);
                textcolor(CYAN);
                printf("Quit\n");
                system("pause");

                useraux.moves += moves;
                useraux.time += seconds;
                *user1 = useraux;

                return 3;
                break;

            case 83: // S
            case 115: // s -> save
                textcolor(WHITE);
                useraux.time += seconds;
                useraux.moves += moves;
                gotoxy(50,15);
                score(&useraux);
                savepoints(useraux);
                gotoxy(1, HEIGHT+4);
                gotoxy(1, HEIGHT+3);
                printf("                                                     ");
                gotoxy(1, HEIGHT+4);
                printf("                                                     ");
                break;

            case 80: // P
            case 112: // p -> Pause
                pause(scene,holes,boxes,useraux,numberofboxes);
                break;

            case 69: // E
            case 101: // e -> Escore
                useraux.time += seconds;
                useraux.moves += moves;
                score(&useraux);
                ranking(useraux);
                return 3;
                break;

            }
            initmenu(WHITE);
            break;

        case 90: //  Z
        case 122: // z -> help: shows holes
            for (i=0; i<numberofboxes; i++){
                textcolor(BLACK);
                textbackground(BLACK);
                cputsxy(holes[i].position.x,holes[i].position.y,"3");
                cputsxy(holes[i].position.x+1,holes[i].position.y,"3");
                cputsxy(holes[i].position.x,holes[i].position.y+1,"3");
                cputsxy(holes[i].position.x+1,holes[i].position.y+1,"3");
            }

            break;

        case 82: // R
        case 114:  // r -> reload: restarts level
            return 2;

        default:
            gotoxy(1, HEIGHT+3);
            textbackground(BLACK);
            textcolor(CYAN);
            printf("Invalid command. Try again. \n");
            system("pause");
            gotoxy(1, HEIGHT+3);
            printf("                                                     ");
            gotoxy(1, HEIGHT+4);
            printf("                                                     ");

        } // End of switch

        for (i=0; i<numberofboxes; i++){
            for (j=0; j<numberofboxes; j++){
                if (boxes[i].position.x == holes[j].position.x &&
                    boxes[i].position.y == holes[j].position.y){
                    boxes[i].placed = 1;
                    boxes[i].color = PINK;
                    printbox(boxes[i]);
                    placed++;
                }
            }
        }
        if (placed == numberofboxes)
            key = 99;
        else
            placed = 0;

    } // End of while

    useraux.moves += moves;
    useraux.time += seconds;

    *user1 = useraux;

    return 0;
}

void gameloop(char name[25], int level, USER *user1)
/* Main game loop.
    Parameters: name: string (player name)
                level: int (current level)
                user1: USER (data about the player and the match) */
{

    PLAYER player1; // Player
    char scene[HEIGHT+3][WIDTH+2]; // Map os walls and boxes
    int numberofboxes; // How many boxes in the level

    switch (level)
    {
        case 1: numberofboxes = 3; break;
        case 2: numberofboxes = 4; break;
        case 3: numberofboxes = 5; break;
        default: return;
    }

    HOLE holes[numberofboxes]; // Vector of holes (goal)
    BOX boxes[numberofboxes]; // Vector of boxes
    USER useraux;

    useraux = *user1;

    // Initialize all variables
    initscene(scene);
    initplayer(&player1);
    initmenu(WHITE);

    useraux.level = level;
    strcpy(useraux.name,name);

    if (loadlevel(&player1,boxes,scene,holes,numberofboxes,level) == 1) // Can't load file
    {
        printf("\nGame could not be loaded.");
        return;
    }

    else
    {
        loadlevel(&player1,boxes,scene,holes,numberofboxes,level);
        inithole(holes,numberofboxes,scene);

        int moving;

        moving = movement(player1,scene,boxes,numberofboxes,name,holes,&useraux);

        switch(moving){
            case 0:         // Next level
                useraux.completed++;
                gameloop(name,level+1,&useraux);
                break;

            case 1:         // New game
                useraux.moves = 0;
                useraux.total_score = 0;
                useraux.time = 0;
                gameloop(name,1,&useraux);
                break;

            case 2:         // Reload
                gameloop(name,level,&useraux);
                break;
        }
    }


    *user1 = useraux;
}

void start(char name[25])
/* First screen
   Parameters: name: string (player name) */
{
    int i;

    textcolor(GREY);
    textbackground(GREY);
    cputsxy(12,4, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

    for(i=0; i<HEIGHT-2;i++){
        cputsxy(12,i+5, "X");
        cputsxy(66,i+5, "X");}

    cputsxy(12,22, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

    textbackground(0);
    textcolor(WHITE);
    cputsxy(16,6,"Enter your name: ");
    textcolor(RED);
    gets(name);
    if (name[0]=='0')
        name = "NO ONE";

    textcolor(WHITE);
    cputsxy(18,9,"Welcome, ");
    textcolor(RED);
    cputsxy(29,9,name);
    textcolor(WHITE);

    cputsxy(15,11,"You are playing the Kovaliov's game.");
    cputsxy(15,12,"Your goal is simple: take boxes to");
    cputsxy(15,13,"holes in random positions.");
    cputsxy(15,15,"Use directional keys to move your");
    cputsxy(15,16,"character up, down, left or right.");
    cputsxy(15,17, "If it's too hard, press z ;D");
    cputsxy(46,20,"Good luck! :) ");

    gotoxy(23,24);

    system("pause");
    clrscr();
}
void end (USER user1)
/* End screen
   Parameter: user1: USER (data about the player and the match) */
{

    textbackground(BLACK);
    textcolor(WHITE);
    clrscr();

    printf("Game developed by Victoria Thibes\nfor the course Algorithms and Programming 2013/1");

    cputsxy(12,4,"Thanks for playing!");
    gotoxy(12,5);
    printf("Your score was: %.2f", user1.total_score);
    gotoxy(12,6);
    printf("Moves: %d", user1.moves);
    gotoxy(12,7);
    printf("Time: %d", user1.time);

    gotoxy(23,22);
    system("pause");
}

int main()
{

    char name[25];                      // Player's name
    int level=1;                        // Level to be played. Starts at 1.
    USER user1;                         // Info about the user and the match

    start(name);                        // First screen
    inituser(&user1);
    gameloop(name, level, &user1);      // Main loop
    score(&user1);
    ranking(user1);
    end(user1);

    return 0;
}

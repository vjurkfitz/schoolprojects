/* Initialization functions */

void inithole (HOLE holes[],int numberofboxes,char scene[HEIGHT+3][WIDTH+2])
/* Creates holes randomly.
    Parameters: holes: HOLE (vector with hole information)
                numberofboxes: int (number of  boxes in the level */
{
    int i,j;
    srand(time(NULL));
    for (i=0; i<numberofboxes; i++)
    {
        do{
            holes[i].position.x = rand()%46;
            holes[i].position.y = rand()%20;}

        while (holes[i].position.y<=4 ||

               scene[holes[i].position.y][holes[i].position.x]=='x' ||
               scene[holes[i].position.y+1][holes[i].position.x]=='x' ||
               scene[holes[i].position.y][holes[i].position.x+1]=='x' ||
               scene[holes[i].position.y+1][holes[i].position.x+1]=='x' ||

               scene[holes[i].position.y][holes[i].position.x]=='1' ||
               scene[holes[i].position.y+1][holes[i].position.x]=='1' ||
               scene[holes[i].position.y][holes[i].position.x+1]=='1' ||
               scene[holes[i].position.y+1][holes[i].position.x+1]=='1' ||

               scene[holes[i].position.y][holes[i].position.x]=='2' ||
               scene[holes[i].position.y+1][holes[i].position.x]=='2' ||
               scene[holes[i].position.y][holes[i].position.x+1]=='2' ||
               scene[holes[i].position.y+1][holes[i].position.x+1]=='2' ||

               scene[holes[i].position.y][holes[i].position.x]=='b' ||
               scene[holes[i].position.y+1][holes[i].position.x]=='b' ||
               scene[holes[i].position.y][holes[i].position.x+1]=='b' ||
               scene[holes[i].position.y+1][holes[i].position.x+1]=='b');


        holes[i].visible = 0; //nao visível
        scene[holes[i].position.y][holes[i].position.x]=='b';
        scene[holes[i].position.y+1][holes[i].position.x]=='b';
        scene[holes[i].position.y][holes[i].position.x+1]=='b';
        scene[holes[i].position.y+1][holes[i].position.x+1]=='b';
    }

    textbackground(BLACK);
}

void initboxes (BOX boxes[],int numberofboxes)
/* Initializates all boxes in position (0,0) and defines their color as green.
    Parameters: boxes: BOX (vector of boxes)
                numberofboxes: int (number of boxes in the level) */
{
    int i;

    for (i=0; i<numberofboxes; i++){
        boxes[i].position.x = 0;
        boxes[i].position.y = 0;
        boxes[i].color = GREEN;
        boxes[i].placed = 0;
    }
}

void initscene (char scene[HEIGHT+3][WIDTH+2])
/* Fills all positions in the array
    Parameter: scene: char (matrix with the scene's map) */
{
    int i,j;

    for (i=0; i<HEIGHT+3; i++)
        for (j=0; j<WIDTH+2; j++)
            scene[i][j] = '0';
}

void inituser (USER *user1)
/* Initializes all user variables as 0.
    Parameters: user1 (represents a player and their match) */
{

    USER useraux;

    useraux = *user1;

    useraux.moves = 0;
    useraux.total_score = 0;
    useraux.time = 0;
    useraux.completed = 0;

    *user1 = useraux;
}

void initplayer (PLAYER *player1)
/* Defines character/color of the player's sprite
    Parameter: player1: PLAYER (current player of the match) */
{
    PLAYER player1aux;

    player1aux = *player1;

    player1aux.ch = '1';
    player1aux.color = RED;
    player1aux.position.x = 0;
    player1aux.position.y = 0;

    *player1 = player1aux;

}

void initmenu(int color)
/* Prints a menu in the upper part of the scene.
    Parameter: color: int (color from conio.h) */
{
    textbackground(BLACK);
    textcolor(color);
    cputsxy(3,2, "NEW GAME (N)");
    cputsxy(20,2, "SAVE (S)");
    cputsxy(34,2, "PAUSE (P)");
    cputsxy(48,2, "SCORE (E)");
    cputsxy(61,2, "QUIT (Q)");
}

void cronometer(int *seconds)
/* Cronometer that is shown in the lateral panel.
    Parameters: seconds: counts the seconds in the match */
{
    clock_t secondstemp;
    int minutes=0;
    int seconds2=0;

    secondstemp = *seconds;

    do {
        secondstemp = clock() / CLOCKS_PER_SEC;
        seconds2 = secondstemp%60;
        minutes = secondstemp/60;

        gotoxy(50,18);
        printf("Time: %.2d:%.2d",minutes,seconds2);

        *seconds = secondstemp;

        if (kbhit())
            return;
        }

    while(secondstemp<1200); // 20 minutes
}

void initscores (USER scores[], int maxranking)
/* Starts all positions in the score vector
    Parameters: scores: USER (vector with all users and their matches)
                maxranking: int (ranking size) */
{

    USER noone;
    int i;

    inituser(&noone);

    for (i=0; i<maxranking; i++)
        scores[i] = noone;
}

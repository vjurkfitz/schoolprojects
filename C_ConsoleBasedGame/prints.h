/* Printing functions */


void eraseplayer (PLAYER player, int key)
/* Erases the player's previous position.
    Parameters: player: PLAYER (object that had changed positions)
                key: int (direction to which the player is moving - ASCII) */
{
    switch(key){

    case 72: // Up -> Erases y below
        textbackground(WHITE);
        gotoxy(player.position.x, player.position.y+2);
        printf(" ");
        gotoxy(player.position.x+1, player.position.y+2);
        printf(" ");break;

    case 80: // Down -> Erases y above
        textbackground(WHITE);
        gotoxy(player.position.x, player.position.y-1);
        printf(" ");
        gotoxy(player.position.x+1, player.position.y-1);
        printf(" ");break;

    case 75: // Left - Erases x on the right
        textbackground(WHITE);
        gotoxy(player.position.x+2, player.position.y);
        printf(" ");
        gotoxy(player.position.x+2, player.position.y+1);
        printf(" ");break;

    case 77: // Right -> Erases x on the left
        textbackground(WHITE);
        gotoxy(player.position.x-1, player.position.y);
        printf(" ");
        gotoxy(player.position.x-1, player.position.y+1);
        printf(" ");break;
    }
}

void printplayer (PLAYER player1)
/* Prints player on its current position.
    Parameter: player1: PLAYER (object that has moved) */
{
    textcolor(player1.color);
    textbackground(player1.color);
    gotoxy(player1.position.x, player1.position.y);
    printf("%c", player1.ch);
    gotoxy(player1.position.x, player1.position.y+1);
    printf("%c", player1.ch);
    gotoxy(player1.position.x+1, player1.position.y);
    printf("%c", player1.ch);
    gotoxy(player1.position.x+1, player1.position.y+1);
    printf("%c", player1.ch);
    textbackground(WHITE);
}

void printbox (BOX box1)
/* Prints a box on its current position.
    Parameter: box1: BOX (data about the box, such as its position and color) */
{
    textcolor(box1.color);
    textbackground(box1.color);

    cputsxy(box1.position.x,box1.position.y,"2");
    cputsxy(box1.position.x+1,box1.position.y,"2");
    cputsxy(box1.position.x,box1.position.y+1,"2");
    cputsxy(box1.position.x+1,box1.position.y+1,"2");

    textbackground(WHITE);
}

void leftpanel (char name[25], int moves)
/* Prints the game's left panel, with information about the player and the game.
    Parameters: name: string (player's name)
                moves: int (number of times the player has moved) */
{
    textbackground(BLACK);
    textcolor(WHITE);
    gotoxy(50,7);
    printf("Player: ");
    gotoxy(59, 7);
    textcolor(RED);
    printf("%s", name);
    gotoxy(50, 9);
    textcolor(WHITE);
    printf("Moves: %.5d", moves);

    cputsxy(50,13,"Press TAB to");
    cputsxy(50,14,"use upper menu");
}

void sceneprint (int x, int y, int option)
/* Used to print fixed walls and empty spaces.
    Parameters: x and y: int (coordinates on the screen)
                option: int (if 1, prints fixed wall; otherwise, empty space) */
{

    if (option == 1)                     // Fixed wall
    {
        textbackground(BLACK);
        textcolor(BLACK);
        cputsxy(x,y,"X");
        cputsxy(x+1,y,"X");
        cputsxy(x,y+1,"X");
        cputsxy(x+1,y+1,"X");
    }
    else                                //  Empty space;
    {
        textbackground(WHITE);
        textcolor(WHITE);
        cputsxy(x,y,"0");
        cputsxy(x+1,y,"0");
        cputsxy(x,y+1,"0");
        cputsxy(x+1,y+1,"0");
    }
}

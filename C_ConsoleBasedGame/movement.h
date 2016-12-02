/* Movement functions */

void movebox (int key, PLAYER *moving, BOX *moved)
/* Moves player and box in the direction indicated by the key.
    Parameters: moving: PLAYER (object that moves the box)
                moved: BOX (a box that is being moved)
                key: int (direction to which the player is moving - ASCII) */
{

    PLAYER movingaux;
    BOX movedaux;

    movingaux = *moving;
    movedaux = *moved;


    switch (key){

    case 72: // Up
        movingaux.position.y -= 1;
        movedaux.position.y -=1;
        printbox(movedaux);
        printplayer(movingaux);
        eraseplayer(movingaux,key);
        break;

    case 80: // Down
        movingaux.position.y += 1;
        movedaux.position.y +=1;
        printbox(movedaux);
        printplayer(movingaux);
        eraseplayer(movingaux,key);
        break;

    case 75: // Left
        movingaux.position.x -= 1;
        movedaux.position.x -=1;
        printbox(movedaux);
        printplayer(movingaux);
        eraseplayer(movingaux,key);
        break;

    case 77: // Right
        movingaux.position.x += 1;
        movedaux.position.x +=1;
        printbox(movedaux);
        printplayer(movingaux);
        eraseplayer(movingaux,key);
        break;

    }

    *moving = movingaux;
    *moved = movedaux;

}

void moveplayer (int key, PLAYER *player)
/* Moves the player in the direction indicated by the key
    Parameters: key: int (direction to which the player is moving - ASCII)
                player: PLAYER (object that will be moved) */
{
    PLAYER playeraux;

    playeraux = *player;

    switch(key){
        case 72: // Up
            playeraux.position.y -=1;
            printplayer(playeraux);
            eraseplayer(playeraux,key);
            break;

        case 80: // Down
            playeraux.position.y +=1;
            printplayer(playeraux);
            eraseplayer(playeraux,key);
            break;

        case 75: // Left
            playeraux.position.x -=1;
            printplayer(playeraux);
            eraseplayer(playeraux,key);
            break;

        case 77: // Right
            playeraux.position.x +=1;
            printplayer(playeraux);
            eraseplayer(playeraux,key);
            break;
    }

    *player = playeraux;
}

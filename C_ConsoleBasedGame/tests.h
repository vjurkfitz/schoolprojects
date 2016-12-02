/* Functions to perform tests */

int testbox(COORDINATE position, BOX box, int key)
/* Function to test if the box hit by a player can be moved or not.
    Parameters: position: COORDINATE (player position)
                box: BOX (box that was hit)
                key: int (direction to which the player is moving - ASCII)

    Returns: int (if 0, box is moved; if 1, box is not moved and player remains still */
{
    int result;

    switch(key)
    {
    case 72:    // Up
        if (position.x == box.position.x && position.y == box.position.y+2)         // Move position: same x (width) and y is the player's + 2 (2 points below)
            {result = 0;break;}

        if (position.x == box.position.x+1 && position.y == box.position.y+2)       // Aligned vertically, but not horizontally (player to the right)
            {result=1;break;}

        if (position.x == box.position.x-1 && position.y == box.position.y+2)   // Aligned vertically, but not horizontally (player to the left)
            {result=1;break;}

        break;

    case 80: // Down
        if (position.x == box.position.x && position.y == box.position.y-2)         // Move position: same x (width) and player's is 2 points smaller than the box's (2 points above)
            {result = 0;break;}

        if (position.x == box.position.x+1 && position.y == box.position.y-2)       // Aligned vertically, but not horizontally (player to the right)
            {result=1;break;}

        if (position.x == box.position.x-1 && position.y == box.position.y-2)       // Aligned vertically, but not horizontally (player to the left)
            {result=1;break;}

        break;

    case 75: // Left
        if (position.x == box.position.x+2 && position.y == box.position.y)         // Move position: same y (height) and player's x is 2 points greater than the box's (2 units to the right)
            {result = 0;break;}

        if (position.x == box.position.x+2 && position.y == box.position.y+1)       // Aligned horizontally, but not vertically - player below
            {result=1;break;}

        if (position.x == box.position.x+2 && position.y == box.position.y-1)       // Aligned horizontally, but not vertically - player above
            {result=1;break;}

        break;

    case 77: // Right
        if (position.x == box.position.x-2 && position.y == box.position.y)         // // Move position: same y (height) and player's x is 2 points less than the box's (2 units to the left)
            {result = 0; break;}

        if (position.x == box.position.x-2 && position.y == box.position.y+1)       // Aligned horizontally, but not vertically - player below
            {result=1;break;}

        if (position.x == box.position.x-2 && position.y == box.position.y-1)       // Aligned horizontally, but not vertically - player above
            {result=1;break;}

        break;
    }

    return result;
}

int testwall (COORDINATE position, char scene[HEIGHT+3][WIDTH+2], int key)
/* Function to test if the player hit a wall while moving
    Parameters: position: COORDINATE (player position)
                scene: character (matrix with the scene map)
                key: int (direction to which the player is moving - ASCII)

    Returns: int (if 0, there is no wall; if 1, there is a wall */
{
    int result = 0;

    switch (key){

    case 72: // Up
        if (scene[position.y-1][position.x] == 'x' ||       // Square above has a wall
            scene[position.y-1][position.x+1] == 'x')
            result = 1;

            break;

    case 80: // Down
         if (scene[position.y+2][position.x] == 'x' ||       // Square below has a wall
            scene[position.y+2][position.x+1] == 'x')
            result = 1;

            break;

    case 75: // Left
         if (scene[position.y][position.x-1] == 'x' ||       // Square to the left has a wall
            scene[position.y+1][position.x-1] == 'x')
            result = 1;

            break;

    case 77: // Right
         if (scene[position.y][position.x+2] == 'x' ||       // Square to the right has a wall
            scene[position.y+1][position.x+2] == 'x')
            result = 1;

            break;

    }

    return result;

}


int boxbox (BOX movingbox, BOX box1, int key)
/* Tests if a box hit another.
    Parameters: movingbox: BOX (box that is moving)
                box1: BOX (box that is standing still)
                key: int (direction to which the player is moving - ASCII)

    Returns: int (0, boxes hit each other; 1, boxes did not hit */
{
    int result = 0;

    switch(key){

    case 72:    // Up
        if (movingbox.position.y == box1.position.y+2 &&
            (movingbox.position.x == box1.position.x ||
             movingbox.position.x == box1.position.x+1 ||
             movingbox.position.x+1 == box1.position.x))
            result = 1;

        break;

    case 80:    // Down
        if (movingbox.position.y == box1.position.y-2 &&
            (movingbox.position.x == box1.position.x ||
             movingbox.position.x == box1.position.x+1 ||
             movingbox.position.x+1 == box1.position.x))
            result = 1;


        break;
    case 75:    // Left
        if (movingbox.position.x == box1.position.x+2 &&
            (movingbox.position.y == box1.position.y ||
             movingbox.position.y == box1.position.y+1 ||
             movingbox.position.y+1 == box1.position.y))
            result = 1;

        break;
    case 77:    // Right
        if (movingbox.position.x == box1.position.x-2 &&
            (movingbox.position.y == box1.position.y ||
             movingbox.position.y == box1.position.y+1 ||
             movingbox.position.y+1 == box1.position.y))
            result = 1;

        break;
    }

    return result;
}

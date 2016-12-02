/* Structures used for the game */

typedef struct coord // COORDINATE x y
{
    int x; // Line
    int y; // Column

} COORDINATE;

typedef struct box // Mobile BOX
{
    int color;
    int placed; // no(0) or yes(1)
    COORDINATE position;
} BOX;

typedef struct hole // HOLE (goal)
{
    int visible; // no(0) or yes(1)
    COORDINATE position;
} HOLE;

typedef struct player // PLAYER
{
    char ch; // Character used to draw the player
    int color; // Player color
    COORDINATE position;
} PLAYER;

typedef struct user // USER - Used to record a score
{
    int time;
    char name[25];
    int moves;
    float total_score;
    int level;
    int completed;
} USER;

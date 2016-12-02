/* Functions to manipulate files */

void score (USER *currentuser)
/* Calculates player score during the match
    Parameter: currentuser: USER (data about the player and the match) */
{
    float totalpoints;
    USER aux;

    aux = *currentuser;

    if ((aux.moves + aux.time)/2 <= 0)
        totalpoints = 0;

    else
        totalpoints = (10 * 10 * 10 * aux.completed * aux.completed * aux.completed) /((aux.moves + aux.time) / 2);

    aux.total_score = totalpoints;

    *currentuser = aux;

}

int readranking (USER scores[], int maxranking)
/* Reads a file with scores and fills a vector of scores
    Parameters: scores: USER (vector with information about users sorted by score)
                maxranking: int (ranking size)

    Returns: int (1 if there is an error, 0 if there's not) */
{

    FILE* highscore;
    int i;

    highscore = fopen("highscore.bin","r+b");

    if (!highscore){
        printf("Error when opening file.");
        return 1;
    }
    else {
        while (!feof(highscore))
            for (i=0; i<maxranking; i++){
                fread(&scores[i],sizeof(USER),1,highscore);
                }

        fclose (highscore);
    }
    return 0;

}

void writeranking (USER scores[], int maxranking)
/* Writes ranking in a .txt file
    Parameters: scores: USER (vector with information about users sorted by score)
                maxranking: int (vector size) */
{
    FILE* highscore;

    int i;

    highscore = fopen("highscore.bin","w+b");

    for (i=0; i<maxranking; i++)
        fwrite(&scores[i],sizeof(USER),1,highscore);

    fclose(highscore);
}

int openfile (int level)
/* Function to open a .txt file representing a level
    Parameter: level: int (level that will be played) */
{
    FILE* lvl;

    switch(level){                   // Open file
        case 1: lvl = fopen ("level1.txt", "r"); break;
        case 2: lvl = fopen ("level2.txt", "r"); break;
        case 3: lvl = fopen ("level3.txt", "r"); break;
    }

    if (!lvl){
        printf("Level could not be loaded."); // Error
        system("pause");
        system("cls");
        return 1;
    } // End program
    else
        return lvl;
}

void savepoints(USER currentuser)
/* Function to save the information of the current player.
    Parameters: currentuser: USER (data about the player and the match) */
{
    FILE* score;
    int num=0;
    char num1[3];   // String version of number
    char name[30];  // Text file name

    strcpy(name,currentuser.name);
    strcat(name,".txt");
    while (fopen(name, "r") != NULL){ // In case there is already a file with that name, a number will be added to it

        num++;
        strcpy(name,currentuser.name);
        itoa(num,num1,10); // Puts num in a string
        strcat(name,num1);
        strcat(name,".txt");
    }

    score = fopen(name, "w+b");

    fwrite(&currentuser, sizeof(USER), 1, score);

        if (ferror(score)){
            gotoxy(1, HEIGHT+3);
            printf("\nError when writing.");}

        else{
            gotoxy(1, HEIGHT+3);
            printf("\nSuccess.");}

    fclose(score);
}

int loadlevel(PLAYER *player1,  BOX boxes[], char scene[HEIGHT+3][WIDTH+2],HOLE holes[], int numberofboxes, int level)
/* Function to load the level that will be player.
    Parameters: player1: PLAYER (object controlled by the user)
                boxes: BOX (vector of boxes)
                scene: char (matrix with the scene's map)
                holes: HOLE (vector of holes)
                numberofboxes: int (number of boxes in the level)
                level: int (level that will be played */
{
    FILE* lvl;
    int option; // Para chamar a função printgeral
    int x, y, i; // Índices de posição / i=Índice para o vetor
    char temp; // Variável para pegar os caracteres do arquivo txt

    PLAYER player1temp;

    player1temp = *player1;

    initboxes(boxes,numberofboxes);

    if (openfile(level) == 1) // Error.
        return 1;

    else {
        lvl = openfile(level);
        while (!feof(lvl)) // Until the end of file
        {
            for (y=3; y<HEIGHT+3; y+=2){            // Scene is put two units below so there's room for the upper menu
                for (x=1; x<WIDTH; x+=2){
                    fscanf (lvl, "%c", &temp);
                    switch(temp){
                        case 'x':   // Wall
                            option = 1;
                            sceneprint (x,y, option);
                            scene[y][x] = 'x';
                            scene[y+1][x] = 'x';
                            scene[y][x+1] = 'x';
                            scene[y+1][x+1] = 'x';
                            break;

                        case '1':   // Player
                            player1temp.position.x = x;
                            player1temp.position.y = y;
                            printplayer(player1temp);
                            scene[y][x] = '1';
                            scene[y+1][x] = '1';
                            scene[y][x+1] = '1';
                            scene[y+1][x+1] = '1';
                            break;

                       case '2':    // Box
                           for (i=0; i<numberofboxes; i++){
                                if (boxes[i].position.x == 0){
                                    boxes[i].position.x = x;
                                    boxes[i].position.y = y;
                                    printbox(boxes[i]);
                                    break;
                                }
                                scene[y][x] = '2';
                                scene[y+1][x] = '2';
                                scene[y][x+1] = '2';
                                scene[y+1][x+1] = '2';
                            }
                            break;

                        case '0':   // Empty
                            option = 0;
                            sceneprint (x,y, option);
                            break;
                        case ' ':
                            break;
                        default: printf("\n");
                    }
                } // End for
        }// End while
    } // End else

    textcolor(0);
    textbackground(0);
    *player1 = player1temp;
    return 0;
    }
}

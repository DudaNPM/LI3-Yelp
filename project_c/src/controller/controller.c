#include "../../includes/controller/controller.h"

void runProgram(){
    int exit = 0;
    char command[50];
    char variavel;
    char variavel2;
    int linha;
    int coluna;
    char * queryEargs = malloc(sizeof(char) * 100);
    char * query = malloc(sizeof(char) * 35);
    char * arg1 = malloc(sizeof(char) * 30);
    char * arg2 = malloc(sizeof(char) * 30);
    TABLE x = NULL;
    TABLE y = NULL;

    char * users_path = "input_files/users_full.csv";
    char * businesses_path = "input_files/business_full.csv";
    char * reviews_path = "input_files/reviews_1M.csv";

    showLoading();
    SGR sgr = load_sgr(users_path, businesses_path, reviews_path);

    while(!exit){
        showMenu();

        if (fgets(command, 50, stdin)){
            command[strlen(command) - 1] = '\0'; // tirar o '\n' que o fgets lê

        // caso de saida do programa
            if (strcmp(command, "quit") == 0){
                exit = 1;
                //clearWindow();
            }

        // caso de mostragem das variaveis .Valgrind -> check
            else if ( (strcmp(command, "show(x)") == 0 && x) || (strcmp(command, "show(y)") == 0 && y) ){
                if (command[5] == 'x') showTable(x);
                if (command[5] == 'y') showTable(y);
            }

        // caso de indexação .Valgrind -> check
            // Nota: nos casos em que as variáveis são iguais é usada uma table auxiliar de forma a libertar toda a memória
            //       utilizada pela table da qual se vai fazer a indexação. Foi confirmado no Valgrind.
            else if ( sscanf(command, "%c = %c[%d][%d]", &variavel, &variavel2, &linha, &coluna) == 4 ){
                if (variavel == 'x'){
                    if (variavel2 == 'x'){
                        TABLE auxT = indexTable(x, linha, coluna);
                        destroyTable(x);
                        x = auxT;
                    }
                    if (variavel2 == 'y') x = indexTable(y, linha, coluna);
                }
                if (variavel == 'y'){
                    if (variavel2 == 'x') y = indexTable(x, linha, coluna);
                    if (variavel2 == 'y'){
                        TABLE auxT = indexTable(y, linha, coluna);
                        y = indexTable(y, linha, coluna);
                        destroyTable(y);
                        y = auxT;
                    }
                }
                clearWindow();
            }

        // caso de execuçao de uma query
            else{
                sscanf(command, "%c = %s", &variavel, queryEargs);
                query = strtok(queryEargs, "(");
                arg1 = strtok(NULL, ",)");
                arg2 = strtok(NULL, ",)");
    
            // caso para query 2 .Valgrind -> check
                if (strcmp(query, "businesses_started_by_letter") == 0){
                    if (variavel == 'x') x = businesses_started_by_letter(sgr, arg1[0]);
                    if (variavel == 'y') y = businesses_started_by_letter(sgr, arg1[0]);
                    clearWindow();
                }

            // caso para query 3 .Valgrind -> check
                if(strcmp(query, "business_info") == 0){
                    if (variavel == 'x') x = business_info(sgr, arg1);
                    if (variavel == 'y') y = business_info(sgr, arg1);
                    clearWindow();
                }

            // caso para query 4 .Valgrind -> check
                if(strcmp(query, "businesses_reviewed") == 0){
                    if (variavel == 'x') x = businesses_reviewed(sgr, arg1);
                    if (variavel == 'y') y = businesses_reviewed(sgr, arg1);
                    clearWindow();
                }

            // caso para query 5 .Valgrind -> check
                if(strcmp(query, "businesses_with_stars_and_city") == 0){
                    float aux = atof(arg1);
                    if (variavel == 'x') x = businesses_with_stars_and_city(sgr, aux, arg2);
                    if (variavel == 'y') y = businesses_with_stars_and_city(sgr, aux, arg2);
                    clearWindow();
                }

            // caso para query 6
                if(strcmp(query, "top_businesses_by_city") == 0){
                    int aux = atoi(arg1);
                    if (variavel == 'x') top_businesses_by_city(sgr, aux);
                    if (variavel == 'y') top_businesses_by_city(sgr, aux);
                    clearWindow();
                }

            // caso para query 8
                if(strcmp(query, "top_businesses_with_category") == 0){
                    int aux = atoi(arg1);
                    if (variavel == 'x') x = top_businesses_with_category(sgr, aux, arg2);
                    if (variavel == 'y') y = top_businesses_with_category(sgr, aux, arg2);
                    clearWindow();
                }

            // caso para query 9 .Valgrind -> check
                if(strcmp(query, "reviews_with_word") == 0){
                    if (variavel == 'x') x = reviews_with_word(sgr, arg1);
                    if (variavel == 'y') y = reviews_with_word(sgr, arg1);
                    clearWindow();
                }
            }
        }
    }
    
    g_free(queryEargs);
    if (x) destroyTable(x);
    if (y) destroyTable(y);
    free_sgr(sgr);
}
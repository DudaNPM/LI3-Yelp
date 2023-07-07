#include "../../includes/view/interface.h"


void showTable(TABLE t){
	int c, l,espacos ,i;

	int colunasOcupadas = getColunasOcupadas(t);
	char * colName;
	char * campName;

	for(c = 0; c < colunasOcupadas; c++){
		colName = getTopName(t, c);
		if (strcmp(colName, "name") == 0)
			printf("--------------------------------------------------------------------");
		else if (strcmp(colName, "business_id") == 0)
			printf("--------------------------");
		else if (strcmp(colName, "city") == 0)
			printf("--------------------------------------------------------------------");
		else printf("----------");
		free(colName);
	}
	printf("\n");

	for(c = 0; c < colunasOcupadas; c++){
		colName = getTopName(t, c);
		if (strcmp(colName, "name") == 0)
			printf("|                               %s                                ", colName);

		else if (strcmp(colName, "business_id") == 0) 
			printf("|       %s      ", colName);

		else if (strcmp(colName, "city") == 0)
			printf("|                               %s                                ", colName);

		else printf("| %s ", colName);

		free(colName);
	}
	printf("|\n");

	for(c = 0; c < colunasOcupadas; c++){
		colName = getTopName(t, c);
		if (strcmp(colName, "name") == 0)
			printf("--------------------------------------------------------------------");
		else if (strcmp(colName, "business_id") == 0)
			printf("--------------------------");
		else if (strcmp(colName, "city") == 0)
			printf("--------------------------------------------------------------------");
		else printf("----------");
		free(colName);
	}
	printf("\n");
	
	for(l = 0; l < getNumLinhas(t); l++){
		for(c = 0; c < colunasOcupadas; c++){
			colName = getTopName(t, c);
			campName = getCampName(t, c, l);
			if(strcmp(colName, "name") == 0){
				int comp = strlen(campName);

				printf("|");

				espacos = ((64 - comp) / 2);

				for(i = 0; i <= espacos; i++){	
					printf(" ");
				}

				printf("%s ", campName);

				for(i = 0; i <= espacos; i++){	
					printf(" ");
				}

				if(comp % 2 != 0) printf(" ");
			}

			else if(strcmp(colName, "city") == 0){
				int comp = strlen(campName);

				printf("|");

				espacos = ((64 - comp) / 2);

				for(i = 0; i <= espacos; i++){	
					printf(" ");
				}

				printf("%s ", campName);

				for(i = 0; i <= espacos; i++){	
					printf(" ");
				}

				if(comp % 2 != 0) printf(" ");
			}

			else if(strcmp(colName, "business_id") == 0){
				printf("| %s ", campName);
			}

			else printf("|   %s   ", campName);
			free(colName);
			free(campName);
		}
		printf("|\n");
		
		for(c = 0; c < colunasOcupadas; c++){
		colName = getTopName(t, c);
		if (strcmp(colName, "name") == 0)
			printf("--------------------------------------------------------------------");
		else if (strcmp(colName, "business_id") == 0)
			printf("--------------------------");
		else if (strcmp(colName, "city") == 0)
			printf("--------------------------------------------------------------------");
		else printf("----------");
		free(colName);
	}
		printf("\n");
	}
}



void showMenu(){
    printf("\n-------------------------------------------\n");
    printf("             LI3 C_Project\n");
    printf("-------------------------------------------\n\n");
    printf("| Comandos possíveis para este programa. |\n\n");
    printf("-> Atribuição do valor de queries.\n");
    printf("Sintaxe: x = nome_da_query(arg1,arg2,...)\n\n");
    printf("-> Visualização de variáveis (x ou y).\n");
    printf("Sintaxe: show(x)\n\n");
    printf("-> Indexação.\n");
    printf("Sintaxe: x = y[linha][coluna]\n\n");
    printf("-> Terminação do programa.\n");
    printf("Sintaxe: quit\n\n");
    printf("Por favor, escreve um comando: ");
}


void clearWindow(){
    system("clear");
}


void showLoading(){
	printf("Loading files...\n");
}
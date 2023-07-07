#ifndef __INTERFACE__
#define __INTERFACE__

#include "../model/stats.h"
#include "../model/table.h"

/* Função que recebe uma table e faz print da mesma no ecrã */
void showTable(TABLE t);

/* Função que faz print do menu do programa */
void showMenu();

/* Função que limpa o ecrã */
void clearWindow();

/* ... */
void showLoading();

#endif
#ifndef __TABLE__
#define __TABLE__

#include <stdlib.h>
#include <string.h>
#include "glibWarningAvoid.h"

typedef struct coluna* COLUNA;
typedef struct table* TABLE;

/* */
int getColunasOcupadas(TABLE t);

/* */
char * getTopName(TABLE t, int numColuna);

/* */
char * getCampName(TABLE t, int numColuna, int numLinha);

/* */
int getNumLinhas(TABLE t);

/* */
void addColuna(TABLE t, char * topo, GSList * campos);

/* */
TABLE initTable(int numColunas);

/* */
void destroyTable(TABLE t);

#endif
#ifndef __BUSINESS__
#define __BUSINESS__

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include"glibWarningAvoid.h"

typedef struct business* Business;

/* Devolve o Id de um Business */
char * getBusinessId(Business b);

/* Devolve o nome de um Business */
char * getBusinessName(Business b);

/* Devolve a cidade de um Business */
char * getBusinessCity(Business b);

/* Devolve o estado de um Business */
char * getBusinessState(Business b);

/* Devolve a lista de categorias de um Business */
GSList * getBusinessCategories(Business b);

/* Criação de um Business a partir de uma linha de texto. */
/* O objetivo aqui pretendido é, obter os parâmetros de   */
/* um Business que se encontram numa string separados por */
/* ";" e passa los à função initBusiness                  */
Business initBusinessFromLine(char * line, char * separator);

/* Free de um Business */
void destroyBusiness(Business b);

#endif
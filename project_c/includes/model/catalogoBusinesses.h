#ifndef CATALOGOBUSINESS_H
#define CATALOGOBUSINESS_H 

#include"business.h"


typedef struct businesses* BusinessC;


/* Aloca espaço para o catalogo de business (BusinessC) criando uma hash table */
BusinessC initBusinessC();

/* Função de leitura de um catalogo de businesses a partir de um ficheiro */
BusinessC loadBusinessCFromFile(BusinessC catalogo, char* filename);

/* Verifica se um Business existe no catalogo dado o seu id */
int businessExists(BusinessC catalogo, char * id);

/* Devolve a uma cópia da hashtable com os business */
GHashTable * getBusinesses(BusinessC catalogo);

/* Devolve um Business com um determinado id */
/* Nota: é preciso verificar primeiro se     */
/* o Business existe no catalogo             */
Business getBusiness(BusinessC catalogo, char *id);

/* Determina o numero total de Business existentes no catalogo */
int getTotalBusiness(BusinessC catalogo);

/* Free de um catalogo de businesses */
void destroyBusinessC(BusinessC catalogo);

#endif
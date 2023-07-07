#ifndef __CATALOGOREVIEWS_H__
#define __CATALOGOREVIEWS_H__ 

#include "review.h"
#include "catalogoUsers.h"
#include "catalogoBusinesses.h"


typedef struct reviews* ReviewC;


/* Aloca espaço para o catalogo de reviews (ReviewC) criando uma hash table */
ReviewC initReviewC();

/* Função de leitura de um catalogo de reviews a partir de um ficheiro */
ReviewC loadReviewCFromFile(ReviewC catalogoR, UserC catalogoU, BusinessC catalogoB, char* filename);

/* Verifica se uma Review existe no catalogo dado o seu id */
int reviewExists(ReviewC catalogo, char * id );

/* Devolve uma cópia da hashtable com as reviews */
GHashTable * getReviews(ReviewC catalogo);

/* Devolve uma Review com um determinado id */
/* Nota: é preciso verificar primeiro se a  */
/* Review existe no catalogo                */
Review getReview(ReviewC catalogo, char *id);

/* Free de um catalogo de Reviews */
void destroyReviewC(ReviewC catalogo);

#endif
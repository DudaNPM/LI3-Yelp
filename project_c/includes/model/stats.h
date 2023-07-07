#ifndef __STATS__
#define __STATS__

#include "catalogoUsers.h"
#include "catalogoReviews.h"
#include "catalogoBusinesses.h"

typedef struct businessData* BD;


/* Função que devolve uma lista com os ids dos negócios que comecam pela letra letter (case insensitive) */
GSList * businessStartedByLetterIds(BusinessC catalogo, char letter);

/* Função que conta o número total de estrelas de um business */
float countStars(ReviewC catalogo, char * business_id);

/* Função que conta o número total de reviewa de um business */
int countReviews(ReviewC catalogo, char * business_id);

/* Função que dado o id de um User retorna uma lista com */
/* os id's dos business, dos quais este fez review       */
GSList * businessReviewedIdList(ReviewC catalogo, char * user_id);

/* Função que dado uma lista de id's de */
/* business, retorna o nome dos mesmos  */
GSList * convertIdsToNames(BusinessC catalogo, GSList * ids);

/* Função que devolve uma lista com os id's dos negócios */
/* que apresentam reviews com mais de n estrelas         */
GSList * businessIdWithStarsList(ReviewC catalogoR, float stars);

/* Função que filtra os id's de negócios numa   */
/* lista de acordo com a cidade a que pertencem */
GSList * businessIdWithStarsListFilter(BusinessC catalogo, GSList * ids, char * city);

char * getBusinessIdBD(BD businessData);
char * getBusinessNameBD(BD businessData);
float getMediaBD(BD businessData);
int getReviewsBD(BD businessData);
void destroyBD(BD businessData);


/* Cria uma hash table de hash tables de forma a guardar informação individualmente   */
/* de todos os negócios de todas as cidades. A exterior terá um valor por cada cidade */
/* existente, por sua vez, cada cidade terá uma hash table associada com informações  */
/* de todos os negociós pertencentes à mesma. INFO + detalhada no .c                  */
GHashTable * citysHT(ReviewC catalogoR, BusinessC catalogoB);

/* Função que torna mais acessivel a informação conseguida na função anterior, criando */
/* uma lista de listas. Cada lista "secundária" terá informação dos negócios de uma    */
/* certa cidade. INFO + detalhada no .c                                                */
GSList * getTopNFromCitys(GHashTable * htOfht, int n);

/* Função que devolve a lista de estados de todas as reviews dado um user_id */
GSList * statesOfUserReviews(ReviewC catalogoR, BusinessC catalogoB, char * user_id);

/* Função que dada a lista de estados, determina se o user é internacional */
int validaUserInternacional(GSList * states);

/* */
GHashTable * categoriesHT(ReviewC catalogoR, BusinessC catalogoB, char * categoria);

/* */
GSList * getTopNCategories(GHashTable * ht, int n);

/* Função que dada uma palavra e um Catalogo de reviews retorna uma    */
/* lista ligada com todos ids de reviews que a mencionam no campo text */
GSList* reviewsWithWordIdList(ReviewC catalogoR, char *word);

#endif
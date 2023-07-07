#ifndef __SGR__
#define __SGR__

#include "stats.h"
#include "table.h"

typedef struct sgr* SGR;

UserC getUserC(SGR sgr);
BusinessC getBusinessC(SGR sgr);
ReviewC getReviewC(SGR sgr);

SGR init_sgr();

void free_sgr(SGR sgr);

/* Dado o caminho para os 3 ficheiros (Users, Businesses, e Reviews), */
/* lê o seu conteúdo e carrega as estruturas de dados correspondentes */
SGR load_sgr(char *users, char *businesses, char *reviews);

/* Determina a lista de nomes de negócios e o número total */
/* de negócios cujo nome se inicia por uma dada letra      */
TABLE businesses_started_by_letter(SGR sgr, char letter);

/* Dado um id de negócio, determinar a sua informação: */
/* nome, cidade, estado, stars, e número total reviews */
TABLE business_info(SGR sgr, char *business_id);

/* Dado um id de utilizador, determinar a */
/* lista de negócios aos quais fez review */
TABLE businesses_reviewed(SGR sgr, char *user_id);

/* Dado um número n de stars e uma cidade, determinar a */
/* lista de negócios com n ou mais stars na dada cidade */
TABLE businesses_with_stars_and_city(SGR sgr, float stars, char *city);

void top_businesses_by_city(SGR sgr, int top);

/* Determina lista de user_ids com reviews de diferentes estados */
TABLE international_users(SGR sgr);

/* */
TABLE top_businesses_with_category(SGR sgr, int top, char *category);

/* Dada um palavra determina a lista de ids */
/* de reviews que a mencionam no campo text */
TABLE reviews_with_word(SGR sgr, char *word);

#endif
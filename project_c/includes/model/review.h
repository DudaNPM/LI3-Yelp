#ifndef __REVIEW__
#define __REVIEW__

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include"glibWarningAvoid.h"


typedef struct review* Review;

/* Devolve o Id de uma Review */
char * getReviewId(Review r);

/* Devolve o id do User associado à Review */
char * getUserIdFromReview(Review r);

/* Devolve o id do Business associado à Review */
char * getBusinessIdFromReview(Review r);

/* Devolve o parametro stars de uma Review */
float getStars(Review r);

/* Devolve o parametro useful de uma Review */
int getUseful(Review r);

/* Devolve o parametro funnny de uma Review */
int getFunny(Review r);

/* Devolve o parametro cool de uma Review */
int getCool(Review r);

/* Devolve a data de uma Review */
char * getDate(Review r);

/* Devolve o parametro texto de uma Review */
GString * getText(Review r);

/* Criação de uma Review a partir de uma linha de texto. */
/* O objetivo aqui pretendido é, obter os parâmetros de  */
/* uma Review que se encontram numa string separados por */
/* ";" e passa los à função initReview                   */
Review initReviewFromLine(char * line, char * separator);

/* Free de uma Review */
void destroyReview(Review r);

#endif
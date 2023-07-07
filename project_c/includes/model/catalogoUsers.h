#ifndef CATALOGOUSERS_H
#define CATALOGOUSERS_H 

#include"user.h"

typedef struct users* UserC;


/* Aloca espaço para o catalogo de users (UserC) criando uma hash table */
UserC initUserC();

/* Função de leitura de um catalogo de users a partir de um ficheiro */
UserC loadUserCFromFile(UserC catalogo, char* filename);

/* Verifica se um Utilizador existe no catalogo dado o seu id */
int UserExists(UserC catalogo, char* id);

/* Devolve uma cópia da hashtable com as reviews */
GHashTable * getUsers(UserC catalogo);

/* Devolve um User com um determinado id */
/* Nota: é preciso verificar primeiro se */
/* o User existe no catalogo             */
User getUser(UserC catalogo, char *id);

/* Determina o numero total de Users existentes no catalogo */
int getTotalUsers(UserC catalogo);

/* Free de um catalogo */
void destroyUserC(UserC catalogo);

#endif
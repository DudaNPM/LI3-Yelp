#ifndef __USER__
#define __USER__

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include"glibWarningAvoid.h"

typedef struct user* User;

/* Devolve o Id de um User */
char * getUserId(User u);

/* Devolve o nome de um User */
char * getUserName(User u);

/* Devolve a lista de amigos de um User */
//GSList * getUserFriends(User u);

/* Criação de um User a partir de uma linha de texto. O */
/* objetivo aqui pretendido é, obter os parâmetros de   */
/* um User que se encontram numa string separados por   */
/* ";" e passa los à função initUser                    */
User initUserFromLine(char * line, char * separator);

/* Free de um User */
void destroyUser(User u);

#endif
#include "../../includes/model/catalogoUsers.h"

#define BUFF_SIZE 350000


UserC initUserC(){
    UserC catalogo = g_hash_table_new_full(g_str_hash, g_str_equal, g_free, (GDestroyNotify) destroyUser);
    return catalogo;
}


UserC loadUserCFromFile(UserC catalogo, char* filename){
    char buffer[BUFF_SIZE];
    FILE* f = fopen(filename, "r");
    
    if (!f) { return NULL; }

    while(fgets(buffer, BUFF_SIZE, f)){
        buffer[strcspn(buffer,"\n")] = 0;
        User user = initUserFromLine(buffer ,";");
        g_hash_table_insert(catalogo, getUserId(user), user);
    }
    
    fclose(f);
    return catalogo;
}


int userExists(UserC catalogo, char * id ){
    int existe = 0;
    if (g_hash_table_contains(catalogo, id))
        existe = 1;
    return existe;
}


void auxGetUsers(gconstpointer key, gconstpointer value, gconstpointer user_data){
    g_hash_table_insert((GHashTable *) user_data, (char *) key, (User) value);
}


GHashTable * getUsers(UserC catalogo){
    GHashTable * clone = g_hash_table_new_full( g_str_hash, g_str_equal, g_free, (GDestroyNotify) destroyUser);
    g_hash_table_foreach(catalogo, (GHFunc) auxGetUsers, clone);
    return clone;
}


User getUser(UserC catalogo, char *id){ return g_hash_table_lookup(catalogo, id); }


int getTotalUsers(UserC catalogo){ return g_hash_table_size(catalogo); }


void destroyUserC(UserC catalogo){ g_hash_table_destroy(catalogo); }
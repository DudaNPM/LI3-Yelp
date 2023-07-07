#include "../../includes/model/catalogoBusinesses.h"

#define BUFF_SIZE 500


BusinessC initBusinessC(){
	BusinessC catalogo = g_hash_table_new_full( g_str_hash, g_str_equal, g_free, (GDestroyNotify) destroyBusiness);
	return catalogo;
}


BusinessC loadBusinessCFromFile(BusinessC catalogo, char* filename){
	char buffer[BUFF_SIZE];
	FILE* f = fopen(filename, "r");
	
	if (!f){
		return NULL;
	}

	while(fgets(buffer, BUFF_SIZE, f)){
		buffer[strcspn(buffer,"\n")] = 0;
		Business b = initBusinessFromLine(buffer ,";");
		//if(validUser(business)) add quando estiver definida.
		g_hash_table_insert(catalogo, getBusinessId(b), b);
	}
	
	fclose(f);
	return catalogo;
}


int businessExists(BusinessC catalogo, char * id){
	int existe = 0;
	if (g_hash_table_contains(catalogo, id))
		existe = 1;
	return existe;
}


void auxGetBusinesses(gconstpointer key, gconstpointer value, gconstpointer user_data){
	g_hash_table_insert((GHashTable *) user_data, (char *) key, (Business) value);
}


GHashTable * getBusinesses(BusinessC catalogo){
	GHashTable * clone = g_hash_table_new_full( g_str_hash, g_str_equal, g_free, (GDestroyNotify) destroyBusiness);
	g_hash_table_foreach(catalogo, (GHFunc) auxGetBusinesses, clone);
	return clone;
}


Business getBusiness(BusinessC catalogo, char *id){ return g_hash_table_lookup(catalogo, id); }


int getTotalBusiness(BusinessC catalogo){ return g_hash_table_size(catalogo); }


void destroyBusinessC(BusinessC catalogo){ g_hash_table_destroy(catalogo); }
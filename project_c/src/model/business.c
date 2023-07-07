#include "../../includes/model/business.h"

struct business{
	char * business_id;
	char * name;
	char * city;
	char * state;
	GSList * categories;
};


char * getBusinessId(Business b) { return g_strdup(b->business_id); }


char * getBusinessName(Business b) { return g_strdup(b->name); }


char * getBusinessCity(Business b) { return g_strdup(b->city); }


char * getBusinessState(Business b) { return g_strdup(b->state); }


GSList * getBusinessCategories(Business b) { return g_slist_copy(b->categories); }


GSList * initCategories(char * categories){
	GSList * list = NULL;
	char * token = strtok(categories, ",\n");
	while(token){
		list = g_slist_prepend(list, g_strdup(token));
		token = strtok(NULL, ",\n");
	}
	return list;
}


Business initBusiness(char * business_id, char * name, char * city, char * state, char * categories){
	Business b = (Business) malloc(sizeof(struct business));
	b->business_id = g_strdup(business_id);
	b->name = g_strdup(name);
	b->city = g_strdup(city);
	b->state = g_strdup(state);
	b->categories = initCategories(categories);
	return b;
}


Business initBusinessFromLine(char * line, char * separator){
	char * business_id = strtok(line, separator);
	char * name = strtok(NULL, separator);
	char * city = strtok(NULL, separator);
	char * state = strtok(NULL, separator);
	char * categories = strtok(NULL, separator);
	return initBusiness(business_id, name, city, state, categories);
}


void destroyBusiness(Business b){
	g_free(b->business_id);
	g_free(b->name);
	g_free(b->city);
	g_free(b->state);
	g_slist_free_full(b->categories, g_free);
	free(b);
}
#include "../../includes/model/catalogoReviews.h"

#define BUFF_SIZE 50000


ReviewC initReviewC(){
	ReviewC catalogo = g_hash_table_new_full( g_str_hash, g_str_equal, g_free, (GDestroyNotify) destroyReview);
	return catalogo;
}


ReviewC loadReviewCFromFile(ReviewC catalogoR, UserC catalogoU, BusinessC catalogoB, char* filename){
	char buffer[BUFF_SIZE];
	FILE* f = fopen(filename, "r");
	
	if (!f) { return NULL; }

	while(fgets(buffer, BUFF_SIZE, f)){
		buffer[strcspn(buffer,"\n")] = 0;
		Review review = initReviewFromLine(buffer ,";");
		//if(validReview(catalogoU, catalogoB, review))
		g_hash_table_insert(catalogoR, getReviewId(review), review);
		//else destroyReview(review);
	}
	
	fclose(f);
	return catalogoR;
}


int reviewExists(ReviewC catalogo, char * id ){
	int existe = 0;
	if (g_hash_table_contains(catalogo, id))
		existe = 1;
	return existe;
}


void auxGetReviews(gconstpointer key, gconstpointer value, gconstpointer user_data){
	g_hash_table_insert((GHashTable *) user_data, (char *) key, (Review) value);
}


GHashTable * getReviews(ReviewC catalogo){
	GHashTable * clone = g_hash_table_new_full( g_str_hash, g_str_equal, g_free, (GDestroyNotify) destroyReview);
	g_hash_table_foreach(catalogo, (GHFunc) auxGetReviews, clone);
	return clone;
}


Review getReview(ReviewC catalogo, char *id){ return g_hash_table_lookup(catalogo, id); }


void destroyReviewC(ReviewC catalogo){ g_hash_table_destroy(catalogo); }


int validReview(UserC catalogoU, BusinessC catalogoB, Review review){
	int result = 1;
	char * user_id = getUserIdFromReview(review);
	char * business_id = getBusinessIdFromReview(review);
	if (!g_hash_table_lookup(catalogoU, user_id)) result = 0;
	if (!g_hash_table_lookup(catalogoB, business_id)) result = 0;
	free(user_id);
	free(business_id);
	return result;
}
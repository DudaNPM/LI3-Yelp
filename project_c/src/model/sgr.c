#include "../../includes/model/sgr.h"

struct sgr{
	UserC catalogoUsers;
	BusinessC catalogoBusinesses;
	ReviewC catalogoReviews;
};


UserC getUserC(SGR sgr) { return sgr->catalogoUsers; }
BusinessC getBusinessC(SGR sgr) { return sgr->catalogoBusinesses; }
ReviewC getReviewC(SGR sgr) { return sgr->catalogoReviews; }


SGR init_sgr(){
	SGR sgr = malloc(sizeof(struct sgr));
	sgr->catalogoUsers = initUserC();
	sgr->catalogoBusinesses = initBusinessC();
	sgr->catalogoReviews = initReviewC();
	return sgr;
}


void free_sgr(SGR sgr){
	destroyUserC(sgr->catalogoUsers);
	destroyBusinessC(sgr->catalogoBusinesses);
	destroyReviewC(sgr->catalogoReviews);
	free(sgr);
}


/* QUERY 1 */
SGR load_sgr(char *users, char *businesses, char *reviews){
	SGR sgr = init_sgr();
	printf("A construir hash table com os users...\n");
	sgr->catalogoUsers = loadUserCFromFile(sgr->catalogoUsers, users);
	printf("hash table contruida.\n");
	printf("A construir hash table com os business...\n");
	sgr->catalogoBusinesses = loadBusinessCFromFile(sgr->catalogoBusinesses, businesses);
	printf("hash table contruida.\n");
	printf("A construir hash table com as reviews...\n");
	sgr->catalogoReviews = loadReviewCFromFile(sgr->catalogoReviews, sgr->catalogoUsers, sgr->catalogoReviews, reviews);
	printf("hash table contruida.\n");
	return sgr;
}


/* QUERY 2 */
TABLE businesses_started_by_letter(SGR sgr, char letter){
	GSList * ids = businessStartedByLetterIds(sgr->catalogoBusinesses, letter);
	GSList * names = convertIdsToNames(sgr->catalogoBusinesses, ids);

	TABLE t = initTable(2);
	addColuna(t, "business_id", ids);
	addColuna(t, "name", names);

	g_slist_free_full (ids, g_free);
	g_slist_free_full (names, g_free);
	return t;
}


/* QUERY 3 */
TABLE business_info(SGR sgr, char *business_id){
	char * name = getBusinessName(getBusiness(sgr->catalogoBusinesses, business_id));
	char * city = getBusinessCity(getBusiness(sgr->catalogoBusinesses, business_id));
	char * state = getBusinessState(getBusiness(sgr->catalogoBusinesses, business_id));
	float stars = countStars(sgr->catalogoReviews, business_id);
	int reviews = countReviews(sgr->catalogoReviews, business_id);

	char * str = malloc(sizeof(char) * 5);
	char * rvs = malloc(sizeof(char) * 5);

	sprintf(str, "%0.f", stars);
	sprintf(rvs, "%d", reviews);

	GSList * l1 = NULL;
	GSList * l2 = NULL;
	GSList * l3 = NULL;
	GSList * l4 = NULL;
	GSList * l5 = NULL;

	l1 = g_slist_append(l1, name);
	l2 = g_slist_append(l2, city);
	l3 = g_slist_append(l3, state);
	l4 = g_slist_append(l4, str);
	l5 = g_slist_append(l5, rvs);
	
	TABLE t = initTable(5);
	addColuna(t, "name", l1);
	addColuna(t, "city", l2);
	addColuna(t, "state", l3);
	addColuna(t, "stars", l4);
	addColuna(t, "reviews", l5);

	g_slist_free_full (l1, g_free);
	g_slist_free_full (l2, g_free);
	g_slist_free_full (l3, g_free);
	g_slist_free_full (l4, g_free);
	g_slist_free_full (l5, g_free);
	return t;
}


/* QUERY 4 */
TABLE businesses_reviewed(SGR sgr, char *user_id){
	GSList * ids = businessReviewedIdList(sgr->catalogoReviews, user_id);
	GSList * names = convertIdsToNames(sgr->catalogoBusinesses, ids);

	TABLE t = initTable(2);
	addColuna(t, "business_id", ids);
	addColuna(t, "name", names);

	g_slist_free_full (ids, g_free);
	g_slist_free_full (names, g_free);
	return t;
}


/* QUERY 5 */
TABLE businesses_with_stars_and_city(SGR sgr, float stars, char *city){
	GSList * ids = businessIdWithStarsList(sgr->catalogoReviews, stars);
	GSList *  idsFiltrados = businessIdWithStarsListFilter(sgr->catalogoBusinesses, ids, city);
	GSList * names = convertIdsToNames(sgr->catalogoBusinesses, idsFiltrados);

	TABLE t = initTable(2);
	addColuna(t, "business_id", idsFiltrados);
	addColuna(t, "name", names);

	g_slist_free_full (ids, g_free);
	g_slist_free_full (idsFiltrados, g_free);
	g_slist_free_full (names, g_free);
	return t;
}


/* QUERY 6 */
void top_businesses_by_city(SGR sgr, int top){
	GHashTable * htOfht = citysHT(sgr->catalogoReviews, sgr->catalogoBusinesses);
	GSList * allCitys = getTopNFromCitys(htOfht, top);

	// Auxiliar ja que nao vamos conseguir meter isto numa TABLE...

	//while(allCitys){
	//	char * city = getBusinessCity(getBusiness(sgr->catalogoBusinesses, getBusinessIdBD( (BD) (((GSList*) (allCitys->data))->data) )));
	//	printf("\n\n\n|----------------------------------------------|\n");
	//	printf("|             %s                    |\n", city);
	//	printf("|----------------------------------------------|\n");
	//	int i = 1;
	//	while(allCitys->data){
	//		char * business_id = getBusinessIdBD((BD) (((GSList*) (allCitys->data))->data));
	//		char * business_name = getBusinessNameBD((BD) (((GSList*) (allCitys->data))->data));
	//		int reviews = getReviewsBD((BD) (((GSList*) (allCitys->data))->data));
	//		float media = getMediaBD((BD) (((GSList*) (allCitys->data))->data));
	//		printf("%d -> ID: %s Name: %s Reviews: %d Media: %f\n", i, business_id, business_name, reviews, media);
	//		allCitys->data = ((GSList*) (allCitys->data))->next;
	//		i++;
	//	}
	//	allCitys = allCitys->next;
	//}

	//g_hash_table_destroy(htOfht);
	//while(allCitys){
	//	g_slist_free_full( (GSList *) (allCitys->data), destroyBD);
	//	allCitys = allCitys->next;
	//}
	//g_slist_free(allCitys);
}


/* QUERY 7 */
TABLE international_users(SGR sgr){
	GSList * list = NULL;

	GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init (&iter, getReviews(getReviewC(sgr)) );

    while (g_hash_table_iter_next (&iter, &key, &value)){
        if(validaUserInternacional(statesOfUserReviews(sgr->catalogoReviews, sgr->catalogoBusinesses, getUserIdFromReview((Review) value) )) == 1)
            list = g_slist_prepend(list, getBusinessIdFromReview((Review) value));
        	printf("user adicionado\n");
    }


    TABLE t = initTable(2);
	
	addColuna(t, "user_id", list);
	g_slist_free_full (list, (GDestroyNotify) free);

    return t;
}


/* QUERY 8 */
TABLE top_businesses_with_category(SGR sgr, int top, char *category){
    GHashTable * ht = categoriesHT(sgr->catalogoReviews, sgr->catalogoBusinesses, category);
    GSList * list = getTopNCategories(ht, top);
    GSList * namesList = NULL;
    GSList * idsList = NULL;
    GSList * mediasList = NULL;

    char str[5];

    while(list){
        namesList = g_slist_prepend(namesList, getBusinessNameBD(list->data));
        idsList = g_slist_prepend(idsList, getBusinessIdBD(list->data));
        sprintf(str, "%0.f", getMediaBD(list->data));
        mediasList = g_slist_prepend(mediasList, str);
        list = list->next;
    }

    TABLE t = initTable(3);

    addColuna(t, "business_id", idsList);
    addColuna(t, "name", namesList);
    addColuna(t, "stars", mediasList);
    g_slist_free_full (idsList, g_free);
	g_slist_free_full (namesList, g_free);
	//g_slist_free_full (mediasList, g_free);
    g_slist_free(list);
    g_hash_table_destroy(ht);
    return t;
}


/* QUERY 9 */
TABLE reviews_with_word(SGR sgr, char *word){
	GSList * list = NULL;

    list = reviewsWithWordIdList(sgr->catalogoReviews, word);

    TABLE t = initTable(1);
	
	addColuna(t, "business_id",(list));
	g_slist_free_full (list, g_free);

    return t;
}
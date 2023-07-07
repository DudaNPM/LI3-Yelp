#include "../../includes/model/stats.h"


int checkLetter(char * business_name, char letter){
    char firstLetter = business_name[0];
    if (firstLetter == letter ||
        firstLetter == letter + 32 ||
        firstLetter == letter - 32)
        return 1;
    else return 0;
}


GSList * businessStartedByLetterIds(BusinessC catalogo, char letter){
    GSList * list = NULL;

    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init (&iter, catalogo);

    while (g_hash_table_iter_next (&iter, &key, &value)){
        char * business_name = getBusinessName((Business) value);
        if(checkLetter(business_name, letter))
            list = g_slist_prepend(list, getBusinessId((Business) value));
        g_free(business_name);
    }

    return list;
}


float countStars(ReviewC catalogo, char * business_id){
    float stars = 0;

    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init (&iter, catalogo);

    while (g_hash_table_iter_next (&iter, &key, &value)){
        char * id = getBusinessIdFromReview((Review) value);
        if(strcmp(id, business_id) == 0)
            stars += getStars((Review) value);
        g_free(id);
    }

    return stars;
}


int countReviews(ReviewC catalogo, char * business_id){
    int reviews = 0;

    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init (&iter, catalogo);

    while (g_hash_table_iter_next (&iter, &key, &value)){
        char * id = getBusinessIdFromReview((Review) value);
        if(strcmp(id, business_id) == 0)
            reviews++;
        g_free(id);
    }
    
    return reviews;
}


GSList * businessReviewedIdList(ReviewC catalogo, char * user_id){
    GSList * list = NULL;

    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init (&iter, catalogo);

    while (g_hash_table_iter_next (&iter, &key, &value)){
        char * tempUserID = getUserIdFromReview((Review) value);
        if(strcmp(user_id, tempUserID) == 0)
            list = g_slist_prepend(list, getBusinessIdFromReview((Review) value));
        g_free(tempUserID);
    }

    return list;
}


GSList * convertIdsToNames(BusinessC catalogo, GSList * ids){
    GSList * list = NULL;
    while(ids){
        Business b = g_hash_table_lookup(catalogo, ids->data);
        if (b) list = g_slist_prepend(list, getBusinessName(b));
        ids = ids->next;
    }
    return g_slist_reverse(list);
}


GSList * businessIdWithStarsList(ReviewC catalogo, float stars){
    GSList * list = NULL;

    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init (&iter, catalogo);

    while (g_hash_table_iter_next (&iter, &key, &value)){
        if(getStars((Review) value) >= stars)
            list = g_slist_prepend(list, getBusinessIdFromReview((Review) value));
    }

    return g_slist_reverse(list);
}


int compareCitys(BusinessC catalogo, char * business_id, char * city){
    int result = 0;
    Business b = getBusiness(catalogo, business_id);
    char * c = getBusinessCity(b);
    if (strcmp(c, city) == 0) result = 1;
    g_free(c);
    return result;
}


GSList * businessIdWithStarsListFilter(BusinessC catalogo, GSList * ids, char * city){
    GSList * list = NULL;

    while(ids){
        if (compareCitys(catalogo, (char *) ids->data, city))
            list = g_slist_prepend(list, g_strdup((char *) ids->data));
        ids = ids->next;
    }

    return g_slist_reverse(list);
}


/* QUERY 6*/

/* Secalhar é melhor criar um modulo auxStructs para meter isto */
/****************************************************************/


struct businessData{
    char * business_id;
    char * business_name;
    float media;
    int reviews;
};


BD initBD(char * business_id, char * business_name, float stars){
    BD bd = (BD) malloc(sizeof(struct businessData));
    bd->business_id = g_strdup(business_id);
    bd->business_name = g_strdup(business_name);
    bd->media = stars;
    bd->reviews = 1;
    return bd;
}

char * getBusinessIdBD(BD businessData){ return g_strdup(businessData->business_id); }
char * getBusinessNameBD(BD businessData){ return g_strdup(businessData->business_name); }
float getMediaBD(BD businessData){ return businessData->media; }
int getReviewsBD(BD businessData){ return businessData->reviews; }


void atualizaBD(GHashTable * ht, BD bd, float estrelas){
    bd->media = ((bd->media * bd->reviews) + estrelas ) / (bd->reviews + 1);
    bd->reviews++;
}


void destroyBD(BD businessData){
    g_free(businessData->business_id);
    g_free(businessData->business_name);
    free(businessData);
}


/****************************************************************/


///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// O objetivo desta função é percorrer o catalogo de reviews e criar uma hash table (ht1), em que a key será //
// o nome de uma cidade e o valor será outra hash table (ht2). Esta ht2 terá como key o business id e como   //
// valor um business data (BD). Assim, para cada cidade teremos uma hash table associada, contendo esta,     //
// uma hash table para cada negocio dessa mesma cidade com info acerca da sua media de estrelas em funcao    //
// das reviews. O objetivo aqui pretendido é percorrer o catalogo apenas uma vez.                            //
///////////////////////////////////////////////////////////////////////////////////////////////////////////////


GHashTable * citysHT(ReviewC catalogoR, BusinessC catalogoB){
    GHashTable * htOfht = g_hash_table_new_full(g_str_hash, g_str_equal, g_free, g_hash_table_destroy);
    
    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init (&iter, catalogoR);

    while (g_hash_table_iter_next (&iter, &key, &value)){

        char * business_id = getBusinessIdFromReview((Review) value);
        char * business_name = getBusinessName(g_hash_table_lookup(catalogoB, business_id));
        char * business_city = getBusinessCity(g_hash_table_lookup(catalogoB, business_id));
        GHashTable * cityHT = g_hash_table_lookup(htOfht, business_city);

        // caso em que uma cidade ja tem uma HASH TABLE associada
        if (cityHT){
            
            BD bd = g_hash_table_lookup(cityHT, business_id);
            float stars = getStars((Review) value);

            // caso em que o business do qual foi feita a review ainda nao tem HASH TABLE associada
            if (!bd){
                g_hash_table_insert(cityHT, business_id, initBD(business_id, business_name, stars));
            }
            // caso em que o business do qual foi feita a review ja tem HASH TABLE associada
            else atualizaBD(cityHT, bd, stars);
        }

        // caso em que uma cidade ainda nao tem uma HASH TABLE associada
        else{
            float stars = getStars((Review) value);
            BD bd = initBD(business_id, business_name, stars);
            GHashTable * newCityHt = g_hash_table_new_full(g_str_hash, g_str_equal, g_free, destroyBD);
            g_hash_table_insert(newCityHt, business_id, bd);
            g_hash_table_insert(htOfht, business_city, newCityHt);
        }
    }

    return htOfht;
}


int auxSort(gconstpointer a, gconstpointer b){
    if ( ((BD) a)->media == ((BD) b)->media) return 0;
    if ( ((BD) a)->media > ((BD) b)->media) return -1;
    if ( ((BD) a)->media < ((BD) b)->media) return 1;
}


////////////////////////////////////////////////////////////////////////////////////////////////////////
// Para esta funçao pretende se retirar a informacao dos top n negocios de todas as cidades que estao //
// armazenadas na hash table criada anteriormente. Para isso percorre se a mesma e cria se uma lista  //
// de business data (BD) para todas as cidades. Ordena se as listas e retira se o top n. Devolve se   //
// uma lista de listas de BD's                                                                        //
////////////////////////////////////////////////////////////////////////////////////////////////////////


GSList * getTopNFromCitys(GHashTable * htOfht, int n){

    GSList * allCitys = NULL;

    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init (&iter, htOfht);

    while (g_hash_table_iter_next (&iter, &key, &value)){
        // para todas as cidades criamos uma lista com os business data  (BD)
        GSList * citylist = g_hash_table_get_values ((GHashTable *) value);
        // ordena se a lista de forma descrescente da media
        citylist = g_slist_sort (citylist, (GCompareFunc) auxSort);
        
        int aux = n;
        GSList * nList = NULL;
        // retira se os top n da lista
        while(citylist && aux > 0){
            nList = g_slist_prepend(nList, citylist->data);
            citylist = citylist->next;
            aux--;
        }

        // insere se a lista de cada cidade com os top n business (de acordo com a media de estrelas) numa lista de listas
        allCitys = g_slist_prepend(allCitys, g_slist_reverse(nList));
    }
    return allCitys;
}


/* QUERY 7 */

GSList * convertIdsToStates(BusinessC catalogo, GSList * ids){
    GSList * list = NULL;
    GHashTable * catalogoB = getBusinesses(catalogo);

    while(ids){
        Business b = g_hash_table_lookup(catalogoB, ids->data);
        if (b) list = g_slist_prepend(list, getBusinessState(b));
        ids = ids->next;
    }
    return list;
}


GSList * statesOfUserReviews(ReviewC catalogoR, BusinessC catalogoB, char * user_id){
    GSList * ids = NULL;
    GSList * states = NULL;

    ids = businessReviewedIdList(catalogoR, user_id); // ids de todos os businessess cujo user fez review
    free(user_id);
    states = convertIdsToStates(catalogoB,ids); // states de todos os ids de businesses
    g_slist_free_full (ids, g_free);
    return states;
}


int validaUserInternacional(GSList * states){
    int valido = 0;

    if(!states && !(states->next)) return valido;

    char* state = states->data;
    states = states->next;

    
    while(states){
        if(strcmp(states->data, state) == 0){
            states = states->next;
        }
        else{
            valido = 1;
            break;
            printf("valido\n");
        }
    }
    g_slist_free_full (states, g_free);
    return valido;
}


/* QUERY 8 */

GHashTable * categoriesHT(ReviewC catalogoR, BusinessC catalogoB, char * categoria){
    GHashTable * ht = g_hash_table_new_full(g_str_hash, g_str_equal, g_free, destroyBD);
    
    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init (&iter, catalogoR);

    while (g_hash_table_iter_next (&iter, &key, &value)){

        char * business_id = getBusinessIdFromReview((Review) value);
        GSList * categories = getBusinessCategories(g_hash_table_lookup(catalogoB, business_id));
        
        if (g_slist_find_custom (categories, categoria, (GCompareFunc) strcmp)){
            
            char * business_name = getBusinessName(g_hash_table_lookup(catalogoB, business_id));
            float stars = getStars((Review) value);
            BD shearch = g_hash_table_lookup(ht, business_id);
            
            if (!shearch)
                g_hash_table_insert(ht, business_id, initBD(business_id, business_name, stars));
            else
                atualizaBD(ht, shearch, stars);

            //g_free(business_name);
        }
        //g_free(business_id);
        //g_slist_free_full(categories, g_free);
    }

    return ht;
}


GSList * getTopNCategories(GHashTable * ht, int n){
    GSList * allValues = g_hash_table_get_values (ht);
    allValues = g_slist_sort (allValues, (GCompareFunc) auxSort);
    GSList * top = NULL;

    while(allValues && n > 0){
        top = g_slist_prepend(top, allValues->data);
        allValues = allValues->next;
        n--;
    }

    //g_slist_free(allValues);

    return top;
}


/* QUERY 9 */

GSList* reviewsWithWordIdList(ReviewC catalogo, char *word){
    char * c;
    GSList * list = NULL;

    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init (&iter, catalogo);

    while (g_hash_table_iter_next (&iter, &key, &value)){
        GString * text = getText((Review) value);
        c = strstr(text->str, word);

        if(c){
            if (c[0] == (text->str)[0] && (c + strlen(word))[0] == ' '   || // caso esteja ente o começo da linha e 1 espaço
                c[0] == (text->str)[0] && ispunct((c + strlen(word))[0]) || // caso esteja ente o começo da linha e 1 sinal de pontuaçao
                ispunct((c - 1)[0]) &&  ispunct((c + strlen(word))[0])   || // caso esteja ente 2 sinais de pontuaçao
                ispunct((c - 1)[0]) &&  (c + strlen(word))[0] == ' '     || // caso esteja ente 1 sinal de pontuaçao e um espaço
                (c - 1)[0] == ' '   &&  ispunct((c + strlen(word))[0])   || // caso esteja ente um espaço e 1 sinal de pontuaçao
                (c - 1)[0] == ' '   &&  (c + strlen(word))[0] == ' '     ){ // caso esteja ente o começo da linha e 1 espaço
                list = g_slist_prepend(list, getReviewId((Review) value));
            }
        }
        g_string_free(text, TRUE);
    }
    return g_slist_reverse(list);
}
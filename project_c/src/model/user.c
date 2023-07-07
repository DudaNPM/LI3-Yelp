#include "../../includes/model/user.h"

struct user{
	char * user_id;
	char * name;
	GSList * friends;
};


char * getUserId(User u) { return g_strdup(u->user_id); }


char * getUserName(User u) { return g_strdup(u->name); }


//GSList * getUserFriends(User u){ return g_slist_copy(u->friends); }


//GSList * initFriends(char * friends){
//	GSList * list = NULL;
//	char * token = strtok(friends, ",\n");
//	while(token){
//		list = g_slist_prepend(list, g_strdup(token));
//		token = strtok(NULL, ",\n");
//	}
//	return list;
//}


User initUser(char * user_id, char * name, char * friends){
	User u = (User) malloc(sizeof(struct user));
	u->user_id = g_strdup(user_id);
	u->name = g_strdup(name);
	u->friends = friends;
	//u->friends = initFriends(g_strdup(friends));
	return u;
}


User initUserFromLine(char * line, char * separator){
	char * user_id = strtok(line, separator);
	char * name = strtok(NULL, separator);
	//char * friends = strtok(NULL, separator);
	return initUser(user_id, name, NULL);
}


void destroyUser(User u){
	g_free(u->user_id);
	g_free(u->name);
	g_slist_free_full(u->friends, g_free);
	free(u);
}
#include "../../includes/model/review.h"

struct review{
	char * review_id;
	char * user_id;
	char * business_id;
	float stars;
	int useful;
	int funny;
	int cool;
	char * date;
	GString * text;
};


char * getReviewId(Review r) { return g_strdup(r->review_id); }


char * getUserIdFromReview(Review r) { return g_strdup(r->user_id); }


char * getBusinessIdFromReview(Review r) { return g_strdup(r->business_id); }


float getStars(Review r) { return r->stars; }


int getUseful(Review r) { return r->useful; }


int getFunny(Review r) { return r->funny; }


int getCool(Review r) { return r->cool; }


char * getDate(Review r) { return g_strdup(r->date); }


GString * getText(Review r) { return g_string_new(r->text->str); }


Review initReview(char * review_id, char * user_id, char * business_id,
				  float stars, int useful, int funny, int cool,
				  char * date, char * text){
	Review r = (Review) malloc(sizeof(struct review));
	r->review_id = g_strdup(review_id);
	r->user_id = g_strdup(user_id);
	r->business_id = g_strdup(business_id);
	r->stars = stars;
	r->useful = useful;
	r->funny = funny;
	r->cool = cool;
	r->date = g_strdup(date);
	r->text = g_string_new(text);
	return r;
}


Review initReviewFromLine(char * line, char * separator){
	char * review_id = strtok(line, separator);
	char * user_id = strtok(NULL, separator);
	char * business_id = strtok(NULL, separator);
	float stars = atof(strtok(NULL, separator));
	int useful = atoi(strtok(NULL, separator));
	int funny = atoi(strtok(NULL, separator));
	int cool = atoi(strtok(NULL, separator));
	char * date = strtok(NULL, separator);
	char * text = strtok(NULL, separator);
	return initReview(review_id, user_id, business_id, stars,
					  useful, funny, cool, date, text);
}


void destroyReview(Review r){
	g_free(r->review_id);
	g_free(r->user_id);
	g_free(r->business_id);
	g_free(r->date);
	g_string_free(r->text, TRUE);
	free(r);
}
#include "../../includes/model/table.h"

struct coluna{
	char * topo;
	char ** campos;
	int numLinhas;
};

struct table{
	COLUNA * colunas;
	int colunasOcupadas;
};


int getColunasOcupadas(TABLE t){ return t->colunasOcupadas; }


char * getTopName(TABLE t, int numColuna){ return strdup(t->colunas[numColuna]->topo); }


char * getCampName(TABLE t, int numColuna, int numLinha){ return strdup(t->colunas[numColuna]->campos[numLinha]); }


int getNumLinhas(TABLE t){ return t->colunas[0]->numLinhas; }


char ** initCampos(GSList * campos, int size){
	char ** linha = malloc(sizeof(char *) * size);
	int i;
	for(i = 0; i < size; i++){
		linha[i] = strdup(campos->data);
		campos = campos->next;
	}
	return linha;
}


COLUNA initColuna(char * topo, GSList * campos){
	COLUNA c = (COLUNA) malloc(sizeof(struct coluna));
	c->numLinhas = g_slist_length(campos);
	c->topo = strdup(topo);
	c->campos = initCampos(campos, c->numLinhas);
	return c;
}


void addColuna(TABLE t, char * topo, GSList * campos){
	COLUNA c = initColuna(topo, campos);
	t->colunas[t->colunasOcupadas] = c;
	t->colunasOcupadas++;
}


TABLE initTable(int numColunas){
	TABLE t = (TABLE) malloc(sizeof(struct table));
	t->colunas = (COLUNA) malloc(sizeof(struct coluna) * numColunas);
	t->colunasOcupadas = 0;
	return t;
}


void destroyColuna(COLUNA c){
	free(c->topo);
	int i;
	for(i = 0; i < c->numLinhas; i++)
		free(c->campos[i]);
	free(c->campos);
	free(c);
}


void destroyTable(TABLE t){
	int i;
	for(i = 0; i < t->colunasOcupadas; i++)
		destroyColuna(t->colunas[i]);
	free(t->colunas);
	free(t);
}


TABLE indexTable(TABLE t, int linha, int coluna){
	TABLE new = initTable(1);
	GSList * campo = NULL;
	campo = g_slist_append(campo, t->colunas[coluna]->campos[linha]);
	addColuna(new, t->colunas[coluna]->topo, campo);
	g_slist_free(campo);
	return new;
}
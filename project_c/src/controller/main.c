#include "../../includes/controller/controller.h"



/* Main function */

int main(int argc, char const *argv[]) {
    runProgram();
    return 0;
}




////////// ***** PONTO DE SITUAÇÃO ***** //////////

// TESTES

// QUERY1 (no memory leaks)
// sem valgrind: 9 seg
// com valgrind: 8 min 45 seg

// QUERY2 valgrind:(no memory leaks)
// input(a): 0.079851 seg
// input(y): 0.060596 seg
// input(w): 0.101150 seg

// QUERY3 valgrind:(no memory leaks)
// input(6iYb2HFDywm3zjuRg0shjw): 0.000030 seg
// input(NlecnbisT1Qz5-Fl9q31ng): 0.000028 seg
// input(ngmLL5Y5OT-bYHKU0kKrYA): 0.000027 seg

// QUERY4 valgrind:(no memory leaks)
// input(RtGqdDBvvBCjcu5dUqwfzA): 0.495026 seg
// input(ZZw6jT9Sq81f0eVvIBu_pA): 0.489019 seg
// input(zZ-LTkL_SQgFjwF2MDW3kg): 0.527532 seg

// QUERY5 valgrind:(no memory leaks)
// input(5,Portland): 0.684469 seg
// input(4,Austin): 1.032332 seg
// input(2,Orlando): 1.181436 seg

// QUERY6 valgrind:(has memory leaks)
// input(10): 2.011809 seg
// input(100): 2.019858 seg
// input(1000): 2.085279 seg

// QUERY8 valgrind:(has memory leaks)
// input(10,Dentists): 1.822568 seg
// input(100,Dentists): 1.821518 seg
// input(1000,Dentists): 1.847948 seg

// QUERY9 valgrind:(no memory leaks)
// input(inGrace): 1.680684 seg
// input(and): 1.918687 seg
// input(the): 1.800394 seg

// Estao a ser contadas 1 000 000 reviews
// Existem reviews que estao escritas no campo text de outras reviews e nos estamos a le las (nao deve ser suposto)
// exemplo linha 25 167 do excel


// Por fazer:

// QUERIES
//  .query 7 (incompleta)

// INTERPRETADOR
//  .escrita para csv
//  .leitura de csv
//  .filtragem de resultados
//  .projeção de colunas

//  PAGINAÇÃO

//  TESTES DE PERFORMANCE

//	MAKEFILE: podia tar melhor mas percebo pouco. Acho que só é suposto compilar ficheiros quando eles são alterados,
//            o que não está a acontecer.
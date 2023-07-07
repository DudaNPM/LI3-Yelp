FLAGS = -std=c99 -Wextra -w -O2 -g -Wall -D_GNU_SOURCE
PKG = `pkg-config --cflags --libs glib-2.0`
COMPILER = gcc
INCLUDES = includes
SRC = src
OBJ = obj
MDL = model
VW = view
CNTRL = controller

program: controller table interface sgr stats review catalogoReviews business catalogoBusinesses user catalogoUsers main allprog

controller: $(SRC)/$(CNTRL)/controller.c $(INCLUDES)/$(CNTRL)/controller.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/controller.o $(PKG) -c $(SRC)/$(CNTRL)/controller.c

table: $(SRC)/$(MDL)/table.c $(INCLUDES)/$(MDL)/table.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/table.o $(PKG) -c $(SRC)/$(MDL)/table.c

interface : $(SRC)/$(VW)/interface.c $(INCLUDES)/$(VW)/interface.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/interface.o $(PKG) -c $(SRC)/$(VW)/interface.c

sgr: $(SRC)/$(MDL)/sgr.c $(INCLUDES)/$(MDL)/sgr.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/sgr.o $(PKG) -c $(SRC)/$(MDL)/sgr.c

stats: $(SRC)/$(MDL)/stats.c $(INCLUDES)/$(MDL)/stats.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/stats.o $(PKG) -c $(SRC)/$(MDL)/stats.c

review: $(SRC)/$(MDL)/review.c $(INCLUDES)/$(MDL)/review.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/review.o $(PKG) -c $(SRC)/$(MDL)/review.c

catalogoReviews: $(SRC)/$(MDL)/catalogoReviews.c $(INCLUDES)/$(MDL)/catalogoReviews.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/catalogoReviews.o $(PKG) -c $(SRC)/$(MDL)/catalogoReviews.c

business: $(SRC)/$(MDL)/business.c $(INCLUDES)/$(MDL)/business.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/business.o $(PKG) -c $(SRC)/$(MDL)/business.c

catalogoBusinesses: $(SRC)/$(MDL)/catalogoBusinesses.c $(INCLUDES)/$(MDL)/catalogoBusinesses.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/catalogoBusinesses.o $(PKG) -c $(SRC)/$(MDL)/catalogoBusinesses.c

user: $(SRC)/$(MDL)/user.c $(INCLUDES)/$(MDL)/user.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/user.o $(PKG) -c $(SRC)/$(MDL)/user.c

catalogoUsers: $(SRC)/$(MDL)/catalogoUsers.c $(INCLUDES)/$(MDL)/catalogoUsers.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/catalogoUsers.o $(PKG) -c $(SRC)/$(MDL)/catalogoUsers.c

main: $(SRC)/$(CNTRL)/main.c $(OBJ)/sgr.o
	$(COMPILER) $(FLAGS) -o $(OBJ)/main.o $(PKG) -c $(SRC)/$(CNTRL)/main.c	

allprog: $(SRC)/$(CNTRL)/main.c $(OBJ)/main.o $(OBJ)/sgr.o $(OBJ)/controller.o
	$(COMPILER) $(FLAGS) $(OBJ)/user.o $(OBJ)/catalogoUsers.o $(OBJ)/business.o $(OBJ)/catalogoBusinesses.o $(OBJ)/review.o $(OBJ)/catalogoReviews.o $(OBJ)/interface.o $(OBJ)/stats.o $(OBJ)/sgr.o $(OBJ)/table.o $(OBJ)/controller.o $(OBJ)/main.o  $(PKG) -o program


memcheck:
	valgrind --leak-check=yes ./program

clean:
	rm -rf $(OBJ)/*.o program
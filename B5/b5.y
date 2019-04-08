%{
#include <stdio.h>
#include <stdlib.h>

extern int yylex();
void yyerror(char *msg);
%}
%union {
         char *e;
       }
%token <e> COM DE
%type <e> C D

%%
S : C D                      {printf("compound sentence\n"); exit(1);}
  ;
C : COM						{$$ = $1;}
  ;
D : DE
  ;
%%


void yyerror(char *msg)
{

  fprintf(stderr, "%s\n", msg);
  //printf("simple sentence\n");
  exit(1);
}

int main()
{
	printf("Write a sentence: ");
 	yyparse();

	return 0;
}


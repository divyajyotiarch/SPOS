%{
#include <stdio.h>
#include <stdlib.h>

extern int yylex();
void yyerror(char *msg);
%}
%union {
         char *e;
       }
%token <e> TYPE ID DE
%type <e> T I D

%%
S : T I D                      {printf("valid\n");}
  ;
T : TYPE					{$$ = $1;}
  ;
I : ID						{$$ = $1;}
  ;
D : DE
  ;
%%

void yyerror(char *msg)
{

  fprintf(stderr, "%s\n", msg);
  exit(1);
}

int main()
{printf("declare variable in java ");
  yyparse();

  return 0;
}


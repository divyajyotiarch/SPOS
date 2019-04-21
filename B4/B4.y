%{
#include<stdio.h>
void yyerror(char *s);
extern int yylex();
%}

%token datatype varname operator num sc eq 

%%
start : datatype varname eq varnum operator varnum sc {printf("Valid syntax");}
      | varname eq varnum sc {printf("Valid syntax");}
      | datatype varname eq varnum sc {printf("Valid syntax");}
      | datatype varname sc {printf("Valid syntax");}
      ;
varnum : varname | num
       ;
%%
void yyerror(char *s)
{
	printf("%s \n", s);
}

int main()
{
	yyparse();
	return 0;
}

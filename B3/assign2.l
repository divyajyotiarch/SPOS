%{
#include<stdio.h>
#include<string.h>
int words=0;
int chars=0;
int lines=0;
%}

character [A-Za-z]
digit [0-9]
word ({character}|{digit})+

%%

{word}	{words++; chars+=strlen(yytext);}
\n	{lines++;}

%%

main()
{
	
	yyin = fopen("file.txt","r");
	
	yylex();
	 {printf("words: %d\n",words); printf("chars: %d\n",chars); printf("lines: %d\n",lines); words=0; chars=0; lines=0;}
	
}
int yywrap(){return 1;};



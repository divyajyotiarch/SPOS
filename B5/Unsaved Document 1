%{
	#include<stdio.h>
	void yyerror(char *s);	
%}
%token	VERB ADVERB NOUN ADJECTIVE SPACE CONJUCTION	PRONOUN	ARTICLE PREPOSITION
%%
start: simple	{printf("SIMPLE");}|
	compound	{printf("COMPOUND");}
;
simple: SUBJECT SPACE VERB SPACE OBJECT|SUBJECT SPACE VERB SPACE ADVERB|SUBJECT SPACE ADVERB SPACE VERB|SUBJECT SPACE VERB SPACE ARTICLE SPACE NOUN|SUBJECT SPACE VERB SPACE ADJECTIVE
;
compound: simple SPACE CONJUCTION SPACE simple
;
SUBJECT : NOUN|PRONOUN;
;
OBJECT : NOUN|PRONOUN;
%%
void main()
{
	yyparse();
}
void yyerror(char *s)
{
	printf("%s\n",s);
}

/*
lex example.l
yacc example.y
gcc -o example y.tab.c
./example
*/
%{
	#include<stdio.h>
	#include "y.tab.h"
%}
%%
"and"|"or"|"but"	{return CONJUCTION;}
"sita"|"gita"|"teacher"|"english"|"mangoes"	{return NOUN;}
"he"|"she"|"it"|"they"|"we"	{return	PRONOUN;}
"eats"|"eat"|"walks"|"walk"|"teaches"|"teach"|"it"	{return VERB;}
"a"|"an"|"the"	{return ARTICLE;}
"slow"|"fast"	{return ADJECTIVE;}
"slowly"|"fastly"	{return ADVERB;}
"of"	{return 	PREPOSITION;}
[" "]	{return SPACE;}
\n {return 0;}
%%
int yywrap()
{
	return 1;
}

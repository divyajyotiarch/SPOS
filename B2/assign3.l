/* lex assign3.l 
   gcc lex.yy.c
   ./a.out
*/

%{
#include<stdio.h>

%}

character [A-Za-z]
digit	[]

%%
for |
while |
do |
if |
else |
continue |
return |
double |
int |
char |
long |
typedef |
sizeof |
static |
struct |
void |
String |
Integer |
break |
switch |
public |
protected |
private |
package |
class |
main |
case  {printf("%s\tis a keyword\n",yytext);}

[{};,()]   {ECHO; printf("\t is a Separator\t\n");}	

[+-/=*%]   {ECHO; printf("\t is an Operator\t\n");}	

([a-zA-Z][0-9])+|[a-zA-Z]*	{printf("%s\tis an identifier\n", yytext);}
%%

main()
{
	yyin = fopen("java.txt","r");
	yylex();
}

int yywrap(){ return 1;};

/* lex assign3.l 
   gcc lex.yy.c
   ./a.out
*/

%{
#include<stdio.h>

%}


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

[_a-zA-Z][_a-zA-Z0-9]*	{printf("%s\tis an identifier\n", yytext);}
%%

main()
{
	yyin = fopen("java.txt","r");
	yylex();
}

int yywrap(){ return 1;};

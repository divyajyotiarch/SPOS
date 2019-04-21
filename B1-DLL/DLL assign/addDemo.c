#include "mymath.h"
#include<stdio.h>

int main()
{
	int x = 10, y = 20;
	scanf("%d",&x); scanf("%d",&y);
	printf("\n %d + %d = %d", x, y, add(x,y));
	printf("\n %d - %d = %d", x, y, sub(x,y));
	return 0;
}

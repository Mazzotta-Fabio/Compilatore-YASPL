#include<stdio.h> 
#include<stdlib.h>

int x,y,n;
int main(void){
	scanf("%d",&x);

	if(x>0)	{
	n=1;
	y=1;
	while(x>1)	{
	n=n+2;
	y=y+n;
	x=x-1;
	}


	printf("RISULTATO %d\n",y);
	}


	printf("PROGRAMMA FINITO\n");
	  system("pause");
	return 0;
}


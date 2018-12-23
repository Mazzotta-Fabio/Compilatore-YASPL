#include<stdio.h> 
#include<stdlib.h>

int x,y,somma;
int main(void){
	printf("Inserisci gli addendi\n");
	scanf("%d",&x);

	scanf("%d",&y);

	somma=x+y;
	if(somma>12)	{
	somma=somma-5;
	}

	else	{
	somma=somma+5;
	}


	printf("SOMMA %d\n",somma);
	  system("pause");
	return 0;
}


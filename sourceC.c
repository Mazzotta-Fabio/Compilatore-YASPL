#include<stdio.h> 
#include<stdlib.h>

double raggio,area;
int i,n;
int main(void){
	printf("Quanti cerchi?\n");
	scanf("%d",&n);

	i=1;
	while(n>0)	{
	printf("cerchio n. %d   raggio: \n",i);
	scanf("%lf",&raggio);

	if(raggio<0.0)	{
	printf("valore del raggio non valido \n");
	}

	else	{
	area=3.14*(raggio*raggio);
	printf("area: %lf \n",area);
	}


	n=n-1;
	i=i+1;
	}


	  system("pause");
	return 0;
}


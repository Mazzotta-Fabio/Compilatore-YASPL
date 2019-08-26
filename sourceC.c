#include<stdio.h> 
#include<stdlib.h>

int a,b,x,y;
int main(void){
	scanf("%d",&x);

	scanf("%d",&y);

	a=x;
	a=y;
	while(!(a==b))	{
	if(a>b)	{
	a=a-b;
	}

	else	{
	b=b-a;
	}


	}


	printf("Il massimo comune divisore è\n",a);
	  system("pause");
	return 0;
}


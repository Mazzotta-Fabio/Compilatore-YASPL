#include<stdio.h> 
#include<stdlib.h>

double media,sum,x,n,i;
int main(void){
	printf("Quanti numeri?\n");
	scanf("%lf",&n);

	i=1.0;
	while(i<=n)	{
	printf("x= \n");
	scanf("%lf",&x);

	sum=sum+x;
	i=i+1.0;
	}


	media=sum/n;
	printf("Valore della media= %f\n",media);
	  system("pause");
	return 0;
}


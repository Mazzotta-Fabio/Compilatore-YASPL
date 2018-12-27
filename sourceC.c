#include<stdio.h> 
#include<stdlib.h>

int fine,op;
int ris1,fib,molt,pot,d,result;
int somma(int x,int y){
      int result;
	result=x+y;
    return result;
}

int moltiplicazione(int add1,int add2){
      int result;
     int i;
	i=0;
	result=0;
	while(i<add2)	{
	result=result+add1;
	i=i+1;
	}


    return result;
}

int divisione(int z,int w){
      int result;
	result=z/w;
    return result;
}

int Fibonacci(int n){
      int fib;
     int i,x,y;
	if(n==0)	{
	fib=0;
	}

	else	{
	if(n==1)	{
	fib=1;
	}

	else	{
	x=0;
	y=1;
	i=2;
	while(i<=n)	{
	fib=x+y;
	x=y;
	y=fib;
	i=i+1;
	}


	}


	}


    return fib;
}

int potenza(int base,int esp){
      int result;
     int i;
	i=0;
	result=1;
	while(i<esp)	{
	result=result*base;
	i=i+1;
	}


    return result;
}

int main(void){
	fine=0;
	while(fine==0)	{
	printf("Seleziona operazione da svolgere: somma(1),fibonacci(2),potenza(3),divisione(4),moltiplicazione(5)\n");
	scanf("%d",&op);

	if(op==1)	{
      ris1=somma(5,5);
	printf("La somma dei due numeri è %d\n",ris1);
	}


	if(op==2)	{
      fib=Fibonacci(5);
	printf("Il risultato della serie è %d\n",fib);
	}


	if(op==3)	{
      pot=potenza(2,3);
	printf("Il risultato della potenza è %d\n",pot);
	}


	if(op==4)	{
      d=divisione(4,2);
	printf("Il risultato della divisione è %d\n",d);
	}


	if(op==5)	{
      molt=moltiplicazione(5,4);
	printf("Il risultato della moltiplicazione è %d\n",molt);
	}


	if(op>5)	{
	printf("ERRORE\n");
	}


	printf("Vuoi continuare 0(continua) e 1(fine)\n");
	scanf("%d",&fine);

	}


	  system("pause");
	return 0;
}


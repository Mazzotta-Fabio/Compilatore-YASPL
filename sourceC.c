#include<stdio.h> 
#include<stdlib.h>

int fine;
int dato,op,dato1,dato2;
int result1;
double result2;
double d1,d2;
int y;
int br;
int b,n;
int menu(int op){
      int tipoOP;
	printf("Seleziona l'operazione da eseguire: 1=addizione,2=sottrazione,3=moltiplicazione,4=divisione\n");
	scanf("%d",&op);

	tipoOP=op;
	printf("Vuoi usare interi(0) o double(1)?\n");
	scanf("%d",&dato);

	if(dato==1)	{
	printf("Inserisci il primo numero double\n");
	scanf("%lf",&d1);

	printf("Inserisci il secondo numero double\n");
	scanf("%lf",&d2);

	}

	else	{
	if(dato==0)	{
	printf("Inserisci il primo numero intero\n");
	scanf("%d",&dato1);

	printf("Inserisci il secondo numero intero\n");
	scanf("%d",&dato2);

	}

	else	{
	printf("ERRORE\n");
	}


	}


    return tipoOP;
}

int calcolaInt(int symbol,int tipo){
      int resultInt;
	if(tipo==0)	{
	if(symbol==1)	{
	resultInt=dato1+dato2;
	}


	if(symbol==2)	{
	resultInt=dato1-dato2;
	}


	if(symbol==3)	{
	resultInt=dato1*dato2;
	}


	if(symbol==4)	{
	resultInt=dato1/dato2;
	}


	printf("Il risultato è %d\n",resultInt);
	}

	else	{
	printf("ERRORE\n");
	}


    return resultInt;
}

double calcola(int symbol,int tipo){
      double resultDouble;
	if(tipo==1)	{
	if(symbol==1)	{
	resultDouble=d1+d2;
	}


	if(symbol==2)	{
	resultDouble=d1-d2;
	}


	if(symbol==3)	{
	resultDouble=d1*d2;
	}


	if(symbol==4)	{
	resultDouble=d1/d2;
	}


	printf("Il risultato double è %lf\n",resultDouble);
	}

	else	{
	printf("ERRORE\n");
	}


    return resultDouble;
}

int main(void){
	fine=0;
	while(fine==0)	{
      op=menu(y);
	if(dato==0)	{
      result1=calcolaInt(op,dato);
	}

	else	{
      result2=calcola(op,dato);
	}


	printf("Vuoi continuare? (digita 0 per continuare o 1 per uscire)\n");
	scanf("%d",&fine);

	br=b&&n;
	}


	  system("pause");
	return 0;
}


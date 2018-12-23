L'esercitazione 4 segue tutte le specifiche assegnate in fase di analisi lessicale e sintattica.
L'albero sintattico generato dall'esercitazione 4 è stato creato secondo le specifiche date.
Per quanto riguarda l'analisi semantica(Esercitazione 5), alcuni nodi seguono le specifiche date,altri nodi no, come:

 -il nodo Def_decl verifica se esiste una funzione con lo stesso nome. Se ciò non è vero, si crea un nuovo scope, e si inseriscono le variabili 
   di input definite nella definizione della funzione sia nello scope globale che in quello locale.
   Poi controlla che vi sia soltanto un unico elemento di output per la funzione definita e se ciò è vero si effettua 
   la stessa cosa descritta precedentemente.

 -il nodo CallOp controlla se la foglia, che sarebbe il nodo Identifier, possiede il nome di una funzione esistente;
  altrimenti lancia l'eccezione. Poi per ogni espressione contenuta all'interno del nodo CallOp, che sarebbero i riferimenti
  ai nodi Expr, verifica se il tipo dell'espressione e conforme al tipo del parametro di input della definizione della funzione. 
  Se non si verifica ciò si lancia un eccezione.
  Succesivamente per ogni idenficatore contenuto nel nodo CallOp, si verifica se il tipo dell'identificatore è uguale al tipo del 
  parametro di outuput della definizione della funzione. Se ciò non è vero, si lancia un eccezione.
  Infine si verifica se il numero degli argomenti di CallOp è uguale al numero degli argomenti di definizione della funzione.
  
  -il nodo ReadOp verifica se gli identificatori sono uguali ai tipi. Se ciò è vero, 
  si controlla che ogni idenficatore al suo interno abbia lo stesso tipo di dato definito all'interno del nodo 
   ReadOp. Se ciò non è vero si lancia un eccezione.
   
  -il nodo Par_decl(ParDeclOP) verifica se si sta creando una funzione; se si verifica ciò, inserisce le variabili di output con il suo tipo
   nella tabella dello scope globale; in questo caso le variabili vengono memorizzate con il nome della funzione più un numero 
   che identifica la loro posizione nella definizione degli argomenti della funzione per fare in modo che il nome di queste variabili 
   non interferisca con il nome delle variabili dello scope globale.
   Altrimenti se ciò non è vero, si inseriscono le variabili di output nella tabella dello scope locale della funzione, 
   verificando che non esistano variabili con lo stesso nome.
   
  -il nodo Var_decl (VarDeclOP) fa le stesse cose definite nel nodo Par_decl,in più,però verifica che non 
   vi siano due variabili uguali nella stessa dichiarazione, 
   se si sta definendo una funzione si verifica che ci sia solo una variabile dichiarata. 
   Il nodo VarOp verifica che non vi siano presenti dichiarazioni uguali con lo
   stesso nome nello stesso scope.
   
   
   
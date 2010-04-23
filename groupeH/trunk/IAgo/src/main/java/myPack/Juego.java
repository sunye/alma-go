package myPack;

import java.util.*;

public class Juego{
  static int filaFija;
  static int columnaFija;
  static int valorFijo;
	Tablero tableauFix;
	Tablero tableau;
	Vector listaTableros;
	int dimension;
	Celda[] jugadas=new Celda[82];


	Juego() {
          inicio();
	}

	void inicio() {
          listaTableros=new Vector();
           filaFija=0;
           columnaFija=0;
           valorFijo=0;
           tableauFix=new Tablero();
           tableau=new Tablero();
           dimension=Tablero.dimension;
          for(int z=0;z<dimension*dimension;z++)jugadas[z]=new Celda(0,0,0);
		for(int x=1;x<=dimension;x++) for(int y=1;y<=dimension;y++) putParty(((x-1)*9)+y-1,x-1,y-1,0);
	}

	void putParty(int p,int f,int c,int v) {
		jugadas[p].putFila(f);
		jugadas[p].putColumna(c);
		jugadas[p].putValor(v);
	}


	boolean listaVacia() {
		return listaTableros.isEmpty();
	}

	Tablero copyTablero(Tablero t) {
		Tablero tt=new Tablero();
		tt.uFila=t.uFila;
		tt.uColumna=t.uColumna;
		tt.uValor=t.uValor;
		for(int fila=0;fila<dimension;fila++)
			for(int columna=0;columna<dimension;columna++)
				tt.casilla[fila][columna].putValor(t.casilla[fila][columna].getValor());
		return tt;
	}

	void putTablero(Tablero t) {
		Tablero tt=new Tablero();
		tt=copyTablero(t);
		listaTableros.addElement(tt);
	}

	Tablero getTablero() {
		Tablero t;
		t=(Tablero)listaTableros.lastElement();
		return copyTablero(t);
	}

	void delTablero() {
		listaTableros.remove(listaTableros.size()-1);
	}

	boolean incrementaPosicion() {
			boolean retorno=true;
			while(retorno) {
				if(Juego.filaFija<dimension-1 && Juego.columnaFija<dimension-1) {
					Juego.columnaFija++;
				} else
                                if(Juego.filaFija==dimension-1 && Juego.columnaFija<dimension-1) {
                                        Juego.columnaFija++;
                                }
				else if(Juego.columnaFija==dimension-1 && Juego.filaFija<dimension-1) {
							Juego.columnaFija=0;
							Juego.filaFija++;
						} else {
							retorno=false;
							break;
                                                }
				if(tableauFix.getValor(Juego.filaFija,Juego.columnaFija)<1) break;
			}
			return retorno;
	}

	boolean resolver(){
		boolean activado;
		Juego.filaFija=0;
		Juego.columnaFija=-1;
		Juego.valorFijo=0;
		activado=true;
		putTablero(tableauFix);
		while(!tableau.completo() && !listaVacia()) {
                  tableau=getTablero();
			Juego.valorFijo=0;
			Juego.filaFija=tableau.getUFila();
			Juego.columnaFija=tableau.getUColumna();
			if(activado) {
				Juego.filaFija=0;
				Juego.columnaFija=-1;
				Juego.valorFijo=0;
				activado=false;
			}
			delTablero();
                          if(incrementaPosicion()) {
                            while(Juego.valorFijo<dimension) {
					Juego.valorFijo++;
					if(tableau.validCase(Juego.filaFija,Juego.columnaFija,Juego.valorFijo)) {
                                          tableau.putValor(Juego.filaFija,Juego.columnaFija,Juego.valorFijo);
                                          putTablero(tableau);
					}
				}
			}
		}
		return tableau.completo();
	}

	void printListaTableros() {
		Tablero t;
		for(int fila=0;fila<dimension;fila++) {
			for(int x=0;x<listaTableros.size();x++) {
				t=(Tablero)listaTableros.elementAt(x);
				String cadena="  ";
				for(int y=0;y<dimension;y++) cadena=cadena+t.getValor(fila,y)+" ";
				System.out.print(cadena);
			}
			System.out.println(" ");
		}
	}

	public static void main(String[] args) {
		Juego j=new Juego();
		Tablero t=new Tablero();
		j.tableauFix=new Tablero();
		j.tableauFix.putValor(0,0,2);
		j.tableauFix.putValor(1,3,1);
		j.tableauFix.putValor(2,1,4);
		Juego.filaFija=0;
		Juego.columnaFija=-1;
		Juego.valorFijo=0;
		boolean activado=true;
		j.putTablero(j.tableauFix);
		while(!t.completo() && !j.listaVacia()) {
			t=j.getTablero();
			//System.out.print("SACO");
			//System.out.println("->"+t.uFila+" "+t.uColumna+" "+t.uValor+" ");
			//j.printListaTableros();
			Juego.valorFijo=0;
			Juego.filaFija=t.uFila;
			Juego.columnaFija=t.uColumna;
			if(activado==true){
				Juego.filaFija=0;
				Juego.columnaFija=-1;
				Juego.valorFijo=0;
				activado=false;
			}
			j.delTablero();
			if(j.incrementaPosicion()==true) {
				while(Juego.valorFijo<j.dimension) {
					Juego.valorFijo++;
					if(t.validCase(Juego.filaFija,Juego.columnaFija,Juego.valorFijo)) {
						t.putValor(Juego.filaFija,Juego.columnaFija,Juego.valorFijo);
						j.putTablero(t);
						//System.out.print("METO");
						//System.out.println("->"+Juego.filaFija+" "+Juego.columnaFija+" "+Juego.valorFijo);
						//j.printListaTableros();
					}
				}
			}
		}
		t.printTablero();
	}
}
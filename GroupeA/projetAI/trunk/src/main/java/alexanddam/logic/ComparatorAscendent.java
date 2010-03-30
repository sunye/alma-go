package main.java.alexanddam.logic;

import java.util.Comparator;

// comparateur ascendent
public class ComparatorAscendent implements Comparator<Noeud_LA> {
    @Override
    public int compare(Noeud_LA n1, Noeud_LA n2) {

        if(n1.valeur == n2.valeur) {
            return 0;//retourEgaliteValeurs(n1.vo[n1.vo.length - 2], n1.vo[n1.vo.length - 1],
                 //n2.vo[n2.vo.length - 2], n2.vo[n2.vo.length - 1]);
        } else {
            return (n1.valeur < n2.valeur) ? -1 : 1;
        }

    }

    static int retourEgaliteValeurs(int x1, int y1, int x2, int y2) {

        float fS, sS;

        fS = (float)(Math.sqrt(Math.pow(x1 - 4, 2) + Math.pow(y1 - 4, 2)));
        sS = (float)(Math.sqrt(Math.pow(x2 - 4, 2) + Math.pow(y2 - 4, 2)));

        if(Math.abs(fS - sS) < 0.1) {
            return 0;
        }
        else
            return fS < sS ? 1 : -1;
    }
}

// comparateur descendent 
class ComparatorDescendent implements Comparator<Noeud_LA> {

    @Override
    public int compare(Noeud_LA n1, Noeud_LA n2) {
        if(n1.valeur == n2.valeur) {
            return 0; //ComparatorAscendent.retourEgaliteValeurs(n1.va[n1.va.length - 2],
                    //n1.va[n1.va.length - 1], n2.va[n2.va.length - 2], n2.va[n2.va.length - 1]);
        } else {
            return (n1.valeur < n2.valeur) ? 1 : -1;
        }
    }
}
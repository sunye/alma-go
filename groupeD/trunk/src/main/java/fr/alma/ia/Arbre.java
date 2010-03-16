package fr.alma.ia;

import java.util.LinkedList;

import fr.alma.atarigo.Pion;
import fr.alma.atarigo.Plateau;

public class Arbre {
	private Plateau plateau_;
	private Arbre pere_;
	private LinkedList<Arbre> fils_;

	public Arbre(Plateau p){
		plateau_ = p;
		pere_=null;
		fils_ = new LinkedList<Arbre>();
	}
	
	public Arbre(Plateau plateau, Arbre pere){
		plateau_ = plateau;
		pere_= pere;
		fils_ = new LinkedList<Arbre>();
	}
	
	public void ajouterFils(Arbre a){
		pere_=this;
		fils_.add(a);
	}
	
	public boolean estFeuille(){
		return getFils().size()==0;
	}
		
	public Arbre getPere(){
		return pere_;
	}
	
	public Plateau getPlateau(){
		return plateau_;
	}
	
	public LinkedList<Arbre> getFils(){
		return fils_;
	}
	
	public void genererFils(Pion pion){
		for(Plateau plateau : getPlateau().genererCoups(pion)){
			ajouterFils(new Arbre(plateau));
		}
	}
	
	public void genererArbre(Arbre a, Pion p, int h){
		if(h>0){
			a.genererFils(p);
			for(Arbre fils : a.getFils()){
				if(p==Pion.BLANC)
					a.genererArbre(fils,Pion.NOIR,h-1);
				else
					a.genererArbre(fils,Pion.BLANC,h-1);
			}
		}
	}
}

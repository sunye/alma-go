package fr.alma.atarigo;

import java.io.*;
import java.util.*;

/**
 * Classe implémentant le jeu AtariGo. Contient les principaux mécanismes de jeu.
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 */
public class AtariGo {

 // +----------------------------------------------------------------------+
 // | Type      : Coup.                                                    |
 // | Utilite   : enumeration representant les differents types de coups.  |
 // | Remarques : aucune.                                                  |
 // +----------------------------------------------------------------------+
/**
 *  Enumération représentant les différents types de coups.
 */
 public enum Coup { INVALIDE, GAGNANT, NEUTRE };
 
 public int nbCoups;
 public boolean fini;
 /**
  *  Plateau de jeu
  */
 public Plateau plateau;
 /**
  * Liste des groupes de pierres de go
  */
 private ListeGroupes listeG; 
 
 public Joueur joueurEnCours;
 
 public Joueur joueur1;
 public Joueur joueur2;

 /**
  *  Joue un nouveau coup et retourne le résultat.
  * @param joueur le joueur jouant le coup.
  * @param position la position du coup
  * @return le type de coup résultant  INVALIDE, GAGNANT ou NEUTRE .
  */
 public Coup jouerCoup(Pion joueur, Position position) {
	// ces conditions n'ont pas besoin de changer l'état du plateau pour savoir si le placement est possible
	if (! plateau.estValide(position) || plateau.lireCase(position) != Pion.VIDE ) {
		System.out.println("coup invalide");
	    return Coup.INVALIDE;
	}
	//ecriture sur le plateau nécessaire pour tester le suicide formé par un groupe
	plateau.ecrireCase(position, joueur);
	//formation d'une nouvelle liste comprenant un nouveau groupe formé
	ListeGroupes nouvelleLG = listeG.calculerGroupes(plateau,position,joueur);
	//test de la prise sur le nouveau groupe
	if(plateau.estPris(nouvelleLG.getGroupe(position))){
		if (plateau.estGagnant(position,nouvelleLG)) {
			nbCoups++;
			System.out.println(joueur.toString()+"a gagné");
			return Coup.GAGNANT;
		}
		System.out.println("est pris !!");
		System.out.println("affichage de listeG");
		listeG.afficher();
		plateau.viderCase(position);
		return Coup.INVALIDE;
	}
	//coup valide, on garde le plateau et on recopie la nouvelle liste de groupes sur l'ancienne
	nbCoups++;
	listeG = nouvelleLG;
	if (plateau.estGagnant(position,listeG)) {
		System.out.println(joueur.toString()+"a gagné");
		return Coup.GAGNANT;
	}
	return Coup.NEUTRE;
 }
 
 	
/**
 * Constructeur logique basique
 * @param lignes nombre de ligne du goban
 * @param colonnes nombre de colonnes du goban
 */
 public AtariGo(int lignes, int colonnes) {
	plateau = new Plateau(lignes, colonnes);
	nbCoups=0;
	listeG = new ListeGroupes();
	fini = false;
	joueurEnCours=joueur1;
 }
 /**
  * Constructeur logique
  * @param lignes nombre de lignes du goban
  * @param colonnes nombre de colonnes du goban
  * @param joueur1 type de joueur (humain ou IA)
  * @param joueur2 type de joueur (humain ou IA)
  */
 public AtariGo(int lignes, int colonnes,Joueur joueur1,Joueur joueur2) {
		plateau = new Plateau(lignes, colonnes);
		fini = false;
		nbCoups=0;
		listeG = new ListeGroupes();
		joueurEnCours=joueur1;
		this.joueur1=joueur1;
		joueur1.couleur=Pion.NOIR;
		this.joueur2=joueur2;
		joueur2.couleur=Pion.BLANC;
	 }

 /**
  * retourne si la partie est fini ou non
  * @return le booleen fini
  */
 public boolean estTermine(){
	 return fini;
 }
 
 /*
  * désactive la partie et donc la fini
  */
 public void desactiver(){
	 fini = true;
 }
 

 /*
  * accesseur en lecture
  */
 public int lireLignes() {
	return plateau.lireLignes();
 }

/*
 * accesseur en lecture
 */
 public int lireColonnes() {
	return plateau.lireColonnes();
 }

/**
 * prépare le jeu pour une nouvelle partie.
 */
 public void nouvellePartie() {
	plateau.nouvellePartie();
	fini = false;
 }

/**
 * @deprecated
 * lance la partie en mode console
 */
 public void jouer(Pion joueur, InputStream entree, PrintStream sortie) {
	int nombreCoups = lireLignes() * lireColonnes();
	Scanner scanner = new Scanner(entree);
	while (nombreCoups != 0) {
	    sortie.println(plateau.toString());
	    sortie.print("=> Joueur " + joueur.toString() + " : ");
	    int ligne;
	    int colonne;
	    try {
		ligne = scanner.nextInt();
		colonne = scanner.nextInt();
	    }
	    catch (InputMismatchException erreur) {
		sortie.println("=> Erreur : position incorrecte");
		continue;
	    }
	    Position position = new Position(ligne, colonne);
	    // joue coup en fonction du joueur...
	    switch (jouerCoup(joueur, position)) {
	    case GAGNANT:
	    fini = true;
		sortie.println(plateau.toString());
		sortie.println("=> Vous avez gagne"); 
		scanner.close();
		return;
	    case NEUTRE:
		nombreCoups --;
		joueur = joueur == Pion.BLANC ? Pion.NOIR : Pion.BLANC;
		break;
	    default:
		sortie.println("=> Erreur : position invalide");
		break;
	    }
	}
	sortie.println(plateau.toString());
	sortie.println("=> Match nul");
	scanner.close();
 }

}

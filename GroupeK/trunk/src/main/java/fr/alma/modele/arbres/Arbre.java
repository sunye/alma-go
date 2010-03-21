package fr.alma.modele.arbres;

public interface Arbre<E> {

public int getArrite() ;

public void supprimer();

public void ajouterFils(Node<E> pere, Node<E> fils) throws ConflitFilsException;

public void ajouterFils(Node<E> pere, Node<E> fils,int ieme)throws ConflitArriteException, ConflitFilsException ;

public Node<E> racine();

public Node<E> Rechercher(String etiq);

public Node<E> iemeFils(Node<E> n, int ieme) throws ConflitArriteException;

public Node<E> pere(Node<E> n);

public int positionFratrie(Node<E> n);

public Node<E> FrereSuiv(Node<E> n) throws ConflitArriteException;

public Node<E> FrerePrec(Node<E> n) throws ConflitArriteException;

public Node<E> getNewNode();

public void parcoursLargeur();

public void parcoursProfondeur();

public boolean estFeuille(Node<E> n);

public void affichageListeAdj();

public void affichageNoeudArc();

public int profondeur(Node<E> n);

public void supprimerIeme(Node<E> pere,int ieme ) throws ConflitArriteException;

public boolean est_sous_feuille(Node<E> pere);

}

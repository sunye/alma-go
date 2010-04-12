package main.java.alexanddam.logic;

public abstract class AbstractArbre {

	// le noeud pere 
	protected AbstractNoeud pere;

	// noeud courant -> la racine de l'arborescence
	protected AbstractNoeud noeud;
	protected int arite;
	protected int hauteur;		// le hauteur de l'arbre
	protected int nbNoeuds;		// nombre total de noeuds	

	protected AbstractArbre(){}

	public void etablirArite(int arite){
		if(arite<=1){
			return;
		}
		this.arite = arite;
	}

	// creer arbre avec la racine r et arite ar
	public abstract AbstractArbre creerArbre(int arite,AbstractNoeud noeud);

	// supprimer un noeud et la sous-arborescence
	public abstract AbstractArbre supprimerNoeud(AbstractNoeud noeud);

	// ajouter un fils a l'arbre courant -> le fils sera ajouter le dernier dans la liste de fils, 
	// si cette liste est ordonne
	public abstract AbstractArbre ajouterFils(AbstractNoeud noeudFils);

	// acceder a la racine -> va retourner l'arbre entier (a partir de la racine)
	public abstract AbstractArbre getRacine();

	// acceder a la racine -> va retourner le noeud racine
	public abstract AbstractNoeud racinen();

	// acceder a un Noeud d'etiquette e -> arborescence avec la racine le Noeud de l'etiquette e
	public abstract AbstractNoeud node(int etiquette);

	// acceder a i-ieme fils d'un Noeud pere -> arborescence avec la racine le i-ieme fils du Noeud pere
	public abstract AbstractNoeud ieme(AbstractNoeud pere,int iemeNoeud);

	// acceder au pere d'un Noeud -> arborescence dont la racince est le pere du Noeud f
	public abstract AbstractNoeud getPere(AbstractNoeud noeud);

	//acceder a l'ordinal d'un Noeud dans sa fratrie -> retour la position
	public abstract int getFrere(AbstractNoeud noeud);

	//affichage par liste d'adjacences
	public abstract void affichageLA();

	//affichage par noeuds/arcs
	public abstract void affichageNA();	

}

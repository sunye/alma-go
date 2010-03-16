package trunk.logic;


public abstract class Arbre {

	// le noeud pere 
	protected Noeud pere;
	
	// noeud courant -> la racine de l'arborescence
	protected Noeud c;
	protected int arite;
	protected int hauteur;		// le hauteur de l'arbre
	protected int nn;		// nombre total de noeuds	
	
	protected Arbre()
	{
		
	}

	public void etablirArite(int a) 
	{
	if(a<=1)
		return;

	arite = a;
	}

	// creer arbre avec la racine r et arite ar
	public abstract Arbre creerArbre(int ar,Noeud r);

	// supprimer un noeud et la sous-arborescence
	public abstract Arbre supprimerNoeud(Noeud n);
		
	// ajouter un fils a l'arbre courant -> le fils sera ajouter le dernier dans la liste de fils, 
	// si cette liste est ordonne
	public abstract Arbre ajouterFils(Noeud f);
	
 	// acceder a la racine -> va retourner l'arbre entier (a partir de la racine)
 	public abstract Arbre racine();

	// acceder a la racine -> va retourner le noeud racine
	public abstract Noeud racinen();
 	
 	// acceder a un Noeud d'etiquette e -> arborescence avec la racine le Noeud de l'etiquette e
 	public abstract Noeud node(int e);
 	
 	// acceder a i-ieme fils d'un Noeud pere -> arborescence avec la racine le i-ieme fils du Noeud pere
 	public abstract Noeud ieme(Noeud pere,int i);
 	
 	// acceder au pere d'un Noeud -> arborescence dont la racince est le pere du Noeud f
 	public abstract Noeud pere(Noeud f);
 	
 	//acceder a l'ordinal d'un Noeud dans sa fratrie -> retour la position
 	public abstract int frere(Noeud f);
 	
	//affichage par liste d'adjacences
	public abstract void affichage_la();
	
	//affichage par noeuds/arcs
	public abstract void affichage_na();	
	
}

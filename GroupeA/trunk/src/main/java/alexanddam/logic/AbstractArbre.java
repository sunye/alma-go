package main.java.alexanddam.logic;

public abstract class AbstractArbre {

	
	protected AbstractNoeud pere; // le noeud pere 

	
	protected AbstractNoeud noeud; // noeud courant -> la racine de l'arborescence
	protected int arite;
	protected int hauteur;			// le hauteur de l'arbre
	protected int nbNoeuds;		// nombre total de noeuds	

	/**
	 * Default constructor
	 */
	protected AbstractArbre(){}

	/**
	 * Set the arity
	 */
	public void etablirArite(int arite){
		if(arite<=1){
			return;
		}
		this.arite = arite;
	}

	/**
	 *  Create a tree with the root and the arity
	 *  
	 * @param arite Arity of the tree
	 * @param noeud Root of the tree
	 * @return Created tree
	 */
	public abstract AbstractArbre creerArbre(int arite,AbstractNoeud noeud);
	
	/**
	 * Delete the node and its sub-arborescence
	 * 
	 * @param noeud Node to delete
	 * @return Tree
	 */
	public abstract AbstractArbre supprimerNoeud(AbstractNoeud noeud);

	/**
	 * Add a son in the curent tree, the son will be added at the end of the sons' list if the list ordered
	 * @param noeudFils Node to add 
	 * @return Tree
	 */
	public abstract AbstractArbre ajouterFils(AbstractNoeud noeudFils);
	
	/**
	 * Get the root of the tree, in fac the whole tree
	 * @return Tree from the root
	 */
	public abstract AbstractArbre getRacine();

	/**
	 * Get the root of the tree
	 * 
	 * @return Root node
	 */
	public abstract AbstractNoeud racinen();

	/**
	 * Get the node with the e label, the arborescence with the root the node of e label
	 * 
	 * @param etiquette label of the node
	 * @return Node with this label
	 */
	public abstract AbstractNoeud node(int etiquette);

	/**
	 * Get the nth son of a node, arborescence with root the nth son of the node 
	 * 
	 * @param pere Node from which we want the son
	 * @param iemeNoeud nth son in integer
	 * @return arborescence from the node
	 */
	public abstract AbstractNoeud ieme(AbstractNoeud pere,int iemeNoeud);

	/**
	 * Get the father of a node; its arborescence with as root this node
	 * 
	 * @param noeud Node from which we went the father
	 * @return arborescence from the node
	 */
	public abstract AbstractNoeud getPere(AbstractNoeud noeud);

	/**
	 * Get the ordinal of a node in the sibling
	 * 
	 * @param noeud Node we want the position
	 * @return Position of the node
	 */
	public abstract int getFrere(AbstractNoeud noeud);

	/**
	 * Display the tree
	 */
	public abstract void affichageLA();
	
	/**
	 * Display the tree
	 */
	public abstract void affichageNA();	

}

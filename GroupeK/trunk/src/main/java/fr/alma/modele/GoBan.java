package fr.alma.modele;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
/*$Author$ 
 * $Date$ 
 * $Revision$  
 *  
 * license
 * 
 * */
/**
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * Class that represent a goban board and is rules
 */
public class GoBan {

	/**
	 * The current board 
	 */
	private Pion[][] goban;
	
	/**
	 * the color of the current player
	 */
	private CouleurPion tour;
	
	
	/**
	 * The groups of stone
	 */
	private HashSet<Groupe> group;
	
	/**
	 * special group for empty slot
	 */
	private Groupe groupeVide;
	
	/**
	 * Array with 1 and -1 used to move in the matrix 
	 */
	private static int[] modifier={1,-1};
	
	/**
	 * Size of the board
	 */
	public static int TAILLE_GO_BAN=9;
	
	/**
	 * Number of black token captured by the white
	 */
	private int ptsBlanc=0;
	
	/**
	 * Number of white token captured by the black
	 */
	private int ptsNoir=0;

	/**
	 * Simple constructor that (re)set the game to an initial stat
	 */
	public GoBan() {
		this.goban = new Pion[9][9];
		this.group= new HashSet<Groupe>();
		this.groupeVide=new Groupe(CouleurPion.EMPTY);
		this.tour= CouleurPion.NOIR;
		this.remiseZero();
	}
	
	

	/**
	 * Reset the game stat
	 */
	public void remiseZero(){
		this.group.clear();
		groupeVide= new Groupe(CouleurPion.EMPTY);
		for (int i=0; i<9; i++){
			for (int k=0; k<9; k++) {
				goban[i][k] = new Pion(i,k,CouleurPion.EMPTY);
				goban[i][k].setGroupe(groupeVide);
				groupeVide.addPion(goban[i][k]);
			}
		}
		this.ptsBlanc=0;
		this.ptsNoir=0;
		this.tour= CouleurPion.NOIR;
	}
	
	
	/**
	 * @param tour the tour to set
	 */
	public void setTour(CouleurPion tour) {
		this.tour = tour;
	}

	/**
	 * @return the tour
	 */
	public CouleurPion getTour() {
		return tour;
	}

	/**
	 * @return the goban
	 */
	public Pion[][] getGoban() {
		return goban;
	}

	/**
	 * @param goban the goban to set
	 */
	public void setGoban(Pion[][] goban) {
		this.goban = goban;
	}

	/**
	 * @return the group
	 */
	public HashSet<Groupe> getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(HashSet<Groupe> group) {
		this.group = group;
	}

	/**
	 * @return the groupeVide
	 */
	public Groupe getGroupeVide() {
		return groupeVide;
	}

	/**
	 * @param groupeVide the groupeVide to set
	 */
	public void setGroupeVide(Groupe groupeVide) {
		this.groupeVide = groupeVide;
	}

	/**
	 * @return the ptsBlanc
	 */
	public int getPtsBlanc() {
		return ptsBlanc;
	}

	/**
	 * @param ptsBlanc the ptsBlanc to set
	 */
	public void setPtsBlanc(int ptsBlanc) {
		this.ptsBlanc = ptsBlanc;
	}

	/**
	 * @return the ptsNoir
	 */
	public int getPtsNoir() {
		return ptsNoir;
	}

	/**
	 * @param ptsNoir the ptsNoir to set
	 */
	public void setPtsNoir(int ptsNoir) {
		this.ptsNoir = ptsNoir;
	}
	
	// Utils methods

	/**
	 * Calculate if the move is allowed
	 * @return the type of move: Valid, NonValid, Prise
	 */
	public TypeCoup isMoveAllowed(Coordonnee cood, CouleurPion coul) {
		
		if( !cood.isValid(GoBan.TAILLE_GO_BAN)){
			return TypeCoup.NONVALID;
		}
			
		Pion pi=goban[cood.getX()][cood.getY()];
		
		if (pi.getCouleur()!=CouleurPion.EMPTY){
			return TypeCoup.NONVALID;
		}
		
		List<Pion> voisin= getVoisin(pi);

		for (Pion v:voisin){
			if( v.getCouleur()==CouleurPion.oppose(coul)){
				if (v.getGroupe().getNbLiberty()==1){
					return TypeCoup.PRISE;
				}
			} else if (v.getCouleur()==coul){
				if( v.getGroupe().getNbLiberty()!=1){
					return TypeCoup.VALID;
				}
			}else if (v.getCouleur()==CouleurPion.EMPTY){
				return TypeCoup.VALID;
			} 
		}
		voisin.clear();
		
		return TypeCoup.NONVALID;
	}

	/**
	 * Calculate the liberty of a stone
	 * @param pi the stone
	 * @return list of his liberty
	 */
	public List<Coordonnee> calculPionLiberte(Pion pi){
		List<Coordonnee> result= new Vector<Coordonnee>();
				
		List<Pion> voisin= getVoisin(pi);
		
		for (Pion v:voisin){
			if( v.getCouleur()==CouleurPion.EMPTY){
				result.add(v.getPosition());
			}
		}
		voisin.clear();

		return result;
	}

	/**
	 * Obtain the neighbors of a stone
	 * @param pi
	 * @return list of the neighbors
	 */ 
	public List<Pion> getVoisin(Pion pi){
		List<Pion> listVoisin= new Vector<Pion>();
		int x=pi.getPosition().getX();
		int y=pi.getPosition().getY();
				
		for (int i=0; i<modifier.length;i++){
			Coordonnee coordtemp=new Coordonnee(x+modifier[i], y);
			if (coordtemp.isValid(GoBan.TAILLE_GO_BAN)){
				listVoisin.add(goban[coordtemp.getX()][coordtemp.getY()]);
			}
		}
		for (int i=0; i<modifier.length;i++){
			Coordonnee coordtemp=new Coordonnee(x, y+modifier[i]);
			if (coordtemp.isValid(GoBan.TAILLE_GO_BAN)){
				listVoisin.add(goban[coordtemp.getX()][coordtemp.getY()]);
			}
		}
		
		return listVoisin;
		
	}
	//end of Utils methods	
	
	
	//Goban pion(stone) public management method
	/**
	 * Add a stone on the board with capture enabled (classical play)
	 * @param cood the coordinate
	 * @return if the stone where add
	 */
	public boolean addPion(Coordonnee cood){
		
		
		boolean jouer=isMoveAllowed(cood, tour)!=TypeCoup.NONVALID;
		if (jouer){
			this.addPion(cood, tour, true);
			
		}
		
		return jouer;
	}
	
	/**
	 * add a stone on the board with capture disabled (AI calculation play)
	 * @param cood the coorninate
	 * @param cc the coolor
	 * @return if the stone where add
	 */
	public boolean addPion(Coordonnee cood, CouleurPion cc){
		boolean jouer =isMoveAllowed(cood, cc)!=TypeCoup.NONVALID;
		if (jouer){
		this.addPion(cood, cc, false);
		}
		return jouer;
	}
	
	/**
	 * remove a stone of the board (AI calculation play)
	 * @param cood the coordinate of the stone
	 * @param coul the color	
	 */
	public void retirerPion(Coordonnee cood, CouleurPion coul){
		removePion(goban[cood.getX()][cood.getY()]);
	}

	//end of Goban pion(stone) public management method
	
	/**
	 * Add a stone on the board
	 * @param cood the coordinate
	 * @param coul the color of the stone to add
	 * @param enlevement if captured stone has to be remove
	 */
	private void addPion(Coordonnee coord, CouleurPion coul, boolean enlevement){
	
		Pion pi=goban[coord.getX()][coord.getY()];
		groupeVide.removePion(pi);
		
		pi.setCouleur(coul);
		pi.setGroupe(new Groupe(coul));
		pi.getGroupe().addPion(pi);
		List<Pion> voisin= getVoisin(pi);
			
		for (Pion v:voisin){
			if (v.getCouleur()==CouleurPion.EMPTY){
				pi.getGroupe().addLiberty(v.getPosition());
			}else if( 
				v.getCouleur()== CouleurPion.oppose(pi.getCouleur())){
				v.getGroupe().removeLiberty(pi.getPosition());
			}else if(v.getCouleur()== pi.getCouleur()){
				group.remove(v.getGroupe());
				pi.getGroupe().fusionGroup(v.getGroupe());
				pi.getGroupe().removeLiberty(pi.getPosition());
			}
		}

		group.add(pi.getGroupe());
		
		
		if (enlevement){
			for (Pion v : voisin) {
				if ((v.getCouleur() == CouleurPion.oppose(pi.getCouleur()))
						&& (v.getGroupe().getNbLiberty() == 0)) {
					removePionAndGroupe(v.getGroupe());
				}
			}
		}
		
		voisin.clear();
		tour= CouleurPion.oppose(tour);
	}
	
	/**
	 * remove a stone from the board (not capture, just remove a move) 
	 * @param pi
	 */
	private void removePion(Pion pi){
		int x=pi.getPosition().getX();
		int y=pi.getPosition().getY();
		
		CouleurPion coulinitial=pi.getCouleur();
		Groupe groupInitial=goban[x][y].getGroupe();
		
		goban[x][y].getGroupe().removePion(goban[x][y]);
		group.remove(groupInitial);
		
		
		goban[x][y].setCouleur(CouleurPion.EMPTY); 
		goban[x][y].setGroupe(groupeVide);
		groupeVide.addPion(goban[x][y]);
		
		
		List<Pion> voisin= getVoisin(pi);
		for (Pion v:voisin){
			if( v.getCouleur()== CouleurPion.oppose(coulinitial)){
				v.getGroupe().addLiberty(pi.getPosition());
			}else if(v.getCouleur()== coulinitial){
				Groupe newGroup= new Groupe(v.getCouleur());
				group.add(newGroup);
				reformeGroup(newGroup, v);
			}else{
				
			}
		}
		
		voisin.clear();
		tour= CouleurPion.oppose(tour);
	}
	
	/**
	 * remove a group that is captured
	 * @param grop
	 */
	private void removePionAndGroupe(Groupe grop){
		this.group.remove(grop);
		
		if (grop.getCouleur()==CouleurPion.BLANC){
			ptsNoir+=grop.getNbPions();
		}else{
			ptsBlanc+=grop.getNbPions();
		}
		
		
		Collection<Pion> list= grop.getPions();
		for (Pion v:list){
			v.setGroupe(groupeVide);
			v.setCouleur(CouleurPion.EMPTY);
			groupeVide.addPion(v);
		}
		
		for(Pion v: list){
			Collection<Pion> voisinVoisin=getVoisin(v);
			for (Pion capt: voisinVoisin){
				Collection <Coordonnee> libertes=calculPionLiberte(capt);
				capt.getGroupe().addLibertys(libertes);
				libertes.clear();
			}
		}
		grop.clear();
		
	}
	
	/**
	 * used to reform the group the removal of a stone (backtracking)
	 * @param grop the new group for the stone
	 * @param pi the stone
	 */
	private void reformeGroup(Groupe grop, Pion pi){
		if( pi.getCouleur()==grop.getCouleur()){
			pi.setGroupe(grop);
			grop.addPion(pi);
			Collection<Coordonnee> libert=calculPionLiberte(pi);
			grop.addLibertys(libert);
			libert.clear();
			
			int x=pi.getPosition().getX();
			int y=pi.getPosition().getY();
			Pion avisiter=null;
			
			for (int i=0; i<modifier.length;i++){
					Coordonnee coordtemp=new Coordonnee(x+modifier[i], y);
					if (coordtemp.isValid(GoBan.TAILLE_GO_BAN)){
						avisiter=goban[coordtemp.getX()][coordtemp.getY()];
						reformationGroup(grop, avisiter, modifier[i]*-1, 0);
					}
				
			}
			for (int i=0; i<modifier.length;i++){
				Coordonnee coordtemp=new Coordonnee(x, y+modifier[i]);
				if (coordtemp.isValid(GoBan.TAILLE_GO_BAN)){
					avisiter=goban[coordtemp.getX()][coordtemp.getY()];
					reformationGroup(grop, avisiter, 0, modifier[i]*-1);
				}
			}
		}
	}
	
	/**
	 * Don't call it. It used by the reformeGroup Method (backtracking)
	 * @param grop
	 * @param pi
	 * @param intmodifierx
	 * @param intmodifiery
	 */
	private void reformationGroup(Groupe grop, Pion pi, int intmodifierx, int intmodifiery){
		
		if( pi.getCouleur()==grop.getCouleur()){
			pi.setGroupe(grop);
			grop.addPion(pi);
			
			Collection<Coordonnee> libert=calculPionLiberte(pi);
			grop.addLibertys(libert);
			libert.clear();
			
			int x=pi.getPosition().getX();
			int y=pi.getPosition().getY();
			Pion avisiter=null;
			
			for (int i=0; i<modifier.length;i++){
				if(intmodifierx!= modifier[i]){
					Coordonnee coordtemp=new Coordonnee(x+modifier[i], y);
					if (coordtemp.isValid(GoBan.TAILLE_GO_BAN)){
						avisiter=goban[coordtemp.getX()][coordtemp.getY()];
						reformationGroup(grop, avisiter, modifier[i]*-1, 0);
					}
				}
			}
			for (int i=0; i<modifier.length;i++){
				if(intmodifiery!= modifier[i]){
					Coordonnee coordtemp=new Coordonnee(x, y+modifier[i]);
					if (coordtemp.isValid(GoBan.TAILLE_GO_BAN)){
						avisiter=goban[coordtemp.getX()][coordtemp.getY()];
						reformationGroup(grop, avisiter, 0, modifier[i]*-1);
					}
				}
			}
		}
	}
}

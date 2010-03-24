package fr.alma.server.ia;

import java.util.ArrayList;
import java.util.List;

import fr.alma.server.core.Coordinator;
import fr.alma.server.core.Emplacement;
import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IStateGame;

public class Tools {

	public static int countFreeDegrees(Coordinator coordinator,boolean player, IEmplacement e, List<IEmplacement> emplacements){
		int nbFreeDegrees = 0;	
		emplacements.add(e);

		//case Top
		short rowTop = (short) (e.getRow() - 1);
		nbFreeDegrees += countCaseFreeDegrees(new Emplacement(e.getCol(), rowTop), coordinator,player, emplacements);

		//case Bottom
		short rowBottom = (short) (e.getRow() + 1);
		nbFreeDegrees += countCaseFreeDegrees(new Emplacement(e.getCol(), rowBottom), coordinator,player, emplacements);

		//case Left
		short colLeft =(short) (e.getCol() -1);
		nbFreeDegrees += countCaseFreeDegrees(new Emplacement(colLeft, e.getRow()), coordinator,player, emplacements);

		//case Right
		short colRight =(short) (e.getCol() +1);
		nbFreeDegrees += countCaseFreeDegrees(new Emplacement(colRight, e.getRow()), coordinator, player, emplacements);

		return nbFreeDegrees;
	}

	private static int countCaseFreeDegrees(IEmplacement e, Coordinator coordinator, boolean player, List<IEmplacement> emplacements){
		int nbFreeDegrees = 0;

		if(coordinator.getStateGame().onGoban(e.getCol(), e.getRow())) {

			if(!((IEmplacement)e).isIn(emplacements)){
				if(coordinator.getStateGame().isFree(e.getCol(), e.getRow())){
					//System.out.println("isFree ["+col+"] ["+row+"]");

					nbFreeDegrees++;
					emplacements.add(e);

				}else if(coordinator.getStateGame().getIntersection(e.getCol(), e.getRow()).equals(player)){
					nbFreeDegrees += countFreeDegrees(coordinator, player, e, emplacements);
				}
			}
		}
		return nbFreeDegrees;
	}
	
	public static void showGobanOnConsole(IStateGame stateGame) {
		StringBuffer chaine = new StringBuffer();
		for (short col = 0; col < stateGame.getMaxCol(); col++) {
			chaine.append(" " + col);
		}
		System.out.println("\n " + chaine.toString());
		
		for (short row = 0; row < stateGame.getMaxRow(); row++) {
			System.out.print(row);
			for (short col = 0; col < stateGame.getMaxCol(); col++) {
				if (stateGame.isPlayer(col, row)) {
					System.out.print("|-");
				} else if (stateGame.isComputer(col, row)) {
					System.out.print("|o");
				} else {
					System.out.print("| ");
				}
			}
			System.out.println("|" + row);
		}
		System.out.println(" " + chaine.toString() + "\n");
	}

	/**
	 * 
	 * @param coordinator
	 * @param e is the emplacement choosen by the current player
	 * @return
	 */
	public static int hasCapturedWithThisEmplacement(Coordinator coordinator, IEmplacement e){
		//System.out.println("Tools.hasCapturedWithThisEmplacement() Pierre placée en ["+e.getCol()+"]["+e.getRow()+"]");

		int hasFreedom=999;
		Boolean otherIPlayer = !coordinator.getCurrentPlayer().getColor();
		List<IEmplacement> emplacements = new ArrayList<IEmplacement>();
		emplacements.add(e);
		//case Top
		short rowTop = (short) (e.getRow() - 1);
		//System.out.println("Voisin TOP ["+e.getCol()+"]["+rowTop+"]");
		
		if(coordinator.getStateGame().onGoban(e.getCol(), rowTop)){
			hasFreedom = checkOpponentHasFreedom(coordinator, otherIPlayer, new Emplacement(e.getCol(), rowTop), emplacements) ;
		}

		if(hasFreedom!=0){
			//case Bottom
			short rowBottom = (short) (e.getRow() + 1);
			//System.out.println("Voisin BOTTOM ["+e.getCol()+"]["+rowBottom+"]");

			if(coordinator.getStateGame().onGoban(e.getCol(), rowBottom)){
				hasFreedom = checkOpponentHasFreedom(coordinator, otherIPlayer, new Emplacement(e.getCol(), rowBottom), emplacements) ;
			}
			

			if(hasFreedom!=0){
				//case Left
				short colLeft =(short) (e.getCol() -1);
				//System.out.println("Voisin LEFT ["+colLeft+"]["+e.getRow()+"]");

				if(coordinator.getStateGame().onGoban(colLeft, e.getRow())){
					hasFreedom = checkOpponentHasFreedom(coordinator, otherIPlayer, new Emplacement(colLeft, e.getRow()), emplacements) ;
				}

				if(hasFreedom!=0){
					//case Right
					short colRight =(short) (e.getCol() +1);
					//System.out.println("Voisin RIGHT ["+colRight+"]["+e.getRow()+"]");
					
					if(coordinator.getStateGame().onGoban(colRight, e.getRow())){
						hasFreedom = checkOpponentHasFreedom(coordinator, otherIPlayer, new Emplacement(colRight, e.getRow()), emplacements) ;
					}
				}
			}
		}
		//System.out.println("Tools.hasCapturedWithThisEmplacement() retour de hasFreedom = "+hasFreedom);
		return hasFreedom;
	}

	/**
	 * Require that the neighbourEmplacement tested must exist on the goban
	 * @param coordinator
	 * @param otherIPlayer
	 * @param neighbourEmplacement
	 * @param emplacements
	 * @return
	 */
	public static int checkOpponentHasFreedom(Coordinator coordinator, Boolean otherIPlayer, IEmplacement neighbourEmplacement, List<IEmplacement> emplacements){
		//System.out.println("Tools.checkOpponentHasFreedom() du voisin : ["+neighbourEmplacement.getCol()+"]["+neighbourEmplacement.getRow()+"]");


		int hasFreedom = 999;

		// if the neighbourEmplacement have been already tested
		if(!((IEmplacement)neighbourEmplacement).isIn(emplacements)){

			emplacements.add(neighbourEmplacement);
			//if neighbourEmplacement is a free Emplacement
			if(coordinator.getStateGame().getIntersection(neighbourEmplacement.getCol(), neighbourEmplacement.getRow()) != null){
				// if neighbourEmplacement is the opponent player
				if(coordinator.getStateGame().getIntersection(neighbourEmplacement.getCol(), neighbourEmplacement.getRow()).equals(otherIPlayer)){

					hasFreedom = countFreeDegrees(coordinator, otherIPlayer, neighbourEmplacement, emplacements); 

				}else{ 
					// if neighbourEmplacement is the current player
					hasFreedom = checkPlayerGroupControlOpponentFreedom(coordinator, otherIPlayer, neighbourEmplacement, emplacements); // WARNING pas assez complet
				}
			}else hasFreedom = 1;
		}

		//System.out.println("Tools.checkOpponentHasFreedom() retour hasfreedom = "+hasFreedom);

		return hasFreedom;
	}

	private static int checkPlayerGroupControlOpponentFreedom(	
			Coordinator coordinator, Boolean otherIPlayer,
			IEmplacement e, List<IEmplacement> emplacements) {
		
		// e est un current user
		// pour tous voisin TOP, BOTTOM,...DOWN


		return 1;
	}


}

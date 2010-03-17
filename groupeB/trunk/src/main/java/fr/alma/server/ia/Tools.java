package fr.alma.server.ia;

import java.util.List;

import fr.alma.server.core.Coordinator;
import fr.alma.server.core.Emplacement;
import fr.alma.server.core.IEmplacement;

public class Tools {
	
	public static int countFreeDegrees(Coordinator coordinator, IEmplacement e, List<IEmplacement> emplacements){
		int nbFreeDegrees = 0;	
		emplacements.add(e);
	
		//case Top
		short rowTop = (short) (e.getRow() - 1);
		nbFreeDegrees += Tools.countCaseFreeDegrees(e.getCol(), rowTop, coordinator, emplacements);

		//case Bottom
		short rowBottom = (short) (e.getRow() + 1);
		nbFreeDegrees += Tools.countCaseFreeDegrees(e.getCol(), rowBottom, coordinator, emplacements);

		//case Left
		short colLeft =(short) (e.getCol() -1);
		nbFreeDegrees += Tools.countCaseFreeDegrees(colLeft, e.getRow(), coordinator, emplacements);

		//case Right
		short colRight =(short) (e.getCol() +1);
		nbFreeDegrees += Tools.countCaseFreeDegrees(colRight, e.getRow(), coordinator, emplacements);
	
		return nbFreeDegrees;
	}
	
	private static int countCaseFreeDegrees(short col, short row, Coordinator coordinator, List<IEmplacement> emplacements){
		int nbFreeDegrees = 0;
		if(coordinator.getStateGame().onGoban(col, row)) {
			IEmplacement nouveauEmplacement = new Emplacement(col, row);
			if(!((IEmplacement)nouveauEmplacement).isIn(emplacements)){
				if(coordinator.getStateGame().isFree(col, row)){
					//System.out.println("isFree ["+col+"] ["+row+"]");
					nbFreeDegrees++;
					emplacements.add(nouveauEmplacement);

				}else if(coordinator.getStateGame().getIntersection(col,row).equals(coordinator.getCurrentPlayer().getColor())){
					nbFreeDegrees += countFreeDegrees(coordinator, nouveauEmplacement, emplacements);
				}
			}
		}
		
		return nbFreeDegrees;
	}

}

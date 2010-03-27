package fr.alma.server.core;

import java.util.List;

public class Emplacement implements IEmplacement {

	private int row;
	private int col;
	
	public Emplacement(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	@Override
	public int getCol() {
		return col;
	}

	@Override
	public int getRow() {
		return row;
	}
	
	@Override
	public boolean equals(IEmplacement e1){
		//System.out.println("Emplacement.equals()["+e1.getCol()+"]["+e1.getRow()+"]");
		return (this.col == e1.getCol() && this.row == e1.getRow());
	}
	
	@Override
	public boolean isIn(List<IEmplacement> emplacements){
		for(IEmplacement e :emplacements ){
			if(this.equals(e))
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "col : " + col + " - row : " + row;
	}

}

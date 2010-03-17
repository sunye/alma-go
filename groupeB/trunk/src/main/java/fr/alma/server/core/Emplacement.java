package fr.alma.server.core;

import java.util.List;

public class Emplacement implements IEmplacement {

	private short row;
	private short col;
	
	public Emplacement(short col, short row) {
		this.col = col;
		this.row = row;
	}
	
	@Override
	public short getCol() {
		return col;
	}

	@Override
	public short getRow() {
		return row;
	}
	
	@Override
	public boolean equals(IEmplacement e1){
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

}

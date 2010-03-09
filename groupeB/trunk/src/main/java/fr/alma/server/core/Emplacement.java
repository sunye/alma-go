package fr.alma.server.core;

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

}

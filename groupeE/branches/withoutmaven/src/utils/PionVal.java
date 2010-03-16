package utils;

public enum PionVal {
	RIEN((byte) 0),NOIR((byte) -1),BLANC((byte) 1);
	
	private final byte  valeur_;
	private PionVal(byte val) {
	    this.valeur_ = val;
	}
	
	public byte valeur(){
		return valeur_;
	}

	@Override
	public String toString() {
		return String.valueOf(this.valeur_);
	}
	
	
}

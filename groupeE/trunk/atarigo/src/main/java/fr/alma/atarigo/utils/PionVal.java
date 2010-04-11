package fr.alma.atarigo.utils;

public enum PionVal {
	RIEN((byte) 5),NOIR((byte) -1),BLANC((byte) 1);
	
	private final byte  value;
	private PionVal(byte val) {
	    this.value = val;
	}
	
	public byte valeur(){
		return value;
	}

	@Override
	public String toString() {
                switch(this.value) {
                    case 5 :
                        return "EMPTY";
                    case -1 :
                        return "BLACK";
                    case 1 :
                        return "WHITE";
                    default:
                        return "EMPTY";
                }
	}
	
	
}

package game;

public enum Color {
	NONE ,WHITE ,BLACK ;
	
	public Color invColor() {
		if(this == WHITE){
			return BLACK;
		}else if (this == BLACK){
			return WHITE;
		}
		return NONE;
	}
	
	public String toString(){
		if(this == WHITE){
			return "blanc";
		}else if (this == BLACK){
			return "noir";
		}
		return "none";
	}
}

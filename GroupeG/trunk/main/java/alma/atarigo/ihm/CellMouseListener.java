package alma.atarigo.ihm;

import alma.atarigo.Cell;
import alma.atarigo.CellEvent;

public interface CellMouseListener {

	public void mouseClicked(Cell event);
	public void mouseEntered(Cell event);
	public void mouseExited(Cell event);
	
}

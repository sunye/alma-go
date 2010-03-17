package fr.alma.server.core;

import java.util.List;

public interface IEmplacement {
	short getCol();
	short getRow();
	public boolean isIn(List<IEmplacement> emplacements);
	boolean equals(IEmplacement e1);
	
}

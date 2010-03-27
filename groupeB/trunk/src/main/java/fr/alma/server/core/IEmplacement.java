package fr.alma.server.core;

import java.util.List;

public interface IEmplacement {
	int getCol();
	int getRow();
	public boolean isIn(List<IEmplacement> emplacements);
	boolean equals(IEmplacement e1);
	
}

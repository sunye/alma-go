package fr.alma.server.rule;

import java.util.ArrayList;
import java.util.List;

import fr.alma.server.core.IEmplacement;

public class RuleManager {
	List<IRule> listRules;
	
	
	private List<IRule> getRules() {
		if (listRules == null) {
			listRules = new ArrayList<IRule>();
		}
		return listRules;
	}
	
	
	public void addRule(IRule rule) {
		getRules().add(rule);
	}

	
	public void removeRule(IRule rule) {
		getRules().remove(rule);
	}
	
	
	public void clear() {
		getRules().clear();
	}

	
	public boolean check(IEmplacement emplacement) {
		for (IRule rule : getRules()) {
			if (! rule.accept(emplacement.getCol(), emplacement.getRow())) {
				return false;
			}
		}
		return true;
	}
}

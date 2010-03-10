package fr.alma.server.rule;

import java.util.ArrayList;
import java.util.List;

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

	
	public boolean check(short col, short row) {
		for (IRule rule : getRules()) {
			if (! rule.accept(col, row)) {
				return false;
			}
		}
		return true;
	}
}

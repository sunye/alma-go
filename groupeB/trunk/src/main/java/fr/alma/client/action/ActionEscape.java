package fr.alma.client.action;


/**
 * @author Romain Gournay & Bruno Belin
 * consideration of an interrupt request
 */
public class ActionEscape implements IAction {

	private Context context = null;
	
	public ActionEscape(Context context) {
		this.context = context;
	}
	
	@Override
	public void run() {
		context.getComputer().interrupt();
	}

}

package coeur;
import ia.Ia;
import ihm.Ihm;

/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
public class Go {

	public static void main(String args[]){
		Coeur coeur = new Coeur();
		 new Ihm(coeur);
		 new Ia(coeur);
	}

}

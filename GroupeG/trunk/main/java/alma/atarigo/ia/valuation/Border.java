package alma.atarigo.ia.valuation;

import java.util.List;

import alma.atarigo.CellContent;
import alma.atarigo.CellPosition;
import alma.atarigo.GobanModel;

/**
* Fonction d'evaluation s'occupant de noter en fonction des cellules du bords rencontrees
* @author gass-mouy
*/
public class Border extends AbstractValuation implements Valuation {
	
	/**
	 * Ecart maximum authorise entre le nombre de cellules du bord des deux joueurs
	 */
	private static final int MAX_BORDER_GAP=6;

    /**
     * Constructeur de la classe avec le content de l'IA
     * @param content Le content de l'IA
     */
	public Border(CellContent content){
		this.content = content;
		this.name="Border";
	}
	
	public Border(){
		this.name="Border";
	}

	public long run(GobanModel goban) {
		
		//recuperation des cellules du bords pour l'IA et pour l'enemi
		List<CellPosition> ia = goban.getBorderCellsFor(content)
						,enemy = goban.getBorderCellsFor(content.getEnemy());

		//stockage des tailles des listes
		int sizeIA=ia.size()
			,sizeEnemy=enemy.size();

		//booleens renseignants pour chaque joueur s'il possede des cellules bords ou non
		boolean iaHasBorder=(sizeIA==0)?false:true
				,enemyHasBorder=(sizeEnemy==0)?false:true;
		
		//l'IA n'a pas de cellule bord (c'est bien)
		if(!iaHasBorder){
			if(!enemyHasBorder){
				//l'evaluation se base sur les borderCells
				//ici ce n'est pas le cas donc la notation du goban ne depend pas d'elle
				return 0;
			}
			//l'IA n'a pas de cellule du bord (c'est bien)
			//l'enemi oui (encore mieux)
			//(la logique est de bien noter le goban mais l'IA joue encore + sur les bords...
			//...si l'on met UP_LIMIT ! :o)
			//(voila le bugg ennonce dans le commit :ooo)
			return UP_LIMIT*sizeEnemy;
		}
		//l'IA en a (moins bien)
		//l'enemi n'en a pas (pas bon du tout)
		if(!enemyHasBorder){
			//idem: bugg
			return DOWN_LIMIT*sizeIA;
		}
		//Les 2 en ont: on definit une algebre
		return algebre(goban,ia,enemy);
	}

	/**
	 * Algebre pour definir une note via les positions du bord
	 * @param goban Le goban de jeu
	 * @param ia Les positions de l'IA
	 * @param enemy Les positions de l'enemi
	 * @return La note du goban
	 */
	private long algebre(GobanModel goban, List<CellPosition> ia, List<CellPosition> enemy){

		//taille des listes et definission de l'ecart
		int sizeIA=ia.size()
			,sizeEnemy=enemy.size()
			,gapIA = sizeIA - sizeEnemy
			,gapEnemy= -gapIA;

		//mauvaise situation si l'IA possede beaucoup plus de bords que son enemi
		//idem: bugg
		if(gapIA > MAX_BORDER_GAP){
			return DOWN_LIMIT*gapIA;
		}
		
		//bonne situation dans le cas contraire
		//idem: bugg
		if(gapEnemy > MAX_BORDER_GAP){
			return UP_LIMIT*gapEnemy;
		}

		//sinon on checke chacun des territoires formes a partir des positions
		//une variable est incrementee a chaque territoires non valides
		//(definissions dans LibertyVal)
		int coefIA=setCoef(goban,ia)
			,coefEnemy=setCoef(goban,enemy);

		//tous les territoires de l'IA sont valides
		if(coefIA==1){
			//idem pour l'enemi
			if(coefEnemy==1){
				return sizeEnemy-sizeIA;
			}
			//sinon c'est une bonne situation
			//multiplication par le coeficient d'erreur
			//idem: bugg
			return UP_LIMIT*coefEnemy;
		}
		
		//certains territoires de l'IA sont invalides
		//tous les territoires de l'enemi sont valides
		if(coefEnemy==1){
			//mauvais goban
			//multiplication par le coeficient d'erreur
			//idem: bugg
			return DOWN_LIMIT*coefIA;
		}
		
		//sinon mini algebre
		return coefEnemy-coefIA;
	}
	
	/**
	 * Incrematation d'un coeficient a chaque territoire non valides
	 * Validation definie dans LibertyVal
	 * @param goban Le goban de jeu
	 * @param borders Les cellules du bords
	 * @return Le coeficient incremente
	 */
	private int setCoef(GobanModel goban,List<CellPosition> borders){
		int coef=1;
		for(CellPosition position : borders){
			boolean check = LibertyVal.check(goban,goban.buildTerritory(position));
			if(!check){
				++coef;
			}
		}
		return coef;
	}

}

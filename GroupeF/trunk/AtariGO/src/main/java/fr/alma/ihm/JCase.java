package fr.alma.ihm;

import java.awt.Point;

import javax.swing.JLabel;

/**
 * Classe qui permet de detecter la position du pion ajouter et renvoyé ses coordonnées
 * @author ZERBITA Mohamed El Hadi
 *
 */
public class JCase {
	
	/**
	 * Cette méthode retourne la case de la grille qui correspond au click de souris.
	 * @return la case de la grille.
	 */
	public static Point getValidePosition(JLabel Grille, boolean jeuEnCours){
				
		Point p = Grille.getMousePosition();
		
		if(jeuEnCours){
		//---------------------- LIGNE 9
		if(p.x<57 && p.x>33 && p.y<57 && p.y>33) return new Point(0,0);
		if(p.x<82 && p.x>58 && p.y<57 && p.y>33) return new Point(1,0);
		if(p.x<106 && p.x>82 && p.y<57 && p.y>33) return new Point(2,0);
		if(p.x<130 && p.x>106 && p.y<57 && p.y>33) return new Point(3,0);
		if(p.x<154 && p.x>130 && p.y<57 && p.y>33) return new Point(4,0);
		if(p.x<179 && p.x>155 && p.y<57 && p.y>33) return new Point(5,0);
		if(p.x<202 && p.x>178 && p.y<57 && p.y>33) return new Point(6,0);
		if(p.x<227 && p.x>203 && p.y<57 && p.y>33) return new Point(7,0);
		if(p.x<252 && p.x>228 && p.y<57 && p.y>33) return new Point(8,0);
		
		//---------------------- LIGNE 8
		if(p.x<57 && p.x>33 && p.y<82 && p.y>58) return new Point(0,1);
		if(p.x<82 && p.x>58 && p.y<82 && p.y>58) return new Point(1,1);
		if(p.x<106 && p.x>82 && p.y<82 && p.y>58) return new Point(2,1);
		if(p.x<130 && p.x>106 && p.y<82 && p.y>58) return new Point(3,1);
		if(p.x<154 && p.x>130 && p.y<82 && p.y>58) return new Point(4,1);
		if(p.x<179 && p.x>155 && p.y<82 && p.y>58) return new Point(5,1);
		if(p.x<202 && p.x>178 && p.y<82 && p.y>58) return new Point(6,1);
		if(p.x<227 && p.x>203 && p.y<82 && p.y>58) return new Point(7,1);
		if(p.x<252 && p.x>228 && p.y<82 && p.y>58) return new Point(8,1);
		
		//---------------------- LIGNE 7
		if(p.x<57 && p.x>33 && p.y<106 && p.y>82) return new Point(0,2);
		if(p.x<82 && p.x>58 && p.y<106 && p.y>82) return new Point(1,2);
		if(p.x<106 && p.x>82 && p.y<106 && p.y>82) return new Point(2,2);
		if(p.x<130 && p.x>106 && p.y<106 && p.y>82) return new Point(3,2);
		if(p.x<154 && p.x>130 && p.y<106 && p.y>82) return new Point(4,2);
		if(p.x<179 && p.x>155 && p.y<106 && p.y>82) return new Point(5,2);
		if(p.x<202 && p.x>178 && p.y<106 && p.y>82) return new Point(6,2);
		if(p.x<227 && p.x>203 && p.y<106 && p.y>82) return new Point(7,2);
		if(p.x<252 && p.x>228 && p.y<106 && p.y>82) return new Point(8,2);
	
		//---------------------- LIGNE 6
		if(p.x<57 && p.x>33 && p.y<130 && p.y>106) return new Point(0,3);
		if(p.x<82 && p.x>58 && p.y<130 && p.y>106) return new Point(1,3);
		if(p.x<106 && p.x>82 && p.y<130 && p.y>106) return new Point(2,3);
		if(p.x<130 && p.x>106 && p.y<130 && p.y>106) return new Point(3,3);
		if(p.x<154 && p.x>130 && p.y<130 && p.y>106) return new Point(4,3);
		if(p.x<179 && p.x>155 && p.y<130 && p.y>106) return new Point(5,3);
		if(p.x<202 && p.x>178 && p.y<130 && p.y>106) return new Point(6,3);
		if(p.x<227 && p.x>203 && p.y<130 && p.y>106) return new Point(7,3);
		if(p.x<252 && p.x>228 && p.y<130 && p.y>106) return new Point(8,3);
		
		//---------------------- LIGNE 5
		if(p.x<57 && p.x>33 && p.y<154 && p.y>130) return new Point(0,4);
		if(p.x<82 && p.x>58 && p.y<154 && p.y>130) return new Point(1,4);
		if(p.x<106 && p.x>82 && p.y<154 && p.y>130) return new Point(2,4);
		if(p.x<130 && p.x>106 && p.y<154 && p.y>130) return new Point(3,4);
		if(p.x<154 && p.x>130 && p.y<154 && p.y>130) return new Point(4,4);
		if(p.x<179 && p.x>155 && p.y<154 && p.y>130) return new Point(5,4);
		if(p.x<202 && p.x>178 && p.y<154 && p.y>130) return new Point(6,4);
		if(p.x<227 && p.x>203 && p.y<154 && p.y>130) return new Point(7,4);
		if(p.x<252 && p.x>228 && p.y<154 && p.y>130) return new Point(8,4);
		
		//---------------------- LIGNE 4
		if(p.x<57 && p.x>33 && p.y<179 && p.y>155) return new Point(0,5);
		if(p.x<82 && p.x>58 && p.y<179 && p.y>155) return new Point(1,5);
		if(p.x<106 && p.x>82 && p.y<179 && p.y>155) return new Point(2,5);
		if(p.x<130 && p.x>106 && p.y<179 && p.y>155) return new Point(3,5);
		if(p.x<154 && p.x>130 && p.y<179 && p.y>155) return new Point(4,5);
		if(p.x<179 && p.x>155 && p.y<179 && p.y>155) return new Point(5,5);
		if(p.x<202 && p.x>178 && p.y<179 && p.y>155) return new Point(6,5);
		if(p.x<227 && p.x>203 && p.y<179 && p.y>155) return new Point(7,5);
		if(p.x<252 && p.x>228 && p.y<179 && p.y>155) return new Point(8,5);
		
		//---------------------- LIGNE 3
		if(p.x<57 && p.x>33 && p.y<202 && p.y>178) return new Point(0,6);
		if(p.x<82 && p.x>58 && p.y<202 && p.y>178) return new Point(1,6);
		if(p.x<106 && p.x>82 && p.y<202 && p.y>178) return new Point(2,6);
		if(p.x<130 && p.x>106 && p.y<202 && p.y>178) return new Point(3,6);
		if(p.x<154 && p.x>130 && p.y<202 && p.y>178) return new Point(4,6);
		if(p.x<179 && p.x>155 && p.y<202 && p.y>178) return new Point(5,6);
		if(p.x<202 && p.x>178 && p.y<202 && p.y>178) return new Point(6,6);
		if(p.x<227 && p.x>203 && p.y<202 && p.y>178) return new Point(7,6);
		if(p.x<252 && p.x>228 && p.y<202 && p.y>178) return new Point(8,6);
		
		//---------------------- LIGNE 2
		if(p.x<57 && p.x>33 && p.y<227 && p.y>203) return new Point(0,7);
		if(p.x<82 && p.x>58 && p.y<227 && p.y>203) return new Point(1,7);
		if(p.x<106 && p.x>82 && p.y<227 && p.y>203) return new Point(2,7);
		if(p.x<130 && p.x>106 && p.y<227 && p.y>203) return new Point(3,7);
		if(p.x<154 && p.x>130 && p.y<227 && p.y>203) return new Point(4,7);
		if(p.x<179 && p.x>155 && p.y<227 && p.y>203) return new Point(5,7);
		if(p.x<202 && p.x>178 && p.y<227 && p.y>203) return new Point(6,7);
		if(p.x<227 && p.x>203 && p.y<227 && p.y>203) return new Point(7,7);
		if(p.x<252 && p.x>228 && p.y<227 && p.y>203) return new Point(8,7);
		
		//---------------------- LIGNE 1
		if(p.x<57 && p.x>33 && p.y<252 && p.y>228) return new Point(0,8);
		if(p.x<82 && p.x>58 && p.y<252 && p.y>228) return new Point(1,8);
		if(p.x<106 && p.x>82 && p.y<252 && p.y>228) return new Point(2,8);
		if(p.x<130 && p.x>106 && p.y<252 && p.y>228) return new Point(3,8);
		if(p.x<154 && p.x>130 && p.y<252 && p.y>228) return new Point(4,8);
		if(p.x<179 && p.x>155 && p.y<252 && p.y>228) return new Point(5,8);
		if(p.x<202 && p.x>178 && p.y<252 && p.y>228) return new Point(6,8);
		if(p.x<227 && p.x>203 && p.y<252 && p.y>228) return new Point(7,8);
		if(p.x<252 && p.x>228 && p.y<252 && p.y>228) return new Point(8,8);
		}
		return null;
			
	}
}

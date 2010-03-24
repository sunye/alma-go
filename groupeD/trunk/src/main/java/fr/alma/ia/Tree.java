package fr.alma.ia;

import java.util.LinkedList;

import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Goban;

public class Tree {
	private Goban goban_;
	private Tree parent_;
	private LinkedList<Tree> children_;

	/**
	 * Construct a node with a Goban
	 * @param goban 
	 */
	public Tree(Goban goban){
		goban_ = goban;
		parent_=null;
		children_ = new LinkedList<Tree>();
	}
	
	/**
	 * Construct a node with a Goban and a parent
	 * @param goban
	 * @param father the parent node
	 */
	public Tree(Goban goban, Tree father){
		goban_ = goban;
		parent_= father;
		children_ = new LinkedList<Tree>();
	}
	
	/**
	 * Add a child in last position to the current tree-node
	 * @param child the node to add as a child
	 */
	public void addChild(Tree child){
		parent_=this;
		children_.add(child);
	}
	
	/**
	 * Test if the tree-node is a leaf
	 * @return true if the node is a leaf
	 */
	public boolean isLeaf(){
		return getChildren().size()==0;
	}
		
	/**
	 * Returns the parent of the node
	 * @return a node or "null"
	 */
	public Tree getParent(){
		return parent_;
	}
	
	/**
	 * Returns the Goban associated with the node
	 * @return a Goban
	 */
	public Goban getGoban(){
		return goban_;
	}
	
	/**
	 * Return the children of the node
	 * @return a LinkedList<Tree>
	 */
	public LinkedList<Tree> getChildren(){
		return children_;
	}
	
	/**
	 * Generate all the possible movements from the current Goban and add them as children
	 * @param stone the color to be played
	 */
	public void generateChildren(Stone stone){
		for(Goban goban : getGoban().computeMoves(stone)){
			addChild(new Tree(goban));
		}
	}
	
	/**
	 * Return the depth of the node
	 * @return an integer>=0
	 */
	public int getDepht(){
		Tree tree = new Tree(null, this.getParent());
		int depth=0;
		while(tree.getParent()!=null){
			depth++;
		}
		return depth;
	}
}

package myPack;

import java.awt.*;
import java.awt.event.*;
import java.applet.*; //import javax.swing.*;
import java.util.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class Applet2Swing extends Applet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isStandalone = false;
	Random aleatoire;
	Label label1 = new Label();

	Button button1 = new Button();
	Button button2 = new Button();
	Button button3 = new Button();
	Button button4 = new Button();
	Button button5 = new Button();
	Button button6 = new Button();
	Button button7 = new Button();
	Button button8 = new Button();
	Button button9 = new Button();
	Button button10 = new Button();
	Button button11 = new Button();
	Button button12 = new Button();
	Button button13 = new Button();
	Button button14 = new Button();
	Button button15 = new Button();
	Button button16 = new Button();
	Button button17 = new Button();
	Button button18 = new Button();
	Button button19 = new Button();
	Button button29 = new Button();
	Button button30 = new Button();
	Button button31 = new Button();
	Button button32 = new Button();
	Button button33 = new Button();
	Button button34 = new Button();
	Button button35 = new Button();
	Button button36 = new Button();
	Button button20 = new Button();
	Button button37 = new Button();
	Button button38 = new Button();
	Button button48 = new Button();
	Button button21 = new Button();
	Button button49 = new Button();
	Button button39 = new Button();
	Button button50 = new Button();
	Button button22 = new Button();
	Button button23 = new Button();
	Button button40 = new Button();
	Button button41 = new Button();
	Button button24 = new Button();
	Button button51 = new Button();
	Button button42 = new Button();
	Button button25 = new Button();
	Button button52 = new Button();
	Button button43 = new Button();
	Button button44 = new Button();
	Button button26 = new Button();
	Button button53 = new Button();
	Button button45 = new Button();
	Button button46 = new Button();
	Button button27 = new Button();
	Button button54 = new Button();
	Button button28 = new Button();
	Button button47 = new Button();
	Button button55 = new Button();
	Button button56 = new Button();
	Button button57 = new Button();
	Button button58 = new Button();
	Button button59 = new Button();
	Button button60 = new Button();
	Button button61 = new Button();
	Button button62 = new Button();
	Button button63 = new Button();
	Button button64 = new Button();
	Button button65 = new Button();
	Button button66 = new Button();
	Button button67 = new Button();
	Button button68 = new Button();
	Button button69 = new Button();
	Button button70 = new Button();
	Button button71 = new Button();
	Button button72 = new Button();
	Button button73 = new Button();
	Button button74 = new Button();
	Button button75 = new Button();
	Button button76 = new Button();
	Button button77 = new Button();
	Button button78 = new Button();
	Button button79 = new Button();
	Button button80 = new Button();
	Button button81 = new Button();
	Button button82 = new Button();
	Button button83 = new Button();
	Button button84 = new Button();
	Label label2 = new Label();
	Label label3 = new Label();
	Juego jeu = new Juego();

	// Get a parameter value
	public String getParameter(String key, String def) {
		return isStandalone ? System.getProperty(key, def)
				: (getParameter(key) != null ? getParameter(key) : def);
	}

	// Construct the applet
	public Applet2Swing() {
	}

	// Initialize the applet
	public void init() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Component initialization
	private void jbInit() throws Exception {
		aleatoire = new Random();
		label1.setAlignment(Label.CENTER);
		label1.setFont(new java.awt.Font("Dialog", 1, 16));
		label1.setForeground(Color.red);
		label1.setText("Su Doku");
		label1.setBounds(new Rectangle(175, 20, 148, 15));
		this.setLayout(null);
		
	/*	for(int a=1;a<=81;a++){
			buttonNo[a].setBounds(new Rectangle(20, 50, 45, 30));
			button1.addKeyListener(new Applet1_button_keyAdapter[a](this));
		}*/
		button1.setBounds(new Rectangle(20, 50, 45, 30));
		button1.addKeyListener(new Applet1_button1_keyAdapter(this));
		button2.setBounds(new Rectangle(70, 50, 45, 30));
		button2.addKeyListener(new Applet1_button2_keyAdapter(this));
		button3.setBounds(new Rectangle(120, 50, 45, 30));
		button3.addKeyListener(new Applet1_button3_keyAdapter(this));
		button4.setBounds(new Rectangle(180, 50, 45, 30));
		button4.addKeyListener(new Applet1_button4_keyAdapter(this));
		button5.setBounds(new Rectangle(230, 50, 45, 30));
		button5.addKeyListener(new Applet1_button5_keyAdapter(this));
		button6.setBounds(new Rectangle(280, 50, 45, 30));
		button6.addKeyListener(new Applet1_button6_keyAdapter(this));
		button7.setBounds(new Rectangle(340, 50, 45, 30));
		button7.addKeyListener(new Applet1_button7_keyAdapter(this));
		button8.setBounds(new Rectangle(390, 50, 45, 30));
		button8.addKeyListener(new Applet1_button8_keyAdapter(this));
		button9.setBounds(new Rectangle(440, 50, 45, 30));
		button9.addKeyListener(new Applet1_button9_keyAdapter(this));

		button10.setBounds(new Rectangle(20, 85, 45, 30));
		button10.addKeyListener(new Applet1_button10_keyAdapter(this));
		button11.setBounds(new Rectangle(70, 85, 45, 30));
		button11.addKeyListener(new Applet1_button11_keyAdapter(this));
		button12.setBounds(new Rectangle(120, 85, 45, 30));
		button12.addKeyListener(new Applet1_button12_keyAdapter(this));
		button13.setBounds(new Rectangle(180, 85, 45, 30));
		button13.addKeyListener(new Applet1_button13_keyAdapter(this));
		button14.setBounds(new Rectangle(230, 85, 45, 30));
		button14.addKeyListener(new Applet1_button14_keyAdapter(this));
		button15.setBounds(new Rectangle(280, 85, 45, 30));
		button15.addKeyListener(new Applet1_button15_keyAdapter(this));
		button16.setBounds(new Rectangle(340, 85, 45, 30));
		button16.addKeyListener(new Applet1_button16_keyAdapter(this));
		button17.setBounds(new Rectangle(390, 85, 45, 30));
		button17.addKeyListener(new Applet1_button17_keyAdapter(this));
		button18.setBounds(new Rectangle(440, 85, 45, 30));
		button18.addKeyListener(new Applet1_button18_keyAdapter(this));

		button19.setBounds(new Rectangle(20, 120, 45, 30));
		button19.addKeyListener(new Applet1_button19_keyAdapter(this));
		button20.setBounds(new Rectangle(70, 120, 45, 30));
		button20.addKeyListener(new Applet1_button20_keyAdapter(this));
		button21.setBounds(new Rectangle(120, 120, 45, 30));
		button21.addKeyListener(new Applet1_button21_keyAdapter(this));
		button22.setBounds(new Rectangle(180, 120, 45, 30));
		button22.addKeyListener(new Applet1_button22_keyAdapter(this));
		button23.setBounds(new Rectangle(230, 120, 45, 30));
		button23.addKeyListener(new Applet1_button23_keyAdapter(this));
		button24.setBounds(new Rectangle(280, 120, 45, 30));
		button24.addKeyListener(new Applet1_button24_keyAdapter(this));
		button25.setBounds(new Rectangle(340, 120, 45, 30));
		button25.addKeyListener(new Applet1_button25_keyAdapter(this));
		button26.setBounds(new Rectangle(390, 120, 45, 30));
		button26.addKeyListener(new Applet1_button26_keyAdapter(this));
		button27.setBounds(new Rectangle(440, 120, 45, 30));
		button27.addKeyListener(new Applet1_button27_keyAdapter(this));

		button28.setBounds(new Rectangle(20, 165, 45, 30));
		button28.addKeyListener(new Applet1_button28_keyAdapter(this));

		button29.setBounds(new Rectangle(70, 165, 45, 30));
		button29.addKeyListener(new Applet1_button29_keyAdapter(this));
		button30.setBounds(new Rectangle(120, 165, 45, 30));
		button30.addKeyListener(new Applet1_button30_keyAdapter(this));
		button31.setBounds(new Rectangle(180, 165, 45, 30));
		button31.addKeyListener(new Applet1_button31_keyAdapter(this));
		button32.setBounds(new Rectangle(230, 165, 45, 30));
		button32.addKeyListener(new Applet1_button32_keyAdapter(this));
		button33.setBounds(new Rectangle(280, 165, 45, 30));
		button33.addKeyListener(new Applet1_button33_keyAdapter(this));
		button34.setBounds(new Rectangle(340, 165, 45, 30));
		button34.addKeyListener(new Applet1_button34_keyAdapter(this));
		button35.setBounds(new Rectangle(390, 165, 45, 30));
		button35.addKeyListener(new Applet1_button35_keyAdapter(this));
		button36.setBounds(new Rectangle(440, 165, 45, 30));
		button36.addKeyListener(new Applet1_button36_keyAdapter(this));

		button37.setBounds(new Rectangle(20, 200, 45, 30));
		button37.addKeyListener(new Applet1_button37_keyAdapter(this));

		button38.setBounds(new Rectangle(70, 200, 45, 30));
		button38.addKeyListener(new Applet1_button38_keyAdapter(this));

		button39.setBounds(new Rectangle(120, 200, 45, 30));
		button39.addKeyListener(new Applet1_button39_keyAdapter(this));

		button40.setBounds(new Rectangle(180, 200, 45, 30));
		button40.addKeyListener(new Applet1_button40_keyAdapter(this));

		button41.setBounds(new Rectangle(230, 200, 45, 30));
		button41.addKeyListener(new Applet1_button41_keyAdapter(this));

		button42.setBounds(new Rectangle(280, 200, 45, 30));
		button42.addKeyListener(new Applet1_button42_keyAdapter(this));

		button43.setBounds(new Rectangle(340, 200, 45, 30));
		button43.addKeyListener(new Applet1_button43_keyAdapter(this));

		button44.setBounds(new Rectangle(390, 200, 45, 30));
		button44.addKeyListener(new Applet1_button44_keyAdapter(this));

		button45.setBounds(new Rectangle(440, 200, 45, 30));
		button45.addKeyListener(new Applet1_button45_keyAdapter(this));

		button46.setBounds(new Rectangle(20, 235, 45, 30));
		button46.addKeyListener(new Applet1_button46_keyAdapter(this));

		button47.setBounds(new Rectangle(70, 235, 45, 30));
		button47.addKeyListener(new Applet1_button47_keyAdapter(this));

		button48.setBounds(new Rectangle(120, 235, 45, 30));
		button48.addKeyListener(new Applet1_button48_keyAdapter(this));

		button49.setBounds(new Rectangle(180, 235, 45, 30));
		button49.addKeyListener(new Applet1_button49_keyAdapter(this));

		button50.setBounds(new Rectangle(230, 235, 45, 30));
		button50.addKeyListener(new Applet1_button50_keyAdapter(this));

		button51.setBounds(new Rectangle(280, 235, 45, 30));
		button51.addKeyListener(new Applet1_button51_keyAdapter(this));

		button52.setBounds(new Rectangle(340, 235, 45, 30));
		button52.addKeyListener(new Applet1_button52_keyAdapter(this));

		button53.setBounds(new Rectangle(390, 235, 45, 30));
		button53.addKeyListener(new Applet1_button53_keyAdapter(this));

		button54.setBounds(new Rectangle(440, 235, 45, 30));
		button54.addKeyListener(new Applet1_button54_keyAdapter(this));

		button55.setBounds(new Rectangle(20, 280, 45, 30));
		button55.addKeyListener(new Applet1_button55_keyAdapter(this));

		button56.setBounds(new Rectangle(70, 280, 45, 30));
		button56.addKeyListener(new Applet1_button56_keyAdapter(this));

		button57.setBounds(new Rectangle(120, 280, 45, 30));
		button57.addKeyListener(new Applet1_button57_keyAdapter(this));

		button58.setBounds(new Rectangle(180, 280, 45, 30));
		button58.addKeyListener(new Applet1_button58_keyAdapter(this));

		button59.setBounds(new Rectangle(230, 280, 45, 30));
		button59.addKeyListener(new Applet1_button59_keyAdapter(this));

		button60.setBounds(new Rectangle(280, 280, 45, 30));
		button60.addKeyListener(new Applet1_button60_keyAdapter(this));

		button61.setBounds(new Rectangle(340, 280, 45, 30));
		button61.addKeyListener(new Applet1_button61_keyAdapter(this));

		button62.setBounds(new Rectangle(390, 280, 45, 30));

		button63.setBounds(new Rectangle(440, 280, 45, 30));

		button64.setBounds(new Rectangle(20, 315, 45, 30));

		button65.setBounds(new Rectangle(70, 315, 45, 30));

		button66.setBounds(new Rectangle(120, 315, 45, 30));

		button67.setBounds(new Rectangle(180, 315, 45, 30));

		button68.setBounds(new Rectangle(230, 315, 45, 30));

		button69.setBounds(new Rectangle(280, 315, 45, 30));

		button70.setBounds(new Rectangle(340, 315, 45, 30));
		button70.addKeyListener(new Applet1_button70_keyAdapter(this));

		button71.setBounds(new Rectangle(390, 315, 45, 30));

		button72.setBounds(new Rectangle(440, 315, 45, 30));

		button73.setBounds(new Rectangle(20, 350, 45, 30));

		button74.setBounds(new Rectangle(70, 350, 45, 30));

		button75.setBounds(new Rectangle(120, 350, 45, 30));

		button76.setBounds(new Rectangle(180, 350, 45, 30));

		button77.setBounds(new Rectangle(230, 350, 45, 30));

		button78.setBounds(new Rectangle(280, 350, 45, 30));

		button79.setBounds(new Rectangle(340, 350, 45, 30));

		button80.setBounds(new Rectangle(390, 350, 45, 30));

		button81.setBounds(new Rectangle(440, 350, 45, 30));
		button62.addKeyListener(new Applet1_button62_keyAdapter(this));
		button63.addKeyListener(new Applet1_button63_keyAdapter(this));
		button64.addKeyListener(new Applet1_button64_keyAdapter(this));
		button65.addKeyListener(new Applet1_button65_keyAdapter(this));
		button66.addKeyListener(new Applet1_button66_keyAdapter(this));
		button67.addKeyListener(new Applet1_button67_keyAdapter(this));
		button68.addKeyListener(new Applet1_button68_keyAdapter(this));
		button69.addKeyListener(new Applet1_button69_keyAdapter(this));

		button71.addKeyListener(new Applet1_button71_keyAdapter(this));
		button72.addKeyListener(new Applet1_button72_keyAdapter(this));
		button73.addKeyListener(new Applet1_button73_keyAdapter(this));
		button74.addKeyListener(new Applet1_button74_keyAdapter(this));
		button75.addKeyListener(new Applet1_button75_keyAdapter(this));
		button76.addKeyListener(new Applet1_button76_keyAdapter(this));
		button77.addKeyListener(new Applet1_button77_keyAdapter(this));
		button78.addKeyListener(new Applet1_button78_keyAdapter(this));
		button79.addKeyListener(new Applet1_button79_keyAdapter(this));
		button80.addKeyListener(new Applet1_button80_keyAdapter(this));
		button81.addKeyListener(new Applet1_button81_keyAdapter(this));
		
		button1.setActionCommand("");		
		button1.setLocale(java.util.Locale.getDefault());
		
		button1.setLabel("");
		button1.setForeground(Color.black);
		button1.setFont(new java.awt.Font("Dialog", 1, 16));
		
		button2.setLabel("");
		button2.setForeground(Color.black);
		button2.setFont(new java.awt.Font("Dialog", 1, 16));

		button3.setLabel("");
		button3.setForeground(Color.black);
		button3.setFont(new java.awt.Font("Dialog", 1, 16));

		button4.setLabel("");
		button4.setForeground(Color.black);
		button4.setFont(new java.awt.Font("Dialog", 1, 16));

		button5.setLabel("");
		button5.setForeground(Color.black);
		button5.setFont(new java.awt.Font("Dialog", 1, 16));

		button6.setLabel("");
		button6.setForeground(Color.black);
		button6.setFont(new java.awt.Font("Dialog", 1, 16));

		button7.setLabel("");
		button7.setForeground(Color.black);
		button7.setFont(new java.awt.Font("Dialog", 1, 16));

		button8.setLabel("");
		button8.setForeground(Color.black);
		button8.setFont(new java.awt.Font("Dialog", 1, 16));

		button9.setLabel("");
		button9.setForeground(Color.black);
		button9.setFont(new java.awt.Font("Dialog", 1, 16));

		button10.setLabel("");
		button10.setForeground(Color.black);
		button10.setFont(new java.awt.Font("Dialog", 1, 16));
		button11.setFont(new java.awt.Font("Dialog", 1, 16));
		button11.setForeground(Color.black);
		button11.setLabel("");

		button12.setFont(new java.awt.Font("Dialog", 1, 16));
		button12.setForeground(Color.black);
		button12.setLabel("");

		button13.setFont(new java.awt.Font("Dialog", 1, 16));
		button13.setForeground(Color.black);
		button13.setLabel("");

		button14.setFont(new java.awt.Font("Dialog", 1, 16));
		button14.setForeground(Color.black);
		button14.setLabel("");

		button15.setFont(new java.awt.Font("Dialog", 1, 16));
		button15.setForeground(Color.black);
		button15.setLabel("");

		button16.setFont(new java.awt.Font("Dialog", 1, 16));
		button16.setForeground(Color.black);
		button16.setLabel("");

		button17.setFont(new java.awt.Font("Dialog", 1, 16));
		button17.setForeground(Color.black);
		button17.setLabel("");

		button18.setFont(new java.awt.Font("Dialog", 1, 16));
		button18.setForeground(Color.black);
		button18.setLabel("");

		button19.setLabel("");
		button19.setForeground(Color.black);
		button19.setFont(new java.awt.Font("Dialog", 1, 16));
		button29.setFont(new java.awt.Font("Dialog", 1, 16));
		button29.setForeground(Color.black);
		button29.setLabel("");

		button30.setLabel("");
		button30.setForeground(Color.black);
		button30.setFont(new java.awt.Font("Dialog", 1, 16));

		button31.setLabel("");
		button31.setForeground(Color.black);
		button31.setFont(new java.awt.Font("Dialog", 1, 16));

		button32.setLabel("");
		button32.setForeground(Color.black);
		button32.setFont(new java.awt.Font("Dialog", 1, 16));

		button33.setLabel("");
		button33.setForeground(Color.black);
		button33.setFont(new java.awt.Font("Dialog", 1, 16));

		button34.setLabel("");
		button34.setForeground(Color.black);
		button34.setFont(new java.awt.Font("Dialog", 1, 16));

		button35.setLabel("");
		button35.setForeground(Color.black);
		button35.setFont(new java.awt.Font("Dialog", 1, 16));

		button36.setLabel("");
		button36.setForeground(Color.black);
		button36.setFont(new java.awt.Font("Dialog", 1, 16));

		button20.setLabel("");
		button20.setForeground(Color.black);
		button20.setFont(new java.awt.Font("Dialog", 1, 16));
		button37.setFont(new java.awt.Font("Dialog", 1, 16));
		button37.setForeground(Color.black);
		button37.setLabel("");

		button38.setLabel("");
		button38.setForeground(Color.black);
		button38.setFont(new java.awt.Font("Dialog", 1, 16));

		button48.setLabel("");
		button48.setForeground(Color.black);
		button48.setFont(new java.awt.Font("Dialog", 1, 16));
		button21.setFont(new java.awt.Font("Dialog", 1, 16));
		button21.setForeground(Color.black);
		button21.setLabel("");

		button49.setFont(new java.awt.Font("Dialog", 1, 16));
		button49.setForeground(Color.black);
		button49.setLabel("");

		button39.setFont(new java.awt.Font("Dialog", 1, 16));
		button39.setForeground(Color.black);
		button39.setLabel("");

		button50.setFont(new java.awt.Font("Dialog", 1, 16));
		button50.setForeground(Color.black);
		button50.setLabel("");

		button22.setFont(new java.awt.Font("Dialog", 1, 16));
		button22.setForeground(Color.black);
		button22.setLabel("");

		button23.setFont(new java.awt.Font("Dialog", 1, 16));
		button23.setForeground(Color.black);
		button23.setLabel("");

		button40.setLabel("");
		button40.setForeground(Color.black);
		button40.setFont(new java.awt.Font("Dialog", 1, 16));
		button41.setFont(new java.awt.Font("Dialog", 1, 16));
		button41.setForeground(Color.black);
		button41.setLabel("");

		button24.setFont(new java.awt.Font("Dialog", 1, 16));
		button24.setForeground(Color.black);
		button24.setLabel("");

		button51.setFont(new java.awt.Font("Dialog", 1, 16));
		button51.setForeground(Color.black);
		button51.setLabel("");

		button42.setLabel("");
		button42.setForeground(Color.black);
		button42.setFont(new java.awt.Font("Dialog", 1, 16));
		button25.setFont(new java.awt.Font("Dialog", 1, 16));
		button25.setForeground(Color.black);
		button25.setLabel("");

		button52.setFont(new java.awt.Font("Dialog", 1, 16));
		button52.setForeground(Color.black);
		button52.setLabel("");

		button43.setLabel("");
		button43.setForeground(Color.black);
		button43.setFont(new java.awt.Font("Dialog", 1, 16));

		button44.setLabel("");
		button44.setForeground(Color.black);
		button44.setFont(new java.awt.Font("Dialog", 1, 16));
		button26.setFont(new java.awt.Font("Dialog", 1, 16));
		button26.setForeground(Color.black);
		button26.setLabel("");

		button53.setFont(new java.awt.Font("Dialog", 1, 16));
		button53.setForeground(Color.black);
		button53.setLabel("");

		button45.setLabel("");
		button45.setForeground(Color.black);
		button45.setFont(new java.awt.Font("Dialog", 1, 16));

		button46.setLabel("");
		button46.setForeground(Color.black);
		button46.setFont(new java.awt.Font("Dialog", 1, 16));
		button27.setFont(new java.awt.Font("Dialog", 1, 16));
		button27.setForeground(Color.black);
		button27.setLabel("");

		button54.setFont(new java.awt.Font("Dialog", 1, 16));
		button54.setForeground(Color.black);
		button54.setLabel("");

		button28.setFont(new java.awt.Font("Dialog", 1, 16));
		button28.setForeground(Color.black);
		button28.setLabel("");

		button47.setLabel("");
		button47.setForeground(Color.black);
		button47.setFont(new java.awt.Font("Dialog", 1, 16));

		button55.setFont(new java.awt.Font("Dialog", 1, 16));
		button55.setForeground(Color.black);
		button55.setLabel("");

		button56.setFont(new java.awt.Font("Dialog", 1, 16));
		button56.setForeground(Color.black);
		button56.setLabel("");

		button57.setFont(new java.awt.Font("Dialog", 1, 16));
		button57.setForeground(Color.black);
		button57.setLabel("");

		button58.setFont(new java.awt.Font("Dialog", 1, 16));
		button58.setForeground(Color.black);
		button58.setLabel("");

		button59.setFont(new java.awt.Font("Dialog", 1, 16));
		button59.setForeground(Color.black);
		button59.setLabel("");

		button60.setFont(new java.awt.Font("Dialog", 1, 16));
		button60.setForeground(Color.black);
		button60.setLabel("");

		button61.setFont(new java.awt.Font("Dialog", 1, 16));
		button61.setForeground(Color.black);
		button61.setLabel("");

		button62.setFont(new java.awt.Font("Dialog", 1, 16));
		button62.setForeground(Color.black);
		button62.setLabel("");

		button63.setFont(new java.awt.Font("Dialog", 1, 16));
		button63.setForeground(Color.black);
		button63.setLabel("");

		button64.setFont(new java.awt.Font("Dialog", 1, 16));
		button64.setForeground(Color.black);
		button64.setLabel("");

		button65.setFont(new java.awt.Font("Dialog", 1, 16));
		button65.setForeground(Color.black);
		button65.setLabel("");

		button66.setFont(new java.awt.Font("Dialog", 1, 16));
		button66.setForeground(Color.black);
		button66.setLabel("");

		button67.setFont(new java.awt.Font("Dialog", 1, 16));
		button67.setForeground(Color.black);
		button67.setLabel("");

		button68.setFont(new java.awt.Font("Dialog", 1, 16));
		button68.setForeground(Color.black);
		button68.setLabel("");

		button69.setFont(new java.awt.Font("Dialog", 1, 16));
		button69.setForeground(Color.black);
		button69.setLabel("");

		button70.setFont(new java.awt.Font("Dialog", 1, 16));
		button70.setForeground(Color.black);
		button70.setLabel("");

		button71.setFont(new java.awt.Font("Dialog", 1, 16));
		button71.setForeground(Color.black);
		button71.setLabel("");

		button72.setFont(new java.awt.Font("Dialog", 1, 16));
		button72.setForeground(Color.black);
		button72.setLabel("");

		button73.setFont(new java.awt.Font("Dialog", 1, 16));
		button73.setForeground(Color.black);
		button73.setLabel("");

		button74.setFont(new java.awt.Font("Dialog", 1, 16));
		button74.setForeground(Color.black);
		button74.setLabel("");

		button75.setFont(new java.awt.Font("Dialog", 1, 16));
		button75.setForeground(Color.black);
		button75.setLabel("");

		button76.setFont(new java.awt.Font("Dialog", 1, 16));
		button76.setForeground(Color.black);
		button76.setLabel("");

		button77.setFont(new java.awt.Font("Dialog", 1, 16));
		button77.setForeground(Color.black);
		button77.setLabel("");

		button78.setFont(new java.awt.Font("Dialog", 1, 16));
		button78.setForeground(Color.black);
		button78.setLabel("");

		button79.setFont(new java.awt.Font("Dialog", 1, 16));
		button79.setForeground(Color.black);
		button79.setLabel("");

		button80.setFont(new java.awt.Font("Dialog", 1, 16));
		button80.setForeground(Color.black);
		button80.setLabel("");

		button81.setFont(new java.awt.Font("Dialog", 1, 16));
		button81.setForeground(Color.black);
		button81.setLabel("");

		button82.setFont(new java.awt.Font("Dialog", 1, 11));
		button82.setForeground(Color.red);
		button82.setLabel("New");
		button82.setBounds(new Rectangle(19, 390, 88, 26));
		button82.addMouseListener(new Applet1_button82_mouseAdapter(this));

		button83.setFont(new java.awt.Font("Dialog", 1, 11));
		button83.setForeground(Color.red);
		button83.setLabel("Solver");
		button83.setBounds(new Rectangle(117, 390, 88, 26));
		button83.addMouseListener(new Applet1_button83_mouseAdapter(this));
		button84.setFont(new java.awt.Font("Dialog", 1, 11));
		button84.setForeground(Color.red);
		button84.setLabel("Atras");
		button84.setBounds(new Rectangle(397, 390, 88, 26));
		button84.addMouseListener(new Applet1_button84_mouseAdapter(this));
		label2.setForeground(Color.blue);
		label2.setLocale(java.util.Locale.getDefault());
		label2.setText("Peter MOUEZA 2010 - petermoueza@gmail.com");
		label2.setBounds(new Rectangle(18, 424, 469, 13));
		label3.setForeground(Color.blue);
		label3.setText("http://www.geocities.com/jacinruiz/");
		label3.setBounds(new Rectangle(18, 442, 467, 12));
		this.setVisible(true);
		this.add(button1, null);
		this.add(button81, null);
		this.add(button80, null);
		this.add(button79, null);
		this.add(button78, null);
		this.add(button77, null);
		this.add(button76, null);
		this.add(button75, null);
		this.add(button74, null);
		this.add(button73, null);
		this.add(button72, null);
		this.add(button71, null);
		this.add(button70, null);
		this.add(button69, null);
		this.add(button68, null);
		this.add(button67, null);
		this.add(button66, null);
		this.add(button65, null);
		this.add(button64, null);
		this.add(button11, null);
		this.add(button10, null);
		this.add(button17, null);
		this.add(button12, null);
		this.add(button13, null);
		this.add(button14, null);
		this.add(button15, null);
		this.add(button16, null);
		this.add(button18, null);
		this.add(button19, null);
		this.add(button20, null);
		this.add(button21, null);
		this.add(button22, null);
		this.add(button23, null);
		this.add(button24, null);
		this.add(button25, null);
		this.add(button26, null);
		this.add(button27, null);
		this.add(button28, null);
		this.add(button29, null);
		this.add(button31, null);
		this.add(button32, null);
		this.add(button33, null);
		this.add(button34, null);
		this.add(button35, null);
		this.add(button30, null);
		this.add(button36, null);
		this.add(button37, null);
		this.add(button38, null);
		this.add(button39, null);
		this.add(button40, null);
		this.add(button41, null);
		this.add(button42, null);
		this.add(button43, null);
		this.add(button44, null);
		this.add(button45, null);
		this.add(button46, null);
		this.add(button47, null);
		this.add(button48, null);
		this.add(button49, null);
		this.add(button50, null);
		this.add(button51, null);
		this.add(button52, null);
		this.add(button53, null);
		this.add(button54, null);
		this.add(button55, null);
		this.add(button56, null);
		this.add(button57, null);
		this.add(button58, null);
		this.add(button59, null);
		this.add(button60, null);
		this.add(button61, null);
		this.add(button62, null);
		this.add(button63, null);
		this.add(button9, null);
		this.add(button8, null);
		this.add(button7, null);
		this.add(button6, null);
		this.add(button5, null);
		this.add(button4, null);
		this.add(button3, null);
		this.add(button2, null);
		this.add(button82, null);
		this.add(button83, null);
		this.add(button84, null);
		this.add(label2, null);
		this.add(label1, null);
		this.add(label3, null);
		nouvJeu(0);
	}

	void nouvJeu(int fijas) {
		boolean acti = false;
		int row = 0;
		int columna = 0;
		jeu.inicio();
		do {
			jeu.tableauFix.init();
			for (int x = 1; x <= jeu.dimension; x++) {
				row = 0;
				columna = aleatoire.nextInt(9);
				jeu.tableauFix.putValor(row, columna, x);
			}
			acti = jeu.resolver();
			// acti=true;
			while (jeu.tableau.numbZeros() < 81 - fijas) {
				row = aleatoire.nextInt(9);
				columna = aleatoire.nextInt(9);
				jeu.tableau.putValor(row, columna, 0);
			}
		} while (!acti);
		jeu.tableauFix = jeu.copyTablero(jeu.tableau);
		llenaRejilla(jeu.tableauFix);
		jeu.tableau = jeu.copyTablero(jeu.tableauFix);
	}

	void llenaRejilla(Tablero t) {
		String v;
		for (int a = 1; a <= 81; a++) {
			Button[] buttonNo = new Button[81];
			if (jeu.tableauFix.getValor((a / 10), (a % 10)) > 0)
				buttonNo[a].setForeground(Color.black);
		}
		if (jeu.tableauFix.getValor(0, 0) > 0)
			button1.setForeground(Color.black);
		if (jeu.tableauFix.getValor(0, 1) > 0)
			button2.setForeground(Color.black);
		if (jeu.tableauFix.getValor(0, 2) > 0)
			button3.setForeground(Color.black);
		if (jeu.tableauFix.getValor(0, 3) > 0)
			button4.setForeground(Color.black);
		if (jeu.tableauFix.getValor(0, 4) > 0)
			button5.setForeground(Color.black);
		if (jeu.tableauFix.getValor(0, 5) > 0)
			button6.setForeground(Color.black);
		if (jeu.tableauFix.getValor(0, 6) > 0)
			button7.setForeground(Color.black);
		if (jeu.tableauFix.getValor(0, 7) > 0)
			button8.setForeground(Color.black);
		if (jeu.tableauFix.getValor(0, 8) > 0)
			button9.setForeground(Color.black);
		if (jeu.tableauFix.getValor(1, 0) > 0)
			button10.setForeground(Color.black);
		if (jeu.tableauFix.getValor(1, 1) > 0)
			button11.setForeground(Color.black);
		if (jeu.tableauFix.getValor(1, 2) > 0)
			button12.setForeground(Color.black);
		if (jeu.tableauFix.getValor(1, 3) > 0)
			button13.setForeground(Color.black);
		if (jeu.tableauFix.getValor(1, 4) > 0)
			button14.setForeground(Color.black);
		if (jeu.tableauFix.getValor(1, 5) > 0)
			button15.setForeground(Color.black);
		if (jeu.tableauFix.getValor(1, 6) > 0)
			button16.setForeground(Color.black);
		if (jeu.tableauFix.getValor(1, 7) > 0)
			button17.setForeground(Color.black);
		if (jeu.tableauFix.getValor(1, 8) > 0)
			button18.setForeground(Color.black);
		if (jeu.tableauFix.getValor(2, 0) > 0)
			button19.setForeground(Color.black);
		if (jeu.tableauFix.getValor(2, 1) > 0)
			button20.setForeground(Color.black);
		if (jeu.tableauFix.getValor(2, 2) > 0)
			button21.setForeground(Color.black);
		if (jeu.tableauFix.getValor(2, 3) > 0)
			button22.setForeground(Color.black);
		if (jeu.tableauFix.getValor(2, 4) > 0)
			button23.setForeground(Color.black);
		if (jeu.tableauFix.getValor(2, 5) > 0)
			button24.setForeground(Color.black);
		if (jeu.tableauFix.getValor(2, 6) > 0)
			button25.setForeground(Color.black);
		if (jeu.tableauFix.getValor(2, 7) > 0)
			button26.setForeground(Color.black);
		if (jeu.tableauFix.getValor(2, 8) > 0)
			button27.setForeground(Color.black);
		if (jeu.tableauFix.getValor(3, 0) > 0)
			button28.setForeground(Color.black);
		if (jeu.tableauFix.getValor(3, 1) > 0)
			button29.setForeground(Color.black);
		if (jeu.tableauFix.getValor(3, 2) > 0)
			button30.setForeground(Color.black);
		if (jeu.tableauFix.getValor(3, 3) > 0)
			button31.setForeground(Color.black);
		if (jeu.tableauFix.getValor(3, 4) > 0)
			button32.setForeground(Color.black);
		if (jeu.tableauFix.getValor(3, 5) > 0)
			button33.setForeground(Color.black);
		if (jeu.tableauFix.getValor(3, 6) > 0)
			button34.setForeground(Color.black);
		if (jeu.tableauFix.getValor(3, 7) > 0)
			button35.setForeground(Color.black);
		if (jeu.tableauFix.getValor(3, 8) > 0)
			button36.setForeground(Color.black);
		if (jeu.tableauFix.getValor(4, 0) > 0)
			button37.setForeground(Color.black);
		if (jeu.tableauFix.getValor(4, 1) > 0)
			button38.setForeground(Color.black);
		if (jeu.tableauFix.getValor(4, 2) > 0)
			button39.setForeground(Color.black);
		if (jeu.tableauFix.getValor(4, 3) > 0)
			button40.setForeground(Color.black);
		if (jeu.tableauFix.getValor(4, 4) > 0)
			button41.setForeground(Color.black);
		if (jeu.tableauFix.getValor(4, 5) > 0)
			button42.setForeground(Color.black);
		if (jeu.tableauFix.getValor(4, 6) > 0)
			button43.setForeground(Color.black);
		if (jeu.tableauFix.getValor(4, 7) > 0)
			button44.setForeground(Color.black);
		if (jeu.tableauFix.getValor(5, 8) > 0)
			button45.setForeground(Color.black);
		if (jeu.tableauFix.getValor(5, 0) > 0)
			button46.setForeground(Color.black);
		if (jeu.tableauFix.getValor(5, 1) > 0)
			button47.setForeground(Color.black);
		if (jeu.tableauFix.getValor(5, 2) > 0)
			button48.setForeground(Color.black);
		if (jeu.tableauFix.getValor(5, 3) > 0)
			button49.setForeground(Color.black);
		if (jeu.tableauFix.getValor(5, 4) > 0)
			button50.setForeground(Color.black);
		if (jeu.tableauFix.getValor(5, 5) > 0)
			button51.setForeground(Color.black);
		if (jeu.tableauFix.getValor(5, 6) > 0)
			button52.setForeground(Color.black);
		if (jeu.tableauFix.getValor(5, 7) > 0)
			button53.setForeground(Color.black);
		if (jeu.tableauFix.getValor(5, 8) > 0)
			button54.setForeground(Color.black);
		if (jeu.tableauFix.getValor(6, 0) > 0)
			button55.setForeground(Color.black);
		if (jeu.tableauFix.getValor(6, 1) > 0)
			button56.setForeground(Color.black);
		if (jeu.tableauFix.getValor(6, 2) > 0)
			button57.setForeground(Color.black);
		if (jeu.tableauFix.getValor(6, 3) > 0)
			button58.setForeground(Color.black);
		if (jeu.tableauFix.getValor(6, 4) > 0)
			button59.setForeground(Color.black);
		if (jeu.tableauFix.getValor(6, 5) > 0)
			button60.setForeground(Color.black);
		if (jeu.tableauFix.getValor(6, 6) > 0)
			button61.setForeground(Color.black);
		if (jeu.tableauFix.getValor(6, 7) > 0)
			button62.setForeground(Color.black);
		if (jeu.tableauFix.getValor(6, 8) > 0)
			button63.setForeground(Color.black);
		if (jeu.tableauFix.getValor(7, 0) > 0)
			button64.setForeground(Color.black);
		if (jeu.tableauFix.getValor(7, 1) > 0)
			button65.setForeground(Color.black);
		if (jeu.tableauFix.getValor(7, 2) > 0)
			button66.setForeground(Color.black);
		if (jeu.tableauFix.getValor(7, 3) > 0)
			button67.setForeground(Color.black);
		if (jeu.tableauFix.getValor(7, 4) > 0)
			button68.setForeground(Color.black);
		if (jeu.tableauFix.getValor(7, 5) > 0)
			button69.setForeground(Color.black);
		if (jeu.tableauFix.getValor(7, 6) > 0)
			button70.setForeground(Color.black);
		if (jeu.tableauFix.getValor(7, 7) > 0)
			button71.setForeground(Color.black);
		if (jeu.tableauFix.getValor(7, 8) > 0)
			button72.setForeground(Color.black);
		if (jeu.tableauFix.getValor(8, 0) > 0)
			button73.setForeground(Color.black);
		if (jeu.tableauFix.getValor(8, 1) > 0)
			button74.setForeground(Color.black);
		if (jeu.tableauFix.getValor(8, 2) > 0)
			button75.setForeground(Color.black);
		if (jeu.tableauFix.getValor(8, 3) > 0)
			button76.setForeground(Color.black);
		if (jeu.tableauFix.getValor(8, 4) > 0)
			button77.setForeground(Color.black);
		if (jeu.tableauFix.getValor(8, 5) > 0)
			button78.setForeground(Color.black);
		if (jeu.tableauFix.getValor(8, 6) > 0)
			button79.setForeground(Color.black);
		if (jeu.tableauFix.getValor(8, 7) > 0)
			button80.setForeground(Color.black);
		if (jeu.tableauFix.getValor(8, 8) > 0)
			button81.setForeground(Color.black);
		v = new Integer(t.getValor(0, 0)).toString();
		if (v.equals("0"))
			button1.setLabel("");
		else
			button1.setLabel(v);
		v = new Integer(t.getValor(0, 1)).toString();
		if (v.equals("0"))
			button2.setLabel("");
		else
			button2.setLabel(v);
		v = new Integer(t.getValor(0, 2)).toString();
		if (v.equals("0"))
			button3.setLabel("");
		else
			button3.setLabel(v);
		v = new Integer(t.getValor(0, 3)).toString();
		if (v.equals("0"))
			button4.setLabel("");
		else
			button4.setLabel(v);
		v = new Integer(t.getValor(0, 4)).toString();
		if (v.equals("0"))
			button5.setLabel("");
		else
			button5.setLabel(v);
		v = new Integer(t.getValor(0, 5)).toString();
		if (v.equals("0"))
			button6.setLabel("");
		else
			button6.setLabel(v);
		v = new Integer(t.getValor(0, 6)).toString();
		if (v.equals("0"))
			button7.setLabel("");
		else
			button7.setLabel(v);
		v = new Integer(t.getValor(0, 7)).toString();
		if (v.equals("0"))
			button8.setLabel("");
		else
			button8.setLabel(v);
		v = new Integer(t.getValor(0, 8)).toString();
		if (v.equals("0"))
			button9.setLabel("");
		else
			button9.setLabel(v);
		v = new Integer(t.getValor(1, 0)).toString();
		if (v.equals("0"))
			button10.setLabel("");
		else
			button10.setLabel(v);
		v = new Integer(t.getValor(1, 1)).toString();
		if (v.equals("0"))
			button11.setLabel("");
		else
			button11.setLabel(v);
		v = new Integer(t.getValor(1, 2)).toString();
		if (v.equals("0"))
			button12.setLabel("");
		else
			button12.setLabel(v);
		v = new Integer(t.getValor(1, 3)).toString();
		if (v.equals("0"))
			button13.setLabel("");
		else
			button13.setLabel(v);
		v = new Integer(t.getValor(1, 4)).toString();
		if (v.equals("0"))
			button14.setLabel("");
		else
			button14.setLabel(v);
		v = new Integer(t.getValor(1, 5)).toString();
		if (v.equals("0"))
			button15.setLabel("");
		else
			button15.setLabel(v);
		v = new Integer(t.getValor(1, 6)).toString();
		if (v.equals("0"))
			button16.setLabel("");
		else
			button16.setLabel(v);
		v = new Integer(t.getValor(1, 7)).toString();
		if (v.equals("0"))
			button17.setLabel("");
		else
			button17.setLabel(v);
		v = new Integer(t.getValor(1, 8)).toString();
		if (v.equals("0"))
			button18.setLabel("");
		else
			button18.setLabel(v);
		v = new Integer(t.getValor(2, 0)).toString();
		if (v.equals("0"))
			button19.setLabel("");
		else
			button19.setLabel(v);
		v = new Integer(t.getValor(2, 1)).toString();
		if (v.equals("0"))
			button20.setLabel("");
		else
			button20.setLabel(v);
		v = new Integer(t.getValor(2, 2)).toString();
		if (v.equals("0"))
			button21.setLabel("");
		else
			button21.setLabel(v);
		v = new Integer(t.getValor(2, 3)).toString();
		if (v.equals("0"))
			button22.setLabel("");
		else
			button22.setLabel(v);
		v = new Integer(t.getValor(2, 4)).toString();
		if (v.equals("0"))
			button23.setLabel("");
		else
			button23.setLabel(v);
		v = new Integer(t.getValor(2, 5)).toString();
		if (v.equals("0"))
			button24.setLabel("");
		else
			button24.setLabel(v);
		v = new Integer(t.getValor(2, 6)).toString();
		if (v.equals("0"))
			button25.setLabel("");
		else
			button25.setLabel(v);
		v = new Integer(t.getValor(2, 7)).toString();
		if (v.equals("0"))
			button26.setLabel("");
		else
			button26.setLabel(v);
		v = new Integer(t.getValor(2, 8)).toString();
		if (v.equals("0"))
			button27.setLabel("");
		else
			button27.setLabel(v);
		v = new Integer(t.getValor(3, 0)).toString();
		if (v.equals("0"))
			button28.setLabel("");
		else
			button28.setLabel(v);
		v = new Integer(t.getValor(3, 1)).toString();
		if (v.equals("0"))
			button29.setLabel("");
		else
			button29.setLabel(v);
		v = new Integer(t.getValor(3, 2)).toString();
		if (v.equals("0"))
			button30.setLabel("");
		else
			button30.setLabel(v);
		v = new Integer(t.getValor(3, 3)).toString();
		if (v.equals("0"))
			button31.setLabel("");
		else
			button31.setLabel(v);
		v = new Integer(t.getValor(3, 4)).toString();
		if (v.equals("0"))
			button32.setLabel("");
		else
			button32.setLabel(v);
		v = new Integer(t.getValor(3, 5)).toString();
		if (v.equals("0"))
			button33.setLabel("");
		else
			button33.setLabel(v);
		v = new Integer(t.getValor(3, 6)).toString();
		if (v.equals("0"))
			button34.setLabel("");
		else
			button34.setLabel(v);
		v = new Integer(t.getValor(3, 7)).toString();
		if (v.equals("0"))
			button35.setLabel("");
		else
			button35.setLabel(v);
		v = new Integer(t.getValor(3, 8)).toString();
		if (v.equals("0"))
			button36.setLabel("");
		else
			button36.setLabel(v);
		v = new Integer(t.getValor(4, 0)).toString();
		if (v.equals("0"))
			button37.setLabel("");
		else
			button37.setLabel(v);
		v = new Integer(t.getValor(4, 1)).toString();
		if (v.equals("0"))
			button38.setLabel("");
		else
			button38.setLabel(v);
		v = new Integer(t.getValor(4, 2)).toString();
		if (v.equals("0"))
			button39.setLabel("");
		else
			button39.setLabel(v);
		v = new Integer(t.getValor(4, 3)).toString();
		if (v.equals("0"))
			button40.setLabel("");
		else
			button40.setLabel(v);
		v = new Integer(t.getValor(4, 4)).toString();
		if (v.equals("0"))
			button41.setLabel("");
		else
			button41.setLabel(v);
		v = new Integer(t.getValor(4, 5)).toString();
		if (v.equals("0"))
			button42.setLabel("");
		else
			button42.setLabel(v);
		v = new Integer(t.getValor(4, 6)).toString();
		if (v.equals("0"))
			button43.setLabel("");
		else
			button43.setLabel(v);
		v = new Integer(t.getValor(4, 7)).toString();
		if (v.equals("0"))
			button44.setLabel("");
		else
			button44.setLabel(v);
		v = new Integer(t.getValor(4, 8)).toString();
		if (v.equals("0"))
			button45.setLabel("");
		else
			button45.setLabel(v);
		v = new Integer(t.getValor(5, 0)).toString();
		if (v.equals("0"))
			button46.setLabel("");
		else
			button46.setLabel(v);
		v = new Integer(t.getValor(5, 1)).toString();
		if (v.equals("0"))
			button47.setLabel("");
		else
			button47.setLabel(v);
		v = new Integer(t.getValor(5, 2)).toString();
		if (v.equals("0"))
			button48.setLabel("");
		else
			button48.setLabel(v);
		v = new Integer(t.getValor(5, 3)).toString();
		if (v.equals("0"))
			button49.setLabel("");
		else
			button49.setLabel(v);
		v = new Integer(t.getValor(5, 4)).toString();
		if (v.equals("0"))
			button50.setLabel("");
		else
			button50.setLabel(v);
		v = new Integer(t.getValor(5, 5)).toString();
		if (v.equals("0"))
			button51.setLabel("");
		else
			button51.setLabel(v);
		v = new Integer(t.getValor(5, 6)).toString();
		if (v.equals("0"))
			button52.setLabel("");
		else
			button52.setLabel(v);
		v = new Integer(t.getValor(5, 7)).toString();
		if (v.equals("0"))
			button53.setLabel("");
		else
			button53.setLabel(v);
		v = new Integer(t.getValor(5, 8)).toString();
		if (v.equals("0"))
			button54.setLabel("");
		else
			button54.setLabel(v);
		v = new Integer(t.getValor(6, 0)).toString();
		if (v.equals("0"))
			button55.setLabel("");
		else
			button55.setLabel(v);
		v = new Integer(t.getValor(6, 1)).toString();
		if (v.equals("0"))
			button56.setLabel("");
		else
			button56.setLabel(v);
		v = new Integer(t.getValor(6, 2)).toString();
		if (v.equals("0"))
			button57.setLabel("");
		else
			button57.setLabel(v);
		v = new Integer(t.getValor(6, 3)).toString();
		if (v.equals("0"))
			button58.setLabel("");
		else
			button58.setLabel(v);
		v = new Integer(t.getValor(6, 4)).toString();
		if (v.equals("0"))
			button59.setLabel("");
		else
			button59.setLabel(v);
		v = new Integer(t.getValor(6, 5)).toString();
		if (v.equals("0"))
			button60.setLabel("");
		else
			button60.setLabel(v);
		v = new Integer(t.getValor(6, 6)).toString();
		if (v.equals("0"))
			button61.setLabel("");
		else
			button61.setLabel(v);
		v = new Integer(t.getValor(6, 7)).toString();
		if (v.equals("0"))
			button62.setLabel("");
		else
			button62.setLabel(v);
		v = new Integer(t.getValor(6, 8)).toString();
		if (v.equals("0"))
			button63.setLabel("");
		else
			button63.setLabel(v);
		v = new Integer(t.getValor(7, 0)).toString();
		if (v.equals("0"))
			button64.setLabel("");
		else
			button64.setLabel(v);
		v = new Integer(t.getValor(7, 1)).toString();
		if (v.equals("0"))
			button65.setLabel("");
		else
			button65.setLabel(v);
		v = new Integer(t.getValor(7, 2)).toString();
		if (v.equals("0"))
			button66.setLabel("");
		else
			button66.setLabel(v);
		v = new Integer(t.getValor(7, 3)).toString();
		if (v.equals("0"))
			button67.setLabel("");
		else
			button67.setLabel(v);
		v = new Integer(t.getValor(7, 4)).toString();
		if (v.equals("0"))
			button68.setLabel("");
		else
			button68.setLabel(v);
		v = new Integer(t.getValor(7, 5)).toString();
		if (v.equals("0"))
			button69.setLabel("");
		else
			button69.setLabel(v);
		v = new Integer(t.getValor(7, 6)).toString();
		if (v.equals("0"))
			button70.setLabel("");
		else
			button70.setLabel(v);
		v = new Integer(t.getValor(7, 7)).toString();
		if (v.equals("0"))
			button71.setLabel("");
		else
			button71.setLabel(v);
		v = new Integer(t.getValor(7, 8)).toString();
		if (v.equals("0"))
			button72.setLabel("");
		else
			button72.setLabel(v);
		v = new Integer(t.getValor(8, 0)).toString();
		if (v.equals("0"))
			button73.setLabel("");
		else
			button73.setLabel(v);
		v = new Integer(t.getValor(8, 1)).toString();
		if (v.equals("0"))
			button74.setLabel("");
		else
			button74.setLabel(v);
		v = new Integer(t.getValor(8, 2)).toString();
		if (v.equals("0"))
			button75.setLabel("");
		else
			button75.setLabel(v);
		v = new Integer(t.getValor(8, 3)).toString();
		if (v.equals("0"))
			button76.setLabel("");
		else
			button76.setLabel(v);
		v = new Integer(t.getValor(8, 4)).toString();
		if (v.equals("0"))
			button77.setLabel("");
		else
			button77.setLabel(v);
		v = new Integer(t.getValor(8, 5)).toString();
		if (v.equals("0"))
			button78.setLabel("");
		else
			button78.setLabel(v);
		v = new Integer(t.getValor(8, 6)).toString();
		if (v.equals("0"))
			button79.setLabel("");
		else
			button79.setLabel(v);
		v = new Integer(t.getValor(8, 7)).toString();
		if (v.equals("0"))
			button80.setLabel("");
		else
			button80.setLabel(v);
		v = new Integer(t.getValor(8, 8)).toString();
		if (v.equals("0"))
			button81.setLabel("");
		else
			button81.setLabel(v);
	}

	void button82_actionPerformed(ActionEvent e) {

	}

	String setPoner(int k, int f, int c) {
		String poner = "";
		if (jeu.tableauFix.getValor(f, c) < 1) {
			poner = KeyEvent.getKeyText(k);
			if (poner.equals("0"))
				jeu.tableau.putValor(f, c, 0);
			else if (poner.equals("1") || poner.equals("2")
					|| poner.equals("3") || poner.equals("4")
					|| poner.equals("5") || poner.equals("6")
					|| poner.equals("7") || poner.equals("8")
					|| poner.equals("9")) {
				jeu.tableau.putValor(f, c, (new Integer(poner).intValue()));
			} else
				poner = "";
		}
		int p = 0;
		if (jeu.tableau.numbZeros() == (jeu.dimension * jeu.dimension))
			p = 1;
		jeu.putParty((jeu.dimension * jeu.dimension) - jeu.tableau.numbZeros()
				- 1, f, c, jeu.tableau.getValor(f, c));
		return poner;
	}

	void button1_keyPressed(KeyEvent e) {
		int f = 0;
		int c = 0;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button1.setLabel(pon);
		button1.setForeground(color);
	}

	void button5_keyPressed(KeyEvent e) {
		int f = 0;
		int c = 4;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button5.setLabel(pon);
		button5.setForeground(color);

	}

	void button6_keyPressed(KeyEvent e) {
		int f = 0;
		int c = 5;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button6.setLabel(pon);
		button6.setForeground(color);

	}

	void button7_keyPressed(KeyEvent e) {
		int f = 0;
		int c = 6;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button7.setLabel(pon);
		button7.setForeground(color);

	}

	void button8_keyPressed(KeyEvent e) {
		int f = 0;
		int c = 7;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button8.setLabel(pon);
		button8.setForeground(color);

	}

	void button9_keyPressed(KeyEvent e) {
		int f = 0;
		int c = 8;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button9.setLabel(pon);
		button9.setForeground(color);

	}

	void button10_keyPressed(KeyEvent e) {
		int f = 1;
		int c = 0;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button10.setLabel(pon);
		button10.setForeground(color);

	}

	void button11_keyPressed(KeyEvent e) {
		int f = 1;
		int c = 1;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button11.setLabel(pon);
		button11.setForeground(color);
	}

	void button12_keyPressed(KeyEvent e) {
		int f = 1;
		int c = 2;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button12.setLabel(pon);
		button12.setForeground(color);

	}

	void button13_keyPressed(KeyEvent e) {
		int f = 1;
		int c = 3;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button13.setLabel(pon);
		button13.setForeground(color);

	}

	void button14_keyPressed(KeyEvent e) {
		int f = 1;
		int c = 4;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button14.setLabel(pon);
		button14.setForeground(color);

	}

	void button15_keyPressed(KeyEvent e) {
		int f = 1;
		int c = 5;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button15.setLabel(pon);
		button15.setForeground(color);

	}

	void button16_keyPressed(KeyEvent e) {
		int f = 1;
		int c = 6;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button16.setLabel(pon);
		button16.setForeground(color);

	}

	void button17_keyPressed(KeyEvent e) {
		int f = 1;
		int c = 7;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button17.setLabel(pon);
		button17.setForeground(color);

	}

	void button18_keyPressed(KeyEvent e) {
		int f = 1;
		int c = 8;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button18.setLabel(pon);
		button18.setForeground(color);

	}

	void button2_keyPressed(KeyEvent e) {
		int f = 0;
		int c = 1;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button2.setLabel(pon);
		button2.setForeground(color);

	}

	void button3_keyPressed(KeyEvent e) {
		int f = 0;
		int c = 2;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button3.setLabel(pon);
		button3.setForeground(color);

	}

	void button4_keyPressed(KeyEvent e) {
		int f = 0;
		int c = 3;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button4.setLabel(pon);
		button4.setForeground(color);

	}

	void button19_keyPressed(KeyEvent e) {
		int f = 2;
		int c = 0;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button19.setLabel(pon);
		button19.setForeground(color);

	}

	void button20_keyPressed(KeyEvent e) {
		int f = 2;
		int c = 1;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button20.setLabel(pon);
		button20.setForeground(color);

	}

	void button21_keyPressed(KeyEvent e) {
		int f = 2;
		int c = 2;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button21.setLabel(pon);
		button21.setForeground(color);

	}

	void button22_keyPressed(KeyEvent e) {
		int f = 2;
		int c = 3;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button22.setLabel(pon);
		button22.setForeground(color);

	}

	void button23_keyPressed(KeyEvent e) {
		int f = 2;
		int c = 4;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button23.setLabel(pon);
		button23.setForeground(color);

	}

	void button24_keyPressed(KeyEvent e) {
		int f = 2;
		int c = 5;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button24.setLabel(pon);
		button24.setForeground(color);

	}

	void button25_keyPressed(KeyEvent e) {
		int f = 2;
		int c = 6;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button25.setLabel(pon);
		button25.setForeground(color);

	}

	void button26_keyPressed(KeyEvent e) {
		int f = 2;
		int c = 7;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button26.setLabel(pon);
		button26.setForeground(color);

	}

	void button27_keyPressed(KeyEvent e) {
		int f = 2;
		int c = 8;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button27.setLabel(pon);
		button27.setForeground(color);

	}

	void button28_keyPressed(KeyEvent e) {
		int f = 3;
		int c = 0;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button28.setLabel(pon);
		button28.setForeground(color);

	}

	void button29_keyPressed(KeyEvent e) {
		int f = 3;
		int c = 1;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button29.setLabel(pon);
		button29.setForeground(color);

	}

	void button30_keyPressed(KeyEvent e) {
		int f = 3;
		int c = 2;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button30.setLabel(pon);
		button30.setForeground(color);

	}

	void button31_keyPressed(KeyEvent e) {
		int f = 3;
		int c = 3;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button31.setLabel(pon);
		button31.setForeground(color);

	}

	void button32_keyPressed(KeyEvent e) {
		int f = 3;
		int c = 4;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button32.setLabel(pon);
		button32.setForeground(color);

	}

	void button33_keyPressed(KeyEvent e) {
		int f = 3;
		int c = 5;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button33.setLabel(pon);
		button33.setForeground(color);

	}

	void button34_keyPressed(KeyEvent e) {
		int f = 3;
		int c = 6;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button34.setLabel(pon);
		button34.setForeground(color);

	}

	void button35_keyPressed(KeyEvent e) {
		int f = 3;
		int c = 7;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button35.setLabel(pon);
		button35.setForeground(color);

	}

	void button36_keyPressed(KeyEvent e) {
		int f = 3;
		int c = 8;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button36.setLabel(pon);
		button36.setForeground(color);

	}

	void button37_keyPressed(KeyEvent e) {
		int f = 4;
		int c = 0;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button37.setLabel(pon);
		button37.setForeground(color);

	}

	void button38_keyPressed(KeyEvent e) {
		int f = 4;
		int c = 1;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button38.setLabel(pon);
		button38.setForeground(color);

	}

	void button39_keyPressed(KeyEvent e) {
		int f = 4;
		int c = 2;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button39.setLabel(pon);
		button39.setForeground(color);

	}

	void button40_keyPressed(KeyEvent e) {
		int f = 4;
		int c = 3;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button40.setLabel(pon);
		button40.setForeground(color);

	}

	void button41_keyPressed(KeyEvent e) {
		int f = 4;
		int c = 4;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button41.setLabel(pon);
		button41.setForeground(color);

	}

	void button42_keyPressed(KeyEvent e) {
		int f = 4;
		int c = 5;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button42.setLabel(pon);
		button42.setForeground(color);

	}

	void button43_keyPressed(KeyEvent e) {
		int f = 4;
		int c = 6;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button43.setLabel(pon);
		button43.setForeground(color);

	}

	void button44_keyPressed(KeyEvent e) {
		int f = 4;
		int c = 7;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button44.setLabel(pon);
		button44.setForeground(color);

	}

	void button45_keyPressed(KeyEvent e) {
		int f = 4;
		int c = 8;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button45.setLabel(pon);
		button45.setForeground(color);

	}

	void button46_keyPressed(KeyEvent e) {
		int f = 5;
		int c = 0;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button46.setLabel(pon);
		button46.setForeground(color);

	}

	void button47_keyPressed(KeyEvent e) {
		int f = 5;
		int c = 1;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button47.setLabel(pon);
		button47.setForeground(color);

	}

	void button48_keyPressed(KeyEvent e) {
		int f = 5;
		int c = 2;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button48.setLabel(pon);
		button48.setForeground(color);

	}

	void button49_keyPressed(KeyEvent e) {
		int f = 5;
		int c = 3;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button49.setLabel(pon);
		button49.setForeground(color);

	}

	void button50_keyPressed(KeyEvent e) {
		int f = 5;
		int c = 4;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button50.setLabel(pon);
		button50.setForeground(color);

	}

	void button51_keyPressed(KeyEvent e) {
		int f = 5;
		int c = 5;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button51.setLabel(pon);
		button51.setForeground(color);

	}

	void button52_keyPressed(KeyEvent e) {
		int f = 5;
		int c = 6;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button52.setLabel(pon);
		button52.setForeground(color);

	}

	void button53_keyPressed(KeyEvent e) {
		int f = 5;
		int c = 7;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button53.setLabel(pon);
		button53.setForeground(color);

	}

	void button54_keyPressed(KeyEvent e) {
		int f = 5;
		int c = 8;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button54.setLabel(pon);
		button54.setForeground(color);

	}

	void button55_keyPressed(KeyEvent e) {
		int f = 6;
		int c = 0;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		String pon = poner;
		if (poner.equals("0"))
			pon = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button55.setLabel(pon);
		button55.setForeground(color);

	}

	void button56_keyPressed(KeyEvent e) {
		int f = 6;
		int c = 1;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button56.setLabel(poner);
		button56.setForeground(color);

	}

	void button57_keyPressed(KeyEvent e) {
		int f = 6;
		int c = 2;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button57.setLabel(poner);
		button57.setForeground(color);

	}

	void button58_keyPressed(KeyEvent e) {
		int f = 6;
		int c = 3;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button58.setLabel(poner);
		button58.setForeground(color);

	}

	void button59_keyPressed(KeyEvent e) {
		int f = 6;
		int c = 4;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button59.setLabel(poner);
		button59.setForeground(color);

	}

	void button60_keyPressed(KeyEvent e) {
		int f = 6;
		int c = 5;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button60.setLabel(poner);
		button60.setForeground(color);

	}

	void button61_keyPressed(KeyEvent e) {
		int f = 6;
		int c = 6;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button61.setLabel(poner);
		button61.setForeground(color);

	}

	void button62_keyPressed(KeyEvent e) {
		int f = 6;
		int c = 7;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button62.setLabel(poner);
		button62.setForeground(color);

	}

	void button63_keyPressed(KeyEvent e) {
		int f = 6;
		int c = 8;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button63.setLabel(poner);
		button63.setForeground(color);

	}

	void button64_keyPressed(KeyEvent e) {
		int f = 7;
		int c = 0;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button64.setLabel(poner);
		button64.setForeground(color);

	}

	void button65_keyPressed(KeyEvent e) {
		int f = 7;
		int c = 1;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button65.setLabel(poner);
		button65.setForeground(color);

	}

	void button66_keyPressed(KeyEvent e) {
		int f = 7;
		int c = 2;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button66.setLabel(poner);
		button66.setForeground(color);

	}

	void button67_keyPressed(KeyEvent e) {
		int f = 7;
		int c = 3;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button67.setLabel(poner);
		button67.setForeground(color);

	}

	void button68_keyPressed(KeyEvent e) {
		int f = 7;
		int c = 4;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button68.setLabel(poner);
		button68.setForeground(color);

	}

	void button69_keyPressed(KeyEvent e) {
		int f = 7;
		int c = 5;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button69.setLabel(poner);
		button69.setForeground(color);

	}

	void button70_keyPressed(KeyEvent e) {
		int f = 7;
		int c = 6;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button70.setLabel(poner);
		button70.setForeground(color);

	}

	void button71_keyPressed(KeyEvent e) {
		int f = 7;
		int c = 7;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button71.setLabel(poner);
		button71.setForeground(color);

	}

	void button72_keyPressed(KeyEvent e) {
		int f = 7;
		int c = 8;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button72.setLabel(poner);
		button72.setForeground(color);

	}

	void button73_keyPressed(KeyEvent e) {
		int f = 8;
		int c = 0;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button73.setLabel(poner);
		button73.setForeground(color);

	}

	void button74_keyPressed(KeyEvent e) {
		int f = 8;
		int c = 1;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button74.setLabel(poner);
		button74.setForeground(color);

	}

	void button75_keyPressed(KeyEvent e) {
		int f = 8;
		int c = 2;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button75.setLabel(poner);
		button75.setForeground(color);

	}

	void button76_keyPressed(KeyEvent e) {
		int f = 8;
		int c = 3;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button76.setLabel(poner);
		button76.setForeground(color);

	}

	void button77_keyPressed(KeyEvent e) {
		int f = 8;
		int c = 4;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button77.setLabel(poner);
		button77.setForeground(color);

	}

	void button78_keyPressed(KeyEvent e) {
		int f = 8;
		int c = 5;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button78.setLabel(poner);
		button78.setForeground(color);

	}

	void button79_keyPressed(KeyEvent e) {
		int f = 8;
		int c = 6;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button79.setLabel(poner);
		button79.setForeground(color);

	}

	void button80_keyPressed(KeyEvent e) {
		int f = 8;
		int c = 7;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button80.setLabel(poner);
		button80.setForeground(color);

	}

	void button81_keyPressed(KeyEvent e) {
		int f = 8;
		int c = 8;
		Color color = Color.black;
		int k = e.getKeyCode();
		String poner = setPoner(k, f, c);
		if (poner.equals("0"))
			poner = "";
		else if (!poner.equals("")) {
			if (jeu.tableau.validCase(f, c, (new Integer(poner).intValue())))
				color = Color.blue;
			else
				color = Color.red;
		}
		button81.setLabel(poner);
		button81.setForeground(color);

	}

	void button84_mouseClicked(MouseEvent e) {
		Celda celda = new Celda(0, 0, 0);
		int x = 0;
		for (x = (jeu.dimension * jeu.dimension) - 1; x >= 0; x--) {
			celda.putValor(jeu.jugadas[x].getValor());
			celda.putFila(jeu.jugadas[x].getFila());
			celda.putColumna(jeu.jugadas[x].getColumna());
			if (celda.getValor() > 0)
				break;
		}
		if (celda.getValor() > 0) {
			jeu.putParty(x, celda.getFila(), celda.getColumna(), 0);
			jeu.tableau.putValor(celda.getFila(), celda.getColumna(), 0);
			llenaRejilla(jeu.tableau);
		}

	}

	void button82_mouseClicked(MouseEvent e) {
		nouvJeu(35);
	}

	void button83_mouseClicked(MouseEvent e) {
		jeu.tableauFix = jeu.copyTablero(jeu.tableau);
		if (jeu.resolver())
			llenaRejilla(jeu.tableau); // else no se puede

	}

}

class Applet1_button1_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	
	Applet1_button1_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button1_keyPressed(e);
	}
}

class Applet1_button2_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button2_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button2_keyPressed(e);
	}
}

class Applet1_button3_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button3_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button3_keyPressed(e);
	}
}

class Applet1_button4_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button4_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button4_keyPressed(e);
	}
}

class Applet1_button5_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button5_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button5_keyPressed(e);
	}
}

class Applet1_button6_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button6_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button6_keyPressed(e);
	}
}

class Applet1_button7_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button7_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button7_keyPressed(e);
	}
}

class Applet1_button8_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button8_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button8_keyPressed(e);
	}
}

class Applet1_button9_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button9_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button9_keyPressed(e);
	}
}

class Applet1_button10_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button10_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button10_keyPressed(e);
	}
}

class Applet1_button11_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button11_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button11_keyPressed(e);
	}
}

class Applet1_button12_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button12_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button12_keyPressed(e);
	}
}

class Applet1_button13_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button13_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button13_keyPressed(e);
	}
}

class Applet1_button14_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button14_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button14_keyPressed(e);
	}
}

class Applet1_button15_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button15_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button15_keyPressed(e);
	}
}

class Applet1_button16_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button16_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button16_keyPressed(e);
	}
}

class Applet1_button17_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button17_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button17_keyPressed(e);
	}
}

class Applet1_button18_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button18_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button18_keyPressed(e);
	}
}

class Applet1_button19_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button19_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button19_keyPressed(e);
	}
}

class Applet1_button20_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button20_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button20_keyPressed(e);
	}
}

class Applet1_button21_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button21_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button21_keyPressed(e);
	}
}

class Applet1_button22_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button22_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button22_keyPressed(e);
	}
}

class Applet1_button23_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button23_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button23_keyPressed(e);
	}
}

class Applet1_button24_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button24_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button24_keyPressed(e);
	}
}

class Applet1_button25_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button25_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button25_keyPressed(e);
	}
}

class Applet1_button26_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button26_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button26_keyPressed(e);
	}
}

class Applet1_button27_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button27_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button27_keyPressed(e);
	}
}

class Applet1_button28_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button28_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button28_keyPressed(e);
	}
}

class Applet1_button29_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button29_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button29_keyPressed(e);
	}
}

class Applet1_button30_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button30_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button30_keyPressed(e);
	}
}

class Applet1_button31_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button31_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button31_keyPressed(e);
	}
}

class Applet1_button32_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button32_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button32_keyPressed(e);
	}
}

class Applet1_button33_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button33_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button33_keyPressed(e);
	}
}

class Applet1_button34_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button34_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button34_keyPressed(e);
	}
}

class Applet1_button35_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button35_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button35_keyPressed(e);
	}
}

class Applet1_button36_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button36_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button36_keyPressed(e);
	}
}

class Applet1_button37_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button37_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button37_keyPressed(e);
	}
}

class Applet1_button38_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button38_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button38_keyPressed(e);
	}
}

class Applet1_button39_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button39_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button39_keyPressed(e);
	}
}

class Applet1_button40_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button40_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button40_keyPressed(e);
	}
}

class Applet1_button41_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button41_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button41_keyPressed(e);
	}
}

class Applet1_button42_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button42_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button42_keyPressed(e);
	}
}

class Applet1_button43_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button43_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button43_keyPressed(e);
	}
}

class Applet1_button44_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button44_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button44_keyPressed(e);
	}
}

class Applet1_button45_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button45_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button45_keyPressed(e);
	}
}

class Applet1_button46_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button46_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button46_keyPressed(e);
	}
}

class Applet1_button47_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button47_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button47_keyPressed(e);
	}
}

class Applet1_button48_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button48_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button48_keyPressed(e);
	}
}

class Applet1_button49_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button49_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button49_keyPressed(e);
	}
}

class Applet1_button50_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button50_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button50_keyPressed(e);
	}
}

class Applet1_button51_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button51_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button51_keyPressed(e);
	}
}

class Applet1_button52_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button52_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button52_keyPressed(e);
	}
}

class Applet1_button53_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button53_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button53_keyPressed(e);
	}
}

class Applet1_button54_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button54_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button54_keyPressed(e);
	}
}

class Applet1_button55_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button55_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button55_keyPressed(e);
	}
}

class Applet1_button56_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button56_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button56_keyPressed(e);
	}
}

class Applet1_button57_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button57_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button57_keyPressed(e);
	}
}

class Applet1_button58_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button58_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button58_keyPressed(e);
	}
}

class Applet1_button59_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button59_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button59_keyPressed(e);
	}
}

class Applet1_button60_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button60_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button60_keyPressed(e);
	}
}

class Applet1_button61_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button61_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button61_keyPressed(e);
	}
}

class Applet1_button62_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button62_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button62_keyPressed(e);
	}
}

class Applet1_button63_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button63_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button63_keyPressed(e);
	}
}

class Applet1_button64_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button64_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button64_keyPressed(e);
	}
}

class Applet1_button65_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button65_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button65_keyPressed(e);
	}
}

class Applet1_button66_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button66_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button66_keyPressed(e);
	}
}

class Applet1_button67_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button67_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button67_keyPressed(e);
	}
}

class Applet1_button68_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button68_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button68_keyPressed(e);
	}
}

class Applet1_button69_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button69_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button69_keyPressed(e);
	}
}

class Applet1_button71_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button71_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button71_keyPressed(e);
	}
}

class Applet1_button72_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button72_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button72_keyPressed(e);
	}
}

class Applet1_button73_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button73_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button73_keyPressed(e);
	}
}

class Applet1_button74_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button74_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button74_keyPressed(e);
	}
}

class Applet1_button75_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button75_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button75_keyPressed(e);
	}
}

class Applet1_button76_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button76_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button76_keyPressed(e);
	}
}

class Applet1_button77_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button77_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button77_keyPressed(e);
	}
}

class Applet1_button78_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button78_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button78_keyPressed(e);
	}
}

class Applet1_button79_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button79_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button79_keyPressed(e);
	}
}

class Applet1_button80_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button80_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button80_keyPressed(e);
	}
}

class Applet1_button81_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button81_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button81_keyPressed(e);
	}
}

class Applet1_button70_keyAdapter extends java.awt.event.KeyAdapter {
	Applet2Swing adaptee;

	Applet1_button70_keyAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.button70_keyPressed(e);
	}
}

class Applet1_button82_mouseAdapter extends java.awt.event.MouseAdapter {
	Applet2Swing adaptee;

	Applet1_button82_mouseAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void mouseClicked(MouseEvent e) {
		adaptee.button82_mouseClicked(e);
	}
}

class Applet1_button83_mouseAdapter extends java.awt.event.MouseAdapter {
	Applet2Swing adaptee;

	Applet1_button83_mouseAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void mouseClicked(MouseEvent e) {
		adaptee.button83_mouseClicked(e);
	}
}

class Applet1_button84_mouseAdapter extends java.awt.event.MouseAdapter {
	Applet2Swing adaptee;

	Applet1_button84_mouseAdapter(Applet2Swing adaptee) {
		this.adaptee = adaptee;
	}

	public void mouseClicked(MouseEvent e) {
		adaptee.button84_mouseClicked(e);
	}
}

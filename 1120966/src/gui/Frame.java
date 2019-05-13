/*
 * Projeto CLUE
 * INF1636 - Programacao Orientada a Objetos
 * Desenvolvido por:
 * 
 * Lucas Eduardo Pereira Martins - 1013475
 * Pedro Schuback Chataignier - 1120966
 * 
 * */

package gui;

import java.awt.*;
import javax.swing.*;

import logica.Tabuleiro;


@SuppressWarnings("serial")
public class Frame extends JFrame {

	private static Frame instance=new Frame();
	
	public final int LARG_DEFAULT = Tabuleiro.getInstance().getWidth() + 285;
	public final int ALT_DEFAULT = 725;
	
	private Tab_Panel t = new Tab_Panel();
	private Crtl_Panel c;
	private Start_Panel sp;
	
	
	private Frame(){
		
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		
		int sl=screenSize.width;
		int sa=screenSize.height;
		int x=sl/2-LARG_DEFAULT/2;
		int y=sa/2-ALT_DEFAULT/2;
		
		setBounds(x,y,LARG_DEFAULT,ALT_DEFAULT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		

		
		//Painel do Tabuleiro
		t.setLayout(null);
		int tab_width= Tabuleiro.getInstance().getWidth();
		t.setBounds(0, 0, tab_width, ALT_DEFAULT);
		
		
		//Painel de Controle
		c = new Crtl_Panel(tab_width);
		c.setLayout(null);
		c.setBounds(tab_width, 0, LARG_DEFAULT-tab_width, ALT_DEFAULT);
		c.setVisible(false);
		
		//Painel de Inicio
		sp = new Start_Panel(tab_width);
		sp.setLayout(null);
		sp.setBounds(tab_width, 0, LARG_DEFAULT-tab_width, ALT_DEFAULT);
		
		this.getContentPane().add(t);

		this.getContentPane().add(sp);
	}
	
	public static Frame getInstance(){
		return instance;
	}
	
	public Crtl_Panel getCrtlPanel(){
		return c;
	}
	
	public void Start(){
		sp.setVisible(false);
		this.getContentPane().add(c);
		c.setVisible(true);
		this.repaint();
	}
	
	public static void main(String[] args){
		
		instance.setTitle("CLUE");
		instance.setVisible(true);
		//instance.Start();
	}
}

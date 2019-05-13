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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

import logica.Casa;
import logica.Controlador;

@SuppressWarnings("serial")
public class Tab_Panel extends JPanel implements MouseListener{
	
	private Image i;
	
	public Tab_Panel(){
	
		i = Controlador.getInstance().getTabuleiro();
		this.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;

		g2d.drawImage(i,0,0,null);
		
		if(Controlador.getInstance().GameStarted()){
			Ellipse2D[] e = Controlador.getInstance().getPieces();
			Color[] c = Controlador.getInstance().getPieceColor();
			for(int i = 0; i < e.length; i++){
				g2d.setColor(c[i]);
				g2d.fill(e[i]);
				g2d.setColor(Color.black);
				g2d.draw(e[i]);
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		
		if(Controlador.getInstance().GameStarted() && Frame.getInstance().getCrtlPanel().rolled()){
			int x = e.getX();
			int y = e.getY();
			int row, col;
		
			if(y < 50 || y> 50+(26*25) || x < 50 || x > 50+(25*25)){}
			else{
				row = (y - 50)/25;
				col = (x - 50)/25;
				Casa dest = new Casa(row, col);
				Controlador.getInstance().moverPara(dest);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	
	
}

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

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import logica.Controlador;

@SuppressWarnings("serial")
public class Start_Panel extends JPanel implements ActionListener, ItemListener{
	
	private JCheckBox[] sel;
	private int[] index = {0, 0, 0, 0, 0, 0};
	
	public Start_Panel(int offset)
	{
		JButton go = null;
		JLabel label1 = null;
		JLabel label2 = null;
		JLabel label3 = null;
		JLabel label4 = null;
		
		label1 = new JLabel("Selecione os Personagens");
		label1.setBounds(offset + 50, 145, 220, 30);
		label1.setFont(new Font("Arial", Font.BOLD, 16));
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(label1);

		int boxWidth = 200, boxHeight = 20, box_x = offset + 100, box_y = 200;
			
		sel = new JCheckBox[6];
		for(int i = 0; i < sel.length; i++){
			String[] s = Controlador.getInstance().getPersonagens();
			sel[i] = new JCheckBox(s[i]);
			sel[i].setBounds(box_x, box_y + (boxHeight * i), boxWidth, boxHeight);
			sel[i].addItemListener(this);
			add(sel[i]);
		}
		
			
		go = new JButton("INICIAR");
		go.addActionListener(this);
		go.setFont(new Font("Arial", Font.BOLD, 26));
		go.setBounds(offset + 50, 340, 220, 70);
		add(go);
		
		label2 = new JLabel("Ou pressione o botao");
		label2.setBounds(offset + 50, 420, 220, 30);
		label2.setFont(new Font("Arial", Font.BOLD, 16));
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		add(label2);
		
		label3 = new JLabel("para iniciar com todos");
		label3.setBounds(offset + 50, 445, 220, 30);
		label3.setFont(new Font("Arial", Font.BOLD, 16));
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		add(label3);
		
		label4 = new JLabel("os personagens");
		label4.setBounds(offset + 50, 470, 220, 30);
		label4.setFont(new Font("Arial", Font.BOLD, 16));
		label4.setHorizontalAlignment(SwingConstants.CENTER);
		add(label4);
	}
	
	public void actionPerformed(ActionEvent arg0) {

		int[] jog;
		int nJog = 0;
		for(int i = 0; i < index.length; i++){
			if(index[i] == 1)
				nJog++;
		}
			
		if(nJog >= Controlador.getInstance().getminJogadores()){
			jog = new int[nJog];
			int count = 0;
			
			for(int i = 0; i< index.length; i++){
				if(index[i] == 1){
					jog[count] = i;
					count++;
				}
						
			}
			
			Frame.getInstance().Start();
			Controlador.getInstance().SartGame(jog);
		}
		if(nJog == 0){
			jog = new int[]{0, 1, 2, 3, 4, 5};
			Frame.getInstance().Start();
			Controlador.getInstance().SartGame(jog);
		}
	}

	public void itemStateChanged(ItemEvent arg0) {
		Object source = arg0.getSource();
		for(int i = 0; i < index.length; i++){
			if(source == sel[i]){
				index[i] = 1 - index[i];
			}
		}
		
	}
	
}
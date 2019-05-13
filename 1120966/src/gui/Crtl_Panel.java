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
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.*;

import logica.Controlador;


@SuppressWarnings("serial")
public class Crtl_Panel extends JPanel{
	private int dado = 0;
	JLabel labelDado, turno;
	JButton rolaDado, palpite, acusacao, fim, cartas;
	JComboBox local, arma, culpado;
	JTextArea anotacoes;
	
	public Crtl_Panel(int offset)
	{
		
		//Personagem
		TitledBorder title;
		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Personagem");
		title.setTitleJustification(TitledBorder.CENTER);
		
		turno = new JLabel("Teste");
		turno.setHorizontalAlignment( SwingConstants.CENTER );
		turno.setBounds(offset + 15, 20, 250, 70);
		turno.setFont(new Font("Arial", Font.BOLD, 24));
		turno.setBorder(title);
		add(turno);
		
		
		//Dado
		labelDado = new JLabel("0");
		labelDado.setFont(new Font("Arial", Font.BOLD, 30));
		labelDado.setHorizontalAlignment( SwingConstants.CENTER );
		labelDado.setBounds(offset + 185, 130, 50, 50);
		labelDado.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		add(labelDado);
		
		rolaDado = new JButton("Dado");
		rolaDado.setFont(new Font("Arial", Font.BOLD, 24));
		rolaDado.addActionListener(new DiceListener());
		rolaDado.setBounds(offset + 35, 130, 100, 50);
		add(rolaDado);
		
		
		//Palpites
		culpado = new JComboBox(Controlador.getInstance().getPersonagens());
		culpado.setBounds(offset + 35, 200, 140, 50);
		add(culpado);
		
		local = new JComboBox(Controlador.getInstance().getComodos());
		local.setBounds(offset + 35, 240, 140, 50);
		add(local);
		
		arma = new JComboBox(Controlador.getInstance().getArmas());
		arma.setBounds(offset + 35, 280, 140, 50);
		add(arma);
		
		palpite = new JButton("Palpite");
		palpite.setBounds(offset + 185, 220, 70, 40);
		palpite.addActionListener(new PalpiteListener());
		palpite.setEnabled(false);
		add(palpite);
		
		acusacao = new JButton("Acusacao");
		acusacao.setBounds(offset + 185, 270, 70, 40);
		acusacao.addActionListener(new AcusacaoListener());
		add(acusacao);
		
		//Cartas do Jogador		
		cartas = new JButton("Minhas Cartas");
		cartas.setFont(new Font("Arial", Font.BOLD, 18));
		cartas.setBounds(offset + 70, 330, 160, 40);
		cartas.addActionListener(new CartasListener());
		add(cartas);

		//Exibicao de todos os palpites dados durante o jogo
		anotacoes = new JTextArea();
		anotacoes.setLineWrap(true);
		anotacoes.setWrapStyleWord(true);
		anotacoes.setEditable(false);
		anotacoes.append("Aqui estao todos os palpites dados:\n");
		JScrollPane scroll = new JScrollPane (anotacoes);
		scroll.setBounds(offset + 30, 380, 235, 200);
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scroll);
		
		//Finalizar Turno
		fim = new JButton("Finalizar Turno");
		fim.setFont(new Font("Arial", Font.BOLD, 26));
		fim.setHorizontalAlignment( SwingConstants.CENTER );
		fim.addActionListener(new FimListener());
		fim.setBounds(offset + 15, 600, 250, 70);
		add(fim);
		
	}
	
	public void addPalpite(String s){
		anotacoes.append(s+";\n");
		Frame.getInstance().repaint();
	}
	
	public void newTurn(String s, boolean palp){
		labelDado.setText("0");
		turno.setText(s);
		rolaDado.setEnabled(true);
		palpite.setEnabled(palp);
		Frame.getInstance().repaint();
	}

	public void setPalpite(boolean palp){
		palpite.setEnabled(palp);
		Frame.getInstance().repaint();
	}
	
	public int getDado(){
		return dado;
	}
	
	public boolean rolled(){
		return !rolaDado.isEnabled();
	}
	
	private class DiceListener implements ActionListener{

		private int RolaDado(){	/* Numero tirado nos dados */

			Random gerador = new Random(System.currentTimeMillis());

			int num = gerador.nextInt(6) + 1;

			//Descomente a linha abaixo e comente a seguinte para rolar o dado com um numero fixo de 10
			//return 10;
			return num;
		}
		
		public void actionPerformed(ActionEvent arg0) {
		
			dado = RolaDado();
			labelDado.setText(Integer.toString(dado));
			rolaDado.setEnabled(false);
			Frame.getInstance().repaint();
		}
	
	}
	
	private class FimListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			
			Controlador.getInstance().nextTurn();
		}
		
	}
	
	private class PalpiteListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			
			String s[] = Controlador.getInstance().palpite(culpado.getSelectedIndex(), arma.getSelectedIndex());
			if(s == null){
				JOptionPane.showMessageDialog(Frame.getInstance(), "Nenhuma carta foi mostrada!", "Palpite", JOptionPane.PLAIN_MESSAGE);
				palpite.setEnabled(false);
			}
			else{
				JOptionPane.showMessageDialog(Frame.getInstance(), s[0]+s[1], "Palpite", JOptionPane.PLAIN_MESSAGE, new ImageIcon("images/"+s[1]+".jpg"));
				palpite.setEnabled(false);
			}
		}
		
	}
	
	private class AcusacaoListener implements ActionListener{
		
		public void actionPerformed(ActionEvent arg0) {
			String loc = (String)local.getSelectedItem();
			String per = (String)culpado.getSelectedItem();
			String armaS = (String)arma.getSelectedItem();
			String corr = Controlador.getInstance().getCurrentPlayer();
			
			
			boolean r = Controlador.getInstance().verificaAcusacao(armaS, per, loc);
			if(r == true){
				//Acusacao certa, jogador venceu
				JOptionPane.showMessageDialog(Frame.getInstance(), corr + " venceu", "Acusacao", JOptionPane.PLAIN_MESSAGE);
				JOptionPane.showMessageDialog(Frame.getInstance(), per + " cometeu o crime com o/a " + armaS + " no/a " + loc, "Fim de Jogo", JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			}
			else{
				if(Controlador.getInstance().getJogadores().length == 1){
					//Se sobrar apenas um jogador, ele vence
					ArrayList<String> env = Controlador.getInstance().getEnvelope();
					corr = Controlador.getInstance().getCurrentPlayer();
					JOptionPane.showMessageDialog(Frame.getInstance(), corr + " venceu", "Acusacao", JOptionPane.PLAIN_MESSAGE);
					JOptionPane.showMessageDialog(Frame.getInstance(), env.get(1) + " cometeu o crime com o/a " + env.get(0) + " no/a " + env.get(2), "Fim de Jogo", JOptionPane.PLAIN_MESSAGE);
					System.exit(0);
				}
				else{
					//Jogador perdeu e foi retirado do jogo
					JOptionPane.showMessageDialog(Frame.getInstance(), corr + " perdeu", "Acusacao", JOptionPane.PLAIN_MESSAGE);
					Controlador.getInstance().nextTurn();
				}	
			
			}
		}
	}
	
	private class CartasListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			
			String s = Controlador.getInstance().getMinhasCartas();
			
			JOptionPane.showMessageDialog(Frame.getInstance(), s, "Minhas Cartas", JOptionPane.PLAIN_MESSAGE);			
		}
		
	}
}
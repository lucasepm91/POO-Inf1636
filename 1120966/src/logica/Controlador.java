/*
 * Projeto CLUE
 * INF1636 - Programacao Orientada a Objetos
 * Desenvolvido por:
 * 
 * Lucas Eduardo Pereira Martins - 1013475
 * Pedro Schuback Chataignier - 1120966
 * 
 * */

package logica;


import gui.*;
import gui.Frame;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;

public class Controlador {

	private static Controlador instance = null; //Instancia Singleton do Controlador
	private boolean started = false; //Controle do inicio do jogo
	private String[] comodos = {"Kitchen", "Ballroom", "Conservatory", "Billard Room", "Library", "Study", "Hall", "Lounge", "Dinning Room"};
	private String[] armas = {"Revolver", "Candlestick", "Rope", "Knife","Pipe","Wrench"};
	private String[] personagens={"Ms. Scarlet", "Col. Mustard", "Ms. White", "Rev. Green", "Mrs. Peacock", "Prof. Plum"};
	private int[] startRow = {24, 17, 0, 0, 6, 19};
	private int[] startCol = {7, 0, 9, 14, 23, 23};
	private Color[] startColor = {Color.RED,Color.YELLOW,  Color.WHITE, Color.GREEN, Color.BLUE, Color.MAGENTA};
	private final int minJog = 3; //O numero minimo de jogadores
	private int current = 0; //Indice do jogador da rodada
	private Personagem[] Jogadores; //Vetor de Jogadores que ainda est‹o jogando
	private Color[] colorPiece; //Cores das pecas de cada jogador ainda em jogo
	private boolean andou = false; //Controle de movimento a cada rodada
	
	private Comodo com[] = new Comodo[]{new Comodo("kitchen"),new Comodo("ballroom"),new Comodo("conservatory"),new Comodo("billiardroom"),
			new Comodo("library"),new Comodo("study"),new Comodo("hall"),new Comodo("lounge"),new Comodo("dinningroom")};
	private ArrayList<String> envelope = new ArrayList<String>(); //O envelope com a solucao do crime
	private ArrayList<String> weapons = new ArrayList<String>();	//Baralhos separados
	private ArrayList<String> rooms = new ArrayList<String>();		//usados para carregar
	private ArrayList<String> suspects = new ArrayList<String>();	//o envelope
	private ArrayList<String> baralho = new ArrayList<String>(); //Baralho todo que e distribuido para os jogadores
	private Personagem[] crtl_cartas; //Copia do vetor Jogadores que permite a checagem de cartas nos palpites
	private int nJogadores = 0; //O numero inicial de Jogadores
	
	private Controlador(){
		weapons.add("Revolver");
		weapons.add("Candlestick");
		weapons.add("Rope");
		weapons.add("Knife");
		weapons.add("Pipe");
		weapons.add("Wrench");

		rooms.add("Kitchen");
		rooms.add("Ballroom");
		rooms.add("Conservatory");
		rooms.add("Billard Room");
		rooms.add("Library");
		rooms.add("Study");
		rooms.add("Hall");
		rooms.add("Lounge");
		rooms.add("Dinning Room");

		suspects.add("Ms. Scarlet");
		suspects.add("Col. Mustard");
		suspects.add("Ms. White");
		suspects.add("Rev. Green");
		suspects.add("Mrs. Peacock");
		suspects.add("Prof. Plum");
	}
	
	static public Controlador getInstance(){
		if(instance == null)
			instance = new Controlador();
		return instance;
	}
	
	//Cria os personagens selecionados e inicia o jogo
	public void SartGame(int[] indx_personagens){
		int cont = 0;
		Jogadores = new Personagem[indx_personagens.length];
		colorPiece = new Color[indx_personagens.length];
		crtl_cartas = new Personagem[indx_personagens.length];
		
		for(int i:indx_personagens){
			Jogadores[cont] = new Personagem(personagens[i], new Casa(startRow[i], startCol[i]), startColor[i]);
			colorPiece[cont] = startColor[i];
			crtl_cartas[cont] = Jogadores[cont];
			cont++;
		}
		
		colocaNoEnvelope();
		distribuiCartas();
		
		nJogadores = cont;
		started = true;
		andou = false;
		current = 0;
		Frame.getInstance().getCrtlPanel().newTurn(Jogadores[current].getNome(), false);
	}
	
	//Move, se possivel, o jogador corrente para a casa selecionada
	public void moverPara(Casa dest){
		Crtl_Panel c = Frame.getInstance().getCrtlPanel();
		boolean validMove = true;
		int resp;
		int z;
		
		if(!andou){ //se o personagem ainda n‹o se movimentou
			
			for(int i = 0; i < Jogadores.length; i++){ //checa se ha algum oponente na casa escolhida.
				if(i != current){
					if(Jogadores[i].getPos().getCol() == dest.getCol() && Jogadores[i].getPos().getRow() == dest.getRow()){
						validMove = false;
					}
				}
			}

			resp = verificaEntrada(dest);
			if(resp != -1)
				validMove = true;
			
			if(validMove && pegouAtalho(dest)){
				
				c.setPalpite(true);
				Frame.getInstance().repaint();
				andou = true;
				
			}
			
			else
			{
				resp = Jogadores[current].getRoom();
				if(validMove == true && resp != -1)
				{
					ArrayList<Casa> ret = com[resp].getEntradas();
					for(z = 0; z < ret.size();z++)
					{
						if(Tabuleiro.getInstance().Movimento(c.getDado(), Jogadores[current],ret.get(z), dest))
						{	
							int local = verificaEntrada(dest);
							if(local == -1){
								c.setPalpite(false);
								Jogadores[current].setRoom(-1);
							}
							else{
								c.setPalpite(true);
								roomEntered(Jogadores[current], local);
							}
							
							Frame.getInstance().repaint();
							andou = true;
						}
					}
				}
				else			
				if(validMove && Tabuleiro.getInstance().Movimento(c.getDado(), Jogadores[current],Jogadores[current].getPos(), dest)){
					int local = verificaEntrada(dest);
					if(local == -1){
						c.setPalpite(false);
						Jogadores[current].setRoom(-1);
					}
					else{
						c.setPalpite(true);
						roomEntered(Jogadores[current], local);
					}
					Frame.getInstance().repaint();
					andou = true;
				}
			}			
		}
	}
	
	//Realiza a mudanca de turno
	public void nextTurn(){
		if(current == Jogadores.length - 1)
			current = 0;
		else
			current++;
		
		boolean palp;
		if(Jogadores[current].getRoom() != -1)
			palp = true;
		else
			palp = false;
		
		andou = false;
		Frame.getInstance().getCrtlPanel().newTurn(Jogadores[current].getNome(), palp);
	}
	
	//Verifica se a casa passada como parametro e uma porta de um comodo. Retorna o indice do comodo ou -1, se nao for uma porta
	private int verificaEntrada(Casa dest){
		
		for(int j = 0; j < com.length;j++)
		{
			ArrayList<Casa> ret = com[j].getEntradas();
			for(int k = 0;k < ret.size();k++)
			{
				if(ret.get(k).getCol() == dest.getCol() && ret.get(k).getRow() == dest.getRow()){
					return j;
				}
			}
		}
		return -1;
	}
	
	//Verifica se o jogador pegou um atalho e o move para a casa correspondente 
	private boolean pegouAtalho(Casa dest){
		
		int indx_dest = verificaEntrada(dest);
		
		if(indx_dest == 0 && Jogadores[current].getRoom() == 5){
			roomEntered(Jogadores[current], indx_dest);
			return true;
		}
		if(indx_dest == 5 && Jogadores[current].getRoom() == 0){
			roomEntered(Jogadores[current], indx_dest);
			return true;
		}
		if(indx_dest == 2 && Jogadores[current].getRoom() == 7){
			roomEntered(Jogadores[current], indx_dest);
			return true;
		}
		if(indx_dest == 7 && Jogadores[current].getRoom() == 2){
			roomEntered(Jogadores[current], indx_dest);
			return true;
		}
		
		return false;
	}
	
	//Checa as cartas de cada jogador em ordem para conferir o palpite
	//Retorna um String[] que contem as informacoes sobre quem mostrou a carta e qual foi a carta mostrada
	public String[] palpite(int indx_culpado, int indx_arma){
		
		int indx_comodo = Jogadores[current].getRoom();
		
		for(Personagem p:Jogadores){
			//checa se o jogador acusado esta no jogo e o move para o comodo de quem deu o palpite
			if(personagens[indx_culpado].compareToIgnoreCase(p.getNome()) == 0){
				if(p.getRoom() != indx_comodo){
					roomEntered(p, indx_comodo);
					break;
				}
			}
		}
		
		int i;
		
		for(i = 0; i < crtl_cartas.length; i++){
			if(Jogadores[current].getNome().compareTo(crtl_cartas[i].getNome()) == 0)
				break;
		}
		
		if(i == nJogadores - 1)
			i = 0;
		else
			i++;
		
		while(crtl_cartas[i].getNome().compareTo(Jogadores[current].getNome()) != 0){
			ArrayList<String> c = crtl_cartas[i].getCartas();
			for(String s:c){
				if(s.compareToIgnoreCase(armas[indx_arma]) == 0 || s.compareToIgnoreCase(personagens[indx_culpado]) == 0
						|| s.compareToIgnoreCase(comodos[indx_comodo]) == 0){
					String[] resp = new String[]{crtl_cartas[i].getNome()+" mostra ", s};
					Frame.getInstance().getCrtlPanel().addPalpite(resp[0]+resp[1]);
					return resp;
				}
			}
			
			if(i == nJogadores - 1)
				i = 0;
			else
				i++;
		}
		
		return null;
	}
	
	//Confere se a acusacao esta correta ou nao e retira o Jogador corrente se ele errou
	public boolean verificaAcusacao(String armS,String per,String loc){
		
		boolean aux = verificaEnvelope(armS,per,loc);
		
		if(aux == true) /* Venceu o jogo */
			return true;
		else
		{
			retiraJogador();  /* Perdeu */			
			return false;
		}
	}
	
	//Remove o jogador corrente do vetor Jogadores e atualiza a variavel current
	private void retiraJogador(){
		
		int i,j;
		Personagem[] aux = new Personagem[Jogadores.length - 1];
		Color[] color = new Color[colorPiece.length - 1];
		
		for(i = 0,j = 0; i < Jogadores.length;i++)
		{
			if(Jogadores[i] != Jogadores[current])
			{
				aux[j] = Jogadores[i];
				color[j] = colorPiece[i];
				j++;
			}
		}
		
		Jogadores = aux;	/* vetor de jogadores com 1 a menos */		
		colorPiece = color;
		
		if(current == 0)	/* Atualiza o corrente */
			current = Jogadores.length - 1;
		else
			current--;
	}
	
	//Confere as cartas do envelope
	public boolean verificaEnvelope(String armS,String per,String loc){
		
		boolean armcerto = false;
		boolean percerto = false;
		boolean loccerto = false;
		
		for(String s: envelope){
			if(s.compareToIgnoreCase(armS) == 0){
				armcerto = true;
				break;
			}
		}
		
		if(armcerto == false)
			return false;
		
		for(String s: envelope){
			if(s.compareToIgnoreCase(per) == 0){
				percerto = true;
				break;
			}
		}
			
		if(percerto == false)
			return false;
		
		for(String s: envelope){
			if(s.compareToIgnoreCase(loc) == 0){
				loccerto = true;
				break;
			}
		}
				
		if(loccerto == false)
			return false;
		

		return true;
	
	}
	
	//Muda a posicao do jogador para coloca-lo dentro do comodo indicado por "local"
	private void roomEntered(Personagem p, int local){

		int row=0 , col=0;
		
		if(local == 0){
			row = 4;
			col = 0;
		}
		else if(local == 1){
			row = 3;
			col = 9;
		}
		else if(local == 2){
			row = 4;
			col = 18;
		}
		else if(local == 3){
			row = 11;
			col = 18;
		}
		else if(local == 4){
			row = 17;
			col = 18;
		}
		else if(local == 5){
			row = 23;
			col = 18;
		}
		else if(local == 6){
			row = 22;
			col = 9;
		}
		else if(local == 7){
			row = 20;
			col = 1;
		}
		else{
			row = 14;
			col = 1;
		}
		
		for(int i = 0; i < crtl_cartas.length; i++){
			if(p.getNome().compareTo(crtl_cartas[i].getNome()) == 0){
				col+=i;
				break;
			}
		}
		
		p.setPos(new Casa(row, col));
		p.setRoom(local);
		
	}
	
	//Retorna as cartas do jogador corrente no formato de uma String
	public String getMinhasCartas(){
		String s = new String("Suas cartas sao: ");
		
		for(String aux: Jogadores[current].getCartas()){
			s = s.concat(aux + "; ");
		}
		
		return s;
	}
	
	//Embaralha os decks separados para colocar no envelope
	private void embaralha(){

		Collections.shuffle(weapons);
		Collections.shuffle(rooms);
		Collections.shuffle(suspects);

	}
	
	//Pega a primeira carta de cada deck e a coloca no envelope
	private void colocaNoEnvelope(){
		
		embaralha();
		envelope.add(weapons.get(0));
		envelope.add(suspects.get(0));
		envelope.add(rooms.get(0));
		
		//Descomente as linhas a seguir para exibir as cartas do envelope
		//System.out.println(weapons.get(0));
		//System.out.println(suspects.get(0));
		//System.out.println(rooms.get(0));
		
		weapons.remove(0);
		suspects.remove(0);
		rooms.remove(0);
		
	}
	
	//Distribui as cartas para os jogadores
	private void distribuiCartas(){
		
		int tam = Jogadores.length;			
		
		baralho.addAll(suspects);
		baralho.addAll(rooms);
		baralho.addAll(weapons);
		Collections.shuffle(baralho);
		
		while(baralho.size() != 0)
		{
			for(int j = 0; j < tam;j++)
			{
				if(baralho.size() == 0)
					break;
				else
				{
					Jogadores[j].recebeCarta(baralho.get(0));
					baralho.remove(0);
				}				
			}
			
		}
	}
	
	public boolean GameStarted(){
		return started;
	}
	
	public String[] getPersonagens(){
		return personagens;
	}
	
	public String[] getComodos(){
		return comodos;
	}
	
	public String[] getArmas(){
		return armas;
	}
	
	public Personagem[] getJogadores(){
		return Jogadores;
	}

	public ArrayList<String> getEnvelope(){
		return envelope;
	}
	
	public String getCurrentPlayer(){
		return Jogadores[current].getNome();
	}
	
	public Ellipse2D.Float[] getPieces(){
		Ellipse2D.Float[] e = new Ellipse2D.Float[Jogadores.length];
		for(int i = 0; i < Jogadores.length; i++){
			e[i] = Jogadores[i].getPiece();
		}
		return e;
	}
	
	public Color[] getPieceColor(){
		return colorPiece;
	}
	
	public int getminJogadores(){
		return minJog;
	}
	
	public Image getTabuleiro(){
		return Tabuleiro.getInstance().getTabuleiro();
	}
}

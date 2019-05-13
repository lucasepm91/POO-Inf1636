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

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Tabuleiro {

	private static Tabuleiro instance = null;
	private Image tabuleiro = null;
	private final int tab_width = 700;
	
	private Quadrado mat[][];
	private AStar aStar;
	private ArrayList<Quadrado> str;
	
	private Tabuleiro(){
		try
		{
			tabuleiro=ImageIO.read(new File("images/tabuleiro.jpg"));
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		int i;
		int j;

		mat = new Quadrado [25][24];
		str = new ArrayList<Quadrado>();

		for(i = 0; i < 25; i++)
			for(j = 0; j < 24; j++)
			{
				mat[i][j] = new Quadrado(i,j);
			}		

		CarregaTabuleiro();
		
	}
	
	public static Tabuleiro getInstance(){
		if(instance == null)
			instance = new Tabuleiro();
		return instance;
	}

	public Image getTabuleiro() {
		return tabuleiro;
	}

	public int getWidth(){
		return tab_width;
	}
	
	//Restaura a condicao inicial do tabuleiro
	private void PreparaQuadrado(){
        int i;
        int j;
        
        for(i = 0;i < 25;i++)
            for(j = 0;j < 24;j++)
            {
                mat[i][j].setCustoG(0);
                mat[i][j].setCustoH(9999);
                mat[i][j].setPai(null);
            }
    }
	
	//Calcula o menor caminho entre inicio e dest e determina se ele e permitido pelo limite do dado
	public boolean Movimento(int numDado, Personagem mov, Casa inicio, Casa dest){
		
		boolean pesquisaOk;
		int tam = 0;
		Quadrado origem = mat[inicio.getRow()][inicio.getCol()];
        Quadrado destino = mat[dest.getRow()][dest.getCol()];
        
        PreparaQuadrado();
        aStar = AStar.getInstance();
        aStar.setBusca(mat, origem, destino,str);

        System.gc();
        
        try{
      	  pesquisaOk = aStar.iniciarPesquisa();
        }
        	catch(Exception e){
          	  return false;
      		}
        		
		if(pesquisaOk == true){
			
			tam = aStar.getListaCaminho().size() - 1;
			if(tam == 0){
				aStar.LimpaAstar();
				this.aStar = null;
				return false;	/* origem = destino */
			}
			else{
				if(numDado < tam){
					aStar.LimpaAstar();
					this.aStar = null;
					return false;
				}
				else{
					mov.setPos(dest);
					aStar.LimpaAstar();
					this.aStar = null;
					return true;
				}				
			}
		}
		aStar.LimpaAstar();
		this.aStar = null;
		return false;
	}
	
	//Carrega os bloqueios de casa necessario para o funcionamento do algoritmo aStar
	private void CarregaTabuleiro(){

	/* carregar casas do centro invalidas */
	
	str.add(mat[10][10]);
	str.add(mat[11][10]);
	str.add(mat[12][10]);
	str.add(mat[13][10]);
	str.add(mat[14][10]);
	str.add(mat[15][10]);
	str.add(mat[16][10]);
	
	str.add(mat[10][11]);
	str.add(mat[11][11]);
	str.add(mat[12][11]);
	str.add(mat[13][11]);
	str.add(mat[14][11]);
	str.add(mat[15][11]);
	str.add(mat[16][11]);
	
	str.add(mat[10][12]);
	str.add(mat[11][12]);
	str.add(mat[12][12]);
	str.add(mat[13][12]);
	str.add(mat[14][12]);
	str.add(mat[15][12]);
	str.add(mat[16][12]);
	
	str.add(mat[10][13]);
	str.add(mat[11][13]);
	str.add(mat[12][13]);
	str.add(mat[13][13]);
	str.add(mat[14][13]);
	str.add(mat[15][13]);
	str.add(mat[16][13]);
	
	str.add(mat[10][14]);
	str.add(mat[11][14]);
	str.add(mat[12][14]);
	str.add(mat[13][14]);
	str.add(mat[14][14]);
	str.add(mat[15][14]);
	str.add(mat[16][14]);

	
	/* Carregar casas invalidas no lado esquerdo do tabuleiro */
	
	str.add(mat[0][0]);
	str.add(mat[1][0]);
	str.add(mat[2][0]);
	str.add(mat[3][0]);
	str.add(mat[4][0]);
	str.add(mat[5][0]);
	str.add(mat[6][0]);
	
	str.add(mat[8][0]);
	str.add(mat[9][0]);
	str.add(mat[10][0]);
	str.add(mat[11][0]);
	str.add(mat[12][0]);
	str.add(mat[13][0]);
	str.add(mat[14][0]);
	str.add(mat[15][0]);
	str.add(mat[16][0]);
	
	str.add(mat[18][0]);
	str.add(mat[19][0]);
	str.add(mat[20][0]);
	str.add(mat[21][0]);
	str.add(mat[22][0]);
	str.add(mat[23][0]);
	str.add(mat[24][0]);


	/* Carregar casas invalidas no lado direito do tabuleiro */
	
	str.add(mat[0][23]);
	str.add(mat[1][23]);
	str.add(mat[2][23]);
	str.add(mat[3][23]);
	str.add(mat[4][23]);
	str.add(mat[5][23]);
	
	str.add(mat[7][23]);		
	str.add(mat[8][23]);
	str.add(mat[9][23]);
	str.add(mat[10][23]);
	str.add(mat[11][23]);
	str.add(mat[12][23]);
	str.add(mat[13][23]);
	str.add(mat[14][23]);
	str.add(mat[15][23]);
	str.add(mat[16][23]);		
	str.add(mat[17][23]);
	str.add(mat[18][23]);
	
	str.add(mat[20][23]);
	str.add(mat[21][23]);
	str.add(mat[22][23]);
	str.add(mat[23][23]);
	str.add(mat[24][23]);
	

	/* Carregar casas invalidas na parte de cima do tabuleiro */
	
	str.add(mat[0][0]);
	str.add(mat[0][1]);
	str.add(mat[0][2]);
	str.add(mat[0][3]);
	str.add(mat[0][4]);
	str.add(mat[0][5]);		
	str.add(mat[0][6]);		
	str.add(mat[0][7]);
	str.add(mat[0][8]);
	
	str.add(mat[0][10]);
	str.add(mat[0][11]);
	str.add(mat[0][12]);
	str.add(mat[0][13]);
	
	str.add(mat[0][15]);
	str.add(mat[0][16]);
	str.add(mat[0][17]);		
	str.add(mat[0][18]);
	str.add(mat[0][19]);		
	str.add(mat[0][20]);
	str.add(mat[0][21]);
	str.add(mat[0][22]);
	str.add(mat[0][23]);		

	
	/* Carregar casas invalidas na parte de baixo do tabuleiro */
	
	str.add(mat[24][0]);
	str.add(mat[24][1]);
	str.add(mat[24][2]);
	str.add(mat[24][3]);
	str.add(mat[24][4]);
	str.add(mat[24][5]);		
	str.add(mat[24][6]);
	
	str.add(mat[24][8]);
	str.add(mat[24][9]);		
	str.add(mat[24][10]);
	str.add(mat[24][11]);
	str.add(mat[24][12]);
	str.add(mat[24][13]);		
	str.add(mat[24][14]);
	str.add(mat[24][15]);
	
	str.add(mat[24][17]);		
	str.add(mat[24][18]);
	str.add(mat[24][19]);		
	str.add(mat[24][20]);
	str.add(mat[24][21]);
	str.add(mat[24][22]);
	str.add(mat[24][23]);

	
	/* Carregar duas invalidas na parte de cima do tabuleiro */
	
	str.add(mat[1][6]);
	str.add(mat[1][17]);

	/* Carregar casas lounge no tabuleiro */
	
	str.add(mat[19][1]);
	str.add(mat[19][2]);
	str.add(mat[19][3]);
	str.add(mat[19][4]);
	str.add(mat[19][5]);
	
	str.add(mat[20][1]);		
	str.add(mat[20][2]);		
	str.add(mat[20][3]);
	str.add(mat[20][4]);
	str.add(mat[20][5]);
	str.add(mat[20][6]);
	
	str.add(mat[21][1]);		
	str.add(mat[21][2]);		
	str.add(mat[21][3]);
	str.add(mat[21][4]);
	str.add(mat[21][5]);
	str.add(mat[21][6]);
	
	str.add(mat[22][1]);		
	str.add(mat[22][2]);		
	str.add(mat[22][3]);
	str.add(mat[22][4]);
	str.add(mat[22][5]);
	str.add(mat[22][6]);
	
	str.add(mat[23][1]);		
	str.add(mat[23][2]);		
	str.add(mat[23][3]);
	str.add(mat[23][4]);
	str.add(mat[23][5]);
	str.add(mat[23][6]);


	/* Carregar casas study no tabuleiro */
	
	str.add(mat[21][18]);		
	str.add(mat[21][19]);		
	str.add(mat[21][20]);
	str.add(mat[21][21]);
	str.add(mat[21][22]);		
	
	str.add(mat[22][17]);		
	str.add(mat[22][18]);		
	str.add(mat[22][19]);
	str.add(mat[22][20]);
	str.add(mat[22][21]);
	str.add(mat[22][22]);
	
	str.add(mat[23][17]);		
	str.add(mat[23][18]);		
	str.add(mat[23][19]);
	str.add(mat[23][20]);
	str.add(mat[23][21]);
	str.add(mat[23][22]);

	
	/* Carregar casas billiardroom no tabuleiro */
	
	str.add(mat[8][18]);		
	str.add(mat[8][19]);		
	str.add(mat[8][20]);
	str.add(mat[8][21]);
	str.add(mat[8][22]);		
			
	str.add(mat[9][19]);		
	str.add(mat[9][20]);
	str.add(mat[9][21]);
	str.add(mat[9][22]);
	
	str.add(mat[10][18]);		
	str.add(mat[10][19]);		
	str.add(mat[10][20]);
	str.add(mat[10][21]);
	str.add(mat[10][22]);
	
	str.add(mat[11][18]);		
	str.add(mat[11][19]);		
	str.add(mat[11][20]);
	str.add(mat[11][21]);
	str.add(mat[11][22]);
	
	str.add(mat[12][18]);		
	str.add(mat[12][19]);		
	str.add(mat[12][20]);
	str.add(mat[12][21]);		


	/* Carregar casas hall no tabuleiro */
	
	str.add(mat[18][9]);		
	str.add(mat[18][10]);
	str.add(mat[18][13]);
	str.add(mat[18][14]);
	
	str.add(mat[19][9]);
	str.add(mat[19][10]);
	str.add(mat[19][11]);
	str.add(mat[19][12]);
	str.add(mat[19][13]);
	str.add(mat[19][14]);
	
	str.add(mat[20][9]);		
	str.add(mat[20][10]);		
	str.add(mat[20][11]);
	str.add(mat[20][12]);
	str.add(mat[20][13]);		
	
	str.add(mat[21][9]);		
	str.add(mat[21][10]);		
	str.add(mat[21][11]);
	str.add(mat[21][12]);
	str.add(mat[21][13]);
	str.add(mat[21][14]);
	
	str.add(mat[22][9]);		
	str.add(mat[22][10]);		
	str.add(mat[22][11]);
	str.add(mat[22][12]);
	str.add(mat[22][13]);
	str.add(mat[22][14]);
	
	str.add(mat[23][9]);		
	str.add(mat[23][10]);		
	str.add(mat[23][11]);
	str.add(mat[23][12]);
	str.add(mat[23][13]);
	str.add(mat[23][14]);
	
	
	/* Carregar casas kitchen no tabuleiro */
	
	str.add(mat[1][1]);		
	str.add(mat[1][2]);		
	str.add(mat[1][3]);
	str.add(mat[1][4]);
	str.add(mat[1][5]);
	
	str.add(mat[2][1]);		
	str.add(mat[2][2]);		
	str.add(mat[2][3]);
	str.add(mat[2][4]);
	str.add(mat[2][5]);
	
	str.add(mat[3][1]);		
	str.add(mat[3][2]);		
	str.add(mat[3][3]);
	str.add(mat[3][4]);
	str.add(mat[3][5]);
	
	str.add(mat[4][1]);		
	str.add(mat[4][2]);		
	str.add(mat[4][3]);
	str.add(mat[4][4]);
	str.add(mat[4][5]);
	
	str.add(mat[5][1]);		
	str.add(mat[5][2]);		
	str.add(mat[5][3]);
	str.add(mat[5][4]);
	str.add(mat[5][5]);
	
	str.add(mat[6][1]);		
	str.add(mat[6][2]);		
	str.add(mat[6][3]);		
	str.add(mat[6][5]);
	

	/* Carregar casas conservatory no tabuleiro */
	
	str.add(mat[1][18]);		
	str.add(mat[1][19]);		
	str.add(mat[1][20]);
	str.add(mat[1][21]);
	str.add(mat[1][22]);
	
	str.add(mat[2][18]);		
	str.add(mat[2][19]);		
	str.add(mat[2][20]);
	str.add(mat[2][21]);
	str.add(mat[2][22]);
	
	str.add(mat[3][18]);		
	str.add(mat[3][19]);		
	str.add(mat[3][20]);
	str.add(mat[3][21]);
	str.add(mat[3][22]);
	
	str.add(mat[4][18]);		
	str.add(mat[4][19]);		
	str.add(mat[4][20]);
	str.add(mat[4][21]);
	str.add(mat[4][22]);
					
	str.add(mat[5][20]);
	str.add(mat[5][21]);
	str.add(mat[5][22]);

	
	/* Carregar casas library no tabuleiro */
			
	str.add(mat[14][18]);		
	str.add(mat[14][19]);		
	str.add(mat[14][21]);
	str.add(mat[14][22]);
	
	str.add(mat[15][17]);
	str.add(mat[15][18]);		
	str.add(mat[15][19]);		
	str.add(mat[15][20]);
	str.add(mat[15][21]);
	str.add(mat[15][22]);
			
	str.add(mat[16][18]);		
	str.add(mat[16][19]);		
	str.add(mat[16][20]);
	str.add(mat[16][21]);
	str.add(mat[16][22]);
	
	str.add(mat[17][17]);
	str.add(mat[17][18]);		
	str.add(mat[17][19]);		
	str.add(mat[17][20]);
	str.add(mat[17][21]);
	str.add(mat[17][22]);
			
	str.add(mat[18][18]);		
	str.add(mat[18][19]);
	str.add(mat[18][20]);
	str.add(mat[18][21]);
	str.add(mat[18][22]);


	/* Carregar casas dinningroom no tabuleiro */
	
	str.add(mat[9][1]);		
	str.add(mat[9][2]);		
	str.add(mat[9][3]);
	str.add(mat[9][4]);
	
	str.add(mat[10][1]);		
	str.add(mat[10][2]);		
	str.add(mat[10][3]);
	str.add(mat[10][4]);
	str.add(mat[10][5]);
	str.add(mat[10][6]);
	str.add(mat[10][7]);
	
	str.add(mat[11][1]);		
	str.add(mat[11][2]);		
	str.add(mat[11][3]);
	str.add(mat[11][4]);
	str.add(mat[11][5]);
	str.add(mat[11][6]);
	str.add(mat[11][7]);
	
	str.add(mat[12][1]);		
	str.add(mat[12][2]);		
	str.add(mat[12][3]);
	str.add(mat[12][4]);
	str.add(mat[12][5]);
	str.add(mat[12][6]);		
	
	str.add(mat[13][1]);		
	str.add(mat[13][2]);		
	str.add(mat[13][3]);
	str.add(mat[13][4]);
	str.add(mat[13][5]);
	str.add(mat[13][6]);
	str.add(mat[13][7]);
	
	str.add(mat[14][1]);		
	str.add(mat[14][2]);		
	str.add(mat[14][3]);
	str.add(mat[14][4]);
	str.add(mat[14][5]);
	str.add(mat[14][6]);
	str.add(mat[14][7]);
	
	str.add(mat[15][1]);		
	str.add(mat[15][2]);		
	str.add(mat[15][3]);
	str.add(mat[15][4]);
	str.add(mat[15][5]);		
	str.add(mat[15][7]);
	
	
	/* Carregar casas ballroom no tabuleiro */
				
	str.add(mat[1][10]);
	str.add(mat[1][11]);
	str.add(mat[1][12]);
	str.add(mat[1][13]);		
	
	str.add(mat[2][8]);		
	str.add(mat[2][9]);		
	str.add(mat[2][10]);
	str.add(mat[2][11]);
	str.add(mat[2][12]);
	str.add(mat[2][13]);
	str.add(mat[2][14]);
	str.add(mat[2][15]);
	
	str.add(mat[3][8]);		
	str.add(mat[3][9]);		
	str.add(mat[3][10]);
	str.add(mat[3][11]);
	str.add(mat[3][12]);
	str.add(mat[3][13]);
	str.add(mat[3][14]);
	str.add(mat[3][15]);
	
	str.add(mat[4][8]);		
	str.add(mat[4][9]);		
	str.add(mat[4][10]);
	str.add(mat[4][11]);
	str.add(mat[4][12]);
	str.add(mat[4][13]);
	str.add(mat[4][14]);
	str.add(mat[4][15]);		
		
	str.add(mat[5][9]);		
	str.add(mat[5][10]);
	str.add(mat[5][11]);
	str.add(mat[5][12]);
	str.add(mat[5][13]);
	str.add(mat[5][14]);		
	
	str.add(mat[6][8]);		
	str.add(mat[6][9]);		
	str.add(mat[6][10]);
	str.add(mat[6][11]);
	str.add(mat[6][12]);
	str.add(mat[6][13]);
	str.add(mat[6][14]);
	str.add(mat[6][15]);
	
	str.add(mat[7][8]);				
	str.add(mat[7][10]);
	str.add(mat[7][11]);
	str.add(mat[7][12]);
	str.add(mat[7][13]);		
	str.add(mat[7][15]);
	

	/* So sobraram casas dos tipos corredor e entrada */
	
	}
	
}
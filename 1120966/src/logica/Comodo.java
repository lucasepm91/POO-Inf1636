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

import java.util.ArrayList;

public class Comodo{

	private String nome; //Nome do comodo
	private ArrayList<Casa> entradas = new ArrayList<Casa>(); //Lista das portas de cada comodo

	public Comodo(String name){
		
		nome = new String(name);
		

		if(nome.contentEquals("kitchen") == true){			
			entradas.add(new Casa(6,4));
		}
		else if(nome.contentEquals("conservatory") == true){
    	entradas.add(new Casa(5,19));
    	}
		else if(nome.contentEquals("study") == true){
			entradas.add(new Casa(21,17));
		}
		else if(nome.contentEquals("lounge") == true){
			entradas.add(new Casa(19,6));
		}
		else if(nome.contentEquals("library") == true){
			entradas.add(new Casa(14,20));
			entradas.add(new Casa(16,17));				    			    
		}
		else if(nome.contentEquals("hall") == true){
			entradas.add(new Casa(18,11));
			entradas.add(new Casa(18,12));
			entradas.add(new Casa(20,14));
		}
		else if(nome.contentEquals("billiardroom") == true){
			entradas.add(new Casa(9,18));
			entradas.add(new Casa(12,22));					    					    
		}
		else if(nome.contentEquals("dinningroom") == true){
			entradas.add(new Casa(12,7));
			entradas.add(new Casa(15,6));												
		}
		else if(nome.contentEquals("ballroom") == true){
			entradas.add(new Casa(5,8));
			entradas.add(new Casa(5,15));
			entradas.add(new Casa(7,9));
			entradas.add(new Casa(7,14));														
		}

		
	}

	public String getNomeComodo(){

		return nome;
	}

	public ArrayList<Casa> getEntradas(){
	
		return entradas;
	}

}
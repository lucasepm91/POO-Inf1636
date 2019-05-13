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

import java.awt.Color;
import java.awt.geom.*;
import java.util.ArrayList;


public class Personagem {

	private String nome;
	private Casa pos;
	private ArrayList<String> cartas = new ArrayList<String>();
	private Ellipse2D.Float piece;
	private Color pieceColor;
	private int room_index;
	
	public Personagem(String n, Casa c, Color col){
		
		nome = n;
		pos = c;
		pieceColor = col;
		piece = new Ellipse2D.Float(c.getPixelColMin(),c.getPixelRowMin(), c.getWidth(), c.getHeigth());
		room_index = -1;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Casa getPos() {
		return pos;
	}

	public void setPos(Casa pos) {
		this.pos = pos;
		piece.setFrame(pos.getPixelColMin(),pos.getPixelRowMin(), pos.getWidth(), pos.getHeigth());
	}

	public Ellipse2D.Float getPiece() {
		return piece;
	}

	public Color getPieceColor() {
		return pieceColor;
	}

	public void setPieceColor(Color pieceColor) {
		this.pieceColor = pieceColor;
	}
	
	public void recebeCarta(String c){
		cartas.add(c);
	}
	
	public ArrayList<String> getCartas(){
		return cartas;
	}

	public int getRoom(){
		return room_index;
	}
	
	public void setRoom(int room){
		room_index = room;
	}
}

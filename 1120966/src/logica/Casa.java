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

public class Casa {

	private int row, col;
	private float pixel_row_min,pixel_row_max, pixel_col_min, pixel_col_max;
	private float width = 25, height = 25;
	private int offset_x = 50, offset_y = 50;
	
	public Casa(int row, int col){
		
		this.setPosition(row, col);
		
	}
	
	public void setPosition(int row, int col) {
		this.row = row;
		this.col = col;
		
		pixel_col_min = (col*width) + offset_x;
		pixel_row_min = (row*height) + offset_y;
		
		
		pixel_row_max = pixel_row_min + height;
		pixel_col_max = pixel_col_min + width;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeigth(){
		return height;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public float getPixelRowMax() {
		return pixel_row_max;
	}

	public float getPixelColMax() {
		return pixel_col_max;
	}
	
	public float getPixelRowMin() {
		return pixel_row_min;
	}

	public float getPixelColMin() {
		return pixel_col_min;
	}


}

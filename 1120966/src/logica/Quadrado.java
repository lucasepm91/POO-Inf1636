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

public class Quadrado{

    private int custoG = 0;
    private int custoH = 9999;
    private int x = 0;
    private int y = 0;
    private Quadrado pai = null;

    public Quadrado(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getCustoF() {
        return custoG + custoH;
    }

    public int getCustoG() {
        return custoG;
    }

    public void setCustoG(int custoG) {
        this.custoG = custoG;
    }

    public int getCustoH() {
        return custoH;
    }

    public void setCustoH(int custoH) {
        this.custoH = custoH;
    }

    public int getX() {
        return x;
    }

    public void setPosicaoX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setPosicaoY(int y) {
        this.y = y;
    }

    public Quadrado getPai() {
        return pai;
    }

    public void setPai(Quadrado pai) {
        this.pai = pai;
    }

    public boolean equals(Object obj) {
        return (((Quadrado)obj).getX() == x && ((Quadrado)obj).getY()==y);
    }

}

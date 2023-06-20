package it.polito.tdp.PremierLeague.model;

public class Arco {

	private int playerID1;
	private int playerID2;
	private int peso;
	
	public Arco(int playerID1, int playerID2, int peso) {
		super();
		this.playerID1 = playerID1;
		this.playerID2 = playerID2;
		this.peso = peso;
	}

	public int getPlayerID1() {
		return playerID1;
	}

	public void setPlayerID1(int playerID1) {
		this.playerID1 = playerID1;
	}

	public int getPlayerID2() {
		return playerID2;
	}

	public void setPlayerID2(int playerID2) {
		this.playerID2 = playerID2;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
	
}

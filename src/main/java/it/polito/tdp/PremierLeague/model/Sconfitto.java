package it.polito.tdp.PremierLeague.model;

public class Sconfitto  implements Comparable<Sconfitto>{

	private Player p;
	private Integer peso;
	
	
	public Sconfitto(Player p, Integer peso) {
		super();
		this.p = p;
		this.peso = peso;
	}


	public Player getP() {
		return p;
	}


	public void setP(Player p) {
		this.p = p;
	}


	public Integer getPeso() {
		return peso;
	}


	public void setPeso(Integer peso) {
		this.peso = peso;
	}


	@Override
	public int compareTo(Sconfitto other) {
		return -1*(this.peso - other.getPeso());
	}


	@Override
	public String toString() {
		return ""+p + " | " + peso;
	}
	
	
	
	
	
	
	
}

package it.polito.tdp.PremierLeague.model;

import java.util.List;

public class TopPlayer{

	Player topPlayer;
	List<Sconfitto> sconfitti;
	
	public TopPlayer(Player topPlayer, List<Sconfitto> sconfitti) {
		super();
		this.topPlayer = topPlayer;
		this.sconfitti = sconfitti;
	}
	public Player getTopPlayer() {
		return topPlayer;
	}
	public void setTopPlayer(Player topPlayer) {
		this.topPlayer = topPlayer;
	}
	public List<Sconfitto> getSconfitti() {
		return sconfitti;
	}
	public void setSconfitti(List<Sconfitto> sconfitti) {
		this.sconfitti = sconfitti;
	}	
	
}

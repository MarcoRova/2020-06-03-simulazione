package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	
	private Graph<Player, DefaultWeightedEdge> grafo;
	
	private Map<Integer, Player> playersIDMap;
	
	private List<Player> dreamTeam;
	private Integer bestDegree;
	
	public Model() {
		super();
		
		this.dao = new PremierLeagueDAO();
	}
	
	public void creaGrafo(double x) {
		
		this.grafo = new SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		this.playersIDMap = new HashMap<>();
		
		this.loadIDMap();
		
		Graphs.addAllVertices(this.grafo, this.dao.getVertexes(x));
		
		List<Arco> archi = this.dao.getEdges(this.dao.getVertexes(x));
		
		for(Arco a : archi) {
			
			int peso = a.getPeso();
			
			if(peso > 0) {
				Graphs.addEdgeWithVertices(this.grafo, this.playersIDMap.get(a.getPlayerID1()), this.playersIDMap.get(a.getPlayerID2()), peso);
			}
			else if(peso < 0) {
				Graphs.addEdgeWithVertices(this.grafo, this.playersIDMap.get(a.getPlayerID2()), this.playersIDMap.get(a.getPlayerID1()), -peso);
			}
		}
		
	}
	
	public Graph<Player, DefaultWeightedEdge> getGrafo(){
		return this.grafo;
	}
	
	public String infoGrafo() {
		return "Numero vertici: "+this.grafo.vertexSet().size() + " Numero archi: "+this.grafo.edgeSet().size();
	}
	
	public void loadIDMap() {
		List<Player> players = this.dao.listAllPlayers();
		
		for(Player p: players) {
			this.playersIDMap.put(p.getPlayerID(), p);
		}		
	}
	
	public TopPlayer getTopPlayer(double x) {
		
		Player bestP = null;
		int best = 0;
		List<Sconfitto> listaSconfitti = new LinkedList<>();
		
		for(Player p : this.dao.getVertexes(x)) {
			
			int sconfitti = this.grafo.outDegreeOf(p);
			if(sconfitti > best) {
				best = sconfitti;
				bestP = p;
			}
		}
		
		for(DefaultWeightedEdge edge : grafo.outgoingEdgesOf(bestP)) {
			listaSconfitti.add(new Sconfitto(grafo.getEdgeTarget(edge), (int) grafo.getEdgeWeight(edge)));
		}
		Collections.sort(listaSconfitti);
		return new TopPlayer(bestP, listaSconfitti);
	}
	
	
	public List<Player> getDreamTeam(int k){
		
		this.bestDegree = 0;
		this.dreamTeam = new ArrayList<>();
		List<Player> parziale = new ArrayList<>();
		
		ricorsione(parziale, new ArrayList<Player>(this.grafo.vertexSet()), k);
		
		return dreamTeam;
		
	}

	public void ricorsione(List<Player> parziale, List<Player> players, int k) {
		
		if(parziale.size() == k) {
			
			int degree = getDegree(parziale);
			
			if(degree > bestDegree) {
				dreamTeam = new ArrayList<>(parziale);
				bestDegree = degree;
			}
			return;	
		}
		
		for(Player p : players) {
			if(!parziale.contains(p)) {
				parziale.add(p);
				//i "battuti" di p non possono più essere considerati
				List<Player> remainingPlayers = new ArrayList<Player>(players);
				remainingPlayers.removeAll(Graphs.successorListOf(this.grafo, p));
				ricorsione(parziale, remainingPlayers, k);
				parziale.remove(p);	
			}
		}
		
	}
	
	private int getDegree(List<Player> team) {
		int degree = 0;
		int in;
		int out;

		for(Player p : team) {
			in = 0;
			out = 0;
			for(DefaultWeightedEdge edge : this.grafo.incomingEdgesOf(p))
				in += (int) this.grafo.getEdgeWeight(edge);
			
			for(DefaultWeightedEdge edge : grafo.outgoingEdgesOf(p))
				out += (int) grafo.getEdgeWeight(edge);
		
			degree += (out-in);
		}
		return degree;
	}
	
	public Integer getBestDegree() {
		return bestDegree;
	}

}

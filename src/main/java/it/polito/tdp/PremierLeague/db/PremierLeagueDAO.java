package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Arco;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Player> getVertexes(double x){
		
		String sql = "SELECT p.PlayerID, p.Name, SUM(a.Goals)/COUNT(a.PlayerID) AS m "
				+ "FROM actions a, players p "
				+ "WHERE p.PlayerID = a.PlayerID "
				+ "GROUP BY p.PlayerID "
				+ "HAVING m > ?";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, x);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Arco> getEdges(List<Player> vertici) {
		
		String sql = "SELECT A1.PlayerID AS id1, A2.PlayerID AS id2, (SUM(A1.TimePlayed) - SUM(A2.TimePlayed)) AS peso " + 
				"FROM 	Actions A1, Actions A2 " + 
				"WHERE A1.TeamID != A2.TeamID " + 
				"	AND A1.MatchID = A2.MatchID " + 
				"	AND A1.starts = 1 AND A2.starts = 1 " + 
				"	AND A1.PlayerID > A2.PlayerID " + 
				"GROUP BY A1.PlayerID,A2.PlayerID";
		
		List<Arco> result = new ArrayList<Arco>();
		Map<Integer, Player> map = new HashMap<>();
		
		for(Player p : vertici) {
			map.put(p.getPlayerID(), p);
		}
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				int id1 = res.getInt("id1");
				int id2 = res.getInt("id2");
				
				if(map.containsKey(id1) && map.containsKey(id2)) {
					Arco a = new Arco(res.getInt("id1"), res.getInt("id2"), res.getInt("peso"));
					result.add(a);
				}
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

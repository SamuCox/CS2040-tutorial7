import java.util.*;

class Player implements Comparable<Player> {

	private String _name;
	private int _score;
	private boolean _isEliminated;

	public Player(String name, int score) {_name = name; _score = score;}
	public int compareTo(Player other) { // 1: score ASC, 2: name ASC
		if (_score != other._score) return _score - other._score;
		return _name.compareTo(other._name);
	}
	public boolean equals(Object other) { // match by name ONLY
		if (!(other instanceof Player)) return false;
		return ((Player) other)._name.equals(_name);
	}
	public String toString() {
		char elim = (_isEliminated ? 'X' : ' ');
		return "" + elim + _score + " : " + _name + elim;
	}

	public String getName() { return _name; }
	public int getScore() { return _score; }
	public boolean isEliminated() { return _isEliminated; }

	public void eliminate() { _isEliminated = true; }
}


class RemainingPlayers {
	private HashMap<String, Player> _nameToPlayer;
	private PriorityQueue<Player> _scores;

	public RemainingPlayers() {
		_nameToPlayer = new HashMap<String, Player>();
		_scores = new PriorityQueue<Player>();
	}

	/************** ORIGINAL METHODS WE NEED TO IMPROVE ******************/
	// ORIGINAL addScore method which we need to improve:
	public void originalAddScore(String name, int toAdd) {
		if (!_nameToPlayer.containsKey(name)) { // Add new player
			Player newPlayer = new Player(name, toAdd);
			_nameToPlayer.put(name, newPlayer);
			_scores.offer(newPlayer);
			return;
		}	
		// Update existing player
		Player old = _nameToPlayer.get(name),
			newPlayer = new Player(old.getName(), old.getScore() + toAdd);
		_scores.remove(old); // O(N) bottleneck
		_scores.offer(newPlayer);
		_nameToPlayer.put(name, newPlayer);
	}
	// ORIGINAL eliminatePlayer method which we need to improve:
	public Player originalEliminatePlayer() {
		Player toEliminate = _scores.poll(); // O(log N)
		toEliminate.eliminate();
		return toEliminate;
	}

	/************** MODIFIED METHODS *************************************/
	// MODIFIED addScore method
	public void addScore(String name, int toAdd) {
		Player old = _nameToPlayer.get(name);
		if (old != null) { // Lazily delete existing player
	    	toAdd += old.getScore();
	    	old.eliminate(); // Mark, still in PriorityQueue - O(N) bottleneck resolved
		}
		Player newPlayer = new Player(name, toAdd);
		_scores.offer(newPlayer); // O(log N) https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html#offer(E)
		_nameToPlayer.put(name, newPlayer); // https://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html#put(K,%20V)
	}

	// MODIFIED eliminatePlayer method
	public Player eliminatePlayer() {
		while (_scores.size() > 0) { // O(log N) average == O(23 log N)
	    	Player toEliminate = _scores.poll(); // O(log N)
	    	if (toEliminate.isEliminated()) continue; // Already "deleted"
	    	toEliminate.eliminate();
	    	return toEliminate;
		}
		return null;
	}

	// Seldom called; No particular ordering
	public List<Player> listRemainingPlayers() {
		ArrayList<Player> list = new ArrayList<Player>();
		for (Player player : _nameToPlayer.values()) // O(N)
			if (!player.isEliminated())
				list.add(player);
		return list;
	}
}

class tut8q1 {

	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

	public static void main(String[] args) {
		int min = 0;
		int max = 100;
		String[] myStrings = { "One", "Two", "Three", "four", "five", "six", "seven", "eight",
		 "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
		 "eighteen", "nineteen", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

		RemainingPlayers remaining = new RemainingPlayers();
		for (int i = 0; i < myStrings.length ;i++) {
			int score = randInt(min, max);
			remaining.addScore(myStrings[i], score); // Add player
		}

		System.out.println(remaining.listRemainingPlayers());

	}
}






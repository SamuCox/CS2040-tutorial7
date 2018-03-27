import java.util.*;


class User implements Comparable<User> {
	private int _score; private String _name;
	public User(String name){ _name = name; }
	public int getScore(){ return _score; }
	public String getName(){ return _name; }
	public String toString(){ return _name + "\t" + _score; } 
	public void addScore(int scoreToAdd) { _score += scoreToAdd; } 
	public int compareTo(User o){return _name.compareTo(o._name);}
}


class UserStatistics { // For pranklist and other user-related stats

	private TreeSet<User> _users; // efficient search
	private HashMap<String, User> _nameToUser; // efficient name lookup

	public UserStatistics() {
	     // don't use natural ordering; use comparator from anonymous class
	     _users = new TreeSet<User>(new Comparator<User>() {
	          public int compare(User left, User right) {
	               if (left.getScore() != right.getScore())
	                    return right.getScore() - left.getScore();
	               return left.compareTo(right);
			} 
		});
		_nameToUser = new HashMap<String, User>();
	}

	public boolean addUser(String userName) {
	    if (_nameToUser.containsKey(userName)) return false;
	    User newUser = new User(userName);
	    _users.add(newUser);
	    _nameToUser.put(userName, newUser);
	    return true;
	}

	public void addUserScore(String userName, int scoreToAdd){
	    User match = _nameToUser.get(userName);
	    _users.remove(match);
	    match.addScore(scoreToAdd);
	    if (match.getScore() < 0) match.addScore(-match.getScore());
	    _users.add(match);
	}

	public List<User> listPranklistFor(String userName){
	    LinkedList<User> prankList = new LinkedList<User>();
	    User lower = _nameToUser.get(userName);
	    prankList.add(lower);
	    for (int i = 0; i < 5; i++) {
	        lower = _users.lower(lower);
	        if (lower == null) break;
	        prankList.addFirst(lower);
	    }
	    User higher = _nameToUser.get(userName);
	    for (int i = 0; i < 2; i++) {
	        higher = _users.higher(higher);
	        if (higher == null) break;
	        prankList.add(higher);
		}
		return prankList;
	}
}


class tut7q3 {
    public static void main(String[] args) {
    	
    	String name = "Billy";
    	UserStatistics userStats = new UserStatistics();
   		
   		userStats.addUser(name);

   		System.out.println(userStats.listPranklistFor(name));

   		userStats.addUserScore(name, 21);

   		System.out.println(userStats.listPranklistFor(name));

   		userStats.addUserScore(name, -81);

   		System.out.println(userStats.listPranklistFor(name));
   }
}



import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FriendList {

	private ArrayList<Friend> listOfFriend;

	public FriendList() {
		listOfFriend = new ArrayList<Friend>();
	}

	public void addFriend(Friend friend) {
		listOfFriend.add(friend);
	}

	public void deleteFriend(int i) {
		listOfFriend.remove(i);
	}

	public int numFriends() {
		return listOfFriend.size();
	}

	public Friend getFriend(int i) {
		return listOfFriend.get(i);
	}

	public void makeFile() throws IOException {
		PrintWriter output = new PrintWriter("friendlist-norm.data");
		String friendLine;

		output.println(new String(
				"// Friend List\r\n" + "// Format::  Name : Group Number : Phone : email : profile picture\r\n"
						+ "// Group Number Family:1 , School:2 , Job 3 "));
		for (int i = 0; i < numFriends(); i++) {
			friendLine = new String(
					getFriend(i).getName() + ":" + getFriend(i).getGroup() + ":" + getFriend(i).getPhoneNumber() + ":"
							+ getFriend(i).getEmailAddress() + ":" + getFriend(i).getPhoto());
			output.println("//");
			output.println(friendLine);
		}
		output.close();
	}

	public boolean isNameOverlap(String name) {
		for (int i = 0; i < numFriends(); i++) {
			if (name.equals(getFriend(i).getName())) {
				return true;
			}
		}
		return false;
	}
}
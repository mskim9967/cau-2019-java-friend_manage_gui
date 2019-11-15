public class TestFriend {
	public static void main(String[] args) {
		FriendListFile friendListFile = new FriendListFile();
		FriendList friendList = new FriendList();

		friendList = friendListFile.readFileToList("friendlist-norm.data");
		
		FriendListGUI friendListGUI = new FriendListGUI(friendList);
	}
}

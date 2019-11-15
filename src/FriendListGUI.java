import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 * 친구목록을 출력하는 JFrame에 관한 정보들을 기술한 class
 * javax.swing.JFrame을 상속한다
 */
public class FriendListGUI extends JFrame {

	private final static int WIDTH = 1000, HEIGHT = 500, ADD = 0, DELETE = 1, MODIFY = 2, SAVE_FILE = 3;
	private final static String[] buttonNameArray = { "Add", "Delete", "Modify", "Save File" };

	private MyJPanel jpnButtons, jpnFriendList;
	private JButton[] jbtArray;
	//FriendComponents: Panel의 한 줄에 Friend정보를 출력할 component들을 변수로 가지는 class 
	private ArrayList<FriendComponents> friendComponentsArray;	
	private FriendList friendList;

	/**
	 * @brief JFrame을 초기화하고 창을 연다.
	 * @param FriendList friendList JFrame에서 보여줄 FriendList
	 */
	public FriendListGUI(FriendList friendList) {
		super();	//JFrame을 상속받는다
		this.friendList = friendList;
		this.setTitle("친구 목록");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new BorderLayout());
		
		friendComponentsArray = new ArrayList<FriendComponents>();	
		for (int i = 0; i < friendList.numFriends(); i++) {
			//Friend정보를 FriendComponets에 set하고 array에 추가시킨다
			addfriendComponentsArray(friendList.getFriend(i));	 
		}

		jbtArray = new JButton[buttonNameArray.length];	//Frame 우측에 붙을 Button들의 Array
		jpnButtons = new MyJPanel();	//Button들을 추가시킬 JPanel
		jpnButtons.setButtons(jbtArray, buttonNameArray);
		this.add(jpnButtons, BorderLayout.EAST);
		for (int i = 0; i < jbtArray.length; i++) {
			jbtArray[i].addActionListener(new Listener());
		}
		
		jpnFriendList = new MyJPanel();	//Frame 좌측에 붙을 FriendList의 정보들
		jpnFriendList.setFriendList(friendComponentsArray);
		this.add(jpnFriendList, BorderLayout.CENTER);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * @brief friendComponentsArray에 Friend의 정보가 담긴 FriendComponents를 추가시킨다
	 * @param Friend friend Array에 추가시킬 FriendComponents의 정보가 담긴 Friend
	 */
	public void addfriendComponentsArray(Friend friend) {
		FriendComponents temp = new FriendComponents();
		
		temp.jlbName.setText(friend.getName());
		temp.jlbName.setHorizontalAlignment(SwingConstants.CENTER);
		temp.jtfGroup.setText(friend.getGroup());
		temp.jtfPhoneNumber.setText(friend.getPhoneNumber());
		temp.jtfEmailAddress.setText(friend.getEmailAddress());
		temp.jlbPhoto.setText(friend.getPhoto());
		temp.jlbPhoto.setHorizontalAlignment(SwingConstants.CENTER);
		
		//각 component들에 actionListener를 추가한다
		temp.jcb.addActionListener(new Listener());
		temp.jtfGroup.addActionListener(new Listener());
		temp.jtfEmailAddress.addActionListener(new Listener());
		temp.jtfPhoneNumber.addActionListener(new Listener());

		friendComponentsArray.add(temp);
	}

	/**
	 * @brief FriendListGUI의 FriendList를 반환한다
	 */
	public FriendList getFriendList() {
		return friendList;
	}
	
	/**
	 * @brief AddFriendGUI에서 입력된 Friend를 FriendListGUI의 FriendList에 추가시킨다
	 * @param Friend friend FriendList에 추가시킬 Friend
	 */
	public void addToFriendList(Friend friend) {
		friendList.addFriend(friend);
	}

	/**
	 * @brief Frame 좌측에 위치한 jpnFriendList Panel을 업데이트하기 위해 Panel을 지우고 다시 생성한다.
	 */
	public void updateJpnFriendList() {
		this.remove(jpnFriendList);
		jpnFriendList = new MyJPanel();
		jpnFriendList.setFriendList(friendComponentsArray);
		this.add(jpnFriendList, BorderLayout.CENTER);
		this.setVisible(true);
	}

	/**
	 * interface인 ActionListener를 implements하여 components들의 event들을 처리하는 class
	 */
	class Listener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {

			//add버튼이 눌렸을 시
			if (e.getSource() == jbtArray[ADD]) {
				AddFriendGUI addFriendGUI = new AddFriendGUI(FriendListGUI.this);
			}

			//delete버튼이 눌렸을 시
			else if (e.getSource() == jbtArray[DELETE]) {
				for (int i = 0; i < friendComponentsArray.size(); i++) {
					//CheckBox가 선택됐을시에만 delete 진행
					if (friendComponentsArray.get(i).jcb.isSelected()) {	
						friendList.deleteFriend(i);
						friendComponentsArray.remove(i);
						JOptionPane.showMessageDialog(null, "Delete Finish");
						updateJpnFriendList();
						break;
					}
				}
			}

			//modify버튼이 눌렸을 시
			else if (e.getSource() == jbtArray[MODIFY]) {
				boolean normalDataFlag = false;
				int temp = 0;

				
				for (int i = 0; i < friendComponentsArray.size(); i++) {
					//CheckBox가 선택됐을시에만 modify 진행
					if (friendComponentsArray.get(i).jcb.isSelected()) {
						normalDataFlag = true;
						
						if (friendList.getFriend(i).isStringInt(friendComponentsArray.get(i).jtfGroup.getText())) {
							friendList.getFriend(i).setGroup(friendComponentsArray.get(i).jtfGroup.getText());
						} 
						else {
							JOptionPane.showMessageDialog(null, "Group is not a correct format(Only number)",
									"Format Error", JOptionPane.WARNING_MESSAGE);
							//잘못된 데이터 타입이 입력됐음으로 TextBox를 기존 data로 변경
							friendComponentsArray.get(i).jtfGroup.setText(friendList.getFriend(i).getGroup());
							normalDataFlag = false;
						}

						if (friendList.getFriend(i)
								.isStringPhoneNumberFormat(friendComponentsArray.get(i).jtfPhoneNumber.getText())) {
							friendList.getFriend(i).setPhoneNumber(friendComponentsArray.get(i).jtfPhoneNumber.getText());
						} 
						else {
							JOptionPane.showMessageDialog(null, "Phone number is not a correct format", "Format Error",
									JOptionPane.WARNING_MESSAGE);
							friendComponentsArray.get(i).jtfPhoneNumber.setText(friendList.getFriend(i).getPhoneNumber());
							normalDataFlag = false;
						}

						if (friendList.getFriend(i)
								.isStringEmailFormat(friendComponentsArray.get(i).jtfEmailAddress.getText())) {
							friendList.getFriend(i).setEmailAddress(friendComponentsArray.get(i).jtfEmailAddress.getText());
						} 
						else {
							JOptionPane.showMessageDialog(null, "Email address is not a correct format", "Format Error",
									JOptionPane.WARNING_MESSAGE);
							friendComponentsArray.get(i).jtfEmailAddress.setText(friendList.getFriend(i).getEmailAddress());
							normalDataFlag = false;
						}
						temp = i;
						break;
					}
				}
				if (normalDataFlag == true) {	//입력된 data가 올바른 형식이라면 modify 진행
					friendComponentsArray.get(temp).jcb.setSelected(false);
					friendComponentsArray.get(temp).setJtfEditible(false);					
					updateJpnFriendList();
					JOptionPane.showMessageDialog(null, "Modify Finish");
				}
			}

			//save file버튼이 눌렸을 시
			else if (e.getSource() == jbtArray[SAVE_FILE]) {
				try {
					friendList.makeFile();
				} catch (IOException e1) {
					System.out.println("file save Error!");
				}
				JOptionPane.showMessageDialog(null, "Save Finish");
			} 
			
			//어떠한 CheckBox가 클릭되었다면
			else {	
				updateJpnFriendList();
				
				for (int i = 0; i < friendComponentsArray.size(); i++) {		
					if (e.getSource() == friendComponentsArray.get(i).jcb) {

						//CheckBox가 선택되었다면 그 줄의 TextBox들을 수정 가능하게 변경
						if(friendComponentsArray.get(i).jcb.isSelected()==true) {
							friendComponentsArray.get(i).setJtfEditible(true);	
						}
						//CheckBox가 해제되었다면 그 줄의 TextBox들을 수정 불가능하게 변경
						else if(friendComponentsArray.get(i).jcb.isSelected()==false) {
							friendComponentsArray.get(i).setJtfEditible(false);
						}
						//클릭된 CheckBox열을 제외한 모든 열들의 TextBox들을 수정불가능하게 변경
						for (int j = 0; j < friendComponentsArray.size(); j++) {
							if (i != j) {
								friendComponentsArray.get(j).jcb.setSelected(false);
								friendComponentsArray.get(j).setJtfEditible(false);
								friendComponentsArray.get(j).jtfGroup.setText(friendList.getFriend(j).getGroup());
								friendComponentsArray.get(j).jtfPhoneNumber.setText(friendList.getFriend(j).getPhoneNumber());
								friendComponentsArray.get(j).jtfEmailAddress.setText(friendList.getFriend(j).getEmailAddress());	
							}
						}
						break;
					}
				}
			}
		}
	}
}

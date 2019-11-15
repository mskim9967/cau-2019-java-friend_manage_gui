import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * 친구목록에 친구를 추가하는 JFrame에 관한 정보들을 기술한 class
 * javax.swing.JFrame을 상속한다
 */
public class AddFriendGUI extends JFrame {

	private static final int WIDTH = 800, HEIGHT = 200, NAME = 0, GROUP = 1, PHONE = 2, EMAIL = 3, PHOTO = 4;
	private JButton jbtDone;
	private JTextField[] jtfFriendInfos;
	private MyJPanel jpnAddFriend;
	private Friend friend = new Friend();
	private FriendListGUI friendListGUI;
	private boolean normalDataFlag = true;

	/**
	 * @brief JFrame을 초기화하고 창을 연다.
	 * @param FriendListGUI friendListGUI AddFriendGUI Frame을 호출시킨 FriendListGUI의 정보
	 */
	public AddFriendGUI(FriendListGUI friendListGUI) {
		super();
		this.friendListGUI = friendListGUI;
		this.setTitle("추가할 친구 정보");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new BorderLayout());

		jbtDone = new JButton("Done");
		this.add(jbtDone, BorderLayout.EAST);
		jbtDone.addActionListener(new Listener());
 
		jtfFriendInfos = new JTextField[friend.getNumOfInfo()];	//정보를 입력할 TextBox의 ArrayList
		jpnAddFriend = new MyJPanel();	
		jpnAddFriend.setLayout(new GridLayout(2, 1));
		jpnAddFriend.setAddFriendTextField(jtfFriendInfos);
		for (int i = 0; i < jtfFriendInfos.length; i++) {
			jtfFriendInfos[i].addFocusListener(new FListener());
		}

		this.add(jpnAddFriend, BorderLayout.CENTER);
		this.setVisible(true);
	}

	/**
	 * interface인 ActionListener를 implements하여 components들의 event들을 처리하는 class
	 */
	class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//사용자가 입력을 마치고 done버튼이 눌렸을 시
			if (e.getSource() == jbtDone) {
				if(!friendListGUI.getFriendList().isNameOverlap(jtfFriendInfos[NAME].getText())) {
					friend.setName(jtfFriendInfos[NAME].getText());
				} else {
					JOptionPane.showMessageDialog(null, "Name is Overlapped", "경고 메시지",
							JOptionPane.WARNING_MESSAGE);
					normalDataFlag = false;
				}

				if (friend.isStringInt(jtfFriendInfos[GROUP].getText())) {
					friend.setGroup(jtfFriendInfos[GROUP].getText());
				} else {
					JOptionPane.showMessageDialog(null, "Group is not a correct format(Only number)", "경고 메시지",
							JOptionPane.WARNING_MESSAGE);
					normalDataFlag = false;
				}

				if (friend.isStringPhoneNumberFormat(jtfFriendInfos[PHONE].getText())) {
					friend.setPhoneNumber(jtfFriendInfos[PHONE].getText());
				} else {
					JOptionPane.showMessageDialog(null, "Phone number is not a correct format", "경고 메시지",
							JOptionPane.WARNING_MESSAGE);
					normalDataFlag = false;
				}
				
				if (friend.isStringEmailFormat(jtfFriendInfos[EMAIL].getText())) {
					friend.setEmailAddress(jtfFriendInfos[EMAIL].getText());
				} else {
					JOptionPane.showMessageDialog(null, "Email address is not a correct format", "경고 메시지",
							JOptionPane.WARNING_MESSAGE);
					normalDataFlag = false;
				}
				
				friend.setPhoto(jtfFriendInfos[PHOTO].getText());

				if (normalDataFlag == true) {	//입력된 data가 올바른 형식이라면 FriendList에 add 진행
					friendListGUI.addToFriendList(friend);
					friendListGUI.addfriendComponentsArray(friend);
					friendListGUI.updateJpnFriendList();
					AddFriendGUI.this.dispose();	//창을 닫는다

				} else {
					normalDataFlag = true;
				}

			}
		}
	}

	/**
	 * interface인 FocusListener를 implements하여 TextBox components들의 focus event들을 처리하는 class
	 */
	class FListener implements FocusListener {
		public void focusPerformed(FocusEvent e) {
		}

		public void focusLost(FocusEvent e) {
		}

		public void focusGained(FocusEvent e) {	//TextBox가 눌렸을 시 입력 format을 작성해놓은 default값들을 지움
			if (e.getSource() == jtfFriendInfos[NAME]) {
				jtfFriendInfos[NAME].setText("");
			} else if (e.getSource() == jtfFriendInfos[GROUP]) {
				jtfFriendInfos[GROUP].setText("");
			} else if (e.getSource() == jtfFriendInfos[PHONE]) {
				jtfFriendInfos[PHONE].setText("");
			} else if (e.getSource() == jtfFriendInfos[EMAIL]) {
				jtfFriendInfos[EMAIL].setText("");
			} else if (e.getSource() == jtfFriendInfos[PHOTO]) {
				jtfFriendInfos[PHOTO].setText("");
			}
		}
	}
}

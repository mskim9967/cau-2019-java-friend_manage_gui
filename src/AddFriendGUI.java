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
 * ģ����Ͽ� ģ���� �߰��ϴ� JFrame�� ���� �������� ����� class
 * javax.swing.JFrame�� ����Ѵ�
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
	 * @brief JFrame�� �ʱ�ȭ�ϰ� â�� ����.
	 * @param FriendListGUI friendListGUI AddFriendGUI Frame�� ȣ���Ų FriendListGUI�� ����
	 */
	public AddFriendGUI(FriendListGUI friendListGUI) {
		super();
		this.friendListGUI = friendListGUI;
		this.setTitle("�߰��� ģ�� ����");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new BorderLayout());

		jbtDone = new JButton("Done");
		this.add(jbtDone, BorderLayout.EAST);
		jbtDone.addActionListener(new Listener());
 
		jtfFriendInfos = new JTextField[friend.getNumOfInfo()];	//������ �Է��� TextBox�� ArrayList
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
	 * interface�� ActionListener�� implements�Ͽ� components���� event���� ó���ϴ� class
	 */
	class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//����ڰ� �Է��� ��ġ�� done��ư�� ������ ��
			if (e.getSource() == jbtDone) {
				if(!friendListGUI.getFriendList().isNameOverlap(jtfFriendInfos[NAME].getText())) {
					friend.setName(jtfFriendInfos[NAME].getText());
				} else {
					JOptionPane.showMessageDialog(null, "Name is Overlapped", "��� �޽���",
							JOptionPane.WARNING_MESSAGE);
					normalDataFlag = false;
				}

				if (friend.isStringInt(jtfFriendInfos[GROUP].getText())) {
					friend.setGroup(jtfFriendInfos[GROUP].getText());
				} else {
					JOptionPane.showMessageDialog(null, "Group is not a correct format(Only number)", "��� �޽���",
							JOptionPane.WARNING_MESSAGE);
					normalDataFlag = false;
				}

				if (friend.isStringPhoneNumberFormat(jtfFriendInfos[PHONE].getText())) {
					friend.setPhoneNumber(jtfFriendInfos[PHONE].getText());
				} else {
					JOptionPane.showMessageDialog(null, "Phone number is not a correct format", "��� �޽���",
							JOptionPane.WARNING_MESSAGE);
					normalDataFlag = false;
				}
				
				if (friend.isStringEmailFormat(jtfFriendInfos[EMAIL].getText())) {
					friend.setEmailAddress(jtfFriendInfos[EMAIL].getText());
				} else {
					JOptionPane.showMessageDialog(null, "Email address is not a correct format", "��� �޽���",
							JOptionPane.WARNING_MESSAGE);
					normalDataFlag = false;
				}
				
				friend.setPhoto(jtfFriendInfos[PHOTO].getText());

				if (normalDataFlag == true) {	//�Էµ� data�� �ùٸ� �����̶�� FriendList�� add ����
					friendListGUI.addToFriendList(friend);
					friendListGUI.addfriendComponentsArray(friend);
					friendListGUI.updateJpnFriendList();
					AddFriendGUI.this.dispose();	//â�� �ݴ´�

				} else {
					normalDataFlag = true;
				}

			}
		}
	}

	/**
	 * interface�� FocusListener�� implements�Ͽ� TextBox components���� focus event���� ó���ϴ� class
	 */
	class FListener implements FocusListener {
		public void focusPerformed(FocusEvent e) {
		}

		public void focusLost(FocusEvent e) {
		}

		public void focusGained(FocusEvent e) {	//TextBox�� ������ �� �Է� format�� �ۼ��س��� default������ ����
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

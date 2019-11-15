import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * javax.swing.JPanel�� Ȯ���Ͽ� �ۼ��� class�̴� 
 * JFrame�� ����ϴ� FriendListGUI, AddFriendGUI�� add�� JPanel�鿡�� �ʿ��� method���� �ۼ��Ͽ���
 */
public class MyJPanel extends JPanel {
	private static final int NAME = 0, GROUP = 1, PHONE = 2, EMAIL = 3, PHOTO = 4;
	private static final boolean NOBLANK = false, BLANK = true;

	public MyJPanel() {
		super(); // �θ�Class�� ������
	}

	/**
	 * @brief JPanel�� Button���� ������ �Ʒ��� �߰��Ѵ�
	 * @param JButton[] buttonArray �߰��� Button���� �迭
	 * @param String[] buttonNameArray �߰��� Button���� �̸����� �迭
	 */
	public void setButtons(JButton[] buttonArray, String[] buttonNameArray) {
		this.setLayout(new GridLayout(buttonNameArray.length, 1));

		for (int i = 0; i < buttonNameArray.length; i++) {
			buttonArray[i] = new JButton(buttonNameArray[i]);
			this.add(buttonArray[i]);
		}
	}

	/**
	 * @brief JPanel�� Friend ���� Label���� �߰��Ѵ�
	 * @param boolean blankFlag �տ� ���� ���Կ���
	 */
	public void setLabels(boolean blankFlag) {
		Friend friend = new Friend();

		if (blankFlag == NOBLANK) {
			this.setLayout(new GridLayout(1, friend.getNumOfInfo()));
		} else {
			this.setLayout(new GridLayout(1, friend.getNumOfInfo() + 1));
			this.add(new JLabel(""));
		}
		this.add(new JLabel("�̸�", SwingConstants.CENTER));
		this.add(new JLabel("�׷�", SwingConstants.CENTER));
		this.add(new JLabel("��ȭ��ȣ", SwingConstants.CENTER));
		this.add(new JLabel("Email", SwingConstants.CENTER));
		this.add(new JLabel("����", SwingConstants.CENTER));
	}

	/**
	 * @brief JPanel�� Friend�� ���� �� ���� �߰��Ѵ�
	 * @param FriendComponents friendComponents �ϳ��� friend ������ ������ component
	 */
	public void setFriend(FriendComponents friendComponents) {
		Friend friend = new Friend();

		this.setLayout(new GridLayout(1, friend.getNumOfInfo() + 1));

		this.add(friendComponents.jcb);
		this.add(friendComponents.jlbName);
		this.add(friendComponents.jtfGroup);
		this.add(friendComponents.jtfPhoneNumber);
		this.add(friendComponents.jtfEmailAddress);
		this.add(friendComponents.jlbPhoto);
	}

	/**
	 * @brief JPanel�� FriendList�� �������� �߰��Ѵ�
	 * @param ArrayList<FriendComponents> friendComponentsArray �ϳ��� friend ������ ������
	 *        components���� ������ FriendComponents class�� ArrayList
	 */
	public void setFriendList(ArrayList<FriendComponents> friendComponentsArray) {
		MyJPanel jpnFriend, jpnLabels = new MyJPanel(), jpnEmpty = new MyJPanel();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		jpnLabels.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1000));
		jpnLabels.setLabels(BLANK);
		this.add(jpnLabels);

		// friend�� component���� JPanel�� �߰��ϴ� ���� �ݺ��Ͽ� ��� ģ���� component���� JPanel�� �߰��Ѵ�
		for (int i = 0; i < friendComponentsArray.size(); i++) {
			jpnFriend = new MyJPanel();
			jpnFriend.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1000));
			jpnFriend.setFriend(friendComponentsArray.get(i));
			this.add(jpnFriend);
		}
		this.add(jpnEmpty);
	}

	/**
	 * @brief AddFriendGUI class���� ����� JTextBox���� JPanel�� �߰��Ѵ�
	 * @param JTextField[] jtfArray JPanel�� �߰��� JTextBox���� �迭
	 */
	public void setAddFriendTextField(JTextField[] jtfArray) {
		MyJPanel jpnLabels = new MyJPanel(), jpnJtfArray = new MyJPanel();
		jpnLabels.setLabels(NOBLANK);
		this.add(jpnLabels);

		jpnJtfArray.setLayout(new GridLayout(1, jtfArray.length));

		// ����ڰ� �Է��ؾ� �� format�� default������ ����
		for (int i = 0; i < jtfArray.length; i++) {
			if (i == GROUP)
				jtfArray[i] = new JTextField("number");
			else if (i == PHONE)
				jtfArray[i] = new JTextField("with hyphen(x-x-x)");
			else if (i == EMAIL)
				jtfArray[i] = new JTextField("(x@x.x)");
			else
				jtfArray[i] = new JTextField();

			jpnJtfArray.add(jtfArray[i]);
		}
		this.add(jpnJtfArray);
	}
}

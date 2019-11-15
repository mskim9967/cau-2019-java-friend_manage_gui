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
 * javax.swing.JPanel를 확장하여 작성한 class이다 
 * JFrame을 상속하는 FriendListGUI, AddFriendGUI에 add할 JPanel들에게 필요한 method들을 작성하였다
 */
public class MyJPanel extends JPanel {
	private static final int NAME = 0, GROUP = 1, PHONE = 2, EMAIL = 3, PHOTO = 4;
	private static final boolean NOBLANK = false, BLANK = true;

	public MyJPanel() {
		super(); // 부모Class의 생성자
	}

	/**
	 * @brief JPanel에 Button들을 위에서 아래로 추가한다
	 * @param JButton[] buttonArray 추가할 Button들의 배열
	 * @param String[] buttonNameArray 추가할 Button들의 이름들의 배열
	 */
	public void setButtons(JButton[] buttonArray, String[] buttonNameArray) {
		this.setLayout(new GridLayout(buttonNameArray.length, 1));

		for (int i = 0; i < buttonNameArray.length; i++) {
			buttonArray[i] = new JButton(buttonNameArray[i]);
			this.add(buttonArray[i]);
		}
	}

	/**
	 * @brief JPanel에 Friend 정보 Label들을 추가한다
	 * @param boolean blankFlag 앞에 공백 포함여부
	 */
	public void setLabels(boolean blankFlag) {
		Friend friend = new Friend();

		if (blankFlag == NOBLANK) {
			this.setLayout(new GridLayout(1, friend.getNumOfInfo()));
		} else {
			this.setLayout(new GridLayout(1, friend.getNumOfInfo() + 1));
			this.add(new JLabel(""));
		}
		this.add(new JLabel("이름", SwingConstants.CENTER));
		this.add(new JLabel("그룹", SwingConstants.CENTER));
		this.add(new JLabel("전화번호", SwingConstants.CENTER));
		this.add(new JLabel("Email", SwingConstants.CENTER));
		this.add(new JLabel("사진", SwingConstants.CENTER));
	}

	/**
	 * @brief JPanel에 Friend의 정보 한 줄을 추가한다
	 * @param FriendComponents friendComponents 하나의 friend 정보를 보여줄 component
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
	 * @brief JPanel에 FriendList의 정보들을 추가한다
	 * @param ArrayList<FriendComponents> friendComponentsArray 하나의 friend 정보를 보여줄
	 *        components들의 모임인 FriendComponents class의 ArrayList
	 */
	public void setFriendList(ArrayList<FriendComponents> friendComponentsArray) {
		MyJPanel jpnFriend, jpnLabels = new MyJPanel(), jpnEmpty = new MyJPanel();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		jpnLabels.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1000));
		jpnLabels.setLabels(BLANK);
		this.add(jpnLabels);

		// friend의 component들을 JPanel에 추가하는 것을 반복하여 모든 친구의 component들을 JPanel에 추가한다
		for (int i = 0; i < friendComponentsArray.size(); i++) {
			jpnFriend = new MyJPanel();
			jpnFriend.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1000));
			jpnFriend.setFriend(friendComponentsArray.get(i));
			this.add(jpnFriend);
		}
		this.add(jpnEmpty);
	}

	/**
	 * @brief AddFriendGUI class에서 사용할 JTextBox들을 JPanel에 추가한다
	 * @param JTextField[] jtfArray JPanel에 추가할 JTextBox들의 배열
	 */
	public void setAddFriendTextField(JTextField[] jtfArray) {
		MyJPanel jpnLabels = new MyJPanel(), jpnJtfArray = new MyJPanel();
		jpnLabels.setLabels(NOBLANK);
		this.add(jpnLabels);

		jpnJtfArray.setLayout(new GridLayout(1, jtfArray.length));

		// 사용자가 입력해야 할 format을 default값으로 설정
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

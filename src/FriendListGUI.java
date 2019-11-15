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
 * ģ������� ����ϴ� JFrame�� ���� �������� ����� class
 * javax.swing.JFrame�� ����Ѵ�
 */
public class FriendListGUI extends JFrame {

	private final static int WIDTH = 1000, HEIGHT = 500, ADD = 0, DELETE = 1, MODIFY = 2, SAVE_FILE = 3;
	private final static String[] buttonNameArray = { "Add", "Delete", "Modify", "Save File" };

	private MyJPanel jpnButtons, jpnFriendList;
	private JButton[] jbtArray;
	//FriendComponents: Panel�� �� �ٿ� Friend������ ����� component���� ������ ������ class 
	private ArrayList<FriendComponents> friendComponentsArray;	
	private FriendList friendList;

	/**
	 * @brief JFrame�� �ʱ�ȭ�ϰ� â�� ����.
	 * @param FriendList friendList JFrame���� ������ FriendList
	 */
	public FriendListGUI(FriendList friendList) {
		super();	//JFrame�� ��ӹ޴´�
		this.friendList = friendList;
		this.setTitle("ģ�� ���");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new BorderLayout());
		
		friendComponentsArray = new ArrayList<FriendComponents>();	
		for (int i = 0; i < friendList.numFriends(); i++) {
			//Friend������ FriendComponets�� set�ϰ� array�� �߰���Ų��
			addfriendComponentsArray(friendList.getFriend(i));	 
		}

		jbtArray = new JButton[buttonNameArray.length];	//Frame ������ ���� Button���� Array
		jpnButtons = new MyJPanel();	//Button���� �߰���ų JPanel
		jpnButtons.setButtons(jbtArray, buttonNameArray);
		this.add(jpnButtons, BorderLayout.EAST);
		for (int i = 0; i < jbtArray.length; i++) {
			jbtArray[i].addActionListener(new Listener());
		}
		
		jpnFriendList = new MyJPanel();	//Frame ������ ���� FriendList�� ������
		jpnFriendList.setFriendList(friendComponentsArray);
		this.add(jpnFriendList, BorderLayout.CENTER);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * @brief friendComponentsArray�� Friend�� ������ ��� FriendComponents�� �߰���Ų��
	 * @param Friend friend Array�� �߰���ų FriendComponents�� ������ ��� Friend
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
		
		//�� component�鿡 actionListener�� �߰��Ѵ�
		temp.jcb.addActionListener(new Listener());
		temp.jtfGroup.addActionListener(new Listener());
		temp.jtfEmailAddress.addActionListener(new Listener());
		temp.jtfPhoneNumber.addActionListener(new Listener());

		friendComponentsArray.add(temp);
	}

	/**
	 * @brief FriendListGUI�� FriendList�� ��ȯ�Ѵ�
	 */
	public FriendList getFriendList() {
		return friendList;
	}
	
	/**
	 * @brief AddFriendGUI���� �Էµ� Friend�� FriendListGUI�� FriendList�� �߰���Ų��
	 * @param Friend friend FriendList�� �߰���ų Friend
	 */
	public void addToFriendList(Friend friend) {
		friendList.addFriend(friend);
	}

	/**
	 * @brief Frame ������ ��ġ�� jpnFriendList Panel�� ������Ʈ�ϱ� ���� Panel�� ����� �ٽ� �����Ѵ�.
	 */
	public void updateJpnFriendList() {
		this.remove(jpnFriendList);
		jpnFriendList = new MyJPanel();
		jpnFriendList.setFriendList(friendComponentsArray);
		this.add(jpnFriendList, BorderLayout.CENTER);
		this.setVisible(true);
	}

	/**
	 * interface�� ActionListener�� implements�Ͽ� components���� event���� ó���ϴ� class
	 */
	class Listener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {

			//add��ư�� ������ ��
			if (e.getSource() == jbtArray[ADD]) {
				AddFriendGUI addFriendGUI = new AddFriendGUI(FriendListGUI.this);
			}

			//delete��ư�� ������ ��
			else if (e.getSource() == jbtArray[DELETE]) {
				for (int i = 0; i < friendComponentsArray.size(); i++) {
					//CheckBox�� ���õ����ÿ��� delete ����
					if (friendComponentsArray.get(i).jcb.isSelected()) {	
						friendList.deleteFriend(i);
						friendComponentsArray.remove(i);
						JOptionPane.showMessageDialog(null, "Delete Finish");
						updateJpnFriendList();
						break;
					}
				}
			}

			//modify��ư�� ������ ��
			else if (e.getSource() == jbtArray[MODIFY]) {
				boolean normalDataFlag = false;
				int temp = 0;

				
				for (int i = 0; i < friendComponentsArray.size(); i++) {
					//CheckBox�� ���õ����ÿ��� modify ����
					if (friendComponentsArray.get(i).jcb.isSelected()) {
						normalDataFlag = true;
						
						if (friendList.getFriend(i).isStringInt(friendComponentsArray.get(i).jtfGroup.getText())) {
							friendList.getFriend(i).setGroup(friendComponentsArray.get(i).jtfGroup.getText());
						} 
						else {
							JOptionPane.showMessageDialog(null, "Group is not a correct format(Only number)",
									"Format Error", JOptionPane.WARNING_MESSAGE);
							//�߸��� ������ Ÿ���� �Էµ������� TextBox�� ���� data�� ����
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
				if (normalDataFlag == true) {	//�Էµ� data�� �ùٸ� �����̶�� modify ����
					friendComponentsArray.get(temp).jcb.setSelected(false);
					friendComponentsArray.get(temp).setJtfEditible(false);					
					updateJpnFriendList();
					JOptionPane.showMessageDialog(null, "Modify Finish");
				}
			}

			//save file��ư�� ������ ��
			else if (e.getSource() == jbtArray[SAVE_FILE]) {
				try {
					friendList.makeFile();
				} catch (IOException e1) {
					System.out.println("file save Error!");
				}
				JOptionPane.showMessageDialog(null, "Save Finish");
			} 
			
			//��� CheckBox�� Ŭ���Ǿ��ٸ�
			else {	
				updateJpnFriendList();
				
				for (int i = 0; i < friendComponentsArray.size(); i++) {		
					if (e.getSource() == friendComponentsArray.get(i).jcb) {

						//CheckBox�� ���õǾ��ٸ� �� ���� TextBox���� ���� �����ϰ� ����
						if(friendComponentsArray.get(i).jcb.isSelected()==true) {
							friendComponentsArray.get(i).setJtfEditible(true);	
						}
						//CheckBox�� �����Ǿ��ٸ� �� ���� TextBox���� ���� �Ұ����ϰ� ����
						else if(friendComponentsArray.get(i).jcb.isSelected()==false) {
							friendComponentsArray.get(i).setJtfEditible(false);
						}
						//Ŭ���� CheckBox���� ������ ��� ������ TextBox���� �����Ұ����ϰ� ����
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

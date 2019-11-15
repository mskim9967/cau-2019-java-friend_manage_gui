import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GUI extends JFrame{

	private JFrame friendListFrame;
	private JFrame addFriendFrame;

	private JPanel controlPanel = new JPanel();
	private JPanel friendLabel = new JPanel();
	private JPanel friendPanel = new JPanel();
	private JPanel addFriendPanel = new JPanel();

	private JLabel temp = new JLabel();

	private JButton jbtAdd = new JButton("Add");
	private JButton jbtDelete = new JButton("Delete");
	private JButton jbtModify = new JButton("Modify");
	private JButton jbtSaveFile = new JButton("Save File");
	private JButton jbtDone = new JButton("Done");

	private JTextField[] jtfAdd = new JTextField[5];

	private GridBagConstraints gbc = new GridBagConstraints();


	public GUI() {
		jbtAdd.addActionListener(new Listener());
		jbtDelete.addActionListener(new Listener());
		jbtModify.addActionListener(new Listener());
		jbtSaveFile.addActionListener(new Listener());
		jbtDone.addActionListener(new Listener());
	}


	public void createFriendListGUI() {
		friendListFrame = new JFrame("친구 목록");
		friendListFrame.setSize(1000, 500);
		friendListFrame.setLayout(new GridBagLayout());

		friendLabel.setLayout(new GridLayout(1, 6));
		friendLabel.add(new JLabel(""));
		friendLabeling(friendLabel);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		friendListFrame.add(friendLabel, gbc);

		controlPanel.setLayout(new GridLayout(5, 1));
		controlPanel.add(jbtAdd);
		controlPanel.add(jbtDelete);
		controlPanel.add(jbtModify);
		controlPanel.add(jbtSaveFile);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		friendListFrame.add(controlPanel, gbc);

		friendListFrame.setVisible(true);
		friendListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateFriendListGUI(FriendList friendList) {
		friendPanel.setLayout(new GridLayout(friendList.numFriends(), 6));
		

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.gridheight = 5;
		friendListFrame.add(friendPanel, gbc);
		
		friendListFrame.setVisible(true);
	}

	
	private void friendLabeling(JPanel panel) {
		panel.add(new JLabel("이름"));
		panel.add(new JLabel("그룹"));
		panel.add(new JLabel("전화번호"));
		panel.add(new JLabel("Email"));
		panel.add(new JLabel("사진"));
	}

	
	public void createAddFriendGUI() {
		addFriendFrame = new JFrame("추가될 친구 정보");
		addFriendFrame.setSize(500, 200);
		
		addFriendPanel.setLayout(new GridLayout(2, 6));
		friendLabeling(addFriendPanel);
		addFriendPanel.add(new JLabel(""));

		for (int i = 0; i < 5; i++) {
			jtfAdd[i] = new JTextField();
			addFriendPanel.add(jtfAdd[i]);
		}
		addFriendPanel.add(jbtDone);

		addFriendFrame.add(addFriendPanel);
		addFriendFrame.setVisible(true);
	}

	
	class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtAdd) {
				System.out.println("add clicked");
			} else if (e.getSource() == jbtDelete) {
				System.out.println("delete clicked");
			} else if (e.getSource() == jbtModify) {
				System.out.println("modify clicked");
			} else if (e.getSource() == jbtSaveFile) {
				System.out.println("save file clicked");
			} else if (e.getSource() == jbtDone) {
				System.out.println("done clicked");
			}
		}
	}
	
}

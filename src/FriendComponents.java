import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FriendComponents {

	JCheckBox jcb = new JCheckBox();
	JLabel jlbName = new JLabel();
	JTextField jtfGroup = new JTextField();
	JTextField jtfPhoneNumber = new JTextField();
	JTextField jtfEmailAddress = new JTextField();
	JLabel jlbPhoto = new JLabel();

	FriendComponents(){
		jtfGroup.setEditable(false);
		jtfPhoneNumber.setEditable(false);
		jtfEmailAddress.setEditable(false);
	}
	
	public void setJtfEditible(boolean flag) {
		jtfGroup.setEditable(flag);
		jtfPhoneNumber.setEditable(flag);
		jtfEmailAddress.setEditable(flag);
	}

}

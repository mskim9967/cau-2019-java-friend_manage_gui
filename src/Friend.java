import java.util.regex.Pattern;

public class Friend {

	public static final int NOBLANK = 0, BLANK = 1, NAME = 0, GROUP = 1, PHONE = 2, EMAIL = 3, PHOTO = 4;
	private static final int numOfInfo = 5;
	private String name;
	private String group;
	private String phoneNumber;
	private String emailAddress;
	private String photo;

	public Friend() {
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getNumOfInfo() {
		return numOfInfo;
	}

	public String getName() {
		return name;
	}

	public String getGroup() {
		return group;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getPhoto() {
		return photo;
	}

	public void print() {
		System.out.println("Name: " + name);
		System.out.println("Group: " + group);
		System.out.println("Phone number: " + phoneNumber);
		System.out.println("Email address: " + emailAddress);
		System.out.println("Photo: " + photo);
	}

	public boolean isStringInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isStringPhoneNumberFormat(String string) {
		String phoneNumberPattern = new String("^[0-9]+-[0-9]+-[0-9]+$");

		if (Pattern.matches(phoneNumberPattern, string)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isStringEmailFormat(String string) {
		String emailPattern = new String("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9\\.]+$");

		if (Pattern.matches(emailPattern, string)) {
			return true;
		} else {
			return false;
		}
	}

}

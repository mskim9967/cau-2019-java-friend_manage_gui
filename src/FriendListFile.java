/**
 * FriendList ������ ��� ������ ó���ϴ� Ŭ����
 * 
 * @author 20186274����
 * 
 * 2019.04.05 ���� �ۼ� 
 * 2019.04.15 ���� ���� 
 */
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;

public class FriendListFile {

	private static final int NUM_OF_INFO = 5, FILE = -2, LINE = -1, NAME = 0, GROUP = 1, PHONE_NUMBER = 2,
			EMAIL_ADDRESS = 3, PHOTO = 4;
	// ��Ʈ���� ������
	private static final String DELIMITER = ":";

	private File file, photo;
	private Friend friend;
	private FriendList friendList;
	private Scanner input;
	private String line;
	private String[] lineToken;
	private int numOfLine;

	public FriendListFile() {
		numOfLine = 0;
	}
	
	public FriendList readFileToList(String fileName) {

		friendList = new FriendList();
		file = new File(fileName);

		// ������ �������� ������ null ��ȯ
		if (!file.exists()) {
			printError(FILE);
			// System.exit(1);
			return null;
		}

		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {}

		// ������ ������ �ݺ�
		while (input.hasNext()) {
			line = input.nextLine();
			numOfLine++;

			if (!isLineSafe(line)) {
				printError(LINE);
				continue;
			}
			if (line.startsWith("//")) {
				continue;
			}
			
			// ���ٿ� ���Ե� ������ lineToken[]�� �����ϱ� ���� ��Ʈ���迭 �ν��Ͻ� ����
			lineToken = new String[NUM_OF_INFO];
			friend = new Friend();

			lineToken = line.split(DELIMITER);
			for (int i = 0; i < NUM_OF_INFO; i++) {
				lineToken[i] = lineToken[i].trim();
			}

			// ��ū���� ���Ŀ� ���� ���� �� ���� �޼��� ��� �� ���� �ݺ� ���� 
			if (!areTokensSafe(lineToken)) {
				continue;
			}

			// Friend Ŭ������ ������ ������ Token���� ����
			for (int i = 0; i < NUM_OF_INFO; i++) {
				switch (i) {
				case NAME:
					friend.setName(lineToken[i]);
					break;
				case GROUP:
					friend.setGroup(lineToken[i]);
					break;
				case PHONE_NUMBER:
					friend.setPhoneNumber(lineToken[i]);
					break;
				case EMAIL_ADDRESS:
					friend.setEmailAddress(lineToken[i]);
					break;
				case PHOTO:
					friend.setPhoto(lineToken[i]);
					break;
				}
			}
			friendList.addFriend(friend);
		}
		input.close();
		
		return friendList;
	}

	/**
	 * ���� �� ���� �ùٸ� �������� Ȯ���ϴ� �޼���
	 * 
	 * @param line �� ��
	 * @return �ùٸ��� true, �׷��� ������ false
	 */
	private boolean isLineSafe(String line) {
		int numOfToken = 0;

		if (line.startsWith("//")) {
			return true;
		}

		for (int i = 0; i < line.length(); i++) {
			numOfToken = line.split(DELIMITER).length;
		}

		if (numOfToken == NUM_OF_INFO) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �̸� ��ū�� ���� �迭�� �ִ� �̸��� �ߺ��Ǵ��� Ȯ���ϴ� �޼���
	 * 
	 * @param string �̸�
	 * @return �ߺ��Ǹ� true, �׷��� ������ false
	 */
	private boolean isNameOverlap(String name) {
		for (int i = 0; i < friendList.numFriends(); i++) {
			if (name.equals(friendList.getFriend(i).getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �׷��� ���������� Ȯ���ϴ� �޼���
	 * 
	 * @param line �׷�
	 * @return �ùٸ��� true, �׷��� ������ false
	 */
	private boolean isStringInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * ��ȭ��ȣ�� �ùٸ� �������� Ȯ���ϴ� �޼���
	 * 
	 * @param line ��ȭ��ȣ
	 * @return �ùٸ��� true, �׷��� ������ false
	 */
	private boolean isStringPhoneNumberFormat(String string) {
		String phoneNumberPattern = new String("^[0-9]+-[0-9]+-[0-9]+$");

		if (Pattern.matches(phoneNumberPattern, string)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �̸����� �ùٸ� �������� Ȯ���ϴ� �޼���
	 * 
	 * @param line �̸���
	 * @return �ùٸ��� true, �׷��� ������ false
	 */
	private boolean isStringEmailFormat(String string) {
		String emailPattern = new String("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9\\.]+$");

		if (Pattern.matches(emailPattern, string)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �� ��ū���� �ùٸ� �������� Ȯ���ϴ� �޼���
	 * 	
	 * @param token[] ��ū �迭
	 * @return �ùٸ��� true, �׷��� ������ �� Token�� Error�� ����ϸ� false
	 */
	private boolean areTokensSafe(String token[]) {
		boolean errorFlag = true;

		if (isNameOverlap(token[NAME])) {
			printError(NAME);
			errorFlag = false;
		}
		if (!isStringInt(token[GROUP])) {
			printError(GROUP);
			errorFlag = false;
		}
		if (!isStringEmailFormat(token[EMAIL_ADDRESS])) {
			printError(EMAIL_ADDRESS);
			errorFlag = false;
		}
		if (!isStringPhoneNumberFormat(token[PHONE_NUMBER])) {
			printError(PHONE_NUMBER);
			errorFlag = false;
		}
		/*
		 * ���������� �����ϴ��� üũ
		 * ���� ������ �������� �������� �ּ�ó��
		photo=new File(token[PHOTO]);
		if (!photo.exists()) {
			printError(PHOTO);
			errorFlag = false;
		}
		*/
		return errorFlag;
	}
	
	/**
	 * ������ ����ϴ� �޼���
	 * 
	 * @param errorType �߻��� ���� ����
	 */
	private void printError(int errorType) {
		System.out.println("----------ERROR----------");
		switch (errorType) {
		case FILE:
			System.out.println("No File!!");
			System.out.println("Return NULL");
			break;
		case LINE:
			System.out.format("Line format error in line %d\r", numOfLine);
			System.out.println(line);
			System.out.println("Skip this line");
			break;
		case NAME:
			System.out.format("Name Overlapped in line %d\r", numOfLine);
			System.out.println(lineToken[NAME]);
			System.out.println("Delete the latest data");
			break;
		case GROUP:
			System.out.format("Group type error in line %d\r", numOfLine);
			System.out.println(lineToken[GROUP]);
			System.out.println("Skip this line");
			break;
		case PHONE_NUMBER:
			System.out.format("Phone number format error in line %d\r", numOfLine);
			System.out.println(lineToken[PHONE_NUMBER]);
			System.out.println("Skip this line");
			break;
		case EMAIL_ADDRESS:
			System.out.format("Email address format error in line %d\r", numOfLine);
			System.out.println(lineToken[EMAIL_ADDRESS]);
			System.out.println("Skip this line");
			break;
		case PHOTO:
			System.out.format("Photo file name error in line %d\r", numOfLine);
			System.out.println(lineToken[PHOTO]);
			System.out.println("Skip this line");
			break;
		}
		System.out.println("-------------------------");
	}
}

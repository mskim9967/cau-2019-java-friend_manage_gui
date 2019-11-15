/**
 * FriendList 정보가 담긴 파일을 처리하는 클래스
 * 
 * @author 20186274김명승
 * 
 * 2019.04.05 최초 작성 
 * 2019.04.15 최종 수정 
 */
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;

public class FriendListFile {

	private static final int NUM_OF_INFO = 5, FILE = -2, LINE = -1, NAME = 0, GROUP = 1, PHONE_NUMBER = 2,
			EMAIL_ADDRESS = 3, PHOTO = 4;
	// 스트링의 구분자
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

		// 파일이 존재하지 않으면 null 반환
		if (!file.exists()) {
			printError(FILE);
			// System.exit(1);
			return null;
		}

		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {}

		// 파일의 끝까지 반복
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
			
			// 한줄에 포함된 정보를 lineToken[]에 대입하기 위해 스트링배열 인스턴스 생성
			lineToken = new String[NUM_OF_INFO];
			friend = new Friend();

			lineToken = line.split(DELIMITER);
			for (int i = 0; i < NUM_OF_INFO; i++) {
				lineToken[i] = lineToken[i].trim();
			}

			// 토큰들이 형식에 맞지 않을 시 에러 메세지 출력 후 다음 반복 실행 
			if (!areTokensSafe(lineToken)) {
				continue;
			}

			// Friend 클래스에 변수에 각각의 Token들을 대입
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
	 * 읽은 한 줄이 올바른 형식인지 확인하는 메서드
	 * 
	 * @param line 한 줄
	 * @return 올바르면 true, 그렇지 않으면 false
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
	 * 이름 토큰이 기존 배열에 있는 이름과 중복되는지 확인하는 메서드
	 * 
	 * @param string 이름
	 * @return 중복되면 true, 그렇지 않으면 false
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
	 * 그룹이 정수형인지 확인하는 메서드
	 * 
	 * @param line 그룹
	 * @return 올바르면 true, 그렇지 않으면 false
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
	 * 전화번호가 올바른 형식인지 확인하는 메서드
	 * 
	 * @param line 전화번호
	 * @return 올바르면 true, 그렇지 않으면 false
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
	 * 이메일이 올바른 형식인지 확인하는 메서드
	 * 
	 * @param line 이메일
	 * @return 올바르면 true, 그렇지 않으면 false
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
	 * 각 토큰들이 올바른 형식인지 확인하는 메서드
	 * 	
	 * @param token[] 토큰 배열
	 * @return 올바르면 true, 그렇지 않으면 각 Token의 Error를 출력하며 false
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
		 * 사진파일이 존재하는지 체크
		 * 아직 파일이 실존하지 않음으로 주석처리
		photo=new File(token[PHOTO]);
		if (!photo.exists()) {
			printError(PHOTO);
			errorFlag = false;
		}
		*/
		return errorFlag;
	}
	
	/**
	 * 에러를 출력하는 메서드
	 * 
	 * @param errorType 발생한 에러 종류
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

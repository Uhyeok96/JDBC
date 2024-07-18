package com.board.www.service;

import java.sql.Connection;
import java.util.Scanner;

import com.board.www.dao.MemberDAO;
import com.board.www.dto.MemberDTO;

public class MemberService {
	// 회원에 대한 처리 C(회원가입) R(로그인) U(회원정보수정) D(회원탈퇴)

	public MemberDTO memberMenu(Scanner scanner, MemberDTO loginMember, Connection connection) { // while문으로 부메뉴 반복 처리
		System.out.println("회원관리용 서비스로 진입");
		boolean memberRun = true;

		while (memberRun) {
			System.out.println("1. 회원가입 | 2. 로그인 | 3. 회원수정 | 4. 회원탈퇴 | 5. 종료");
			System.out.print(">>>");
			int memberSelect = scanner.nextInt();
			switch (memberSelect) {
			case 1:
				join(scanner, connection);
				break;
			case 2:
				loginMember = login(scanner, loginMember, connection);
				if (loginMember.getMid() == null) {
					System.out.println("로그인에 실패했습니다. id, pw를 확인 후 다시 시도해주세요.");
				} else {
					System.out.println(loginMember.getMnickname() + "님 로그인 성공!");
				}
				break;
			case 3:
				modify(scanner, loginMember, connection);
				break;
			case 4:
				delete(scanner, loginMember, connection);
				break;
			case 5:
				System.out.println("회원관리 메뉴를 종료합니다.");
				memberRun = false;
			} // switch 종료
		} // while 종료
		return loginMember;
	} // memberMenu() 종료

	public void join(Scanner scanner, Connection connection) { // 회원가입용 메서드
		System.out.println("회원가입 메서드로 진입");
		System.out.print("id : ");
		String joinId = scanner.next();
		System.out.print("pw : ");
		String joinPw = scanner.next();
		System.out.print("nickname : ");
		String joinNickname = scanner.next();
		MemberDTO joinMemberDTO = new MemberDTO(joinId, joinPw, joinNickname); // 키보르도 입력받은 값을 객체로 생성

		MemberDAO memberDAO = new MemberDAO();
		memberDAO.register(connection, joinMemberDTO);

	} // join() 메서드 종료

	public MemberDTO login(Scanner scanner, MemberDTO loginMember, Connection connection) { // 로그인용 메서드
		System.out.println("로그인 메서드로 진입");
		System.out.print("id : ");
		String loginId = scanner.next();
		System.out.print("pw : ");
		String loginPw = scanner.next();
		MemberDTO loginMemberDTO = new MemberDTO(loginId, loginPw);
		// 키보드로 입력받은 값을 객체로 생성

		MemberDAO memberDAO = new MemberDAO();
		return memberDAO.login(connection, loginMemberDTO);
		// db에서 넘어온 객체를 바로 리턴한다.
	} // login() 메서드 종료

	public void modify(Scanner scanner, MemberDTO loginMember, Connection connection) {
		System.out.println("회원정보수정 메서드로 진입");
		try {
			if (loginMember.getMid() == null) {
				System.out.println("로그인 후 진행해주세요.");
			} else {
				System.out.println("본인인증을 위해 비밀번호를 한번 더 입력해주세요");
				System.out.println("pw : ");
				String loginPw = scanner.next();
				if (loginPw.equals(loginMember.getMpw())) {
					System.out.println("현재 고객님의 정보");
					System.out.println("id : " + loginMember.getMid() + "\n" + "pw : " + loginMember.getMpw() + "\n"
							+ "nickname : " + loginMember.getMnickname() + "\n");
					System.out.print("변경할 id : ");
					String modifyId = scanner.next();
					System.out.print("변경할 pw : ");
					String modifyPw = scanner.next();
					System.out.print("변경할 nickname : ");
					String modifyNickname = scanner.next();
					MemberDTO modifyMemberDTO = new MemberDTO(modifyId, modifyPw, modifyNickname);

					MemberDAO memberDAO = new MemberDAO();
					memberDAO.update(modifyMemberDTO, connection, loginMember);
				} else {
					System.out.println("비밀번호가 일치하지 않습니다. 인증실패!");
				} // if 종료
			} // 로그인 if 종료
		} catch (Exception e) {
			System.out.println("로그인 후 진행해주세요");
			// e.printStackTrace();
		}
	} // modify() 메서드 종료

	public void delete(Scanner scanner, MemberDTO loginMember, Connection connection) {
		System.out.println("회원탈퇴 메서드로 진입");
		try {
			if (loginMember.getMid() == null) {
				System.out.println("로그인 후 진행해주세요.");
			} else {
				System.out.println("본인인증을 위해 비밀번호를 한번 더 입력해주세요");
				System.out.println("pw : ");
				String loginPw = scanner.next();
				if (loginPw.equals(loginMember.getMpw())) {
					System.out.println("정말 탈퇴하시겠습니까? 1.yes | 2.no");
					int select = scanner.nextInt();
					if (select == 1) {
						MemberDAO memberDAO = new MemberDAO();
						memberDAO.delete(loginMember, connection);
					}else {
						System.out.println("처음부터 다시 진행해주세요.");
					} // select if 종료
				}else {
					System.out.println("비밀번호가 일치하지 않습니다. 인증실패!");
				} // pw 비교 if 종료
			} // if 종료
		} catch (Exception e) {
			System.out.println("로그인 후 진행해주세요");
			// e.printStackTrace();
		}
	} // delete() 메서드 종료

}

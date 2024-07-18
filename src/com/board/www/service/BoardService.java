package com.board.www.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import com.board.www.dao.BoardDAO;
import com.board.www.dto.BoardDTO;
import com.board.www.dto.MemberDTO;

public class BoardService {
	// board의 부메뉴 ( c, r, u, d, l )

	public void menu(Scanner scanner, Connection connection, MemberDTO loginMember) { // 게시물 목록 보기
		boolean boardRun = true;

		while (boardRun) {
			System.out.println("1.게시물 생성 | 2. 게시물 확인 | 3. 게시물 수정 | 4. 게시물 삭제 | 5. 종료");
			int selectBoard = scanner.nextInt();
			switch (selectBoard) {
			case 1:
				System.out.println("게시물 생성으로 진입합니다.");
				createBoard(scanner, connection, loginMember);
				break;
			case 2:
				System.out.println("게시물 확인으로 진입합니다.");
				listBoard(connection, scanner);
				break;
			case 3:
				System.out.println("게시물 수정으로 진입합니다.");
				modifyBoard(loginMember, scanner, connection);
				break;
			case 4:
				System.out.println("게시물 삭제로 진입합니다.");
				deleteBoard(scanner, connection, loginMember);
				break;
			case 5:
				System.out.println("종료합니다.");
				boardRun = false;
			} // switch 종료
		} // while 종료
	} // menu() 종료

	private void deleteBoard(Scanner scanner, Connection connection, MemberDTO loginMember) { // 게시물 삭제 메서드

		try {
			if (loginMember.getMid() != null) {
				// System.out.println("게시물을 삭제합니다");
				System.out.println(loginMember.getMnickname() + "님이 작성하신 게시물입니다.");

				BoardDTO boardDTO = new BoardDTO(loginMember.getMid());
				BoardDAO boardDAO = new BoardDAO();
				boardDAO.readBoard(connection, loginMember, boardDTO);
				System.out.println("삭제하려는 게시물의 번호를 입력해주세요");
				System.out.print(">>>");
				int selectNum = scanner.nextInt();
				boardDAO.select(selectNum, connection);
				System.out.println("이 게시물을 삭제 하시겠습니까? 1.Y | 2.N");
				System.out.print(">>>");
				int choiceNum = scanner.nextInt();
				if (choiceNum == 1) {
					boardDAO.delete(connection, selectNum);
					//System.out.println("삭제가 완료되었습니다.");
				} else {
					System.out.println("다시 진행해주세요.");
				}
			} else {

			} // if 종료
		} catch (Exception e) {
			System.out.println("로그인 후 진행해주세요.");
			// e.printStackTrace();
		}
	} // deleteBoard() 종료

	private void modifyBoard(MemberDTO loginMember, Scanner scanner, Connection connection) { // 게시물 수정 메서드

		try {
			if (loginMember.getMid() != null) {
				// System.out.println("게시물을 수정합니다");
				System.out.println(loginMember.getMnickname() + "님이 작성하신 게시물입니다.");

				BoardDTO boardDTO = new BoardDTO(loginMember.getMid());
				BoardDAO boardDAO = new BoardDAO();
				boardDAO.readBoard(connection, loginMember, boardDTO);
				System.out.println("수정하려는 게시물의 번호를 입력해주세요");
				System.out.print(">>>");
				int selectNum = scanner.nextInt();
				boardDAO.select(selectNum, connection);
				System.out.println("이 게시물을 수정하시겠습니까? 1.Y | 2.N");
				System.out.print(">>>");
				int choiceNum = scanner.nextInt();
				if (choiceNum == 1) {
					System.out.print("수정할 제목 : ");
					String title = scanner.next();
					System.out.print("수정할 내용 : ");
					String content = scanner.next();
					BoardDTO boardDTO2 = new BoardDTO(title, content);
					boardDAO.update(connection, selectNum, boardDTO2);

				} else {
					System.out.println("다시 진행해주세요.");
				}
			} else {

			} // if 종료
		} catch (Exception e) {
			System.out.println("로그인 후 진행해주세요.");
			// e.printStackTrace();
		}

	} // modifyBoard() 종료

	private void listBoard(Connection connection, Scanner scanner) { // 게시물 확인 메서드
		BoardDAO boardDAO = new BoardDAO();
		System.out.println("====================대나무숲 게시판========================");
		System.out.println("-------------------------------------------------------");
		System.out.println("no            title            writer             date");
		System.out.println("-------------------------------------------------------");

		boardDAO.list(connection);
		System.out.println("================================");
		System.out.println();
		
		System.out.println("1. 게시물 번호로 확인하기 | 2. 뒤로가기");
		System.out.print(">>>");
		int select = scanner.nextInt();
		if (select == 1) {
			System.out.println("원하시는 게시물의 번호를 입력해주세요");
			int choice = scanner.nextInt();
			boardDAO.select(choice, connection);
		}else {
			
		}

	} // listBoard() 종료

	private void createBoard(Scanner scanner, Connection connection, MemberDTO loginMember) { // 게시물 생성 메서드

		try {
			if (loginMember.getMid() != null) {
				System.out.println("게시물을 작성합니다.");
				System.out.print("제목 : ");
				String createTitle = scanner.next();
				System.out.print("내용 : ");
				String createContent = scanner.next();

				BoardDTO boardDTO = new BoardDTO(createTitle, createContent);

				BoardDAO boardDAO = new BoardDAO();
				boardDAO.createBoard(connection, loginMember, boardDTO);
				System.out.println("게시물 등록 완료!!");
			} else {

			} // if 종료
		} catch (Exception e) {
			System.out.println("로그인 후 진행해주세요.");
			// e.printStackTrace();
		}
	} // createBoard() 종료

}

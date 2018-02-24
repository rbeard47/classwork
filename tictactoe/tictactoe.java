import java.util.Scanner; 
import java.lang.Character;

class tictactoe {

	public static void main(String[] args) {

		char currentPlayer = 'X';
		char[] board = {
			'A', 'B', 'C',
			'D', 'E', 'F',
			'G', 'H', 'I'
		};

		Scanner scanner = new Scanner(System.in);

		int currentRound = 1;
		
		while(true) {

			drawBoard(board);

			System.out.format("Round #%d\n", currentRound);
			System.out.format("Player %c's turn\n", currentPlayer);

			boolean inputValid = false;

			do {
				String input = scanner.nextLine();

				if(input.length() >  0) {
					char selection = Character.toUpperCase(input.charAt(0));
					if(selection >= 'A' && selection <= 'I') {

						for(int i = 0; i < 9; i++) {
							if(board[i] == selection) {
								board[i] = currentPlayer;
								inputValid = true;
							}
						}
					}
				}
			} while(!inputValid);

			if(isWinner(board)) { 
				drawBoard(board);
				System.out.format("Player %c wins!\n", currentPlayer);
				break;
			} else if (currentRound++ > 8) {
				drawBoard(board);
				System.out.println("Game tied!");
				break;
			} else {
				currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
			}
		}


	}

	public static void drawBoard(char[] board) {

		System.out.println("   |   |   ");
		System.out.format(" %c | %c | %c\n", board[0], board[1], board[2]);
		System.out.println("___|___|___");
		System.out.println("   |   |   ");
		System.out.format(" %c | %c | %c\n", board[3], board[4], board[5]);
		System.out.println("___|___|___");
		System.out.println("   |   |   ");
		System.out.format(" %c | %c | %c\n", board[6], board[7], board[8]);
		System.out.println("   |   |   ");
	}

	public static boolean isWinner(char[] board) {

		if((board[0] == board[1] && board[1] == board[2]) ||
				(board[3] == board[4] && board[4] == board[5]) ||
				(board[6] == board[7] && board[7] == board[8]) ||
				(board[0] == board[3] && board[3] == board[6]) ||
				(board[1] == board[4] && board[4] == board[7]) ||
				(board[2] == board[5] && board[5] == board[8]) ||
				(board[0] == board[4] && board[4] == board[8]) ||
				(board[2] == board[4] && board[4] == board[6])){
			return true;
				}

		return false;
	}
}

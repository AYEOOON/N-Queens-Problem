public class PrintQ {
    static int solutionCount = 0;

    // 체스판을 시각적으로 출력하는 함수
    static void print(int[][] board) {
        solutionCount++;
        System.out.println("# 해답 #");
        for (int i = 0; i < NQStart.boardSize; i++) {
            for (int j = 0; j < NQStart.boardSize; j++) {
                if (board[i][j] == 1) {
                    System.out.print(" Q ");
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
        System.out.println("--------------------");  // 해답 구분선
    }
}

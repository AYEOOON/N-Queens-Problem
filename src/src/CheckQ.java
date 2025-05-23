public class CheckQ {
    // 퀸이 해당 위치에 안전하게 배치될 수 있는지 확인하는 함수
    static boolean checkSafe(int[][] board, int row, int col) {
        // 해당 행에 다른 퀸이 있는지 확인
        for (int i = 0; i < col; ++i) {
            if (board[row][i] == 1) {
                return false;
            }
        }
        // 왼쪽 상단 대각선에 다른 퀸이 있는지 확인
        for (int i = row, j = col; i >= 0 && j >= 0; --i, --j) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        // 왼쪽 하단 대각선에 다른 퀸이 있는지 확인
        for (int i = row, j = col; i < NQStart.boardSize && j >= 0; ++i, --j) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        return true;
    }
}
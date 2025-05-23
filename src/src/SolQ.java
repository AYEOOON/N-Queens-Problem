// FindQ 클래스는 필요 없음. SolQ 클래스만 사용 가능.
public class SolQ {
    static int sol;
    static int[][] lastSolution;  // 마지막 해답을 저장할 배열

    // N-Queens 문제를 해결하는 함수 (퀸 배치)
    static boolean check(int [][] board, int col) {
        if (col >= NQStart.boardSize) {
            ++sol;  // 해답을 찾았을 때 카운트 증가
            // 현재 해답을 마지막 해답으로 저장
            lastSolution = new int[NQStart.boardSize][NQStart.boardSize];
            for (int i = 0; i < NQStart.boardSize; i++) {
                System.arraycopy(board[i], 0, lastSolution[i], 0, NQStart.boardSize);
            }
            return true;
        }

        boolean res = false;  // 현재 열에 배치할 수 있는지 여부

        // 현재 열에 가능한 모든 행을 순회하며 퀸을 배치
        for (int i = 0; i < NQStart.boardSize; i++) {
            if (CheckQ.checkSafe(board, i, col)) {
                board[i][col] = 1;  // 퀸 배치
                res = check(board, col + 1) || res;  // 다음 열로 재귀 호출
                board[i][col] = 0;  // 백트래킹: 퀸 제거
            }
        }
        return res;
    }

    // 문제 해결을 시작하는 함수
    static int solve(int boardSize) {
        sol = 0;
        int [][] board = new int[boardSize][boardSize];

        // 시간 측정을 시작
        long startTime = System.nanoTime();

        lastSolution = new int[boardSize][boardSize];  // 마지막 해답 배열 초기화
        check(board, 0);

        // 시간 측정 종료
        long endTime = System.nanoTime();
        PrintQ.print(lastSolution);  // 해답 출력

        // 걸린 시간 계산 (나노초를 밀리초로 변환)
        long duration = (endTime - startTime) / 1_000_000;  // 밀리초 단위

        System.out.println("탐색 시간: " + duration + " ms");

        return sol;
    }
}

import java.awt.EventQueue;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Stack;

public class NQueenSolver extends SwingWorker<Void, Void> {
    private final int boardSize;
    private final QueenPlacementGUI queenPlacementGUI;
    private final StackStateGUI stackStateGUI;
    private final int[][] board;
    private final Stack<String> stack; // 퀸 좌표를 저장할 스택
    private final JSlider speedSlider; // 속도 조절 슬라이더를 참조
    private volatile boolean isPaused = false; // 일시정지 여부
    private final Object lock = new Object(); // 일시정지 상태 관리
    private int solution = 0;

    public NQueenSolver(int boardSize, QueenPlacementGUI queenPlacementGUI, StackStateGUI stackStateGUI, JSlider speedSlider) {
        this.boardSize = boardSize;
        this.queenPlacementGUI = queenPlacementGUI;
        this.stackStateGUI = stackStateGUI;
        this.speedSlider = speedSlider; // 슬라이더 참조
        board = new int[boardSize][boardSize];
        stack = new Stack<>(); // 스택 초기화
    }

    // 탐색 일시정지
    public void pause() {
        isPaused = true;
    }

    // 탐색 재개
    public void resume() {
        synchronized (lock) {
            isPaused = false;
            lock.notify(); // 재개 신호 보내기
        }
    }

    private boolean checkSafe(int row, int col) {
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 1) return false;
        }
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) return false;
        }
        for (int i = row, j = col; i < boardSize && j >= 0; i++, j--) {
            if (board[i][j] == 1) return false;
        }
        return true;
    }

    private void solveNQueens(int col) throws InterruptedException {
        if (col >= boardSize){
            solution++;
            return;
        }

        for (int row = 0; row < boardSize; row++) {
            if (checkSafe(row, col)) {
                board[row][col] = 1;
                stack.push("(" + row + ", " + col + ")"); // 퀸 좌표 스택에 추가
                updateStackDisplay(); // 스택 상태 업데이트

                queenPlacementGUI.updateBoard(row, col, true); // 퀸 배치
                Thread.sleep(speedSlider.getValue()); // 애니메이션 속도 조절

                // 일시정지 상태 확인
                synchronized (lock) {
                    while (isPaused) {
                        lock.wait(); // 일시정지 상태일 때 대기
                    }
                }

                solveNQueens(col + 1);

                // 백트래킹: 퀸 제거
                board[row][col] = 0;
                stack.pop(); // 스택에서 좌표 제거
                updateStackDisplay(); // 스택 상태 업데이트

                queenPlacementGUI.updateBoard(row, col, false); // 퀸 제거
                Thread.sleep(speedSlider.getValue());
            }
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        solveNQueens(0);
        // 사용자에게 결과 화면 띄우기
        JOptionPane.showMessageDialog(null, "총 해답의 갯수 : " + solution);
        System.exit(0); // 프로그램 종료
        return null;
    }

    // 스택 상태를 업데이트하는 메서드
    private void updateStackDisplay() {
        ArrayList<String> stackCopy = new ArrayList<>(stack); // 스택을 복사하여 전달
        stackStateGUI.updateStackDisplay(stackCopy); // 스택 상태 GUI 업데이트
    }
}

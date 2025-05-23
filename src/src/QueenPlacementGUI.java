import javax.swing.*;
import java.awt.*;

public class QueenPlacementGUI extends JFrame {
    private final JLabel[][] chessBoard;
    private final int boardSize;

    public QueenPlacementGUI(int boardSize) {
        this.boardSize = boardSize;

        setTitle("Queen Placement");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 체스판 크기에 맞게 창 크기를 조절
        int cellSize = 600 / boardSize; // 각 셀의 크기를 동적으로 계산
        setBounds(100, 100, cellSize * boardSize, cellSize * boardSize);

        // 퀸 크기를 조절할 폰트 크기 설정 (셀 크기에 맞게 동적 설정)
        int queenFontSize = cellSize / 2; // 폰트 크기를 셀 크기의 절반으로 설정
        // 퀸의 크기를 조절할 폰트
        Font queenFont = new Font("Serif", Font.BOLD, queenFontSize);

        JPanel chessPanel = new JPanel();
        chessPanel.setLayout(new GridLayout(boardSize, boardSize));
        setContentPane(chessPanel);

        chessBoard = new JLabel[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                chessBoard[i][j] = new JLabel();
                chessBoard[i][j].setOpaque(true);
                chessBoard[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                chessBoard[i][j].setBackground((i + j) % 2 == 0 ? Color.BLACK : Color.WHITE);
                chessBoard[i][j].setFont(queenFont); // 퀸 크기를 조절하는 폰트 설정
                chessPanel.add(chessBoard[i][j]);
            }
        }
    }

    // 퀸 배치를 업데이트하는 메서드
    public void updateBoard(int row, int col, boolean place) {
        chessBoard[row][col].setText(place ? "●" : "");
        chessBoard[row][col].setForeground(Color.YELLOW);
    }
}

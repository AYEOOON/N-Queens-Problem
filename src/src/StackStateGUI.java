import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StackStateGUI extends JFrame {
    private final ArrayList<JLabel> stackLabels; // 스택 좌표를 표시할 JLabel 리스트
    private final int boardSize; // 보드 크기

    public StackStateGUI(int boardSize) {
        this.boardSize = boardSize;
        setTitle("Stack State");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(750, 100, 300, 400);

        // GridLayout을 사용해 스택 좌표를 격자 형태로 배치 (위에서부터 아래로 배치)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(boardSize + 1, 1)); // 보드 크기만큼 스택 좌표와 1개의 버튼을 배치
        setContentPane(panel);

        stackLabels = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            JLabel label = new JLabel("", SwingConstants.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            stackLabels.add(label);
            panel.add(label);
        }
    }

    // 스택 상태를 업데이트하는 메서드 (아래에서 위로 쌓이게)
    public void updateStackDisplay(ArrayList<String> stack) {
        // 모든 레이블을 초기화
        for (JLabel label : stackLabels) {
            label.setText(""); // 초기화 (빈 칸으로 만들기)
        }

        // 스택 상태를 위에서 아래로 쌓이게 표시 (최신 좌표가 위에 오게)
        for (int i = 0; i < stack.size() && i < boardSize; i++) {
            stackLabels.get(boardSize - 1 - i).setText(stack.get(i)); // 최신 스택 항목이 위로 가게 설정
        }
    }
}

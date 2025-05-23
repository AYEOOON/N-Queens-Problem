import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NQueenGUI extends JFrame {
    public static AbstractButton startButton;
    private final JTextField boardSizeField;
    private static int boardSize;
    private final JButton pauseButton, resumeButton;
    private final JSlider speedSlider; // 속도 조절 슬라이더
    private QueenPlacementGUI queenPlacementGUI;
    private StackStateGUI stackStateGUI;
    private int animationDelay = 300; // 기본 애니메이션 속도
    private NQueenSolver solver; // 탐색을 관리할 Solver 객체
    private JButton exitButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                NQueenGUI frame = new NQueenGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public NQueenGUI() {
        setTitle("N-Queens Problem Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 400);
        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new GridLayout(5, 1));

        JLabel label = new JLabel("체스판 크기를 입력하세요 (4 ~ 16):");
        panel.add(label);

        boardSizeField = new JTextField();
        panel.add(boardSizeField);

        startButton = new JButton("탐색 시작");
        panel.add(startButton);

        exitButton = new JButton("종료");
        panel.add(exitButton);

        // 속도 조절 슬라이더 추가
        speedSlider = new JSlider(50, 1000, 300); // 슬라이더 설정 (50ms ~ 1000ms)
        speedSlider.setMajorTickSpacing(500);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setPreferredSize(new Dimension(300, 50));
        panel.add(speedSlider);

        // 일시정지 및 재개 버튼 추가
        pauseButton = new JButton("일시 정지");
        pauseButton.setEnabled(false);
        panel.add(pauseButton);

        resumeButton = new JButton("재개");
        resumeButton.setEnabled(false);
        panel.add(resumeButton);


        JTextArea timeMessage = new JTextArea(
                "체스판의 크기가 16일 경우는 약 8분 이상의 시간이 소요되며\n" +
                        "15의 경우 1분 이상 소요됩니다.\n" +
                        "위 소요시간은 사용자의 PC환경에 따라 변동이 있을 수 있습니다."
        );
        timeMessage.setEditable(false); // 텍스트 수정 불가능
        timeMessage.setLineWrap(true); // 줄바꿈 설정
        timeMessage.setWrapStyleWord(true); // 단어 기준 줄바꿈
        panel.add(timeMessage);

        // 시작 버튼 클릭 시 각 GUI 창을 띄움
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    boardSize = Integer.parseInt(boardSizeField.getText());
                    if (boardSize < 4 || boardSize > 16) {
                        JOptionPane.showMessageDialog(null, "체스판 크기는 4 이상 16 이하로 입력해주세요.");
                        return;
                    }

                    // 슬라이더 값을 가져와서 애니메이션 속도를 설정
                    animationDelay = speedSlider.getValue();

                    queenPlacementGUI = new QueenPlacementGUI(boardSize);
                    stackStateGUI = new StackStateGUI(boardSize);
                    queenPlacementGUI.setVisible(true);
                    stackStateGUI.setVisible(true);

                    solver = new NQueenSolver(boardSize, queenPlacementGUI, stackStateGUI, speedSlider);
                    solver.execute(); // 백그라운드에서 탐색 시작

                    pauseButton.setEnabled(true); // 일시정지 버튼 활성화
                    startButton.setEnabled(false); // 시작 버튼 비활성화
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "유효한 숫자를 입력하세요.");
                }
            }
        });

        // 일시정지 버튼 클릭 이벤트
        pauseButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                solver.pause(); // 탐색 일시정지
                pauseButton.setEnabled(false);
                resumeButton.setEnabled(true);
            }
        });

        exitButton.addActionListener(e -> {
            System.exit(0); // 프로그램 종료
        });

        // 재개 버튼 클릭 이벤트
        resumeButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                solver.resume(); // 탐색 재개
                pauseButton.setEnabled(true);
                resumeButton.setEnabled(false);
            }
        });
    }

}

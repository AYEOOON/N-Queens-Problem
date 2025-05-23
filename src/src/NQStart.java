import java.awt.EventQueue;
import java.util.Scanner;

public class NQStart {
    static int boardSize;

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        // 사용자에게 GUI 또는 콘솔 실행 옵션을 묻는 부분
        System.out.println("실행 방식을 선택하세요: ");
        System.out.println("1: 콘솔");
        System.out.println("2: GUI");
        System.out.print("선택: ");
        int choice = InCheck.CheckInput(sc, "1 또는 2를 선택하세요: ");

        if (choice == 1) {
            System.out.println("체스판의 크기가 16일 경우는 약 8분 이상의 시간이 소요되며\n15의 경우 1분 이상 소요됩니다. \n위 소요시간은 사용자의 PC환경에 따라 변동이 있을 수 있습니다.\n");
            // 체스판 크기를 입력받음
            boardSize = InCheck.CheckInput(sc, "4와 16사이의 정수를 입력하세요 (체스판의 크기): ");
            // 콘솔 모드에서 실행
            int solutions = SolQ.solve(boardSize);
            System.out.println("총 해답의 갯수: " + solutions);
        } else if (choice == 2) {
            // GUI 모드에서 실행
            EventQueue.invokeLater(() -> {
                try {
                    NQueenGUI frame = new NQueenGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            System.out.println("잘못된 선택입니다. 프로그램을 종료합니다.");
        }

        sc.close();
    }
}

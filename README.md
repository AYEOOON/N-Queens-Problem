## 2024-2 어드벤처디자인 N-Queens Problem

### 1. 서론
 8-Queens 문제는 8 * 8 체스보드에서 8개의 여왕이 서로를 공격할 수 없도록 배치하      는 문제이다. 체스에서 여왕은 장애물이 없을 경우 전후좌우 그리고 대각선까지 원하는      칸 수 만큼 움직일 수 있다. 즉 체스보드에서 8개 여왕을 어떻게 배치하면 서로 공격할      수 있는 위치에 놓을 수 있는가에 대한 문제이다. 8-Queens 문제를 해결하여 알고리즘      설계 능력을 향상시키고, 이를 통해 백트래킹 및 제약 충족문제와 같은 탐색 알고리즘을      학습하는데 목적을 둔다. 

---

### 2. 실행 흐름도

 **<콘솔 모드>**
 
 ![image](https://github.com/user-attachments/assets/4361049b-ebf9-4bc8-ad9e-a0ed23edfcf4)

1. User(사용자)가 N-Queens 프로그램을 실행
2. NQStart(N-Queens 시작) 클래스가 실행되고, 프로그램이 시작
3. NQStart는 InCheck 클래스를 통해 사용자에게 체스판 크기를 입력하도록 요청
4. User는 체스판 크기를 입력
5. 이 입력값은 InCheck로 전달되어 유효성 검사를 거친 후 NQStart로 전달
6. NQStart는 입력된 크기를 바탕으로 SolQ(Solver for Queens) 클래스에게 N-Queens 문제를 해결하라고 요청
7. SolQ는 체스판에서 가능한 모든 해답을 탐색
8. SolQ가 모든 해답을 찾으면, PrintQ 클래스를 호출해 해답을 출력
9. SolQ는 모든 탐색이 끝난 후 총 몇 개의 해답이 있는지 NQStart에게 반환
10. NQStart는 이 결과를 사용자에게 출력


**<GUI 모드>**

![image](https://github.com/user-attachments/assets/68b49aa3-2049-4363-bb5a-c3613348486e)

1. User가 프로그램을 실행하고, NQStart 클래스가 실행
2. NQStart는 InCheck 클래스를 통해 사용자에게 콘솔 모드 또는 GUI 모드 중 하나를 선택하도록 요청
3. User는 2번을 선택하여 GUI 모드를 실행. 이 선택은 InCheck에서 NQStart로 전달
4. NQStart는 GUI 모드를 실행하기 위해 NQueenGUI 클래스를 호출
5. NQueenGUI는 사용자에게 체스판 크기를 입력하라고 요청
6. User는 GUI 화면에서 체스판 크기를 입력.
7. NQueenGUI는 입력된 크기를 바탕으로 NQueenSolver 클래스에게 N-Queens 문제 해결을 요청
8. NQueenSolver는 퀸 배치 상태를 QueenPlacementGUI로, 스택 상태를 StackStateGUI로 각각 업데이트하며 탐색을 진행
9. 탐색이 진행되는 동안 QueenPlacementGUI와 StackStateGUI는 퀸의 배치와 스택 상태를 실시간으로 시각적으로 업데이트
10. 탐색이 진행될 때마다 퀸이 추가되거나 제거되고, 그에 따라 스택의 상태도 업데이트
11. 모든 탐색이 끝나면, NQueenSolver는 탐색 결과(총 해답 수) 사용자에게 알림

---

### 3. 시스템 구성

![image](https://github.com/user-attachments/assets/51580e90-7ae2-44dc-9dbd-407032f052fe)


- NQStart: 메인 진입점, 실행 방식을 선택하고, GUI 또는 콘솔모드로 분기됨
- SolQ: 콘솔 모드에서 퀸 배치를 관리하고, N-Queens 문제를 해결
- CheckQ: 퀸을 안전하게 배치할 수 있는지 여부를 확인하는 메서드 포함
- NQueenGUI: GUI모드에서 N-Queens 문제를 백그라운드에서 해결, 퀸 배치 상태 및 스택 상태를 시각적으로 업데이트
- QueenPlancementGUI: 퀸의 현재 배치 상태를 시각적으로 보여주는 창
- StackStateGUI: 탐색 과정에서의 스택 상태를 시각적으로 보여주는 창
- InCheck: 사용자 입력을 검증하는 유틸리티 클래스
- PrintQ: 탐색 후 마지막 해를 출력하는 클래스


---

### 4. 주요 알고리즘
**백트래킹 알고리즘**
- N-Queens 문제의 해결을 위해 재귀적으로 모든 가능한 퀸 배치를 시도, 각 배치가         유효한지 확인하며, 유효하지 않은 경우 이전 단계로 되돌아감
- DFS와는 다르게 해를 찾는 도중, 지금의 경로가 해가 될 것 같지 않으면 그 경로를         더 이상 가지 않고 되돌아간다. ▶ **가지치기**  
  ![image](https://github.com/user-attachments/assets/fdd6658e-1154-492b-a80e-0db523252e33)

- 의사코드
```
	procedure bt(c) is 
    		if reject(P, c) then return   
    		if accept(P, c) then output(P, c)
    		s ← first(P, c)
    		while s ≠ NULL do
        		bt(s)
        		s ← next(P, s)
```  
1. reject(P, c) : 만약 다음의 노드가 후보가 아니라면 return으로 끝낸다.
2. accept(P, c) : 만약 현재 노드까지가 답과 일치한다면 답을 출력한다.
3. first(P, c) : 후보 C의 첫 번째 확장을 시작한다. 
4. 답이 없을 때까지 다음 함수를 계속 반복한다. 

**백트래킹을 통한 N-Queens 풀이 과정**
1. 첫 번째 열에서 퀸을 놓을 수 있는 위치를 찾는다. 퀸이 공격하지 않는 위치를 찾기 위해 행과 열, 대각선 방향을 확인한다.
2. 퀸을 첫 번째 열에 놓고, 두 번째 열로 넘어간다. 두 번째 열에서도 퀸을 놓을 수 있는 위치를 찾는다.
3. 두 번째 열에서도 퀸을 놓을 수 있으면 다음 열로 넘어간. 만약 놓을 수 없다면, 다시 첫 번째 열로 돌아가 퀸의 위치를 변경한다.(백트래킹).
4. 이런 식으로 모든 열에 퀸을 놓을 수 있을 때까지 계속 탐색한다. 모든 열에 퀸을 놓으면 하나의 해답을 찾은 것이며, 이후 새로운 해답을 찾기 위해 다시 백트래킹한다.

---

### 5. 개발 내용 및 실행 화면
**<콘솔 실행 화면>**  
![image](https://github.com/user-attachments/assets/1ec4278a-ecdc-4a4b-a6d7-93a0f4fce650)

- 처음 모든 해를 다 출력하려고 했지만, 입력크기가 커질수록 모든 해 출력이 
 불가능하다는 결론
- 따라서 수많은 해 중 마지막 해와 해의 개수만 출력하도록 조정하였다.

![image](https://github.com/user-attachments/assets/dd5fb4a1-832a-4f9e-88df-20717d69e938)

- 잘못된 입력을 받을 경우 오류 메시지를 출력하고, 프로그램 종료한다.


**<GUI 실행 화면>**  
![image](https://github.com/user-attachments/assets/af79a3ea-7827-4a9c-a14c-c24b34740447)

- 먼저 사용자의 입력을 받는다. GUI로 실행할 것이기 때문에 2를 입력한다.

![image](https://github.com/user-attachments/assets/7885f5d1-eb7a-4e33-85d7-a9a52f8a0a76)

- 위와 같은 GUI창이 뜨는데 빈 칸에 탐색하고 싶은 크기를 입력하고 탐색 시작 버튼을 누르면 입력 값을 알고리즘에 전달해 프로그램을 실행시킨다.
- GUI 창엔 안내 문구와 함께 동작 속도를 조절할 수 있는 슬라이드 바와 일시정지 버튼과 종료버튼이 위치한다.

![image](https://github.com/user-attachments/assets/e50ac07d-97bb-49a0-8d63-86bb44900407)
![image](https://github.com/user-attachments/assets/c699e96e-579c-4801-8956-b2215fa2de6f)

- 입력창에서 정수 값이 아닌 문자가 입력되면 잘못된 값을 입력하였다는 메시지를 띄우고, 다시 입력 값을 받을 수 있도록 예외처리 하여 프로그램실행에 차질이 생기지 않게 하였다.


![image](https://github.com/user-attachments/assets/3d98f3d7-9667-439c-ae45-e47042b5cf24)

- 탐색이 시작되면 사용자가 선택한 크기의 체스판에서 Queen이 서로 공격하지 않도록 배치되는지 확인하며, 스택의 상태와 함께 사용자에게 표시된다.
- 슬라이드 바의 크기가 큰 쪽으로 옮기면 슬립타임이 길어지고, 크기가 작은 쪽으로 슬라이드를 옮기면 슬립타임이 적어지도록 설계했다.

![image](https://github.com/user-attachments/assets/0fd6043a-02e1-4f1a-8fbb-28e36503bd1c)

- 일시정지 버튼을 누르면 다음과 같은 화면을 보이게 된다.
- 일시정지 버튼은 비 활성화되며, 재개버튼이 활성화된다.


![image](https://github.com/user-attachments/assets/6036b174-20e9-4ce5-b002-570e4d01f9ab)

- 탐색이 끝나면 총 해답의 갯수를 출력해주는 텍스트 박스가 띄워지도록 구현하였다.

---

### 6. 결론
main 메서드에서 NQueenGUI 프레임이 실행된다. 
NQueenGUI 생성자에서는 다음과 같은 GUI 요소들이 초기화된다.

```
	boardSizeField: 사용자로부터 체스판 크기를 입력받는 텍스트 필드.
	startButton: 탐색을 시작하는 버튼.
	exitButton: 프로그램을 종료하는 버튼.
	pauseButton/resumeButton: 탐색을 일시정지/재개하는 버튼
	speedSlider: 탐색 시각화 속도를 조절하는 슬라이더.
	timeMessage: 큰 보드 크기일 경우 예상 시간을 안내하는 텍스트 메시지.
```

체스판 크기 입력 확인을 위해 startButton을 클릭하면 입력된 체스판 크기 (boardSizeField 값)가 Integer.parseInt로 파싱된다. 4 ~ 16 범위 내의 값인지 확인하고, 범위 밖일 경우 경고 메시지를 표시하게된다.

이후 슬라이더에서 애니메이션 속도(지연 시간)를 가져와 animationDelay에 저장하게 되고,
보조 GUI 창이 생성되어 QueenPlacementGUI는 체스판에 퀸 배치를 시각화할 창을 띄우고,
 StackStateGUI는 현재 스택 상태를 표시하는 창을 생성하고 표시한다.
마지막으로 NQueenSolver 객체를 생성하여 탐색을 비동기로 시작하고, pauseButton을 활성화하며 startButton은 비 활성화하게 된다.

NQueenSolver는 SwingWorker를 상속하여 GUI 스레드와 별도로 백그라운드에서 N-Queens 문제를 해결하는데, doInBackground 메서드에서 solveNQueens(0) 메서드를 호출하여 첫 번째 열부터 탐색을 시작하며, 탐색이 완료되면 해답의 갯수를 사용자에게 JOptionPane으로 표시하고 프로그램을 종료하게된다.

solveNQueens 메서드에서는 열을 하나씩 순회하며 가능한 모든 퀸 배치를 시도한다. 현재 위치의 안전성을 확인하는 checkSafe 메서드를 호출하여 안전할 경우에만 퀸을 배치하고, stack에 좌표를 저장하게되며, 또한 updateStackDisplay 메서드로 StackStateGUI에 스택 상태를 반영하고, queenPlacementGUI.updateBoard 메서드로 퀸이 놓인 위치를 시각적으로 표시한다. 애니메이션 속도는 Thread.sleep을 통해 슬라이더 값에 따라 조절하며, lock 객체를 사용하여 isPaused 상태를 확인해 일시정지일 경우 lock.wait로 대기 상태에 들어가게된다.

일시정지 버튼을 클릭하면 solver.pause()를 호출하여 isPaused를 true로 설정하고, pauseButton을 비활성화하며 resumeButton을 활성화한다. 재개 버튼을 클릭하면 solver.resume()를 호출하여 isPaused를 false로 변경하고 lock.notify로 대기 상태를 해제하게되는데, 이때 pauseButton은 활성화되고 resumeButton은 비활성화된다.

QueenPlacementGUI는 현재 퀸의 배치 상태를 시각적으로 표시한다. updateBoard 메서드를 통해 퀸을 배치하거나 제거할 때 호출되며, 특정 위치에 "●"를 표시하여 퀸의 위치를 나타내고, StackStateGUI는 탐색 중 스택 상태를 시각적으로 표시한다. updateStackDisplay 메서드를 통해 현재 스택 상태를 갱신하여 최신 좌표가 상단에 오도록 표시한다.

사용자는 체스판 크기와 애니메이션 속도를 입력하고 탐색 과정을 시각화하며, 탐색이 종료되면 해답의 개수를 확인할 수 있다. 이 구조를 통해 사용자는 N-Queens 문제를 직관적으로 이해하고, 다양한 속도로 탐색 과정을 시각화할 수 있다.

---


**[프로그램 실행 영상]**
https://youtu.be/eJLiNFQ6KWU

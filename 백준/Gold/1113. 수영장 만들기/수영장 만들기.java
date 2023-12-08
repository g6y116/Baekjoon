import java.util.*;
import java.io.*;

public class Main {
	static int[] dx = {0,1,0,-1};
	static int[] dy = {1,0,-1,0};
	static int N,M,totalScore;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		int[][] board = new int[N][M];
		for (int i = 0; i < N; i++) {
			String s = br.readLine();
			for (int j = 0; j < M; j++) {
				board[i][j] = s.charAt(j)-'0';
			}
		}
		boolean[][] v = new boolean[N][M];
		for (int k = 1; k <= 9; k++) { // 낮은 숫자부터 BFS탐색을 진행
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if(board[i][j] == k && !v[i][j]) {
						v[i][j] = true;
						BFS(new int[] {i,j},board,v,k);
					}
				}
			}
		}
		System.out.println(totalScore);
		
	}
	
	public static void BFS(int[] start, int[][] board, boolean[][] v, int num) {
		Queue<int[]> q = new LinkedList<int[]>();
		q.add(start);
		List<int[]> candi = new ArrayList<int[]>();
		candi.add(start);
		int minHeight = Integer.MAX_VALUE; // 나를 둘러싼 벽 중 가장 낮은 높이 (물을 최대한으로 담을 수 있는 높이)
		boolean flood = false;
		while(!q.isEmpty()) {
			int[] now = q.poll();
			// 테두리와 연결되어 있으면 물이 흘러넘침
			if(now[0] == 0 || now[0] == N-1 || now[1] == 0 || now[1] == M-1) {
				flood = true;
			}
			for (int d = 0; d < 4; d++) {
				int nx = now[0]+dx[d];
				int ny = now[1]+dy[d];
				if(0 <= nx && nx < N && 0 <= ny && ny < M) {
                	// 웅덩이 후보
					if(!v[nx][ny] && board[nx][ny] == num) {
						v[nx][ny] = true;
						q.add(new int[] {nx,ny});
						candi.add(new int[] {nx,ny});
					}
                    // 내 옆(벾)이 나보다 낮으면 물이 흘러넘침
					if(board[nx][ny] < num) {
						flood = true;
					}
                    // 내 옆(벾)이 나보다 크면 벽이 됨
					if(board[nx][ny] > num) {
                    	// 나를 둘러싼 벽 중 가장 낮은 높이를 구함
						minHeight = Math.min(minHeight, board[nx][ny]);
					}
				}
			}
		}
        // 물이 흘러넘치지 않았으면
		if(!flood) {
        	// 물의 양을 더함
			totalScore += candi.size() * (minHeight - num);
			for (int i = 0; i < candi.size(); i++) {
				int[] now = candi.get(i);
				board[now[0]][now[1]] = minHeight; // 물을 채움
				v[now[0]][now[1]] = false; // 물이 더 찰수 있기 때문에 방문을 취소함
			}
		}
	}

}
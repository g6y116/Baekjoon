import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int M;
    static int N;
    static int[][] map;
    static boolean[][][] visited;
    static Robot start;
    static Robot finish;
    static int[] dx = {0, 0, 1, -1}; //동, 서, 남, 북
    static int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) throws Exception {
        init();
        bfs();
    }

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        map = new int[M + 1][N + 1];
        visited = new boolean[M + 1][N + 1][5];

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        start = new Robot(
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()),
                0
        );

        st = new StringTokenizer(br.readLine());
        finish = new Robot(
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()),
                0
        );
    }

    static void bfs() {
        Queue<Robot> q = new LinkedList<>();
        visited[start.x][start.y][start.dir] = true;
        q.add(start);

        while (!q.isEmpty()) {
            Robot r = q.poll();
            int x = r.x;
            int y = r.y;
            int dir = r.dir;
            int count = r.count;

            if (x == finish.x && y == finish.y && dir == finish.dir) {
                System.out.println(count);
                return;
            }

            for (int i = 1; i <= 3; i++) {
                int nx = x + (dx[dir - 1] * i);
                int ny = y + (dy[dir - 1] * i);

                if (nx <= 0 || ny <= 0 || nx > M || ny > N) continue;

                if (map[nx][ny] == 0) {
                    if (!visited[nx][ny][dir]) {
                        visited[nx][ny][dir] = true;
                        q.add(new Robot(nx, ny, dir, count + 1));
                    }
                } else {
                    break;
                }
            }

            for (int i = 1; i <= 4; i++) {
                if (dir != i && !visited[x][y][i]) {
                    int turn = 1;
                    if (dir == 1) {
                        if (i == 2) {
                            turn++;
                        }
                    } else if (dir == 2) {
                        if (i == 1) {
                            turn++;
                        }
                    } else if (dir == 3) {
                        if (i == 4) {
                            turn++;
                        }
                    } else {
                        if (i == 3) {
                            turn++;
                        }
                    }

                    visited[x][y][i] = true;
                    q.add(new Robot(x, y, i, count + turn));
                }
            }
        }
    }

    static class Robot {
        int x;
        int y;
        int dir;
        int count;

        public Robot(int x, int y, int dir, int count) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.count = count;
        }
    }
}
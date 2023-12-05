import java.util.*;
import java.io.*;

public class Main {

    static int N,M;
    static int[][] req = new int[9][9];
    static int[][] check = new int[9][9];
    static int res = 81;
    static ArrayList<Pair> cctvList = new ArrayList<>();
    static int[] dx = {0,1,0,-1};
    static int[] dy = {1,0,-1,0};

    static int voidNum(){
        int ans = 0;
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                if(check[i][j]==0) ans++;
            }
        }
        return ans;
    }

    static void move(int y, int x, int dir, boolean isCheck){
        int ny = y;
        int nx = x;

        while(true){
            ny += dy[dir];
            nx += dx[dir];

            if(nx<0 || nx>=M || ny<0 || ny>=N) break;
            if(req[ny][nx] == 6) break;

            if(isCheck) check[ny][nx]++;
            else{
                if(check[ny][nx]>0) check[ny][nx]--;
            }
        }
    }

    static void backtracking(int cnt){
        if(cnt == cctvList.size()){
            int ans = voidNum();
            if(res > ans) res = ans;
            return;
        }

        int y = cctvList.get(cnt).y;
        int x = cctvList.get(cnt).x;

        switch (req[y][x]){
            case 1:
                for(int dir=0;dir<4;dir++){
                    move(y,x,dir,true);
                    backtracking(cnt+1);
                    move(y,x,dir,false);
                }
                break;
            case 2:
                for(int dir=0;dir<2;dir++){
                    move(y,x,dir,true);
                    move(y,x,dir+2,true);
                    backtracking(cnt+1);
                    move(y,x,dir,false);
                    move(y,x,dir+2,false);
                }
                break;
            case 3:
                for(int dir=0;dir<4;dir++){
                    int newDir = dir+1;
                    if(newDir==4) newDir = 0;
                    move(y,x,dir,true);
                    move(y,x,newDir,true);
                    backtracking(cnt+1);
                    move(y,x,dir,false);
                    move(y,x,newDir,false);
                }
                break;
            case 4:
                move(y,x,0,true);
                move(y,x,1,true);
                move(y,x,2,true);
                move(y,x,3,true);
                for(int dir=0;dir<4;dir++){
                    move(y,x,dir,false);
                    backtracking(cnt+1);
                    move(y,x,dir,true);
                }
                move(y,x,0,false);
                move(y,x,1,false);
                move(y,x,2,false);
                move(y,x,3,false);
                break;
            case 5:
                move(y,x,0,true);
                move(y,x,1,true);
                move(y,x,2,true);
                move(y,x,3,true);
                backtracking(cnt+1);
                move(y,x,0,false);
                move(y,x,1,false);
                move(y,x,2,false);
                move(y,x,3,false);
                break;
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;j++){
                req[i][j] = Integer.parseInt(st.nextToken());
                if(req[i][j] != 0 && req[i][j] != 6){
                    cctvList.add(new Pair(i,j));
                    check[i][j] = 81;
                }
                else if(req[i][j] == 6) check[i][j] = 81;
            }
        }
        backtracking(0);
        System.out.println(res);
    }
}

class Pair{
    int y;
    int x;

    public Pair(int y, int x){
        this.y = y;
        this.x = x;
    }
}
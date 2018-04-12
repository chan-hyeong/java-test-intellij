package dfs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main2178 {

    private static final int X = 0, Y = 1;
    private static int N, M;
    private static int[][] a;
    private static boolean isFind = false;
    private static int cnt = 0;
    private static int test;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        N = sc.nextInt(); //가로 크기
        M = sc.nextInt(); //세로 크기

        a = new int[N + 2][M + 2]; //미로+테두리 표현, 0으로 초기
        sc.nextLine();

        for (int row = 1, col = 1; row <= N; row++) {
            for (char c : sc.nextLine().toCharArray()) {
                a[row][col++] = c - '0';
            }
            col = 1;
        }

        //todo : 입력 확인
//        for (int[] row : a) {
//            System.out.println(Arrays.toString(row));
//        }


        //탐색을 시작한다
        //bfs(new Point(1, 1), a, new boolean[N + 2][M + 2]);
        dfsRecursive(new Point(1, 1), new boolean[N + 2][M + 2]);


        System.out.println(cnt);
    }


    public static void dfsRecursive(Point V, boolean[][] visit) {
        visit[V.y][V.x] = true;

        if (V.x == M && V.y == N) {
            isFind = true;
        } else {
            for (int[] p : new int[][]{{V.x, V.y - 1}, {V.x + 1, V.y}, {V.x, V.y + 1}, {V.x - 1, V.y}}) {
                //System.out.print((++test)%10!=0? "." : ".\n");
                if (p[X] < 0 || p[Y] < 0 || p[X] > M || p[Y] > N) {
                    continue;
                }
                if (a[p[Y]][p[X]] == 1 && !visit[p[Y]][p[X]]) {
                  dfsRecursive(new Point(p[X], p[Y]), visit);
                }
            }
        }
        if (isFind) cnt++;
        else cnt--;
    }


    public static void bfs(Point V, int[][] a, boolean[][] visit) {
        Queue<Point> queue = new LinkedList<>();

        queue.offer(V);
        visit[V.x][V.y] = true;

        while (!queue.isEmpty()) {

            Point head = queue.peek();
            int x = head.x, y = head.y;

            //head 에서 이동가능한 곳을 살펴본다
            int[][] cross = {{x, y - 1}, {x + 1, y}, {x, y + 1}, {x - 1, y}};
            for (int[] t : cross) {
                if (a[t[0]][t[1]] == 1 && !visit[t[0]][t[1]]) {
                    queue.offer(new Point(t[0], t[1]));
                }
            }

        }

    }


}


class Point {
    int x, y; //미로의 좌표를 저장한다

    public Point() {
        this(0, 0);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x +
                ", " + y +
                ')';
    }
}



/*
미로 탐색
    시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
2 초	128 MB	32332	10178	6253	29.957%

문제
        N×M크기의 배열로 표현되는 미로가 있다.

        1	0	1	1	1	1
        1	0	1	0	1	0
        1	0	1	0	1	1
        1	1	1	0	1	1
        미로에서 1은 이동할 수 있는 칸을 나타내고, 0은 이동할 수 없는 칸을 나타낸다. 이러한 미로가 주어졌을 때, (1, 1)에서 출발하여 (N, M)의 위치로 이동할 때 지나야 하는 최소의 칸 수를 구하는 프로그램을 작성하시오.

        위의 예에서는 15칸을 지나야 (N, M)의 위치로 이동할 수 있다. 칸을 셀 때에는 시작 위치와 도착 위치도 포함한다.

입력
        첫째 줄에 두 정수 N, M(2≤N, M≤100)이 주어진다. 다음 N개의 줄에는 M개의 정수로 미로가 주어진다. 각각의 수들은 붙어서 입력으로 주어진다.

출력
        첫째 줄에 지나야 하는 최소의 칸 수를 출력한다. 항상 도착위치로 이동할 수 있는 경우만 입력으로 주어진다.


*/

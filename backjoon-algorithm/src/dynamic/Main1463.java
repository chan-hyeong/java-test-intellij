package dynamic;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main1463 {

    public static int[] dp ;

    public static int[] d; //queue 이용한 풀이 alsgur077 https://www.acmicpc.net/source/8381829

    public static Map<Integer, Integer> dMap;

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        //todo : [1] 가장 간단하게 dp 어레이 채워나가는 방법, 가장 오래걸리고 메모리 많이먹으나 쉽다
        long start = System.nanoTime(), end;
        dp = new int[N + 1];

        for (int i = 2; i <= N; i++) {
            dp[i] = dp[i - 1] + 1; // -1 경우
            if (i % 2 == 0)
                dp[i] = dp[i] > dp[i / 2] ? dp[i / 2] + 1 : dp[i];
            if (i % 3 == 0)
                dp[i] = dp[i] > dp[i / 3] ? dp[i / 3] + 1 : dp[i];
        }
        System.out.println(dp[N]);
        end = System.nanoTime();
        System.out.println("[" + (end - start) + "] fill dp array from '2' to 'N' in for loop.");
        //System.out.println(Arrays.toString(dp));


        System.out.println();

        //todo : [2] queue 를 이용하면 N만큼 반복돌지 않는듯, 속도 약 3배차이남
        start = System.nanoTime();
        d = new int[N + 1];
        dp(N);
        end = System.nanoTime();
        System.out.println("[" + (end - start) + "] fill dp array from 'N' to '1' in while loop by queue.");
        //System.out.println(Arrays.toString(d));

        System.out.println();

        //todo : [3] 배열 전체를 사용할 필요가 없다면 map을 쓰는게? 한 300,000 이상이면 [2]번보다 빨라짐
        start = System.nanoTime();

        dMap = new HashMap<>();
        dpMap(N);
        end = System.nanoTime();
        System.out.println("[" + (end - start) + "] fill dpMap from 'N' to '1' in while loop by queue.");
        //System.out.println(dMap.size());


    }//end main


    /**
     * 재귀로 모든 경우수를 찾는건 중복 작업이 많음
     *
     * @param n
     * @param dept
     * @return
     */
    public static int getMinStep(int n, String dept) {
        if (n == 1) return 0;

        List<Integer> comp = new ArrayList<>();


        if (n % 3 == 0) {
            comp.add(getMinStep(n / 3, dept + ">"));
        }
        if (n % 2 == 0) {
            comp.add(getMinStep(n / 2, dept + ">"));
        }
        if (n > 1) {
            comp.add(getMinStep(n - 1, dept + ">"));
        }


        int min = comp.get(0);
        for (int val : comp) {
            if (min > val) //최소값 찾기
                min = val;
        }

        return 1 + min;


    }//end recursive function

    /**
     * queue 를 이용하면 더 빠른듯, 전체 경우를 안보나??
     *
     * @param number
     * @throws Exception
     */
    public static void dp(int number) throws Exception {

        int temp;
        Queue<Integer> q = new LinkedList<Integer>();
        q.offer(number);

        while (!q.isEmpty()) {
            temp = q.poll();
            if (temp <= 1)
                break;
            if (temp % 3 == 0 && d[temp / 3] == 0) {
                d[temp / 3] = d[temp] + 1;
                q.offer(temp / 3);
            }

            if (temp % 2 == 0 && d[temp / 2] == 0) {
                d[temp / 2] = d[temp] + 1;
                q.offer(temp / 2);
            }

            if (temp - 1 > 0 && d[temp - 1] == 0) {
                d[temp - 1] = d[temp] + 1;
                q.offer(temp - 1);
            }
        }
        System.out.println(d[1]);
    }

    public static void dpMap(int number) throws Exception {

        int temp;
        Queue<Integer> q = new LinkedList<Integer>();
        q.offer(number);

        while (!q.isEmpty()) {
            temp = q.poll();
            if (temp <= 1)
                break;

            int thisval = dMap.containsKey(temp) ? dMap.get(temp) : 0;
            if (temp % 3 == 0 && (!dMap.containsKey(temp/3) || dMap.get(temp / 3) == 0)) {
                dMap.put(temp / 3, thisval + 1);
                q.offer(temp / 3);
            }

            if (temp % 2 == 0 && (!dMap.containsKey(temp/2) || dMap.get(temp / 2) == 0)) {
                dMap.put(temp / 2, thisval + 1);
                q.offer(temp / 2);
            }

            if (temp - 1 > 0 && (!dMap.containsKey(temp-1) || dMap.get(temp - 1) == 0)) {
                dMap.put(temp - 1, thisval + 1);
                q.offer(temp - 1);
            }
        }
        System.out.println(dMap.get(1));
    }

}
/*

https://www.acmicpc.net/problem/1463

1로 만들기
        시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
        2 초	128 MB	40371	13041	8614	32.571%
        문제
        정수 X에 사용할 수 있는 연산은 다음과 같이 세 가지 이다.

        X가 3으로 나누어 떨어지면, 3으로 나눈다.
        X가 2로 나누어 떨어지면, 2로 나눈다.
        1을 뺀다.
        정수 N이 주어졌을 때, 위와 같은 연산 세 개를 적절히 사용해서 1을 만들려고 한다. 연산을 사용하는 횟수의 최소값을 출력하시오.

입력
        첫째 줄에 1보다 크거나 같고, 106보다 작거나 같은 자연수 N이 주어진다.

출력
        첫째 줄에 연산을 하는 횟수의 최소값을 출력한다.

예제 입력 1  복사
        2
예제 출력 1  복사
        1
예제 입력 2  복사
        10
예제 출력 2  복사
        3
힌트
        10의 경우에 10 -> 9 -> 3 -> 1 로 3번 만에 만들 수 있다.

*/

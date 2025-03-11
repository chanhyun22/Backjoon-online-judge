import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    public static class Point implements Comparable<Point> {
        int a;
        int b;
        int w;
        String num;

        public Point(int a, int b, int w, String num) {
            this.a = a;
            this.b = b;
            this.w = w;
            this.num = num;
        }

        @Override
        public int compareTo(Point o) {
            if (this.w != o.w)
                return this.w - o.w;
            // 사전순 비교: 경로 문자열을 공백으로 분리하여 정수 단위로 비교
            String[] tokens1 = this.num.split(" ");
            String[] tokens2 = o.num.split(" ");
            int len = Math.min(tokens1.length, tokens2.length);
            for (int i = 0; i < len; i++) {
                int v1 = Integer.parseInt(tokens1[i]);
                int v2 = Integer.parseInt(tokens2[i]);
                if (v1 != v2)
                    return v1 - v2;
            }
            return tokens1.length - tokens2.length;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<Integer>[] list = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            list[u].add(v);
            list[v].add(u);
        }
        
        // 인접 리스트를 정렬하여 오름차순 탐색
        for (int i = 1; i <= N; i++) {
            Collections.sort(list[i]);
        }
        
        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        boolean[] visited = new boolean[N + 1];
        visited[a] = true;
        PriorityQueue<Point> pq = new PriorityQueue<>();
        // 초기 경로 문자열은 시작 정점을 포함
        pq.add(new Point(a, a, 0, a + ""));
        int ans = 0;
        Point pathToB = null;

        while (!pq.isEmpty()) {
            Point p = pq.poll();
            if (p.b == b) {
                pathToB = p;
                break;
            }
            for (int next : list[p.b]) {
                if (!visited[next]) {
                    visited[next] = true;
                    pq.add(new Point(p.b, next, p.w + 1, p.num + " " + next));
                }
            }
        }
        
        if (pathToB != null) {
            ans += pathToB.w;
            // 첫 번째 경로의 중간 정점을 재방문하지 않도록 표시 (시작과 끝 제외)
            visited = new boolean[N + 1];
            String[] tokens = pathToB.num.split(" ");
            for (int i = 1; i < tokens.length - 1; i++) {
                visited[Integer.parseInt(tokens[i])] = true;
            }
        }
        
        // 두 번째 경로: b에서 a로 탐색 (첫 번째 경로의 중간 노드 제외)
        pq.clear();
        visited[b] = true;
        pq.add(new Point(b, b, 0, b + ""));
        Point pathToA = null;
        while (!pq.isEmpty()) {
            Point p = pq.poll();
            if (p.b == a) {
                pathToA = p;
                break;
            }
            for (int next : list[p.b]) {
                if (!visited[next]) {
                    visited[next] = true;
                    pq.add(new Point(p.b, next, p.w + 1, p.num + " " + next));
                }
            }
        }
        if (pathToA != null) {
            ans += pathToA.w;
        }
        bw.write(ans + "");
        br.close();
        bw.close();
    }
}

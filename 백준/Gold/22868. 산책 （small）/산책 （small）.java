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
			if (this.w!= o.w) {
				return this.w- o.w;
			}
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
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			list[a].add(b);
			list[b].add(a);
		}
		
		st = new StringTokenizer(br.readLine());

		int a = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());

		boolean[] visited = new boolean[N + 1];
		visited[a] = true;
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(a, a, 0, a+""));

		int ans = 0;

		while (!pq.isEmpty()) {
			Point p = pq.poll();
			if (p.b == b) {
				ans += p.w;
				visited = new boolean[N+1];
				st = new StringTokenizer(p.num);
				st.nextToken();
				for (int i = 0; i < p.w - 1; i++) {
					visited[Integer.parseInt(st.nextToken())] = true;
				}
				break;
			}
			for (int num : list[p.b]) {
				if (!visited[num]) {
					visited[num] = true;
					pq.add(new Point(p.b, num, p.w + 1 , p.num+" " +num));
				}
			}
		}

		pq.clear();
		
		visited[a] = false;
		pq.add(new Point(b, b, 0, b+ ""));
		visited[b] = true;

		while (!pq.isEmpty()) {
			Point p = pq.poll();
			if (p.b == a) {
				ans += p.w;
				break;
			}
			for (int num : list[p.b]) {
				if (!visited[num]) {
					visited[num] = true;
					pq.add(new Point(p.b, num, p.w + 1, p.num + " " + num));
				}
			}
		}

		bw.write(ans + "");

		br.close();
		bw.close();
	}
}

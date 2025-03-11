import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static int N, M, R;
	static int[][] map;
	static int[][] ans;
	static int[][] temp;
	static int[][] temp2;
	static int[] arr = { 0, 1, 2, 3 };
	static boolean xflip = false;
	static boolean yflip = false;
	static int rotate = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		map = new int[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < R; i++) {
			int num = Integer.parseInt(st.nextToken());
			int temp = arr[0];
			if (num == 1) {
				arr[0] = arr[2];
				arr[2] = temp;
				temp = arr[1];
				arr[1] = arr[3];
				arr[3] = temp;

				if (rotate % 2 == 0) {
					xflip = !xflip;
				} else {
					yflip = !yflip;
				}
			} else if (num == 2) {
				arr[0] = arr[1];
				arr[1] = temp;
				temp = arr[2];
				arr[2] = arr[3];
				arr[3] = temp;

				if (rotate % 2 == 0) {
					yflip = !yflip;
				} else {
					xflip = !xflip;
				}
			} else if (num == 3) {
				arr[0] = arr[2];
				arr[2] = arr[3];
				arr[3] = arr[1];
				arr[1] = temp;

				rotate = (rotate + 1) % 4;
			} else if (num == 4) {
				arr[0] = arr[1];
				arr[1] = arr[3];
				arr[3] = arr[2];
				arr[2] = temp;

				rotate = (rotate + 3) % 4;
			} else if (num == 5) {
				arr[0] = arr[2];
				arr[2] = arr[3];
				arr[3] = arr[1];
				arr[1] = temp;
			} else {
				arr[0] = arr[1];
				arr[1] = arr[3];
				arr[3] = arr[2];
				arr[2] = temp;
			}
		}

		answer();
		
		for (int i=0; i<ans.length; i++) {
			for (int j=0; j<ans[0].length; j++) {
				bw.write(ans[i][j] + " ");
			}
			bw.write("\n");
		}

		br.close();
		bw.close();
	}

	private static void answer() {
		if (rotate % 2 == 0) {
			ans = new int[N][M];
		} else {
			ans = new int[M][N];
		}

		for (int i = 0; i < 4; i++) {
			temp = new int[N / 2][M / 2];
			temp2 = new int[N / 2][M / 2];
			for (int r = 0; r < N / 2; r++) {
				for (int c = 0; c < M / 2; c++) {
					temp[r][c] = map[(N / 2) * (arr[i] / 2) + r][(M / 2) * (arr[i] % 2) + c];
				}
			}

			if (xflip) {
				xflip(i);
			}

			if (yflip) {
				yflip(i);
			}

			rotate(i, rotate);
		}

	}

	private static void rotate(int i, int state) {
		if (state == 1) {
			for (int r = 0; r < M / 2; r++) {
				for (int c = 0; c < N / 2; c++) {
					temp2[N/2-c-1][r] = temp[c][r];
				}
			}
		} else if (state == 2) {
			for (int r=0; r<N/2; r++ ) {
				for (int c=0; c<M/2; c++) {
					temp2[N/2-r-1][M/2-c-1] = temp[r][c];
				}
			}
		} else if (state == 3) {
			for (int r=0; r<M/2; r++ ) {
				for (int c=0; c<N/2; c++) {
					temp2[c][M / 2 - r - 1] = temp[c][r];
				}
			}
		} else {
			for (int r=0; r<N/2; r++ ) {
				for (int c=0; c<M/2; c++) {
					temp2[r][c] = temp[r][c];
				}
			}
		}
		
		if (rotate % 2 == 1) {
			for (int r=0; r<M/2; r++) {
				for(int c=0; c<N/2; c++) {
					ans[(M/2) * (i/2) + r][(N/2) * (i%2) + c] = temp2[c][r]; 
				}
			}
		} else {
			for (int r=0; r<N/2; r++) {
				for(int c=0; c<M/2; c++) {
					ans[(N/2) * (i/2) + r][(M/2) * (i%2) + c] = temp2[r][c]; 
				}
			}
		}

	}

	private static void yflip(int i) {
		for (int r = 0; r < N / 2; r++) {
			for (int c = 0; c < M / 2; c++) {
				temp2[r][c] = temp[r][M / 2 - c - 1];
			}
		}

		for (int r = 0; r < N / 2; r++) {
			for (int c = 0; c < M / 2; c++) {
				temp[r][c] = temp2[r][c];
			}
		}
	}

	private static void xflip(int i) {
		for (int r = 0; r < N / 2; r++) {
			for (int c = 0; c < M / 2; c++) {
				temp2[r][c] = temp[N / 2 - r - 1][c];
			}
		}

		for (int r = 0; r < N / 2; r++) {
			for (int c = 0; c < M / 2; c++) {
				temp[r][c] = temp2[r][c];
			}
		}

	}

}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		int n = 50, p = 5, c = 5;
		int ctrlFile = 0;
		int[] y = new int[50];
		int[] salary = new int[311];

		try {
			File demand = new File("yearly_player_demand.txt");
			Scanner in = new Scanner(demand);
			String line = in.nextLine();
			while (in.hasNextLine()) {
				line = in.nextLine();
				if (line != null) {
					if (line.length() == 3)
						y[ctrlFile] = Integer.parseInt(line.substring(2, 3));
					else if (line.length() == 4)
						y[ctrlFile] = Integer.parseInt(line.substring(3, 4));
					else
						y[ctrlFile] = Integer.parseInt(line.substring(3, 5));
					ctrlFile++;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ctrlFile = 1;
		salary[0] = 0;

		try {
			File salaries = new File("players_salary.txt");
			Scanner in = new Scanner(salaries);
			String line = in.nextLine();
			while (in.hasNextLine()) {
				line = in.nextLine();
				if (line != null) {
					int length = line.length();
					switch (length) {
					case 3:
						salary[ctrlFile] = Integer.parseInt(line.substring(2, 3));
						break;
					case 4:
						salary[ctrlFile] = Integer.parseInt(line.substring(2, 4));
						break;
					case 5:
						salary[ctrlFile] = Integer.parseInt(line.substring(3, 5));
						break;
					case 6:
						salary[ctrlFile] = Integer.parseInt(line.substring(3, 6));
						break;
					default:
						salary[ctrlFile] = Integer.parseInt(line.substring(4, 7));
						break;
					}
					ctrlFile++;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int arrayLength = 0;
		int controlMin = 0;
		int caseControl = 0;
		for (int i = 0; i < n; i++)
			arrayLength = arrayLength + y[i];

		int[][] dp = new int[n][arrayLength];
		dp[0][0] = 0; // Baþlangýç deðeri

		for (int i = 0; i < n; i++) {
			if (y[i] > p) {
				for (int j = 0; j < arrayLength; j++) {
					controlMin = 0;
					caseControl = 0;
					for (int k = 0; k < y[i] - p + j + 1; k++) {
						caseControl = (y[i] - p - k) * c + salary[k];
						if (controlMin <= caseControl) 
							controlMin = caseControl;
					}
					if (i != 0) 
						dp[i][j] = dp[i - 1][j] + controlMin;
					else 
						dp[i][j] = controlMin;
				}
			} else if (p >= y[i]) {
				for (int j = 0; j < arrayLength; j++) {
					controlMin = 0;
					caseControl = 0;
					for (int k = 0; k < p - y[i] + j + 1; k++) {
						caseControl = salary[k];
						if (controlMin <= caseControl) 
							controlMin = caseControl;
					}
					if (i != 0) 
						dp[i][j] = dp[i - 1][j] + controlMin;
					else 
						dp[i][j] = controlMin;
				}
			}
		}

		int minCost = Integer.MAX_VALUE;
		for (int j = 0; j < arrayLength; j++) {
			minCost = Math.min(minCost, dp[n - 1][j]);
		}
		System.out.println("Minimum cost to promote players: " + minCost);

		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[i].length; j++) {
				System.out.print(dp[i][j] + " ");
			}
			System.out.println();
		} 

	}
}

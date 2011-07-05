package no.uka.findmyapp.service;

import javax.inject.Singleton;

import org.springframework.stereotype.Service;

@Service
@Singleton
public class EditDistanceService {
	/*
	 * Edit distance finds the number of delete, add and substitute operations needed to convert qry
	 * to str. splitDistance does the sum of editdistance for each word in qry. fromStart and toEnd
	 * decides if qry has to match str from the start or/and to the end respectively
	 */
	public static int splitDistance(String str, String qry) {
		int n = 0;
		String qrys[] = qry.split(" ");
		for (int i = 0; i < qrys.length; i++) {
			if (qrys[i].length() > 0) {
				n += editDistance(str, qrys[i], false, false);
			}
		}
		return n;
	}
	public static int editDistance(String str, String qry, Boolean fromStart, Boolean toEnd) {
		int dTable[][] = new int[qry.length()][str.length()]; 
		for (int i = 0; i < qry.length(); i++) {
			dTable[i][0] = i;
		}
		if (fromStart) {
			for (int j = 0; j < str.length(); j++) {
				dTable[0][j] = j;
			}
		}
		
		for (int i = 1; i < qry.length(); i++) {
			for (int j = 1; j < str.length(); j++) {
				dTable[i][j] = dTable[i-1][j-1] + substCost(qry.charAt(i), str.charAt(j));
				dTable[i][j] = Math.min(dTable[i][j], dTable[i-1][j] + 1);
				dTable[i][j] = Math.min(dTable[i][j], dTable[i][j-1] + 1);
			}
		}
		
		int min = dTable[qry.length()-1][str.length()-1];
		if (toEnd) {
			return min;
		}

		for (int j = 0; j < str.length(); j++) {
			min = Math.min(min, dTable[qry.length()-1][j]);
		}
		return min;
	}
	
	private static int substCost(char a, char b) {
		if (a == b) {
			return 0;
		}
		return 1;
	}
}

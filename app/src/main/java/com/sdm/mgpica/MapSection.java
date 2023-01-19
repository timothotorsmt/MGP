package com.sdm.mgpica;

import 	android.content.Context;
import android.view.SurfaceView;

import java.io.BufferedReader;
import java.io.File;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Random;

public class MapSection {
    // pre-defined section oop
    int[][] Section = new int[5][9];

    public void importCSV(SurfaceView _view, String CSV) {
        try {
            Context context = _view.getContext();
            String csvfileString = context.getApplicationInfo().dataDir + File.separatorChar + CSV;
            File csvfile = new File(csvfileString);
            CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
            //final CSVReader reader = new CSVReader(new InputStreamReader(context.getAssets().open(CSV)));

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                System.out.println(nextLine[0] + nextLine[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("The specified file was not found.");
        }
    }

    public void GenerateChunk() {
        // Instance of random class
        Random rand = new Random();
        int HasPlatform = rand.nextInt(5);
        if (HasPlatform == 0) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            int Seed = rand.nextInt(3);
            if (Seed < 2) {
                for (int j = 0; j < 9; j++) {
                    Section[i][j] = 0;
                }
            } else {
                // CSV AINT WORKING AHH
                // Doing this instead.
                // Generates a number between 0 and 2
                // For walls
                int LwallNum = rand.nextInt(5);
                if (LwallNum > 3) {
                    LwallNum -= 3;
                    for (int j = 0; j < (LwallNum); j++) {
                        Section[i][j] = 1;
                    }
                } else {
                    LwallNum = 0;
                }
                int RwallNum = rand.nextInt(5);
                if (RwallNum > 3) {
                    RwallNum -= 3;
                    for (int j = 0; j < (RwallNum); j++) {
                        Section[i][8 - j] = 1;
                    }
                } else {
                    RwallNum = 0;
                }
                int CWGap = (9 - LwallNum - RwallNum);
                int BreakableNum = rand.nextInt(CWGap * 2);
                int WallGap = 0;
                if (BreakableNum < CWGap) {
                    WallGap = rand.nextInt(CWGap - BreakableNum);
                    for (int j = LwallNum + WallGap; j < BreakableNum; j++) {
                        Section[i][8-j] = 2;
                    }
                }
            }
        }
    }
}

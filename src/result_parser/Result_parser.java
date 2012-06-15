/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package result_parser;

import java.io.*;

/**
 *
 * @author jota
 */
public class Result_parser {

    /**
     * @param args the command line arguments
     */
    public static int threads = 6;
    public static int current_thread = 0;
    public static String file;
    public static String experiment = "";
    public static String[][] data;
    public static String[][] sd;
    public static int number_of_files = 5;
    public static int file_number = 0;
    public static boolean starting_table = true;
    public static String table = "cache%"; /*time for time charts, cache for total load misses tables, cache% for % of cache misses*/

    public static void main(String[] args) {
        try {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream("result.txt");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            data = new String[1000][1000];
            sd = new String[1000][1000];
            int x = 0;
            int y = 0;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                /*
                 * start of stats
                 */
                if (strLine.contains(".cnf")) {
                    int pos_end = strLine.indexOf(".cnf");
                    int pos_start = pos_end;
                    while (strLine.charAt(pos_start - 1) != '/') {
                        pos_start = pos_start - 1;
                    }
                    file = strLine.substring(pos_start, pos_end);
                    pos_start = strLine.indexOf('\'') + 3;
                    pos_end = strLine.indexOf("-f") - 1;
                    String previous_experiment = String.copyValueOf(experiment.toCharArray());
                    experiment = strLine.substring(pos_start, pos_end);
                    if (!experiment.equals(previous_experiment) && starting_table != true) {
                        starting_table = true;
                        x = x + threads + 2;
                        file_number = 0;
                    }
                    if (starting_table == true) {
                        data[x][y] = String.copyValueOf(experiment.toCharArray());
                        x = x + 1;
                        data[x][y] = "Threads";
                        starting_table = false;
                        for (int i = 1; i < threads + 1; i++) {
                            data[x + i][y] = String.valueOf(i);
                        }
                    }
                    current_thread = Integer.parseInt(strLine.substring(strLine.indexOf(" -t ") + 4).substring(0, 1));
                    if (current_thread == 1) {
                        file_number = file_number + 1;
                        data[x][y + file_number] = String.copyValueOf(file.toCharArray());
                    }
                }
                if (table.equals("time")) {
                    if (strLine.contains("seconds time elapsed")) {
                        int pos_start = 0;
                        int pos_end;
                        while (!Character.isDigit(strLine.charAt(pos_start))) {
                            pos_start++;
                        }
                        pos_end = pos_start;
                        while (Character.isDigit(strLine.charAt(pos_end)) || strLine.charAt(pos_end) == '.') {
                            pos_end++;
                        }
                        double time = Double.parseDouble(strLine.substring(pos_start, pos_end));
                        data[x + current_thread][y + file_number] = String.valueOf(time);
                    }
                }
                if (table.equals("cache")) {
                    if (strLine.contains("LLC-load-misses")) {
                        int pos_start = 0;
                        int pos_end;
                        while (!Character.isDigit(strLine.charAt(pos_start))) {
                            pos_start++;
                        }
                        pos_end = pos_start;
                        while (Character.isDigit(strLine.charAt(pos_end)) || strLine.charAt(pos_end) == '.' || strLine.charAt(pos_end) == ',') {
                            pos_end++;
                        }
                        long misses = Long.parseLong(strLine.substring(pos_start, pos_end).replaceAll(",",""));
                        data[x + current_thread][y + file_number] = String.valueOf(misses);
                    }
                }
                if (table.equals("cache%")) {
                    if (strLine.contains("LLC-load-misses")) {
                        int pos_start = strLine.indexOf("#");
                        int pos_end;
                        while (!Character.isDigit(strLine.charAt(pos_start))) {
                            pos_start++;
                        }
                        pos_end = pos_start;
                        while (Character.isDigit(strLine.charAt(pos_end)) || strLine.charAt(pos_end) == '.' || strLine.charAt(pos_end) == ',') {
                            pos_end++;
                        }
                        float misses = Float.parseFloat(strLine.substring(pos_start, pos_end).replaceAll(",",""));
                        data[x + current_thread][y + file_number] = String.valueOf(misses);
                        pos_start = strLine.indexOf("+-") + 4;
                        pos_end = pos_start;
                        while(strLine.charAt(pos_end)!=('%')){
                            pos_end = pos_end+1;
                        }
                        float error = Float.parseFloat(strLine.substring(pos_start, pos_end).replaceAll(",",""));
                        sd[x + current_thread][y + file_number] = String.valueOf(error);
                    }
                }
            }
            for (int i = 0; i < 3 * (threads + 3); i++) {
                for (int j = 0; j < 50; j++) {
                    if (data[i][j] != null) {
                        System.out.print(data[i][j] + " ; ");
                    }
                }
                System.out.print("\n");
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        try {
            // Create file 
            FileWriter fstream = new FileWriter("out.csv");
            BufferedWriter out = new BufferedWriter(fstream);
            for (int i = 0; i < 3 * (threads + 3); i++) {
                for (int j = 0; j < 50; j++) {
                    if (data[i][j] != null) {
                        out.write(data[i][j] + " ; ");
                    }
                }
                out.write("\n");
            }
            for (int i = 0; i < 3 * (threads + 3); i++) {
                for (int j = 0; j < 50; j++) {
                    if (sd[i][j] != null) {
                        out.write(sd[i][j] + " ; ");
                    }
                }
                out.write("\n");
            }
            //Close the output stream
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
}

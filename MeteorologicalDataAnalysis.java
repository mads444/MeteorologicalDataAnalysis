// Madison Crewse Program 6 
import java.util.Scanner;

public class MeteorologicalDataAnalysis {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        // maximum number of records (100)
        int MAX_RECORDS = 100;

        System.out.print("\nThis Program will provide the user with the "
                         + "necessary information to answer"
                         + " meteorologist's questions.\n\n");

     // number of records from the keyboard
        double method_input = 0;       
     // number of records using predefined sample
        double method_predefined = 0;   

        // arrays for the fields
        double days[]        = new double[MAX_RECORDS];
        double high_temps[]  = new double[MAX_RECORDS];
        double record_highs[]= new double[MAX_RECORDS];
        int record_years[]   = new int   [MAX_RECORDS];
        double humidities[]  = new double[MAX_RECORDS];
        int method_option;
        int numberOfRecords;

        // loop repeating until the user decides to not
        char continueChoice;
        do {
            System.out.println("\n--- Meteorologist’s Data Analysis ---");
            System.out.println(" Welcome! In this program you can load "
            		+ "weather data\n and answer questions about "
            		+ "temperature and humidity.");

            // loading data from loadData function
            System.out.print("Would you like to load data from the "
            		+ "keyboard (1) or use predefined sample data (2)? : ");
            method_option = kb.nextInt();

            numberOfRecords = loadData(kb, method_option,
                                       days, high_temps, record_highs,
                                       record_years, humidities);

            // track which method was chosen (kb (1) or sample (2))
            if (method_option == 1) {
                method_input = numberOfRecords;
            } else {
                method_predefined = numberOfRecords;
            }

            // menu-driven analysis
            serviceMenu(kb,
                        days, high_temps, record_highs,
                        record_years, humidities,
                        numberOfRecords);

            System.out.print("Analyze another data set? (Y/N): ");
            continueChoice = kb.next().charAt(0);
        } while (continueChoice == 'Y' || continueChoice == 'y');

        System.out.println("Bye Bye! :]");
        kb.close();
    }

    // loads either keyboard or predefined data
    public static int loadData(Scanner kb, int method_option, double days[],
    		double high_temps[], double record_highs[], int record_years[],
            double humidities[]) {
    	
        if (method_option == 1) {
            return loadKeyboardData(kb, days, high_temps,
                                    record_highs, record_years, humidities);
        } else {
            return loadPredefinedData(days, high_temps,
                                      record_highs, record_years, humidities);
        }
    }

    // reads keyboard data until day = 0
    public static int loadKeyboardData(Scanner kb, double days[],
    		double high_temps[], double record_highs[], int record_years[],
    		double humidities[]) {
    	
        System.out.println("\nEnter each record by day. To end, enter day = 0.");
        int count = 0;
        
        System.out.print("Day: (1–365, 0 to stop): ");
        int day = kb.nextInt();
        
        while (count < days.length && day != 0) {
            days[count]         = day;
            System.out.print("High temp (°F): ");
            high_temps[count]   = kb.nextDouble();
            System.out.print("Record high (°F): ");
            record_highs[count] = kb.nextDouble();
            System.out.print("Year record set: ");
            record_years[count] = kb.nextInt();
            System.out.print("Humidity (%): ");
            humidities[count]   = kb.nextDouble();
            count++;
            System.out.print("Day (1–365, 0 to stop): ");
            day = kb.nextInt();
        }
        return count;
    }

    // loads the predefined sample data
    public static int loadPredefinedData(
            double days[], double high_temps[],
            double record_highs[], int record_years[],
            double humidities[]) {
        double sampleDays[]     = {41, 44, 46, 50, 51, 52, 0};
        double sampleHighs[]    = {98.25, 96.12, 92.58, 88.51, 90.21, 82.02};
        double sampleRecHighs[] = {101.1, 98.2, 97.3, 88.6, 91.9, 88.8};
        int    sampleRecYears[] = {1975, 1966, 1942, 1937, 1912, 1998};
        double sampleHums[]     = {10.1, 6.2, 9.3, 12.5, 15.3, 7.9};

        int count = 0;
        for (int i = 0; i < sampleDays.length && sampleDays[i] != 0; i++) {
            days[count]         = sampleDays[i];
            high_temps[count]   = sampleHighs[i];
            record_highs[count] = sampleRecHighs[i];
            record_years[count] = sampleRecYears[i];
            humidities[count]   = sampleHums[i];
            count++;
        }
        return count;
    }

    // displays the analysis menu
    public static void serviceMenu(Scanner kb, double days[],
    		double high_temps[], double record_highs[], int record_years[],
    		double humidities[], int numberOfRecords) {
        int choice;
        
        do {
            System.out.println("\n--- Analysis Menu ---");
            System.out.println("(1) Display the data, and the heat index"
            		+ " for each day.");
            System.out.println("(2) Display the record high temperature"
            		+ " and the year it was recorded for a given day"); 
            System.out.println("(3) Display the average, the maximum,"
            		+ " the minimum, and the range of one field");
            System.out.println("(4) Display the largest difference between"
            		+ " two consecutive values in one field");
            System.out.println("(5) Stop analyzing this collection of data");
            System.out.print("Enter (1–5): ");
            choice = kb.nextInt();

            if (choice == 1) {
                displayDataAndHeatIndex(kb, days, high_temps, humidities,
                		numberOfRecords);
            } else if (choice == 2) {
                displayRecordHighForDay(kb, days, record_highs, record_years,
                		numberOfRecords);
            } else if (choice == 3) {
                displayStatsForField(kb, high_temps, record_highs, humidities,
                		numberOfRecords);
            } else if (choice == 4) {
                displayLargestDifferenceForField(kb, high_temps, record_highs,
                		humidities, days, numberOfRecords);
            } else if (choice == 5) {
                System.out.println("Okay then.. o7");
            } else {
                System.out.println("Invalid choice. Please enter (1-5): ");
            }
        } while (choice != 5);
    }

    // option 1: displays data and the heat index
    public static void displayDataAndHeatIndex(Scanner kb, double days[],
    		double high_temps[], double humidities[], int numberOfRecords) {
        System.out.printf("%5s %10s %10s %12s%n", "Day", "High(°F)",
        		"Humidity", "HeatIndex");
        int lines = 0;
        for (int i = 0; i < numberOfRecords; i++) {
            double hi = computeHeatIndex(high_temps[i], humidities[i]);
            
            System.out.printf("%5.0f %10.2f %10.2f %12.2f%n",
            		days[i], high_temps[i], humidities[i], hi);
            lines++;
            if (lines == 10) {
                System.out.print("Enter (0) to continue : ");
                int pause = kb.nextInt();
                lines = 0;
            }
        }
    }

     //option 2: shows record high temperature and year for a specific day
    public static void displayRecordHighForDay(Scanner kb, double days[],
    		double record_highs[], int record_years[], int numberOfRecords) {
        System.out.print("Enter day to look up: ");
        int dayOfYear = kb.nextInt();
        int index = findLocation(days, dayOfYear, numberOfRecords);
        
        if (index == -1) {
            System.out.println("Day not found.");
        } else {
            System.out.printf("Day %d -> record high = %.2f°F (Year %d)%n",
              dayOfYear, record_highs[index], record_years[index]);
        }
    }

     // option 3:  computes the average, minimum, maximum, and range of a chosen field.
    public static void displayStatsForField(Scanner kb, double high_temps[],
    		double record_highs[], double humidities[], int numberOfRecords) {
        int fieldChoice = chooseField(kb);
        double[] selected = selectFieldArray(fieldChoice,
        		high_temps, record_highs, humidities);
        double[] stats = computeStats(selected, numberOfRecords);
        System.out.printf("Average = %.2f, Min = %.2f, Max = %.2f,"
        		+ " Range = %.2f%n", stats[0], stats[1],
        		stats[2], stats[2] - stats[1]);
    }

     // option 4:  finds the largest absolute difference (triangle) between consecutive values.
    public static void displayLargestDifferenceForField(Scanner kb,
    		double high_temps[], double record_highs[], double humidities[],
    		double days[], int numberOfRecords) {
        int fieldChoice = chooseField(kb);
        double[] selected = selectFieldArray(fieldChoice, high_temps,
        		record_highs, humidities);
        double[] diffInfo = computeLargestDifference(selected,
        		days, numberOfRecords);
        System.out.printf("Largest triangle = %.2f between days %.0f & %.0f%n",
                          diffInfo[0], diffInfo[1], diffInfo[2]);
    }

    // asking the user which field they would like to choose.
    private static int chooseField(Scanner kb) {
        System.out.println("Select field: (1) High Temperature"
        		+ "  (2) Record High  (3) Humidity : ");
        return kb.nextInt();
    }

    
     // array for the selected field
    private static double[] selectFieldArray(int choice, double high_temps[],
    		double record_highs[], double humidities[]) {
        if (choice == 1) {
            return high_temps;
        } else if (choice == 2) {
            return record_highs;
        } else {
            return humidities;
        }
    }

    // finds the index of a given day in the days array, (-1 if not found)
    public static int findLocation(double days[], int targetDay,
            int numberOfRecords) {
        
        for (int i = 0; i < numberOfRecords; i++) {
            if ((int)(days[i] + 0.5) == targetDay) {
                return i;
            }
        }
        return -1;
    }

     //computes the average, minimum, and maximum of the first numberOfRecords elements
     // returns the [average, min, max]
    public static double[] computeStats(double arr[], int numberOfRecords) {
        double sum = 0, min = arr[0], max = arr[0];
        
        for (int i = 0; i < numberOfRecords; i++) {
            sum += arr[i];
            if (arr[i] < min) min = arr[i];
            if (arr[i] > max) max = arr[i];
        }
        return new double[] { sum / numberOfRecords, min, max };
    }

     // computes the largest absolute difference between consecutive entries
     // returns the [largestDiff, dayBefore, dayAfter]
    public static double[] computeLargestDifference(double arr[],
            double days[], int numberOfRecords) {
        double maxDiff = 0;
        int idx = 0;

        for (int i = 1; i < numberOfRecords; i++) {
            double diff = arr[i] - arr[i - 1];

            // absolute value
            if (diff < 0) {
                diff = -diff;
            }
            if (diff > maxDiff) {
                maxDiff = diff;
                idx = i;
            }
        }
        return new double[] { maxDiff, days[idx - 1], days[idx] };
    }

     // computes the heat index using the Rothfusz regression equation
    //checks for maximum HI and 80-87fahr degree adjustments
    // returns the [HI] (heat index)
    public static double computeHeatIndex(double T, double RH) {
        double HI = -42.379  + 2.04901523 * T  + 10.14333127 * RH 
        		- 0.22475541 * T * RH  - 0.00683783 * T * T  - 0.05481717
        		* RH * RH + 0.00122874 * T * T * RH + 0.00085282 * T * RH
        		* RH - 0.00000199 * T * T * RH * RH;

        if (RH < 13 && T >= 80 && T <= 112) {
            double difference = T - 95;
            if (difference < 0) {
                difference = -difference; // absolute value
            }
            HI -= ((13 - RH) / 4) * Math.sqrt((17 - difference) / 17);
        } else if (RH > 85 && T >= 80 && T <= 87) {
            HI += ((RH - 85) / 10) * ((87 - T) / 5);
        }

        return HI;
    }
}

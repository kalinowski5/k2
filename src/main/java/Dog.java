import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Dog
{
    public static void main(String[] args)
    {
        final String name = scanDogName();
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        final int yearOfBirth = scanYearOfBirth();
        final int age = currentYear - yearOfBirth;
        final int realAge = age * 7;

        //saveBasicDogInfo(name, age);
        printBasicDogInfo(name, age, realAge);
        printAgeSimulation(yearOfBirth);
    }

    private static String scanDogName()
    {
        Scanner nameScanner = new Scanner(System.in);
        System.out.println("Enter dog name:");
        return nameScanner.nextLine();
    }

    private static int scanYearOfBirth()
    {
        Scanner yearOfBirthScanner = new Scanner(System.in);
        System.out.println("Enter year of birth:");
        int yearOfBirth = 0;
        while (yearOfBirth == 0) {
            try {
                yearOfBirth = Integer.parseInt(yearOfBirthScanner.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println("Invalid year. Try again:");
            }
        }
        return yearOfBirth;
    }

    private static void printAgeSimulation(int yearOfBirth) {

        final int estimatedLifeExpentncy = 40;
        int year = yearOfBirth;
        for (int age = 0; age < estimatedLifeExpentncy; age++) {
            ++year;
            System.out.println(year +": "+ age);
        }
    }
    private static void printBasicDogInfo(String name, int age, int realAge) {
        System.out.println(name + " is " + age +  " years old (" + realAge +").");
    }

    private static void saveBasicDogInfo(String name, int age) {
        try {
            PrintWriter dogInfoWriter = new PrintWriter("../dog1.txt");
            dogInfoWriter.println(name + ", " +age);
            dogInfoWriter.close();
        } catch (Exception exception) {

        }
    }
}

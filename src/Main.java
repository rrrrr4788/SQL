import java.sql.*;
import java.util.Scanner;


public class Main
{
    public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String path = "jdbc:sqlite://C:\\Users\\Macrotough\\IdeaProjects\\SQL\\flowers.db";
            c = DriverManager.getConnection(path);

            Scanner reader = new Scanner(System.in);

            //query();
            stmt = c.createStatement();
            String toExec = "SELECT DISTINCT NAME \n" +
                    "FROM 'SIGHTINGS';";

            ResultSet result = stmt.executeQuery(toExec);
            while (result.next()){
                System.out.println(result.getString("NAME"));
            }
            System.out.println();

            System.out.println("Select the flower that you want to know the details: ");
            String input = reader.nextLine();

            toExec = "SELECT * \n" +
                    "FROM 'SIGHTINGS'\n" +
                    "WHERE NAME = \"" + input +"\" \n" +
                    "ORDER BY SIGHTED DESC \n" +
                    "LIMIT 10;";

            result = stmt.executeQuery(toExec);
            while (result.next()) {
                System.out.println("NAME: " + result.getString("NAME"));
                System.out.println("PERSON: " + result.getString("PERSON"));
                System.out.println("LOCATION: " + result.getString("LOCATION"));
                System.out.println("SIGHTED: " + result.getString("SIGHTED"));
                System.out.println();
            }

            //update();
            int whichRow = -1;
            int counter = 0;

            System.out.println("Select the row that you want to modify, or type 0 to skip: ");
            whichRow = reader.nextInt();
            if(whichRow != 0) {
                whichRow -= 1;

                String name2, person2, location2, sighted2;
                System.out.println("Please type in name, person, location and sighted for update.");

                name2 = reader.nextLine();//this one is set for clearing the scanner
                name2 = reader.nextLine();
                person2 = reader.nextLine();
                location2 = reader.nextLine();
                sighted2 = reader.nextLine();

                String exec = "SELECT * \n" +
                        "FROM 'SIGHTINGS'\n" +
                        "WHERE NAME = \"" + input + "\"\n" +
                        "ORDER BY SIGHTED DESC\n" +
                        "LIMIT 10 OFFSET " + whichRow + " ;";
                ResultSet result2 = stmt.executeQuery(exec);
                String name3 = "";
                String person3 = "";
                String location3 = "";
                String sighted3 = "";

                while (result2.next() && (counter != whichRow + 1)) {
                    name3 = result2.getString("NAME");
                    person3 = result2.getString("PERSON");
                    location3 = result2.getString("LOCATION");
                    sighted3 = result2.getString("SIGHTED");
                    counter += 1;
                }

                String exec2 = "UPDATE SIGHTINGS \n" +
                        "SET NAME = \"" + name2 + "\", PERSON = \"" + person2 + "\", LOCATION = \"" + location2 + "\", SIGHTED = \"" + sighted2 + "\"\n" +
                        "WHERE NAME = \"" + name3 + "\" AND PERSON = \"" + person3 + "\" AND LOCATION = \"" + location3 + "\" AND SIGHTED = \"" + sighted3 + "\";";

                stmt.executeUpdate(exec2);
                result2.close();
            }
            else{
                String string = reader.nextLine();//take out the redundant line
            }

            //insert();
            System.out.println("Type yes to begin insertion.");
            String option = reader.nextLine();
            if(option.equals("yes")) {

                System.out.println("Please type in name, person, location and sighted for insertion.");

                String name = reader.nextLine();
                String person = reader.nextLine();
                String location = reader.nextLine();
                String sighted = reader.nextLine();

                String sql = "INSERT INTO SIGHTINGS VALUES ( \"" + name + "\", \"" + person + "\", \"" + location + "\", \"" + sighted + "\");";
                stmt.execute(sql);
            }
            result.close();
            reader.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}
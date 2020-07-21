package my_experiments;

// By PRASUN ROY
//Input your country name
import java.util.Scanner;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.net.URL;

public class Covid19_Case
{
    public static void main(String[] args)throws IOException {
        Scanner input = new Scanner(System.in);
        ArrayList<String> dataCovid = new ArrayList<String>();
        BufferedReader readLink = null;
        String country = null;
        try {
            country = input.nextLine().trim().toUpperCase().replace(" ", "");
            String link = "https://coronavirus-19-api.herokuapp.com/countries/" + country;
            URL openLink = new URL(link);
            readLink = new BufferedReader(new InputStreamReader(openLink.openStream()));
            String line = null;
            while((line = readLink.readLine()) != null) {
                String[] dataArr = line.split(",");
                addIn(dataCovid, dataArr);           
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            input.close();
            readLink.close();
        }
        HashMap<String, String> data = makeMap(dataCovid);
        printInfo(data, country);
        
    }
    
    public static void addIn(ArrayList<String> addTo, String[] readFrom) {
        for(String i:readFrom) {
            addTo.add(i.replace("\"", ""));
        }
    }
    public static HashMap<String, String> makeMap(ArrayList<String> data) {
        HashMap<String, String> dataMap = new HashMap<>();
        for(String i:data) {
            String[] dataSpl = i.split(":");
            if(dataSpl[0].equals("cases")) {
                dataMap.put("Confirmed Cases", dataSpl[1]);
            }
            else if(dataSpl[0].equals("active")) {
                dataMap.put("Active Cases", dataSpl[1]);
            }
            else if(dataSpl[0].equals("deaths")) {
                dataMap.put("Deaths", dataSpl[1]);
            }
            else {
                continue;
            }
        }
        return dataMap;
    }
    public static void printInfo(HashMap<String,String> data, String country) {
        if(data.size() == 0) {
            System.out.println("No information about country " + country);
        }
        else {
            System.out.println("COVID19 CASES");
            System.out.println("——————————————\n");
            System.out.println("Country: " + country + "\n");
            Set<String> title = data.keySet();
            for(String i:title) {
                System.out.println(i + ": " + data.get(i) + "\n");
            }
            System.out.println("Made by Prasun Roy using Java Programmimg.");

        }
    }
}
//https://coronavirus-19-api.herokuapp.com/countries/India
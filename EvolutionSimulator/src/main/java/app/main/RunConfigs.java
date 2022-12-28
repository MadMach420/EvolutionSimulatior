package app.main;

import app.main.maps.MapType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RunConfigs {
    public static Engine fromFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String[] parameters = new String[16];
            int i = 0;
            String line;
            while ((line = br.readLine()) != null) {
                String[] valuesInLine = line.split(",");
                parameters[i++] = valuesInLine[1];
            }
            return fromParameters(parameters);
        } catch (IOException e) {
            throw new IOException("File not found");
        }
    }

    public static Engine fromParameters(String[] parameters) {
        return new Engine(
                Integer.parseInt(parameters[0]),
                Integer.parseInt(parameters[1]),
                Integer.parseInt(parameters[2]),
                Integer.parseInt(parameters[3]),
                Boolean.parseBoolean(parameters[4]),
                MapType.fromString(parameters[5]),
                Integer.parseInt(parameters[6]),
                Integer.parseInt(parameters[7]),
                Integer.parseInt(parameters[8]),
                Integer.parseInt(parameters[9]),
                Integer.parseInt(parameters[10]),
                Integer.parseInt(parameters[11]),
                Integer.parseInt(parameters[12]),
                Integer.parseInt(parameters[13]),
                Boolean.parseBoolean(parameters[14]),
                Boolean.parseBoolean(parameters[15])
        );
    }
}

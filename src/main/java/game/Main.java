package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Game;
import models.GameConfig;
import models.GameResult;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            String configPath = null;
            double bettingAmount = 0;

            for (int i = 0; i < args.length; i += 2) {
                if (i + 1 >= args.length) break;

                switch (args[i]) {
                    case "--config":
                        configPath = args[i + 1];
                        break;
                    case "--betting-amount":
                        bettingAmount = Double.parseDouble(args[i + 1]);
                        break;
                }
            }

            if (configPath == null || bettingAmount <= 0) {
                System.out.println("Usage: java -jar game.jar --config <config_file> --betting-amount <amount>");
                System.exit(1);
            }

            ObjectMapper mapper = new ObjectMapper();
            GameConfig config = mapper.readValue(new File(configPath), GameConfig.class);
            Game game = new Game(config);
            GameResult result = game.play(bettingAmount);

            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
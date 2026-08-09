package womp.shellfishmod.util.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ShellfishConfig {
    
    private static final Gson GSON = new Gson();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "shellfish_config.json");

    private static int shellfishGraphics = 1;

    public ShellfishConfig() {
        shellfishGraphics = 1;
    }

    public static int getShellfishGraphics() {
        return shellfishGraphics;
    }

    public static void setShellfishGraphics(int value) {
        shellfishGraphics = value;
        saveConfig();
    }

    public static void loadConfig() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                JsonObject json = GSON.fromJson(reader, JsonObject.class);
                shellfishGraphics = json.has("shellfishGraphics") ? json.get("shellfishGraphics").getAsInt() : 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ShellfishGameOptions.getShellfishGraphicsMode().set(ShellfishGraphicsMode.byId(shellfishGraphics));
    }

    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            JsonObject json = new JsonObject();
            json.addProperty("shellfishGraphics", shellfishGraphics);
            GSON.toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

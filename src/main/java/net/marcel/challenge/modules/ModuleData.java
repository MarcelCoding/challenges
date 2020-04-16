package net.marcel.challenge.modules;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.marcel.challenge.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModuleData {

    private static final Gson gson = new Gson();

    private final Logger logger;
    private final File file;

    private JsonObject json;

    public ModuleData(final Module module) {
        this.logger = module.getPlugin().getLogger();

        this.file = new File("modules", module.getName().toLowerCase() + ".json");

        try {
            if (!Utils.createFile(this.file, false)) {
                try (final Reader reader = new InputStreamReader(new FileInputStream(this.file))) {
                    this.json = gson.fromJson(reader, JsonObject.class);
                }
            }
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, "Unable to create Module Data file \"" + this.file.getAbsolutePath() + "\".", e);
        }
        if (this.json == null) this.json = new JsonObject();
    }

    public void set(final String key, final Object value) {
        this.json.add(key, gson.toJsonTree(value));
    }

    public <T> T get(final String key, final Class<T> type) {
        if (this.json.has(key)) return gson.fromJson(this.json.get(key), type);
        else return null;
    }

    public void clean() {
        this.json.entrySet().forEach((entry) -> this.json.remove(entry.getKey()));
    }

    public void save() {
        try (final Writer writer = new OutputStreamWriter(new FileOutputStream(this.file))) {
            gson.toJson(this.json, writer);
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, "Unable to save Module Data file \"" + this.file.getAbsolutePath() + "\".", e);
        }
    }
}

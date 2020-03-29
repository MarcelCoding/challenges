package net.marcel.challenge.data;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Data {

    private final File dataFile;
    private final FileConfiguration fileConfiguration;

    public Data(final File pluginDir) throws IOException, InvalidConfigurationException {
        this.dataFile = new File(pluginDir, "data.yml");
        if (!this.dataFile.getParentFile().exists()) this.dataFile.getParentFile().mkdirs();
        if (!this.dataFile.exists()) dataFile.createNewFile();
        this.fileConfiguration = new YamlConfiguration();
        this.fileConfiguration.load(this.dataFile);
    }

    public void save() throws IOException {
        this.fileConfiguration.save(this.dataFile);
    }

    public boolean has(final String path) {
        return this.fileConfiguration.contains(path);
    }

    public void set(final String path, final Object value) {
        this.fileConfiguration.set(path, value);
    }

    public String getAsString(final String path) {
        return this.fileConfiguration.getString(path);
    }

    public long getAsLong(final String path) {
        return this.fileConfiguration.getLong(path);
    }
}

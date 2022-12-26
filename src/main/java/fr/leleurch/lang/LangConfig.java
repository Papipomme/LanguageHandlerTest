package fr.leleurch.lang;

import fr.leleurch.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LangConfig {
    private Map<String, String> valueCache = new ConcurrentHashMap<>();

    private File file;
    private FileConfiguration config;

    public LangConfig(Locale locale, Plugin plugin) {
        String localeName = locale.toString();
        file = new File(plugin.getDataFolder(), localeName + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        Main.getInstance().getConfigFromLocale().put(localeName, this);
    }

    public File getFile() {
        return file;
    }
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Get a LangConfig file value and if the path doesn't exist, create it.
     *
     * @return  String, value associated to path.
     */
    public String get(String path) {
        if (valueCache.containsKey(path)) return valueCache.get(path);

        String value = this.getConfig().getString(path);

        if (value == null) {
            this.getConfig().set(path, path);
            try {
                this.getConfig().save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        valueCache.put(path, value);

        return this.getConfig().getString(path);
    }

    /**
     * Parses a String so it can be Minecraft-readable.
     *
     * @return  String
     */
    public String parse(String value) {
        value = value.replaceAll("&", "§");
        Pattern pattern = Pattern.compile("[{]+@[a-zA-z]+[}]");
        Matcher matcher = pattern.matcher(value);
        // TODO : If {@id} replace with parsed value of id.
        // TODO : If a lot of steps are required to parse the str, setup a cache.
        return value;
    }
}

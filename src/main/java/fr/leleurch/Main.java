package fr.leleurch;

import fr.leleurch.lang.LangConfig;
import fr.leleurch.lang.LangManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends JavaPlugin {
    private static Main instance;
    private final Map<String, LangConfig> configFromLocale = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        LangConfig fr = new LangConfig(new Locale("fr", "FR"), this);
        LangConfig en = new LangConfig(new Locale("en", "EN"), this);

        System.out.println(LangManager.getMessage("test", "fr_FR"));
        System.out.println(LangManager.getMessage("test", "en_EN"));
        System.out.println(LangManager.getMessage("test1", "fr_FR", "Baguette", "Bite"));
        System.out.println(LangManager.getMessage("test1", "en_US"));
    }

    public static Main getInstance() {
        return instance;
    }
    public Map<String, LangConfig> getConfigFromLocale() {
        return configFromLocale;
    }
}
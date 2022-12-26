package fr.leleurch.lang;

import fr.leleurch.Main;

import javax.annotation.Nullable;
import java.text.MessageFormat;

public class LangManager {
    public static String getMessage(String path, String locale, @Nullable Object... args) {
        LangConfig langConfig = Main.getInstance().getConfigFromLocale().get(locale);

        if (langConfig == null) return "<Unable to find language file>";
        String str = langConfig.get(path);
        str = langConfig.parse(str);

        if(args == null) {
            return str;
        }
        return MessageFormat.format(str , args);
    }
}

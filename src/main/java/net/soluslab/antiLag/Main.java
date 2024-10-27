package net.soluslab.antiLag;

import net.soluslab.antiLag.test.ScanAllPlots;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        String message =
                """
                 \s
                                   _   _ _                \s
                       /\\         | | (_) |               \s
                      /  \\   _ __ | |_ _| |     __ _  __ _\s
                     / /\\ \\ | '_ \\| __| | |    / _` |/ _` |
                    / ____ \\| | | | |_| | |___| (_| | (_| |
                   /_/    \\_\\_| |_|\\__|_|______\\__,_|\\__, |
                                                      __/ |
                                                     |___/\s
                 \s
                AntiLag v%s has been enabled!
               \s
                By SolusLabs (https://github.com/soluslabs)
               \s""".formatted(getDescription().getVersion());

        getLogger().info(message);
        // Commands:
        Objects.requireNonNull(getCommand("test")).setExecutor(new ScanAllPlots());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}

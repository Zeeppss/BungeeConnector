package dev.zeeppss.bungeeconnector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {
    public static Configuration cf;
    public static Main plugin;

    public Main() {
    }

    public void onEnable() {
        plugin = this;
        ProxyServer.getInstance().getPluginManager().registerListener(this, new CommandEvent());
        this.createFiles();
        registerConfig();
        SpigotUpdater up = new SpigotUpdater(plugin, 65210);

        try {
            if (up.checkForUpdates()) {
                this.getLogger().info("§6§lBC §a/ §cA new version is available!");
            } else {
                this.getLogger().info("§6&lBC §a/ §aYou are using the latest version!");
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void createFiles() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                InputStream in = this.getResourceAsStream("config.yml");
                Files.copy(in, file.toPath(), new CopyOption[0]);
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }

    }

    public static void registerConfig() {
        try {
            cf = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }
}


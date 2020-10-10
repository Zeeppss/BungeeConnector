package dev.zeeppss.bungeeconnector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import net.md_5.bungee.api.plugin.Plugin;

public class SpigotUpdater {
    private int project = 0;
    private URL checkURL;
    private String newVersion = "";
    private Plugin plugin;

    public SpigotUpdater(Plugin plugin, int projectID) {
        this.plugin = plugin;
        this.newVersion = plugin.getDescription().getVersion();
        this.project = projectID;

        try {
            this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
        } catch (MalformedURLException var4) {
        }

    }

    public int getProjectID() {
        return this.project;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public String getLatestVersion() {
        return this.newVersion;
    }

    public String getResourceURL() {
        return "https://www.spigotmc.org/resources/" + this.project;
    }

    public boolean checkForUpdates() throws Exception {
        URLConnection con = this.checkURL.openConnection();
        this.newVersion = (new BufferedReader(new InputStreamReader(con.getInputStream()))).readLine();
        return !this.plugin.getDescription().getVersion().equals(this.newVersion);
    }
}


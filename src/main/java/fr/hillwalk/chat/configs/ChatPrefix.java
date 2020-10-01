package fr.hillwalk.chat.configs;

import fr.hillwalk.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ChatPrefix {

    private static File prefixFiles;
    private static File prefixFilesIn;
    private static FileConfiguration prefix;


    public static void setup() {
        Chat instance = JavaPlugin.getPlugin(Chat.class);
        prefixFilesIn = new File(Bukkit.getServer().getPluginManager().getPlugin(instance.getName()).getDataFolder(),"prefix");
        prefixFiles = new File(prefixFilesIn, "prefix.yml");


        if(!prefixFilesIn.exists()) {

            try {
                instance.saveResource("prefix/prefix.yml", false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        if(!prefixFiles.exists()) {
            try {
                prefixFiles.createNewFile();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        prefix = YamlConfiguration.loadConfiguration(prefixFiles);


    }


    public static FileConfiguration getChat() {

        return prefix;
    }


    public static void save() {
        Chat instance = JavaPlugin.getPlugin(Chat.class);

        try {
            prefix.save(prefixFiles);
        } catch (IOException e) {

            instance.getLogger().warning("Sauvegarde non effectuée !");
        }
    }

    public static void reload() {
        if (prefixFiles == null) {
            prefixFiles = new File(prefixFilesIn, "prefix.yml");
        }
        prefix = YamlConfiguration.loadConfiguration(prefixFiles);



    }


}

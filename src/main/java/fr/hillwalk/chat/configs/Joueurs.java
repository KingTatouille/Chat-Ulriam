package fr.hillwalk.chat.configs;

import fr.hillwalk.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Joueurs {


    Chat instance = JavaPlugin.getPlugin(Chat.class);
    private File playerFileIn = new File(Bukkit.getServer().getPluginManager().getPlugin(instance.getName()).getDataFolder(), "players");
    private File playerFile;
    private FileConfiguration playerYml;


    public void setup(Player player) {
        playerFile = new File(playerFileIn, String.valueOf(player.getUniqueId()) + ".yml");


        if(!playerFileIn.exists()) {

            try {
                playerFileIn.mkdirs();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        if(!playerFile.exists()) {
            try {
                playerFile.createNewFile();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        playerYml = YamlConfiguration.loadConfiguration(playerFile);


    }


    public FileConfiguration getPlayer(Player player) {
        playerFile = new File(playerFileIn, String.valueOf(player.getUniqueId()) + ".yml");
        playerYml = YamlConfiguration.loadConfiguration(playerFile);

        return playerYml;
    }


    public void save(Player player) {

        try {
            playerFile = new File(playerFileIn, String.valueOf(player.getUniqueId()) + ".yml");
            playerYml.save(playerFile);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void reload(Player player) {
        if (playerFile == null) {
            playerFile = new File(playerFileIn, String.valueOf(player.getUniqueId()) + ".yml");
        }
        playerYml = YamlConfiguration.loadConfiguration(playerFile);



    }


}
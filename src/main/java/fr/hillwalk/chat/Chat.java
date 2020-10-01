package fr.hillwalk.chat;

import fr.hillwalk.chat.commands.Commandes;
import fr.hillwalk.chat.configs.ChatPrefix;
import fr.hillwalk.chat.listener.ChatSync;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Chat extends JavaPlugin {

    public String prefix;

    //On instancie les classes
    ChatPrefix chat = new ChatPrefix();

    @Override
    public void onEnable(){


    //Start plugin
    getLogger().info("est fonctionnel !");

    //On sauvegarde la config
     saveDefaultConfig();

    //On sauvegade prefix.yml
    chat.setup();


    //Commandes
    getCommand("chat").setExecutor(new Commandes());

    //Events
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new ChatSync(), this);


    prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix") + " ");


    }






}

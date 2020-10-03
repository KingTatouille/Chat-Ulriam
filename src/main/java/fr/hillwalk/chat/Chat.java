package fr.hillwalk.chat;

import fr.hillwalk.chat.commands.Commandes;
import fr.hillwalk.chat.configs.ChatPrefix;
import fr.hillwalk.chat.listener.ChatSync;
import fr.hillwalk.chat.listener.JoinEvent;
import fr.hillwalk.chat.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Chat extends JavaPlugin {

    public String prefix;

    //On instancie les classes
    ChatPrefix chat = new ChatPrefix();
    UtilsRef util = new UtilsRef();

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
    pm.registerEvents(new JoinEvent(), this);


    for(Player player : Bukkit.getServer().getOnlinePlayers()){

        util.hideNametag(player);

    }


    prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix") + " ");


    }






}

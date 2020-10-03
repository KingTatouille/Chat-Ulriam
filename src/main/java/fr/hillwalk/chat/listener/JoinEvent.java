package fr.hillwalk.chat.listener;

import fr.hillwalk.chat.Chat;
import fr.hillwalk.chat.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.omg.CORBA.NO_MEMORY;

public class JoinEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent e){

        UtilsRef util = new UtilsRef();
        Chat instance = JavaPlugin.getPlugin(Chat.class);


        if(!e.getPlayer().hasPlayedBefore()){

            Bukkit.getServer().broadcastMessage(instance.getConfig().getString("Messages.welcome"));

        } else {

            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&',instance.getConfig().getString("Messages.join")));

        }




        util.hideNametag(e.getPlayer());



    }



    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLeave(PlayerQuitEvent e){
        Chat instance = JavaPlugin.getPlugin(Chat.class);

        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&',instance.getConfig().getString("Messages.leave")));
    }


}

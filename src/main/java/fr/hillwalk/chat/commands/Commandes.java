package fr.hillwalk.chat.commands;

import fr.hillwalk.chat.Chat;
import fr.hillwalk.chat.configs.Joueurs;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Commandes implements CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Chat instance = JavaPlugin.getPlugin(Chat.class);
        Joueurs config = new Joueurs();

        Player player = (Player) sender;


            if(args.length == 0){

                sender.sendMessage(instance.prefix + "Syntax : /chat help");
                return true;
            }



            if(args[0].equalsIgnoreCase("name")){

                String name = args[1];

                config.setup(player);

                for(Entity entity : Bukkit.getServer().getOnlinePlayers()){


                    if(entity.getLocation().distance(player.getEyeLocation()) <= 5 && !entity.getName().equalsIgnoreCase(player.getName())){


                        if(instance.getConfig().getConfigurationSection("Joueurs") == null){

                            config.getPlayer(player).set("Joueurs."  + entity.getName(), name);
                            config.save(player);
                            config.reload(player);
                            return true;
                        }

                        //On va mettre en place les configurations
                        for(String str : instance.getConfig().getConfigurationSection("Joueurs").getKeys(false)){

                            if(instance.getConfig().getString("Joueurs." + str) != entity.getName()){

                                config.getPlayer(player).set("Joueurs."  + entity.getName(), name);
                                config.save(player);
                                config.reload(player);
                                return true;

                            }


                        }

                    }
                }



                return true;
            }


        return false;
    }
}

package fr.hillwalk.chat.commands;

import fr.hillwalk.chat.Chat;
import fr.hillwalk.chat.configs.ChatPrefix;
import fr.hillwalk.chat.configs.Joueurs;
import fr.hillwalk.chat.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        ChatPrefix prefix = new ChatPrefix();
        UtilsRef util = new UtilsRef();

        Player player = (Player) sender;


            if(args.length == 0){

                sender.sendMessage(instance.prefix + "Syntax : /chat help");
                return true;
            }



            if(args[0].equalsIgnoreCase("name")){

                String name = args[1];

                if(name == null){

                    player.sendMessage(instance.prefix + "Vous devenez entrer un nom.\n/ch name <nom>");
                    return true;
                }

                config.setup(player);

                UtilsRef.getEntityInLineOfSightBlockIterator(player, 4, name);

                return true;
            }


            if(!player.isOp() || !player.hasPermission("chat.admin")){

                player.sendMessage(instance.prefix + "Vous n'avez pas la permission pour faire ça.");
                return true;
            }

            if(args[0].equalsIgnoreCase("real")){

                UtilsRef.commandAdmin(player, 50);

                return true;
            }

        if(args[0].equalsIgnoreCase("setname")){


            if(args.length <= 2){

                player.sendMessage(instance.prefix + "Syntax : /ch setname <target> <fauxNom>");
                return true;
            }

            Player target = Bukkit.getServer().getPlayer(args[1]);
            String fauxNom = args[2];

            if(target == null){

                player.sendMessage(instance.prefix + "Le nom du joueur n'est pas correcte !");
                return true;
            }

            if(fauxNom == null){

                player.sendMessage(instance.prefix + "Vous devez entrer un faux nom.");
                return true;
            }


            //Si la target ne possède pas de fichier.
            config.setup(target);

            config.getPlayer(target).set("Joueurs."  + target.getName(), fauxNom);
            config.save(target);
            config.reload(target);

            player.sendMessage(instance.prefix + "Vous venez de renommer : " + target.getName() + " par : " + fauxNom);


            return true;
        }


            if(args[0].equalsIgnoreCase("reload")){


                instance.getLogger().info("Initialisation des données ...");
                instance.saveDefaultConfig();
                instance.reloadConfig();

                prefix.save();
                prefix.reload();


                for (Player target : Bukkit.getServer().getOnlinePlayers()){

                    util.hideNametag(target);
                    config.setup(target);
                    config.save(target);
                    config.reload(target);


                }

                instance.prefix = ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("prefix") + " ");
                instance.getLogger().info("Tout est CHECK !");

                player.sendMessage(instance.prefix + "Le reload est un succès !");

                return true;
            }


        return false;
    }

}

package fr.hillwalk.chat.listener;

import fr.hillwalk.chat.Chat;
import fr.hillwalk.chat.configs.ChatPrefix;
import fr.hillwalk.chat.configs.Joueurs;
import fr.hillwalk.chat.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class ChatSync implements Listener {

    Chat instance = JavaPlugin.getPlugin(Chat.class);
    Joueurs joueurs = new Joueurs();
    UtilsRef util = new UtilsRef();
    ChatPrefix config = new ChatPrefix();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void chatSync(AsyncPlayerChatEvent e){


    for(String str : config.getChat().getConfigurationSection("prefix").getKeys(false)){

        e.setCancelled(true);

        /*

                                                                                      __ _             _           _
                                                 /\                                  / _(_)           | |         | |
                                                /  \__   _____  ___   _ __  _ __ ___| |_ ___  __   ___| |__   __ _| |_
                                               / /\ \ \ / / _ \/ __| | '_ \| '__/ _ \  _| \ \/ /  / __| '_ \ / _` | __|
                                              / ____ \ V /  __/ (__  | |_) | | |  __/ | | |>  <  | (__| | | | (_| | |_
                                             /_/    \_\_/ \___|\___| | .__/|_|  \___|_| |_/_/\_\  \___|_| |_|\__,_|\__|
                                                                     | |
                                                                     |_|

         */

        if(e.getMessage().startsWith(config.getChat().getString("prefix." + str + ".prefixChat"))){


            if(config.getChat().getBoolean("prefix." + str + ".before")){

                /*

                                                                   _____        __  __ _
                                                                  / ____|      / _|/ _(_)
                                                                 | (___  _   _| |_| |_ ___  __
                                                                  \___ \| | | |  _|  _| \ \/ /
                                                                  ____) | |_| | | | | | |>  <
                                                                 |_____/ \__,_|_| |_| |_/_/\_\

                 */

                String word = config.getChat().getString("prefix." + str + ".prefixChat") + e.getMessage();
                String rawMessage = word.replace(config.getChat().getString("prefix." + str + ".prefixChat"), "");
                int distance = config.getChat().getInt("prefix." + str + ".radius");
                Particle particule = Particle.valueOf(config.getChat().getString("prefix." + str + ".particles"));

                e.getPlayer().getWorld().spawnParticle(particule, new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX(),e.getPlayer().getLocation().getY() + 2, e.getPlayer().getLocation().getZ()), config.getChat().getInt("prefix." + str + ".count"), config.getChat().getDouble("prefix." + str + ".offsetX"), config.getChat().getDouble("prefix." + str + ".offsetY"),config.getChat().getDouble("prefix." + str + ".offsetZ"), config.getChat().getDouble("prefix." + str + ".speed"));


                if(distance != -1){

                    for (Player target : Bukkit.getServer().getOnlinePlayers()) {

                        if(target.getLocation().distanceSquared(e.getPlayer().getLocation()) <= distance){


                            if(target.getName().equalsIgnoreCase(e.getPlayer().getName())){

                                if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){
                                    target.sendMessage(util.nameBeforeTrue(e.getPlayer(), str, joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));
                                    //Pour les logs:
                                    instance.getLogger().info(util.nameBeforeTrue(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));

                                } else {

                                    target.sendMessage(util.nameBeforeTrue(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));
                                    //Pour les logs:
                                    instance.getLogger().info(util.nameBeforeTrue(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));
                                }

                            } else {

                                //Si le joueur qui parle a déjà mit un nom sur la tête de se joueur.
                                if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){

                                    target.sendMessage(util.nameBeforeTrue(e.getPlayer(), str, joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));

                                } else {

                                    //Si le joueur est op ou possède la permission de voir le nom du joueur.
                                    if(target.isOp() || target.hasPermission("chat.name")){

                                        //On met le pseudo à côté du faux nom pour l'identifier plus facilement.
                                        target.sendMessage(util.nameBeforeFalse(target, str, "???", e.getPlayer().getName(), rawMessage));

                                    } else {
                                        //Si le joueur n'est pas op alors il voit le tchat normalement.
                                        target.sendMessage(util.nameBeforeFalse(target, str, "???", rawMessage));

                                    }
                                }
                            }

                        } else {

                            //On enlève de la liste.
                            e.getRecipients().remove(target);


                        }



                    }

                } else {

                    for (Player target : Bukkit.getServer().getOnlinePlayers()) {


                            if(target.getName().equalsIgnoreCase(e.getPlayer().getName())){

                                if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){
                                    target.sendMessage(util.nameBeforeTrue(e.getPlayer(), str, joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));
                                    //Pour les logs:
                                    instance.getLogger().info(util.nameBeforeTrue(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));

                                } else {

                                    target.sendMessage(util.nameBeforeTrue(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));
                                    //Pour les logs:
                                    instance.getLogger().info(util.nameBeforeTrue(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));
                                }


                            } else {


                                if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){

                                    //Si le joueur est op ou possède la permission de voir le nom du joueur.
                                    if(target.isOp() || target.hasPermission("chat.name")){

                                        //On met le pseudo à côté du faux nom pour l'identifier plus facilement.
                                        target.sendMessage(util.nameBeforeTrue(e.getPlayer(), str, joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), e.getPlayer().getName(), rawMessage));

                                    } else {
                                        //Si le joueur n'est pas op alors il voit le tchat normalement.
                                        target.sendMessage(util.nameBeforeTrue(e.getPlayer(), str, joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));


                                    }



                                } else {

                                    //Si le joueur est op ou possède la permission de voir le nom du joueur.
                                    if(target.isOp() || target.hasPermission("chat.name")){

                                        //On met le pseudo à côté du faux nom pour l'identifier plus facilement.
                                        target.sendMessage(util.nameBeforeTrue(target, str, "???", e.getPlayer().getName(), rawMessage));

                                    } else {
                                        //Si le joueur n'est pas op alors il voit le tchat normalement.
                                        target.sendMessage(util.nameBeforeTrue(target, str, "???", rawMessage));

                                    }
                                }
                            }

                    }

                }

            } else {

                /*

                                                                  _____           __ _
                                                                 |  __ \         / _(_)
                                                                 | |__) | __ ___| |_ ___  __
                                                                 |  ___/ '__/ _ \  _| \ \/ /
                                                                 | |   | | |  __/ | | |>  <
                                                                 |_|   |_|  \___|_| |_/_/\_\


                 */


                String word = config.getChat().getString("prefix." + str + ".prefixChat") + e.getMessage();
                String rawMessage = word.replace(config.getChat().getString("prefix." + str + ".prefixChat"), "");
                int distance = config.getChat().getInt("prefix." + str + ".radius");
                Particle particule = Particle.valueOf(config.getChat().getString("prefix." + str + ".particles"));

                e.getPlayer().getWorld().spawnParticle(particule, new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX(),e.getPlayer().getLocation().getY() + 2, e.getPlayer().getLocation().getZ()), config.getChat().getInt("prefix." + str + ".count"), config.getChat().getDouble("prefix." + str + ".offsetX"), config.getChat().getDouble("prefix." + str + ".offsetY"),config.getChat().getDouble("prefix." + str + ".offsetZ"), config.getChat().getDouble("prefix." + str + ".speed"));


                if(distance != -1){

                    for (Player target : Bukkit.getServer().getOnlinePlayers()) {

                        if(target.getLocation().distanceSquared(e.getPlayer().getLocation()) <= distance){


                            if(target.getName().equalsIgnoreCase(e.getPlayer().getName())){

                                if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){
                                    target.sendMessage(util.nameBeforeFalse(e.getPlayer(), str, joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));
                                    //Pour les logs:
                                    instance.getLogger().info(util.nameBeforeFalse(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));

                                } else {

                                    target.sendMessage(util.nameBeforeFalse(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));
                                    //Pour les logs:
                                    instance.getLogger().info(util.nameBeforeFalse(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));
                                }


                            } else {

                                if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){


                                    //Si le joueur est op ou possède la permission de voir le nom du joueur.
                                    if(target.isOp() || target.hasPermission("chat.name")){

                                        //On met le pseudo à côté du faux nom pour l'identifier plus facilement.
                                        target.sendMessage(util.nameBeforeFalse(e.getPlayer(), str,joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), e.getPlayer().getName(), rawMessage));



                                    } else {
                                        //Si le joueur n'est pas op alors il voit le tchat normalement.
                                        target.sendMessage(util.nameBeforeFalse(e.getPlayer(), str,joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));

                                    }


                                } else {

                                    //Si le joueur est op ou possède la permission de voir le nom du joueur.
                                    if(target.isOp() || target.hasPermission("chat.name")){

                                        //On met le pseudo à côté du faux nom pour l'identifier plus facilement.
                                        target.sendMessage(util.nameBeforeFalse(target, str, "???", e.getPlayer().getName(), rawMessage));

                                    } else {
                                        //Si le joueur n'est pas op alors il voit le tchat normalement.
                                        target.sendMessage(util.nameBeforeFalse(target, str, "???", rawMessage));

                                    }

                                }
                            }

                        } else {

                            //On enlève la personne de la liste.
                            e.getRecipients().remove(target);


                        }


                    }

                } else {

                    for (Player target : Bukkit.getServer().getOnlinePlayers()) {


                            if(target.getName().equalsIgnoreCase(e.getPlayer().getName())){

                                if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){
                                    target.sendMessage(util.nameBeforeFalse(e.getPlayer(), str, joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));
                                    //Pour les logs:
                                    instance.getLogger().info(util.nameBeforeFalse(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));

                                } else {

                                    target.sendMessage(util.nameBeforeFalse(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));
                                    //Pour les logs:
                                    instance.getLogger().info(util.nameBeforeFalse(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));
                                }


                            } else {


                                if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){

                                    //Si le joueur est op ou possède la permission de voir le nom du joueur.
                                    if(target.isOp() || target.hasPermission("chat.name")){

                                        target.sendMessage(util.nameBeforeFalse(e.getPlayer(), str, joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), e.getPlayer().getName(), rawMessage));


                                    } else {
                                        target.sendMessage(util.nameBeforeFalse(e.getPlayer(), str, joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));

                                    }



                                } else {

                                    target.sendMessage(util.nameBeforeFalse(target, str, "???", rawMessage));
                                }
                            }

                    }

                }


            }

    return;
        }
    }


                /*

                                                          _   _       _                  _
                                                         | \ | |     | |                | |
                                                         |  \| | __ _| |_ _   _ _ __ ___| |
                                                         | . ` |/ _` | __| | | | '__/ _ \ |
                                                         | |\  | (_| | |_| |_| | | |  __/ |
                                                         |_| \_|\__,_|\__|\__,_|_|  \___|_|


                 */


                //Quand la personne parle normalement
                int distance = instance.getConfig().getInt("DEFAULT.radius");
                String rawMessage = instance.getConfig().getString("DEFAULT.prefixChat") + e.getMessage();
                Particle particule = Particle.valueOf(instance.getConfig().getString("DEFAULT.particles"));

                e.getPlayer().getWorld().spawnParticle(particule, new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX(),e.getPlayer().getLocation().getY() + 2, e.getPlayer().getLocation().getZ()), instance.getConfig().getInt("DEFAULT.count"), instance.getConfig().getDouble("DEFAULT.offsetX"), instance.getConfig().getDouble("DEFAULT.offsetY"), instance.getConfig().getDouble("DEFAULT.offsetZ"), instance.getConfig().getDouble("DEFAULT.speed"));

                for (Player target : Bukkit.getServer().getOnlinePlayers()) {

                    if(target.getLocation().distanceSquared(e.getPlayer().getLocation()) <= distance){


                                if(target.getName().equalsIgnoreCase(e.getPlayer().getName())){

                                    if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){
                                    target.sendMessage(util.nameDefaut(e.getPlayer(), joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));
                                    //Pour les logs:
                                    instance.getLogger().info(util.nameDefaut(e.getPlayer(), e.getPlayer().getName(), rawMessage));
                                    } else {
                                        target.sendMessage(util.nameDefaut(e.getPlayer(), e.getPlayer().getName(), rawMessage));
                                        //Pour les logs:
                                        instance.getLogger().info(util.nameDefaut(e.getPlayer(), e.getPlayer().getName(), rawMessage));
                                    }

                                } else {

                                   if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){


                                       //Si le joueur est op ou possède la permission de voir le nom du joueur.
                                       if(target.isOp() || target.hasPermission("chat.name")){

                                           target.sendMessage(util.nameDefaut(e.getPlayer(), joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), e.getPlayer().getName(), rawMessage));

                                       } else {
                                           target.sendMessage(util.nameDefaut(e.getPlayer(), joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));
                                       }



                                   } else {

                                       //Si le joueur est op ou possède la permission de voir le nom du joueur.
                                       if(target.isOp() || target.hasPermission("chat.name")){

                                           //On met le pseudo à côté du faux nom pour l'identifier plus facilement.
                                           target.sendMessage(util.nameDefaut(target, "???", e.getPlayer().getName(), rawMessage));

                                       } else {
                                           //Si le joueur n'est pas op alors il voit le tchat normalement.
                                           target.sendMessage(util.nameDefaut(target, "???", rawMessage));

                                       }

                                   }
                                }

                            } else {

                                //On enlève la personne de la liste.
                                e.getRecipients().remove(target);

                            }
                }

            return;

    }

}

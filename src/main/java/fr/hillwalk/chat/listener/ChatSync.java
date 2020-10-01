package fr.hillwalk.chat.listener;

import fr.hillwalk.chat.Chat;
import fr.hillwalk.chat.configs.ChatPrefix;
import fr.hillwalk.chat.configs.Joueurs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class ChatSync implements Listener {

    Chat instance = JavaPlugin.getPlugin(Chat.class);
    Joueurs joueurs = new Joueurs();

    @EventHandler
    public void chatSync(AsyncPlayerChatEvent e){


    for(String str : ChatPrefix.getChat().getConfigurationSection("prefix").getKeys(false)){

        e.setCancelled(true);

        if(e.getMessage().startsWith(ChatPrefix.getChat().getString("prefix." + str + ".prefixChat"))){
            System.out.println("Before : 1");


            if(ChatPrefix.getChat().getBoolean("prefix." + str + ".before")){
                System.out.println("Before : 2");


                String word = ChatPrefix.getChat().getString("prefix." + str + ".prefixChat") + e.getMessage();
                String rawMessage = word.replace(ChatPrefix.getChat().getString("prefix." + str + ".prefixChat"), "");
                int distance = ChatPrefix.getChat().getInt("prefix." + str + ".radius");
                Particle particule = Particle.valueOf(ChatPrefix.getChat().getString("prefix." + str + ".particles"));

                e.getPlayer().getWorld().spawnParticle(particule, new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX(),e.getPlayer().getLocation().getY() + 2, e.getPlayer().getLocation().getZ()), 5);



                if(distance != -1){

                    System.out.println("Before : 3");
                    for (Player target : Bukkit.getServer().getOnlinePlayers()) {

                        if(target.getLocation().distanceSquared(e.getPlayer().getLocation()) <= distance){


                            if(target.getName().equalsIgnoreCase(e.getPlayer().getName())){

                                target.sendMessage(nameBeforeTrue(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));

                            } else {


                                if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){

                                    target.sendMessage(nameBeforeTrue(e.getPlayer(), str, joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));



                                } else {

                                    target.sendMessage(nameBeforeTrue(target, str, "???", rawMessage));
                                }
                            }

                        } else {

                            e.getRecipients().remove(target);


                        }



                    }

                } else {

                    for (Player target : Bukkit.getServer().getOnlinePlayers()) {


                            if(target.getName().equalsIgnoreCase(e.getPlayer().getName())){

                                target.sendMessage(nameBeforeTrue(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));

                            } else {


                                if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){

                                    target.sendMessage(nameBeforeTrue(e.getPlayer(), str, joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));



                                } else {

                                    target.sendMessage(nameBeforeTrue(target, str, "???", rawMessage));
                                }
                            }

                    }

                }

            } else {

                String word = ChatPrefix.getChat().getString("prefix." + str + ".prefixChat") + e.getMessage();
                String rawMessage = word.replace(ChatPrefix.getChat().getString("prefix." + str + ".prefixChat"), "");
                int distance = ChatPrefix.getChat().getInt("prefix." + str + ".radius");
                Particle particule = Particle.valueOf(ChatPrefix.getChat().getString("prefix." + str + ".particles"));

                e.getPlayer().getWorld().spawnParticle(particule, new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX(),e.getPlayer().getLocation().getY() + 2, e.getPlayer().getLocation().getZ()), 5);


                if(distance != -1){

                    for (Player target : Bukkit.getServer().getOnlinePlayers()) {

                        if(target.getLocation().distanceSquared(e.getPlayer().getLocation()) <= distance){


                            if(target.getName().equalsIgnoreCase(e.getPlayer().getName())){


                                target.sendMessage(nameBeforeFalse(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));

                            } else {

                                if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){


                                    target.sendMessage(nameBeforeFalse(e.getPlayer(), str,joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));



                                } else {

                                    target.sendMessage(nameBeforeFalse(target, str, "???", rawMessage));
                                }
                            }

                        } else {

                            e.getRecipients().remove(target);


                        }


                    }

                } else {

                    for (Player target : Bukkit.getServer().getOnlinePlayers()) {


                            if(target.getName().equalsIgnoreCase(e.getPlayer().getName())){

                                target.sendMessage(nameBeforeFalse(e.getPlayer(), str, e.getPlayer().getName(), rawMessage));

                            } else {


                                if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){

                                    target.sendMessage(nameBeforeFalse(e.getPlayer(), str, joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));



                                } else {

                                    target.sendMessage(nameBeforeFalse(target, str, "???", rawMessage));
                                }
                            }





                    }

                }


            }

    return;
        }
    }


                //Quand la personne parle normalement
                int distance = instance.getConfig().getInt("DEFAULT.radius");
                String rawMessage = instance.getConfig().getString("DEFAULT.prefixChat") + e.getMessage();


                for (Player target : Bukkit.getServer().getOnlinePlayers()) {

                    if(target.getLocation().distanceSquared(e.getPlayer().getLocation()) <= distance){


                                if(target.getName().equalsIgnoreCase(e.getPlayer().getName())){

                                    target.sendMessage(nameDefaut(e.getPlayer(), e.getPlayer().getName(), rawMessage));

                                } else {



                                   if(joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()) != null){


                                               target.sendMessage(nameDefaut(e.getPlayer(), joueurs.getPlayer(target).getString("Joueurs." + e.getPlayer().getName()), rawMessage));



                                   } else {

                                       target.sendMessage(nameDefaut(target, "???", rawMessage));
                                   }
                                }

                            } else {

                                e.getRecipients().remove(target);


                            }



                }


            return;

    }





    public String nameDefaut(Player player, String name, String message){

        HashMap<UUID, String> names = new HashMap<UUID, String>();
        for(Player target : Bukkit.getServer().getOnlinePlayers()){

            String format = ChatColor.valueOf(instance.getConfig().getString("DEFAULT.color")) + ChatColor.translateAlternateColorCodes('&', name + " : " + message);
            names.put(target.getUniqueId(), format);


        }


        return names.get(player.getUniqueId());
    }

    public String nameBeforeTrue(Player player, String params, String name, String message){

        HashMap<UUID, String> names = new HashMap<UUID, String>();
        for(Player target : Bukkit.getServer().getOnlinePlayers()){


                String format = ChatColor.valueOf(ChatPrefix.getChat().getString("prefix." + params + ".color")) + ChatColor.translateAlternateColorCodes('&', ChatPrefix.getChat().getString("prefix." + params + ".prefix") + " " + name + " : " + message);
                names.put(target.getUniqueId(), format);



        }


        return names.get(player.getUniqueId());
    }

    public String nameBeforeFalse(Player player, String params, String name, String message){

        HashMap<UUID, String> names = new HashMap<UUID, String>();
        for(Player target : Bukkit.getServer().getOnlinePlayers()){


                String format = ChatColor.translateAlternateColorCodes('&', name + " " + ChatColor.valueOf(ChatPrefix.getChat().getString("prefix." + params + ".color")) + ChatPrefix.getChat().getString("prefix." + params + ".prefix") + " : " + message);
                names.put(target.getUniqueId(), format);
            }



        return names.get(player.getUniqueId());
    }
    

}

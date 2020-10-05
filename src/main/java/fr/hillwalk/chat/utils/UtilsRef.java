package fr.hillwalk.chat.utils;

import fr.hillwalk.chat.Chat;
import fr.hillwalk.chat.configs.ChatPrefix;
import fr.hillwalk.chat.configs.Joueurs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.BlockIterator;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UtilsRef {


    /*


            BIG THANK TO https://www.spigotmc.org/threads/tutorial-get-the-entity-another-entity-is-looking-at.202495/


     */

    public void getEntityInLineOfSightBlockIterator(Player p, int range, String name){
        Chat instance = JavaPlugin.getPlugin(Chat.class);
        Joueurs config = new Joueurs();

        long time = System.currentTimeMillis();


        List<Entity> targetList = p.getNearbyEntities(range, range, range);

        BlockIterator bi = new BlockIterator(p, range);

        Entity target = null;


        while(bi.hasNext()){


            Block b = bi.next();

            int bx = b.getX();

            int by = b.getY();

            int bz = b.getZ();


            if (b.getType().isSolid()) { //Modified this for obvious reasons (old runtime O(n))

// line of sight is broken, stop without target

                break;

            } else {

                for(Entity e : targetList) {

                    Location l = e.getLocation();

                    double ex = l.getX();

                    double ey = l.getY();

                    double ez = l.getZ();

                    if ((bx-.75 <= ex && ex <= bx+1.75) && (bz-.75 <= ez && ez <= bz+1.75) && (by-1 <= ey && ey <= by+2.5)) {

                        target = e;

                    }

                }

            }

        }



        if(target != null){

            p.sendMessage(instance.prefix + "Vous venez d'assigner le nom : " + name + " à cette personne.");

            if(instance.getConfig().getConfigurationSection("Joueurs") == null){

                config.getPlayer(p).set("Joueurs."  + target.getName(), name);
                config.save(p);
                config.reload(p);
                return;
            } else {
                //On va mettre en place les configurations
                for(String str : instance.getConfig().getConfigurationSection("Joueurs").getKeys(false)){

                    if(instance.getConfig().getString("Joueurs." + str) != target.getName()){

                        config.getPlayer(p).set("Joueurs."  + target.getName(), name);
                        config.save(p);
                        config.reload(p);
                        return;

                    }
                }

            }

        }

        p.sendMessage(instance.prefix + "Vous devez regarder la personne pour enregistrer son nom.");

    }

    public void commandAdmin(Player p, int range){
        Chat instance = JavaPlugin.getPlugin(Chat.class);

        Joueurs config = new Joueurs();

        long time = System.currentTimeMillis();


        List<Entity> targetList = p.getNearbyEntities(range, range, range);

        BlockIterator bi = new BlockIterator(p, range);

        Entity target = null;


        while(bi.hasNext()){


            Block b = bi.next();

            int bx = b.getX();

            int by = b.getY();

            int bz = b.getZ();


            if (b.getType().isSolid()) { //Modified this for obvious reasons (old runtime O(n))

// line of sight is broken, stop without target

                break;

            } else {

                for(Entity e : targetList) {

                    Location l = e.getLocation();

                    double ex = l.getX();

                    double ey = l.getY();

                    double ez = l.getZ();

                    if ((bx-.75 <= ex && ex <= bx+1.75) && (bz-.75 <= ez && ez <= bz+1.75) && (by-1 <= ey && ey <= by+2.5)) {

                        target = e;

                    }

                }

            }

        }



        if(target != null){

            p.sendMessage(instance.prefix + "Le joueur se nomme : " + target.getName());
            return;

        }

        p.sendMessage(instance.prefix + "Vous devez regarder la personne pour voir son pseudo.");
        return;

    }


    public void hideNametag(Player player){

        Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();

        Team t = score.getTeam("hiddenName");
        if(t == null) {
            t = score.registerNewTeam("hiddenName");
            t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        }
        t.addEntry(player.getName());

    }

    /*

                                            _                                     _
                                           | |                                   | |
                                      _ __ | | __ _ _   _  ___ _ __   _ __   ___ | |_    ___  _ __
                                     | '_ \| |/ _` | | | |/ _ \ '__| | '_ \ / _ \| __|  / _ \| '_ \
                                     | |_) | | (_| | |_| |  __/ |    | | | | (_) | |_  | (_) | |_) |
                                     | .__/|_|\__,_|\__, |\___|_|    |_| |_|\___/ \__|  \___/| .__/
                                     | |             __/ |                                   | |
                                     |_|            |___/                                    |_|

     */

    public String nameDefaut(Player player, String name, String message){
        Chat instance = JavaPlugin.getPlugin(Chat.class);


        HashMap<UUID, String> names = new HashMap<UUID, String>();
        for(Player target : Bukkit.getServer().getOnlinePlayers()){

            String format = ChatColor.valueOf(instance.getConfig().getString("DEFAULT.color")) + ChatColor.translateAlternateColorCodes('&', name + " : " + message);
            names.put(target.getUniqueId(), format);


        }


        return names.get(player.getUniqueId());
    }

    public String nameBeforeTrue(Player player, String params, String name, String message){
        ChatPrefix prefix = new ChatPrefix();

        HashMap<UUID, String> names = new HashMap<UUID, String>();
        for(Player target : Bukkit.getServer().getOnlinePlayers()){


            String format = ChatColor.valueOf(prefix.getChat().getString("prefix." + params + ".color")) + ChatColor.translateAlternateColorCodes('&', prefix.getChat().getString("prefix." + params + ".prefix") + " " + name + " : " + message);
            names.put(target.getUniqueId(), format);



        }


        return names.get(player.getUniqueId());
    }

    public String nameBeforeFalse(Player player, String params, String name, String message){
        ChatPrefix prefix = new ChatPrefix();

        HashMap<UUID, String> names = new HashMap<UUID, String>();
        for(Player target : Bukkit.getServer().getOnlinePlayers()){


            String format = ChatColor.translateAlternateColorCodes('&', name + " " + ChatColor.valueOf(prefix.getChat().getString("prefix." + params + ".color")) + prefix.getChat().getString("prefix." + params + ".prefix") + " : " + message);
            names.put(target.getUniqueId(), format);
        }



        return names.get(player.getUniqueId());
    }



    /*

                                                            _
                                                           | |
                                                      _ __ | | __ _ _   _  ___ _ __    ___  _ __
                                                     | '_ \| |/ _` | | | |/ _ \ '__|  / _ \| '_ \
                                                     | |_) | | (_| | |_| |  __/ |    | (_) | |_) |
                                                     | .__/|_|\__,_|\__, |\___|_|     \___/| .__/
                                                     | |             __/ |                 | |
                                                     |_|            |___/                  |_|

     */


    public String nameDefaut(Player player, String name,String spyName, String message){
        Chat instance = JavaPlugin.getPlugin(Chat.class);


        HashMap<UUID, String> names = new HashMap<UUID, String>();
        for(Player target : Bukkit.getServer().getOnlinePlayers()){

            String format = ChatColor.valueOf(instance.getConfig().getString("DEFAULT.color")) + ChatColor.translateAlternateColorCodes('&', name + "(" + spyName + ")" + " : " + message);
            names.put(target.getUniqueId(), format);


        }


        return names.get(player.getUniqueId());
    }

    public String nameBeforeTrue(Player player, String params, String name, String spyName, String message){
        ChatPrefix prefix = new ChatPrefix();

        HashMap<UUID, String> names = new HashMap<UUID, String>();
        for(Player target : Bukkit.getServer().getOnlinePlayers()){


            String format = ChatColor.valueOf(prefix.getChat().getString("prefix." + params + ".color")) + ChatColor.translateAlternateColorCodes('&', prefix.getChat().getString("prefix." + params + ".prefix") + " " + name + "(" + spyName + ")" + " : " + message);
            names.put(target.getUniqueId(), format);



        }


        return names.get(player.getUniqueId());
    }

    public String nameBeforeFalse(Player player, String params, String name, String spyName, String message){
        ChatPrefix prefix = new ChatPrefix();

        HashMap<UUID, String> names = new HashMap<UUID, String>();
        for(Player target : Bukkit.getServer().getOnlinePlayers()){


            String format = ChatColor.translateAlternateColorCodes('&', name + "(" + spyName + ")" + " " + ChatColor.valueOf(prefix.getChat().getString("prefix." + params + ".color")) + prefix.getChat().getString("prefix." + params + ".prefix") + " : " + message);
            names.put(target.getUniqueId(), format);
        }



        return names.get(player.getUniqueId());
    }


}

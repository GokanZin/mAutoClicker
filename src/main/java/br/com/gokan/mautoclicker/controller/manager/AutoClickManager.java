package br.com.gokan.mautoclicker.controller.manager;

import br.com.gokan.mautoclicker.Main;
import br.com.gokan.mautoclicker.controller.AutoClickController;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class AutoClickManager {

    private final Main main;
    private HashMap<String, AutoClickController> mapAutoClicker = new HashMap<>();


    public AutoClickManager(Main main){
        this.main = main;
    }

    public boolean hasActiveAutoClick(Player player) {
        return mapAutoClicker.containsKey(player.getUniqueId().toString());
    }

    public void activateAutoClick(Player player) {
        if (mapAutoClicker.containsKey(player.getUniqueId().toString())) {
            deactivateAutoClick(player);
        }
        AutoClickController auto = new AutoClickController(player, main, getDamageVip(player));
        auto.startAutoClickTask(player);
        mapAutoClicker.put(player.getUniqueId().toString(), auto);
    }

    public void deactivateAutoClick(Player player) {
        AutoClickController auto = mapAutoClicker.get(player.getUniqueId().toString());
        if (auto != null) {
            auto.desactiveAutoClickTask();
        }
        mapAutoClicker.remove(player.getUniqueId().toString());
    }


    public double getDamageVip( Player player){
        ConfigurationSection listDamages = main.getConfig().getConfigurationSection("Damages");
        if (listDamages != null && !listDamages.getKeys(false).isEmpty()) {
            for (String key : listDamages.getKeys(false)){
                if (player.hasPermission("mautoclick.damage." + key)){
                    return listDamages.getDouble(key);
                }
            }
        }
        return listDamages.getDouble("default", 10);
    }

}

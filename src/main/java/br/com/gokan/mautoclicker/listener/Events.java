package br.com.gokan.mautoclicker.listener;

import br.com.gokan.mautoclicker.Main;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    private final Main main;
    public Events( Main main ){
        this.main = main;
    }
    @EventHandler
    void onEntityDeath( EntityDeathEvent event) {
        if (event.getEntity().getLastDamageCause() instanceof Player) {
            Player player = (Player) event.getEntity().getLastDamageCause();
            ItemStack item = player.getInventory().getItemInHand();
            if (item != null && item.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
                int lootingLevel = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                player.sendMessage(lootingLevel + " level");
                for (int i = 0; i < lootingLevel; i++) {
                    event.getDrops().forEach(drop -> event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), drop.clone()));
                }
            }
        }
    }

}

package br.com.gokan.mautoclicker.controller;

import br.com.gokan.mautoclicker.Main;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class AutoClickController {


    private final Player player;
    BukkitTask task;
    private final Double damage;

    private final Main main;
    public AutoClickController( Player player, Main main, Double damage){
        this.player = player;
        this.main = main;
        this.damage = damage;
    }


    public void startAutoClickTask(Player player) {
        if (task != null){
            task.cancel();
            task = null;
        }
        task = new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack item = player.getInventory().getItemInHand();
                if (!item.getType().equals(Material.AIR)){
                    for (Entity entity : player.getNearbyEntities(2, 2, 2)) {
                        if (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            livingEntity.damage(main.getManager().getDamageVip(player), player);
                            livingEntity.setLastDamageCause(new AutoClickDamage(player));
                        }
                    }
                }
            }
        }.runTaskTimer(main, 0L, 10L); // Run every 10 ticks (0.5 seconds)
    }


    public void desactiveAutoClickTask(){
        if (task != null){
            task.cancel();
            task = null;
        }
    }

    public static class AutoClickDamage extends org.bukkit.event.entity.EntityDamageByEntityEvent {
        public AutoClickDamage(Player damager) {
            super(damager, null, DamageCause.CUSTOM, 1.0);
        }

        @Override
        public Player getDamager() {
            return (Player) super.getDamager();
        }
    }

}

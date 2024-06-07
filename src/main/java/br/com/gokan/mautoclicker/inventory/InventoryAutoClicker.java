package br.com.gokan.mautoclicker.inventory;

import br.com.gokan.mautoclicker.Main;
import br.com.gokan.mautoclicker.utils.ItemBuilder;
import fr.mrmicky.fastinv.FastInv;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryAutoClicker extends FastInv {
    private final Main main;
    private final Player player;


    public InventoryAutoClicker( Main main, Player player ) {
        super(main.getConfig().getInt("gui.tamanho", 3) * 9, main.getConfig().getString("gui.nome", "mAutoClicker").replace("&", "§"));
        this.main = main;
        this.player = player;
        eventClick();
    }

    protected void onClick( InventoryClickEvent event ) {
        super.onClick(event);

        if (event.getSlot() == main.getConfig().getInt("gui.items.active.slot", 13)){
            if (main.getManager().hasActiveAutoClick(player)){
                event.setCurrentItem(getItemActive());
                main.getManager().deactivateAutoClick(player);
            }else {
                main.getManager().activateAutoClick(player);
                event.setCurrentItem(getItemDesactive());
            }
        }
    }


    public void eventClick(){
        if (main.getManager().hasActiveAutoClick(player)){
            setItem(main.getConfig().getInt("gui.items.active.slot", 13),getItemDesactive());
        }else{
            setItem(main.getConfig().getInt("gui.items.active.slot", 13),getItemActive());
        }
    }

    public ItemStack getItemActive(){
        ConfigurationSection itemConfig = main.getConfig().getConfigurationSection("gui.items.active");
        String nome = itemConfig.getString("nome");
        String id = itemConfig.getString("id");
        List<String> lore = itemConfig.getStringList("lore");
        String skull = itemConfig.getString("texture");
        ItemStack item = new ItemBuilder(id).setLore(lore).setDisplayName(nome).setSkullTexture(skull).build();
        return item;
    }

    public ItemStack getItemDesactive(){
        ConfigurationSection itemConfig = main.getConfig().getConfigurationSection("gui.items.desactive");
        String nome = itemConfig.getString("nome");
        String id = itemConfig.getString("id");
        List<String> lore = itemConfig.getStringList("lore");
        String skull = itemConfig.getString("texture");
        ItemStack item = new ItemBuilder(id).setLore(lore).setDisplayName(nome).setSkullTexture(skull).build();
        return item;
    }

}

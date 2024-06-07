package br.com.gokan.mautoclicker.commands;

import br.com.gokan.mautoclicker.Main;
import br.com.gokan.mautoclicker.inventory.InventoryAutoClicker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MemberCommands implements CommandExecutor {
    private final Main main;

    public MemberCommands(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String s, String[] args ) {
        if (!(sender instanceof Player)){
            sender.sendMessage("§cApenas jogadores podem utilizar esse comando.");
            return true;
        }
        main.reloadConfig();
        InventoryAutoClicker inv = new InventoryAutoClicker(main, (Player) sender);
        inv.open((Player) sender);
        return false;
    }
}

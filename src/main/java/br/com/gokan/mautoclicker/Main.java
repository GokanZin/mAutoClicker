package br.com.gokan.mautoclicker;

import br.com.gokan.mautoclicker.commands.MemberCommands;
import br.com.gokan.mautoclicker.controller.manager.AutoClickManager;
import br.com.gokan.mautoclicker.listener.Events;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.FastInvManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {


    private final AutoClickManager getManager = new AutoClickManager(this);

    @Override
    public void onEnable() {
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()){
            saveDefaultConfig();
        }
        reloadConfig();
        registrar();
        FastInvManager.register(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public AutoClickManager getManager() {
        return getManager;
    }

    public void registrar(){
        this.getCommand("autoclick").setExecutor(new MemberCommands(this));
        this.getServer().getPluginManager().registerEvents(new Events(this), this);
    }


}

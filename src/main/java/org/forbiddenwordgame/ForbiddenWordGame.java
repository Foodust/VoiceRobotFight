package org.forbiddenwordgame;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.forbiddenwordgame.Command.CommandManager;
import org.forbiddenwordgame.Data.TaskData;
import org.forbiddenwordgame.Event.EventManager;
import org.forbiddenwordgame.Module.GameModule.GameModule;
import org.forbiddenwordgame.Module.GameModule.RequestModule;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

@Getter
public final class ForbiddenWordGame extends JavaPlugin {
    private BukkitAudiences adventure;
    private Plugin plugin;
    private Logger log;
    private GameModule gameModule;

    public @NonNull BukkitAudiences getAdventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.log = Bukkit.getLogger();
        this.adventure = BukkitAudiences.create(this);
        this.plugin = this;
        CommandManager commandManager = new CommandManager(this);
        EventManager eventManager = new EventManager(this.getServer(), this);
        this.gameModule = new GameModule(this);

        try{
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8081), 0);
            server.createContext("/forbiddenwordgame", new RequestModule(this));
            server.setExecutor(null);
            server.start();
        }catch (Exception ignore){
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        TaskData.release();
    }
}

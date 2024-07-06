package org.forbiddenwordgame;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.forbiddenwordgame.Command.CommandManager;
import org.forbiddenwordgame.Data.TaskData;
import org.forbiddenwordgame.Event.EventManager;
import org.forbiddenwordgame.Module.BaseModule.ConfigModule;
import org.forbiddenwordgame.Module.GameModule.GameModule;
import org.forbiddenwordgame.Module.GameModule.RequestModule;
import org.forbiddenwordgame.Module.GameModule.SSLContextUtil;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
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
        new ConfigModule(this).getWords();
        this.gameModule = new GameModule(this);

        try {
            String keystorePath = getDataFolder().getAbsolutePath() + "/server.jks";
            char[] keystorePassword = "123456".toCharArray();

            // Load the keystore
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(keystorePath), keystorePassword);

            // Create SSL context
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, keystorePassword);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            HttpsServer server = HttpsServer.create(new InetSocketAddress("okangun.kro.kr", 8081), 0);
            server.setHttpsConfigurator(new HttpsConfigurator(sslContext));
            server.createContext("/word", new RequestModule(this));
            server.setExecutor(null);
            server.start();
        } catch (Exception exception) {
            log.info(exception.getMessage());
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

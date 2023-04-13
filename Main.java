import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private Location spawnLocation;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadSpawnLocation();
        getServer().getPluginManager().registerEvents(this, this);
    }

    private void loadSpawnLocation() {
        FileConfiguration config = getConfig();
        World world = Bukkit.getWorld(config.getString("world"));
        double x = config.getDouble("x");
        double y = config.getDouble("y");
        double z = config.getDouble("z");
        spawnLocation = new Location(world, x, y, z);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.VOID && event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            player.teleport(spawnLocation);
        }
    }
}

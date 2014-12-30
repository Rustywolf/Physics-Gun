package codes.rusty.physicsgun;

import codes.rusty.physicsgun.handlers.PhysicsHandler;
import codes.rusty.physicsgun.listeners.PhysicsListener;
import codes.rusty.physicsgun.tasks.PhysicsUpdater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class PhysicsGun extends JavaPlugin {
    
    private static final ItemStack physicsGun;
    
    static {
        physicsGun = new ItemStack(Material.DIAMOND_BARDING, 1);
        ItemMeta meta = physicsGun.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Physics Gun");
        physicsGun.setItemMeta(meta);
    }
    
    public static ItemStack getPhysicsGun() {
        return physicsGun.clone();
    }
    
    public static boolean isPhysicsGun(ItemStack item) {
        if (item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null) {
            return false;
        }
        
        if (item.getType() != Material.DIAMOND_BARDING) {
            return false;
        }
        
        return item.getItemMeta().getDisplayName().equals(physicsGun.getItemMeta().getDisplayName());
    }
    
    private PhysicsHandler handler;
    private PhysicsListener listener;
    private PhysicsUpdater updater;
    
    @Override
    public void onEnable() {
        this.handler = new PhysicsHandler(this);
        this.listener = new PhysicsListener(this);
        this.updater = new PhysicsUpdater(this);
        
        Bukkit.getPluginManager().registerEvents(listener, this);
        this.updater.runTaskTimer(this, 2, 2);
    }
    
    @Override
    public void onDisable() {
        if (updater != null) {
            updater.cancel();
        }
    }
    
    public PhysicsHandler getHandler() {
        return handler;
    }

    public PhysicsListener getListener() {
        return listener;
    }

    public PhysicsUpdater getUpdater() {
        return updater;
    }
    
}

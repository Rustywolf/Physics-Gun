package codes.rusty.physicsgun.listeners;

import codes.rusty.physicsgun.PhysicsGun;
import codes.rusty.physicsgun.handlers.PhysicsEntity;
import codes.rusty.physicsgun.handlers.PhysicsObject;
import java.util.ArrayList;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PhysicsListener implements Listener {

    private final PhysicsGun plugin;
    
    private ArrayList<String> handled = new ArrayList<>();
    
    public PhysicsListener(PhysicsGun plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (ItemStack item : player.getInventory().getContents()) {
            if (PhysicsGun.isPhysicsGun(item)) {
                return;
            }
        }
        
        player.getInventory().addItem(PhysicsGun.getPhysicsGun());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getHandler().removeObject(event.getPlayer());
        handled.remove(event.getPlayer().getName());
    }
    
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!PhysicsGun.isPhysicsGun(event.getPlayer().getItemInHand())) {
            return;
        }
        
        Player player = event.getPlayer();
        PhysicsObject object = plugin.getHandler().getObject(player);
        if (object == null && event.getRightClicked() instanceof LivingEntity) {
            PhysicsEntity entity = new PhysicsEntity(player, (LivingEntity) event.getRightClicked());
            plugin.getHandler().putObject(player, entity);
            handled.add(player.getName());
        }
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PhysicsObject object = plugin.getHandler().getObject(player);
        if (object == null || event.getAction() == Action.PHYSICAL) {
            return;
        }
        
        if (handled.contains(player.getName())) {
            handled.remove(player.getName());
            return;
        }
        
        boolean leftClick = event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK;
        if (player.isSneaking()) {
            object.onScroll(leftClick);
        } else {
            if (leftClick) {
                object.onThrow();
            } else {
                object.onDrop();
            }
            
            plugin.getHandler().removeObject(player);
        }
    }
    
}

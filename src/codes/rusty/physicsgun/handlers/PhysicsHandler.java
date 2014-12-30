package codes.rusty.physicsgun.handlers;

import codes.rusty.physicsgun.PhysicsGun;
import java.util.HashMap;
import java.util.function.BiConsumer;
import org.bukkit.entity.Player;

public class PhysicsHandler {
    
    private final HashMap<String, PhysicsObject> objects;
    private final PhysicsGun plugin;
    
    public PhysicsHandler(PhysicsGun plugin) {
        this.plugin = plugin;
        this.objects = new HashMap<>();
    }
    
    public PhysicsObject putObject(Player player, PhysicsObject object) {
        return objects.put(player.getName(), object);
    }
    
    public PhysicsObject removeObject(Player player) {
        return objects.remove(player.getName());
    }
    
    public PhysicsObject getObject(Player player) {
        return objects.get(player.getName());
    }
    
    public void forEach(BiConsumer<String, PhysicsObject> consumer) {
        objects.forEach(consumer);
    }
    
}

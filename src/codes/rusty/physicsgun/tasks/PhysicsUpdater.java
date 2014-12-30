package codes.rusty.physicsgun.tasks;

import codes.rusty.physicsgun.PhysicsGun;
import codes.rusty.physicsgun.handlers.PhysicsObject;
import java.util.function.BiConsumer;
import org.bukkit.scheduler.BukkitRunnable;

public class PhysicsUpdater extends BukkitRunnable {

    private static final BiConsumer<String, PhysicsObject> consumer = (String name, PhysicsObject object) -> {
        if (object == null) {
            System.out.println((name == null) ? "null key" : name);
        }
        object.onUpdate();
    };
    
    private final PhysicsGun plugin;
    
    public PhysicsUpdater(PhysicsGun plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void run() {
        plugin.getHandler().forEach(consumer);
    }
}

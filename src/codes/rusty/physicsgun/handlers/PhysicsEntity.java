package codes.rusty.physicsgun.handlers;

import java.util.HashSet;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PhysicsEntity implements PhysicsObject {

    public static void setNoAI(LivingEntity entity, boolean enable) {
        ((CraftLivingEntity) entity).getHandle().getDataWatcher().watch(15, (byte) (enable ? 1 : 0));
    }

    private Player player;
    private LivingEntity entity;

    private double distance;
    
    public PhysicsEntity(Player player, LivingEntity entity) {
        this.player = player;
        this.entity = entity;

        this.distance = entity.getLocation().distance(player.getEyeLocation());
        PhysicsEntity.setNoAI(entity, true);
    }
    
    @Override
    public void onUpdate() {
        List<Block> blos = player.getLineOfSight(null, (int) Math.ceil(distance));
        double newDistance = this.distance;

        Block block = blos.get(0);
        for (Block b : blos) {
            if (b.getType() != Material.AIR) {
                break;
            }

            block = b;
        }

        newDistance = block.getLocation().distance(player.getLocation());
        entity.teleport(player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(newDistance - 1)));
    }

    @Override
    public void onScroll(boolean forward) {
        if (forward) {
            this.distance++;
            if (this.distance > 16) {
                this.distance = 16;
            }
        } else {
            this.distance--;
            if (this.distance < 1) {
                this.distance = 1;
            }
        }
    }

    @Override
    public void onDrop() {
        PhysicsEntity.setNoAI(entity, false);
        this.player = null;
        this.entity = null;
    }

    @Override
    public void onThrow() {
        PhysicsEntity.setNoAI(entity, false);
        this.entity.setVelocity(this.player.getEyeLocation().getDirection().multiply(2));
        this.player = null;
        this.entity = null;
    }

}

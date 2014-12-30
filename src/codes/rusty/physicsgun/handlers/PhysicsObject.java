package codes.rusty.physicsgun.handlers;

public interface PhysicsObject {
    
    public void onDrop();
    public void onUpdate();
    public void onThrow();
    public void onScroll(boolean forward);
    
}

package utils.method_provider.providers;

import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.event.InteractionEvent;
import utils.method_provider.CustomMethodProvider;

public class InteractionHelper extends CustomMethodProvider {

    public boolean canInteract(final Entity entity) {
        return entity != null &&
                entity.isVisible() &&
                getMap().canReach(entity);
    }

    public boolean interactNoMovement(final Entity entity) {
        InteractionEvent event = new InteractionEvent(entity);
        event.setOperateCamera(false);
        event.setWalkTo(false);
        return execute(event).hasFinished();
    }
}
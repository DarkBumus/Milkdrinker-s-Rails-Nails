package darkbum.mdrailsnails.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;

public class PlayerOpenContainerEventHandler {

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onPlayerOpenContainerEvent(PlayerOpenContainerEvent event) {
        event.setResult(Event.Result.ALLOW);
    }
}

package darkbum.mdrailsnails.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.GameRules;
import net.minecraftforge.event.world.WorldEvent;

import static darkbum.mdrailsnails.common.config.ModConfigurationVanillaChanges.*;
import static java.lang.String.*;

public class WorldLoadEventHandler {

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (!event.world.isRemote) {
            GameRules rules = event.world.getGameRules();
            if (!rules.hasRule("minecartMaxSpeed")) {
                rules.addGameRule("minecartMaxSpeed", valueOf(minecartMaxSpeedBaseValue));
            }
        }
    }
}

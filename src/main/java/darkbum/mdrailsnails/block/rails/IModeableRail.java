package darkbum.mdrailsnails.block.rails;

import net.minecraft.block.Block;

public interface IModeableRail {

    Block getTurnedBlock();

    void setTurnedBlock(Block block);

    String getModeChangeMessageKey();

    void setModeChangeMessageKey(String key);
}

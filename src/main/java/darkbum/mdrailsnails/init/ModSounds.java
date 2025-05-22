package darkbum.mdrailsnails.init;

import net.minecraft.block.Block;

/**
 * Sounds class.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModSounds {

/*    public static final Block.SoundType soundTypeMud = new Block.SoundType("MudBlock", 1.0F, 1.0F) {
        public String getBreakSound() {
            return "mdrailsnails:block.mud.break";
        }
        public String getStepResourcePath() {
            return "mdrailsnails:block.mud.step";
        }
    };*/


    /**
     * A customizable sound type class that allows for the use of default sounds or custom sound mappings.
     */
    @SuppressWarnings("unused")
    private static final class CustomSound extends Block.SoundType {

        private final boolean useDefaults;

        public CustomSound(String name, float volume, float pitch, boolean useDefaults) {
            super(name, volume, pitch);
            this.useDefaults = useDefaults;
        }

        public CustomSound(String name) {
            this(name, 1.0F, 1.0F, false);
        }

        public String getBreakSound() {
            return this.useDefaults ? super.getBreakSound() : this.soundName;
        }

        public String getStepResourcePath() {
            return this.useDefaults ? super.getStepResourcePath() : this.soundName;
        }
    }
}

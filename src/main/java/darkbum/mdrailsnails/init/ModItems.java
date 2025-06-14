package darkbum.mdrailsnails.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;

import darkbum.mdrailsnails.item.*;

import static darkbum.mdrailsnails.common.config.ModConfigurationEntities.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationItems.*;
import static darkbum.mdrailsnails.common.proxy.CommonProxy.*;
import static darkbum.mdrailsnails.util.ConditionalRegistrar.*;
import static net.minecraft.creativetab.CreativeTabs.*;

/**
 * Items class.
 *
 * @author DarkBum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ModItems {

    public static final int speed = Potion.moveSpeed.id;
    public static final int slowness = Potion.moveSlowdown.id;
    public static final int haste = Potion.digSpeed.id;
    public static final int mining_fatigue = Potion.digSlowdown.id;
    public static final int strength = Potion.damageBoost.id;
    public static final int instant_health = Potion.heal.id;
    public static final int instant_damage = Potion.harm.id;
    public static final int jump_boost = Potion.jump.id;
    public static final int nausea = Potion.confusion.id;
    public static final int regeneration = Potion.regeneration.id;
    public static final int resistance = Potion.resistance.id;
    public static final int fire_resistance = Potion.fireResistance.id;
    public static final int water_breathing = Potion.waterBreathing.id;
    public static final int invisibility = Potion.invisibility.id;
    public static final int blindness = Potion.blindness.id;
    public static final int night_vision = Potion.nightVision.id;
    public static final int hunger = Potion.hunger.id;
    public static final int weakness = Potion.weakness.id;
    public static final int poison = Potion.poison.id;
    public static final int wither = Potion.wither.id;
    public static final int health_boost = Potion.field_76434_w.id;
    public static final int absorption = Potion.field_76444_x.id;
    public static final int saturation = Potion.field_76443_y.id;

    public static final float one_third = 1.0f / 3.0f;
    public static final float two_thirds = 2.0f / 3.0f;

    public static CreativeTabs tab = tabMDRNItems;

    public static Item dev_controller;
    public static Item railway_lever;
    public static Item awakened_ender_eye;
    public static Item hauler_minecart;

    /**
     * Initializes and registers all blocks.
     */
    public static void init() {

        dev_controller = new ItemDevController("dev_controller", tab).setTextureName("mdrailsnails:dev/dev_controller");
        railway_lever = new ItemRailwayLever("railway_lever", tab).setTextureName("mdrailsnails:railway_lever");
        awakened_ender_eye = new ItemAwakenedEnderEye("awakened_ender_eye", tab).setTextureName("mdrailsnails:awakened_ender_eye");
        hauler_minecart = new ItemMinecartHauler("hauler_minecart", tabTransport).setUnlocalizedName("hauler_minecart").setTextureName("mdrailsnails:hauler_minecart");


        registerItem(dev_controller, "dev_controller");
        registerItem(railway_lever, "railway_lever", enableRailwayLever);
        registerItem(awakened_ender_eye, "awakened_ender_eye", enableAwakenedEnderEye);
        registerItem(hauler_minecart, "hauler_minecart", enableHaulerMinecart);
    }
}

package darkbum.mdrailsnails.init;

import darkbum.mdrailsnails.block.itemblock.ItemBlockConductor;
import darkbum.mdrailsnails.block.itemblock.ItemBlockRail;
import darkbum.mdrailsnails.block.rails.IModeableRail;
import net.minecraft.block.*;
import net.minecraft.creativetab.CreativeTabs;

import darkbum.mdrailsnails.block.*;

import static darkbum.mdrailsnails.common.config.ModConfigurationBlocks.*;
import static darkbum.mdrailsnails.common.proxy.CommonProxy.*;
import static darkbum.mdrailsnails.util.ConditionalRegistrar.*;

/**
 * Blocks class.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModBlocks {

    static CreativeTabs tab = tabMDRNBlocks;

    public static Block dev_block;
    public static Block copper_rail_windlass;
    public static Block copper_rail;
    public static Block conductor;
    public static Block junction_rail;
    public static Block wye_rail_j;
    public static Block wye_rail_r;
    public static Block wye_rail_l;
    public static Block one_way_rail_r;
    public static Block one_way_rail_l;
    public static Block locking_rail;
    public static Block locking_release_rail;
    public static Block locking_release_rail_i;
    public static Block locking_release_rail_r;
    public static Block locking_release_rail_l;
    public static Block dismounting_rail_se;
    public static Block dismounting_rail_nw;
    public static Block mounting_rail;
    public static Block coupling_rail;
    public static Block decoupling_rail;
    public static Block suspended_rail;
    public static Block disposing_rail;
    public static Block launching_rail;
    public static Block cart_dislocating_rail;
    public static Block one_way_detector_rail_r;
    public static Block one_way_detector_rail_l;
    public static Block cart_detector_rail;
    public static Block chain_lift_rail;
    public static Block slowdown_rail;
    public static Block high_speed_rail;
    public static Block high_speed_booster_rail;
    public static Block high_speed_transition_rail_r;
    public static Block high_speed_transition_rail_l;
    /**
     * Initializes and registers all blocks.
     */
    public static void init() {

        dev_block = new BlockDevBlock("dev_block", tab);
        copper_rail_windlass = new BlockCopperRailWindlass("copper_rail_windlass", tab);
        copper_rail = new BlockRailCopper("copper_rail", null, 1000).setBlockTextureName("mdrailsnails:rails/rail_copper");
        conductor = new BlockConductor("conductor", tab);
        junction_rail = new BlockJunctionRail("junction_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_junction");
        wye_rail_j = new BlockWyeRail("wye_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_wye_junction");
        wye_rail_r = new BlockWyeRail("wye_rail", null).setBlockTextureName("mdrailsnails:rails/rail_wye_right");
        wye_rail_l = new BlockWyeRail("wye_rail", null).setBlockTextureName("mdrailsnails:rails/rail_wye_left");
        one_way_rail_r = new BlockRailOneWay("one_way_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_oneway_right");
        one_way_rail_l = new BlockRailOneWay("one_way_rail", null).setBlockTextureName("mdrailsnails:rails/rail_oneway_left");
        locking_rail = new BlockRailLocking("locking_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_locking");
        locking_release_rail = new BlockRailLocking("locking_rail", null).setBlockTextureName("mdrailsnails:rails/rail_locking_release");
        locking_release_rail_i = new BlockRailLocking("locking_rail", null).setBlockTextureName("mdrailsnails:rails/rail_locking_release_inverted");
        locking_release_rail_r = new BlockRailLocking("locking_rail", null).setBlockTextureName("mdrailsnails:rails/rail_locking_release_right");
        locking_release_rail_l = new BlockRailLocking("locking_rail", null).setBlockTextureName("mdrailsnails:rails/rail_locking_release_left");
        dismounting_rail_se = new BlockRailDismounting("dismounting_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_dismounting_southeast");
        dismounting_rail_nw = new BlockRailDismounting("dismounting_rail", null).setBlockTextureName("mdrailsnails:rails/rail_dismounting_northwest");
        mounting_rail = new BlockRailMounting("mounting_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_mounting");
        coupling_rail = new BlockRailCoupling("coupling_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_coupling");
        decoupling_rail = new BlockRailCoupling("coupling_rail", null).setBlockTextureName("mdrailsnails:rails/rail_decoupling");
        suspended_rail = new BlockRailSuspended("suspended_rail", tab, suspendedRailReach).setBlockTextureName("mdrailsnails:rails/rail_suspended");
        disposing_rail = new BlockRailDisposing("disposing_rail", tab, suspendedRailReach).setBlockTextureName("mdrailsnails:rails/rail_disposing");
        launching_rail = new BlockRailLaunching("launching_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_launching");
        cart_dislocating_rail = new BlockRailCartDislocating("cart_dislocating_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_cart_dislocating");
        one_way_detector_rail_r = new BlockRailOneWayDetector("one_way_detector_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_oneway_detector_right");
        one_way_detector_rail_l = new BlockRailOneWayDetector("one_way_detector_rail", null).setBlockTextureName("mdrailsnails:rails/rail_oneway_detector_left");
        cart_detector_rail = new BlockRailCartDetector("cart_detector_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_cart_detector");
        slowdown_rail = new BlockRailSlowdown("slowdown_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_slowdown");
        high_speed_rail = new BlockRailHighSpeed("high_speed_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_high_speed");
        high_speed_booster_rail = new BlockRailHighSpeedBooster("high_speed_booster_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_high_speed_booster");
        high_speed_transition_rail_r = new BlockRailHighSpeedTransition("high_speed_transition_rail", tab).setBlockTextureName("mdrailsnails:rails/rail_high_speed_transition_right");
        high_speed_transition_rail_l = new BlockRailHighSpeedTransition("high_speed_transition_rail", null).setBlockTextureName("mdrailsnails:rails/rail_high_speed_transition_left");


        registerBlock(dev_block, "dev_block");
//        registerBlock(copper_rail_windlass, "copper_rail_windlass", enableWindlass);
//        registerBlock(copper_rail, "copper_rail", enableWindlass);
//        registerBlock(conductor, ItemBlockConductor.class, "conductor", enableConductor);
        registerBlock(junction_rail, ItemBlockRail.class, "junction_rail", enableJunctionRail);
        registerBlock(wye_rail_j, ItemBlockRail.class, "wye_rail_j", enableWyeRail);
        registerBlock(wye_rail_r, ItemBlockRail.class, "wye_rail_r", enableWyeRail);
        registerBlock(wye_rail_l, ItemBlockRail.class, "wye_rail_l", enableWyeRail);
        registerBlock(one_way_rail_r, ItemBlockRail.class, "one_way_rail_r", enableOneWayRail);
        registerBlock(one_way_rail_l, ItemBlockRail.class, "one_way_rail_l", enableOneWayRail);
        registerBlock(locking_rail, ItemBlockRail.class, "locking_rail", enableLockingRail);
        registerBlock(locking_release_rail, ItemBlockRail.class, "locking_release_rail", enableLockingRail);
        registerBlock(locking_release_rail_i, ItemBlockRail.class, "locking_release_rail_i", enableLockingRail);
        registerBlock(locking_release_rail_r, ItemBlockRail.class, "locking_release_rail_r", enableLockingRail);
        registerBlock(locking_release_rail_l, ItemBlockRail.class, "locking_release_rail_l", enableLockingRail);
        registerBlock(dismounting_rail_se, ItemBlockRail.class, "dismounting_rail_se", enableDismountingRail);
        registerBlock(dismounting_rail_nw, ItemBlockRail.class, "dismounting_rail_nw", enableDismountingRail);
        registerBlock(mounting_rail, ItemBlockRail.class, "mounting_rail", enableMountingRail);
        registerBlock(coupling_rail, ItemBlockRail.class, "coupling_rail", enableCouplingRail);
        registerBlock(decoupling_rail, ItemBlockRail.class, "decoupling_rail", enableCouplingRail);
        registerBlock(suspended_rail, ItemBlockRail.class, "suspended_rail", enableSuspendedRail);
        registerBlock(disposing_rail, ItemBlockRail.class, "disposing_rail", enableDisposingRail);
        registerBlock(launching_rail, ItemBlockRail.class, "launching_rail", enableLaunchingRail);
        registerBlock(cart_dislocating_rail, ItemBlockRail.class, "cart_dislocating_rail", enableCartDislocatingRail);
        registerBlock(one_way_detector_rail_r, ItemBlockRail.class, "one_way_detector_rail_r", enableOneWayDetectorRail);
        registerBlock(one_way_detector_rail_l, ItemBlockRail.class, "one_way_detector_rail_l", enableOneWayDetectorRail);
        registerBlock(cart_detector_rail, ItemBlockRail.class, "cart_detector_rail", enableCartDetectorRail);
        registerBlock(slowdown_rail, ItemBlockRail.class, "slowdown_rail", enableSlowdownRail);
        registerBlock(high_speed_rail, ItemBlockRail.class, "high_speed_rail", enableHighSpeedRails);
        registerBlock(high_speed_booster_rail, ItemBlockRail.class, "high_speed_booster_rail", enableHighSpeedRails);
        registerBlock(high_speed_transition_rail_r, ItemBlockRail.class, "high_speed_transition_rail_r", enableHighSpeedRails);
        registerBlock(high_speed_transition_rail_l, ItemBlockRail.class, "high_speed_transition_rail_l", enableHighSpeedRails);


        ((IModeableRail) wye_rail_j).setTurnedBlock(wye_rail_r);
        ((IModeableRail) wye_rail_r).setTurnedBlock(wye_rail_l);
        ((IModeableRail) wye_rail_l).setTurnedBlock(wye_rail_j);

        ((IModeableRail) one_way_rail_r).setTurnedBlock(one_way_rail_l);
        ((IModeableRail) one_way_rail_l).setTurnedBlock(one_way_rail_r);

        ((IModeableRail) locking_rail).setTurnedBlock(locking_release_rail);
        ((IModeableRail) locking_release_rail).setTurnedBlock(locking_release_rail_i);
        ((IModeableRail) locking_release_rail_i).setTurnedBlock(locking_release_rail_r);
        ((IModeableRail) locking_release_rail_r).setTurnedBlock(locking_release_rail_l);
        ((IModeableRail) locking_release_rail_l).setTurnedBlock(locking_rail);

        ((IModeableRail) dismounting_rail_se).setTurnedBlock(dismounting_rail_nw);
        ((IModeableRail) dismounting_rail_nw).setTurnedBlock(dismounting_rail_se);

        ((IModeableRail) coupling_rail).setTurnedBlock(decoupling_rail);
        ((IModeableRail) decoupling_rail).setTurnedBlock(coupling_rail);

        ((IModeableRail) one_way_detector_rail_r).setTurnedBlock(one_way_detector_rail_l);
        ((IModeableRail) one_way_detector_rail_l).setTurnedBlock(one_way_detector_rail_r);

        ((IModeableRail) high_speed_transition_rail_r).setTurnedBlock(high_speed_transition_rail_l);
        ((IModeableRail) high_speed_transition_rail_l).setTurnedBlock(high_speed_transition_rail_r);


        ((IModeableRail) wye_rail_j).setModeChangeMessageKey("tile.wye_rail.mess.j");
        ((IModeableRail) wye_rail_r).setModeChangeMessageKey("tile.wye_rail.mess.r");
        ((IModeableRail) wye_rail_l).setModeChangeMessageKey("tile.wye_rail.mess.l");

        ((IModeableRail) one_way_rail_r).setModeChangeMessageKey("tile.one_way_rail.mess.r");
        ((IModeableRail) one_way_rail_l).setModeChangeMessageKey("tile.one_way_rail.mess.l");

        ((IModeableRail) locking_rail).setModeChangeMessageKey("tile.locking_rail.mess.base");
        ((IModeableRail) locking_release_rail).setModeChangeMessageKey("tile.locking_rail.mess.release");
        ((IModeableRail) locking_release_rail_i).setModeChangeMessageKey("tile.locking_rail.mess.release_i");
        ((IModeableRail) locking_release_rail_r).setModeChangeMessageKey("tile.locking_rail.mess.release_r");
        ((IModeableRail) locking_release_rail_l).setModeChangeMessageKey("tile.locking_rail.mess.release_l");

        ((IModeableRail) dismounting_rail_se).setModeChangeMessageKey("tile.dismounting_rail.mess.se");
        ((IModeableRail) dismounting_rail_nw).setModeChangeMessageKey("tile.dismounting_rail.mess.nw");

        ((IModeableRail) coupling_rail).setModeChangeMessageKey("tile.coupling_rail.mess.base");
        ((IModeableRail) decoupling_rail).setModeChangeMessageKey("tile.coupling_rail.mess.de");

        ((IModeableRail) one_way_detector_rail_r).setModeChangeMessageKey("tile.one_way_detector_rail.mess.r");
        ((IModeableRail) one_way_detector_rail_l).setModeChangeMessageKey("tile.one_way_detector_rail.mess.l");

        ((IModeableRail) high_speed_transition_rail_r).setModeChangeMessageKey("tile.high_speed_transition_rail.mess.r");
        ((IModeableRail) high_speed_transition_rail_l).setModeChangeMessageKey("tile.high_speed_transition_rail.mess.l");
    }
}

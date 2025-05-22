package darkbum.mdrailsnails.init;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import static net.minecraft.init.Blocks.*;
import static net.minecraftforge.common.AchievementPage.*;

/**
 * Achievement class.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModAchievementList {

    public static Achievement achiev1 = new Achievement(
        "achievement.achiev1",
        "find_salt",
        0,
        0,
        dirt,
        null).registerStat();

    public static AchievementPage achievSaltPage = new AchievementPage(
        "Milkdrinker's Rails&Nails",
        achiev1);

    /**
     * Initializes all achievements.
     */
    public static void init() {
        registerAchievementPage(achievSaltPage);
    }
}

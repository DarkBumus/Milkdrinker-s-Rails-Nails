package darkbum.mdrailsnails.util;

import java.util.HashMap;
import java.util.Map;

public final class SoundHelper {

    private static final Map<String, Boolean> toggleSoundState = new HashMap<>();
    private static final Map<String, Long> lastClickTime = new HashMap<>();
    private static final long FORGET_MS = 5000;

    public static boolean getToggleState(int x, int y, int z) {
        String posKey = x + "," + y + "," + z;
        long now = System.currentTimeMillis();

        if (lastClickTime.containsKey(posKey)) {
            long lastTime = lastClickTime.get(posKey);
            if (now - lastTime > FORGET_MS) {

                toggleSoundState.put(posKey, false);
            }
        } else {
            toggleSoundState.put(posKey, false);
        }

        boolean state = toggleSoundState.get(posKey);

        toggleSoundState.put(posKey, !state);
        lastClickTime.put(posKey, now);

        return state;
    }
}

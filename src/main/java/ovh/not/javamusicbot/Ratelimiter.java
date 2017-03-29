package ovh.not.javamusicbot;

import java.util.*;
import java.util.concurrent.*;
import net.dv8tion.jda.core.entities.User;

public final class Ratelimiter {
    private static final Map<String, Long> ratelimitMap = new ConcurrentHashMap<>();
    private static final int threshold = 1; // time since last message was sent in seconds

    private Ratelimiter() {}

    public static boolean isRatelimited(User user) {
        // Check ratelimits:
        if(ratelimitMap.containsKey(user.getId())) {
            long time = ratelimitMap.get(user.getId());
            if(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - time) <= threshold) {
                return true;
            } else {
                ratelimitMap.put(user.getId(), System.currentTimeMillis());
                return false;
            }
        } else {
            // user not in ratelimit table, assume not ratelimited yet
            ratelimitMap.put(user.getId(), System.currentTimeMillis());
            return false;
        }
    }
}

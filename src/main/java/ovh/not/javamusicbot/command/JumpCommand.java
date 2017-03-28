package ovh.not.javamusicbot.command;

import ovh.not.javamusicbot.Command;
import ovh.not.javamusicbot.GuildMusicManager;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JumpCommand extends Command {
    private static final Pattern TIME_PATTERN = Pattern.compile("(?:(?<hours>\\d{1,2}):)?(?:(?<minutes>\\d{1,2}):)?(?<seconds>\\d{1,2})");

    public JumpCommand() {
        super("jump");
    }

    @Override
    public void on(Context context) {
        GuildMusicManager musicManager = GuildMusicManager.get(context.event.getGuild());
        if (musicManager == null || musicManager.player.getPlayingTrack() == null) {
            context.reply("There is no music playing on this server.");
            return;
        }
        if (context.args.length == 0) {
            context.reply("Use ?jump (time) to play the song at a certain time. For example, ?jump 03:51 would play at time 3 minutes and 51 seconds.");
            return;
        }
        Matcher matcher = TIME_PATTERN.matcher(context.args[0]);
        if (!matcher.find()) {
            context.reply("Use ?jump (time) to play the song at a certain time. For example, ?jump 03:51 would play at time 3 minutes and 51 seconds.");
            return;
        }
        String sHours = matcher.group("hours");
        String sMinutes = matcher.group("minutes");
        if (sMinutes == null && sHours != null) {
            sMinutes = sHours;
            sHours = null;
        }
        String sSeconds = matcher.group("seconds");
        long hours = 0, minutes = 0, seconds = 0;
        try {
            if (sHours != null) {
                hours = Long.parseLong(sHours);
            }
            if (sMinutes != null) {
                minutes = Long.parseLong(sMinutes);
            }
            if (sSeconds != null) {
                seconds = Long.parseLong(sSeconds);
            }
        } catch (NumberFormatException e) {
            context.reply("Use ?jump (time) to play the song at a certain time. For example, ?jump 03:51 would play at time 3 minutes and 51 seconds.");
            return;
        }
        long time = Duration.ofHours(hours).toMillis();
        time += Duration.ofMinutes(minutes).toMillis();
        time += Duration.ofSeconds(seconds).toMillis();
        musicManager.player.getPlayingTrack().setPosition(time);
        context.reply(":white_check_mark: Jumped to the specified position!");
    }
}

package ovh.not.javamusicbot.command;

import ovh.not.javamusicbot.Command;
import ovh.not.javamusicbot.GuildMusicManager;

public class RepeatCommand extends Command {
    public RepeatCommand() {
        super("repeat", "loop");
    }

    @Override
    public void on(Context context) {
        GuildMusicManager musicManager = GuildMusicManager.get(context.event.getGuild());
        if (musicManager == null || musicManager.player.getPlayingTrack() == null) {
            context.reply("Music is not playing on this server.");
            return;
        }
        boolean repeat = !musicManager.scheduler.repeat;
        musicManager.scheduler.repeat = repeat;
        context.reply("**" + (repeat ? "AceBot has enabled" : "AceBot has disabled") + "** song repeating. :white_check_mark:");
    }
}

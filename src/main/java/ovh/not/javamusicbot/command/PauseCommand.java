package ovh.not.javamusicbot.command;

import ovh.not.javamusicbot.Command;
import ovh.not.javamusicbot.GuildMusicManager;

public class PauseCommand extends Command {
    public PauseCommand() {
        super("pause", "resume");
    }

    @Override
    public void on(Context context) {
        GuildMusicManager musicManager = GuildMusicManager.get(context.event.getGuild());
        if (musicManager == null || musicManager.player.getPlayingTrack() == null) {
            context.reply("No song is playing on this server.");
            return;
        }
        boolean action = !musicManager.player.isPaused();
        musicManager.player.setPaused(action);
        if (action) {
            context.reply("AceBot has paused the music for you. :white_check_mark:");
        } else {
            context.reply("AceBot is playing the music again! :white_check_mark:");
        }
    }
}

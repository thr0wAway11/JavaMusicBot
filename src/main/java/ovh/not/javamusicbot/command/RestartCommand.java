package ovh.not.javamusicbot.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import ovh.not.javamusicbot.Command;
import ovh.not.javamusicbot.GuildMusicManager;

import static ovh.not.javamusicbot.Utils.formatDuration;

public class RestartCommand extends Command {
    public RestartCommand() {
        super("restart");
    }

    @Override
    public void on(Context context) {
        GuildMusicManager musicManager = GuildMusicManager.get(context.event.getGuild());
        if (musicManager == null || musicManager.player.getPlayingTrack() == null) {
            context.reply("No music is playing!");
            return;
        }
        AudioTrack currentTrack = musicManager.player.getPlayingTrack();
        currentTrack.setPosition(0);
        context.reply(String.format("Restarted **%s** by **%s** `[%s]`", currentTrack.getInfo().title,
                currentTrack.getInfo().author, formatDuration(currentTrack.getDuration())));
    }
}

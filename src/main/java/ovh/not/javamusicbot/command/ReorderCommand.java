package ovh.not.javamusicbot.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import ovh.not.javamusicbot.Command;
import ovh.not.javamusicbot.GuildMusicManager;

import java.util.List;

@SuppressWarnings("unchecked")
public class ReorderCommand extends Command {
    public ReorderCommand() {
        super("reorder", "order");
    }

    @Override
    public void on(Context context) {
        GuildMusicManager musicManager = GuildMusicManager.get(context.event.getGuild());
        if (musicManager == null || musicManager.player.getPlayingTrack() == null) {
            context.reply("No music is playing on this guild!");
            return;
        }
        if (context.args.length < 2) {
            context.reply("Usage: `?reorder <song number> <position>`\nExample: `?reorder 5 1` - moves song at "
                    + "position 5 in queue to position 1");
            return;
        }
        int songNum, newPosition;
        try {
            songNum = Integer.parseInt(context.args[0]);
            newPosition = Integer.parseInt(context.args[1]);
        } catch (NumberFormatException e) {
            context.reply("Invalid song number or position!");
            return;
        }
        List<AudioTrack> queue = (List<AudioTrack>) musicManager.scheduler.queue;
        int index = songNum - 1;
        AudioTrack track = queue.get(index);
        if (track == null) {
            context.reply("Could not find the specified song!");
            return;
        }
        queue.remove(index);
        queue.add(newPosition - 1, track);
        context.reply(String.format("Moved **%s** by **%s** from position **%d** to position **%d**!",
                track.getInfo().title, track.getInfo().author, songNum, newPosition));
    }
}

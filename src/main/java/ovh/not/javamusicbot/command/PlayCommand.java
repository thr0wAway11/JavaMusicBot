package ovh.not.javamusicbot.command;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.VoiceChannel;
import ovh.not.javamusicbot.Command;
import ovh.not.javamusicbot.CommandManager;
import ovh.not.javamusicbot.GuildMusicManager;
import ovh.not.javamusicbot.LoadResultHandler;

import java.util.Set;

public class PlayCommand extends Command {
    private final CommandManager commandManager;
    private final AudioPlayerManager playerManager;

    public PlayCommand(CommandManager commandManager, AudioPlayerManager playerManager) {
        super("play", "p");
        this.commandManager = commandManager;
        this.playerManager = playerManager;
    }

    @Override
    public void on(Context context) {
        if (context.args.length == 0) {
            context.reply("Use ?play (song) to play a song. Then, ?choose the song you want. If your song doesn't show up, type ?cancel and get the URL of the song instead.");
            return;
        }
        VoiceChannel channel = context.event.getMember().getVoiceState().getChannel();
        if (channel == null) {
            context.reply("You need to be in a voice channel to play music!");
            return;
        }
        GuildMusicManager musicManager = GuildMusicManager.getOrCreate(context.event.getGuild(),
                context.event.getTextChannel(), playerManager);
        if (musicManager.open && musicManager.player.getPlayingTrack() != null
                && musicManager.channel != channel
                && !context.event.getMember().hasPermission(musicManager.channel, Permission.VOICE_MOVE_OTHERS)) {
            context.reply("AceBot is already playing music in " + musicManager.channel.getName() + " so it cannot " +
                    "be moved. Members with the `VOICE_MOVE_OTHERS` permission are exempt from this.");
            return;
        }
        LoadResultHandler handler = new LoadResultHandler(commandManager, musicManager, playerManager, context);
        handler.allowSearch = true;
        Set<String> flags = context.parseFlags();
        if (flags.contains("first") || flags.contains("f")) {
            handler.setFirstInQueue = true;
        }
        playerManager.loadItem(String.join(" ", context.args), handler);
        if (!musicManager.open) {
            musicManager.open(channel, context.event.getAuthor());
        }
    }
}

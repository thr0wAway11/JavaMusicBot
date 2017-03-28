package ovh.not.javamusicbot.command;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.managers.AudioManager;
import ovh.not.javamusicbot.Command;
import ovh.not.javamusicbot.GuildMusicManager;

public class StopCommand extends Command {
    public StopCommand() {
        super("stop", "leave", "clear");
    }

    @Override
    public void on(Context context) {
        Guild guild = context.event.getGuild();
        GuildMusicManager musicManager = GuildMusicManager.get(guild);
        if (musicManager != null) {
            musicManager.close();
            musicManager.scheduler.queue.clear();
            musicManager.scheduler.next(null);
            context.reply("AceBot has stopped playing music and left the voice channel. Type ?play (song) to invite the bot back!");
        } else {
            AudioManager audioManager = guild.getAudioManager();
            audioManager.closeAudioConnection();
            context.reply("AceBot left the voice channel.");
        }
    }
}

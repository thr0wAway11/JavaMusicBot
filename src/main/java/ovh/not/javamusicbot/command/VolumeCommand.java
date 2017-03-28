package ovh.not.javamusicbot.command;

import ovh.not.javamusicbot.Command;

public class VolumeCommand extends Command {
    public VolumeCommand() {
        super("volume", "v");
        hide = true;
    }

    @Override
    public void on(Context context) {
        context.reply("Changing song volume has been disabled.");
        /*GuildMusicManager musicManager = GuildMusicManager.get(context.event.getGuild());
        if (musicManager == null || musicManager.player.getPlayingTrack() == null) {
            context.reply("No music is playing on this guild!");
            return;
        }
        if (context.args.length == 0) {
            context.reply(String.format("Current volume: **%d**", musicManager.player.getVolume()));
            return;
        }
        try {
            int newVolume = Math.max(10, Math.min(100, Integer.parseInt(context.args[0])));
            musicManager.player.setVolume(newVolume);
            context.reply(String.format("Set volume to **%d**", newVolume));
        } catch (NumberFormatException e) {
            context.reply("Invalid volume. Bounds: `10 - 100`");
        }*/
    }
}

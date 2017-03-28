package ovh.not.javamusicbot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Member;
import ovh.not.javamusicbot.command.*;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    public final Map<String, Command> commands = new HashMap<>();
    public final Map<Member, Selection<AudioTrack, String>> selectors = new HashMap<>();

    CommandManager(Config config, Constants constants, ShardManager.Shard shard) {
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        CommandManager.register(commands,
                new AdminCommand(config, shard, playerManager),
                new ChooseCommand(this),
                new HelpCommand(this, constants),
                new InviteCommand(config),
                new JumpCommand(),
                new MoveCommand(),
                new NowPlayingCommand(),
                new PauseCommand(),
                new PlayCommand(this, playerManager),
                new QueueCommand(),
                new ReorderCommand(),
                new RepeatCommand(),
                new ResetCommand(),
                new RestartCommand(),
                new SearchCommand(this),
                new SkipCommand(),
                new StopCommand(),
                new VolumeCommand()
        );
    }

    public static void register(Map<String, Command> commands, Command... cmds) {
        for (Command command : cmds) {
            for (String name : command.names) {
                if (commands.containsKey(name)) {
                    throw new RuntimeException(String.format("Command name collision %s in %s!", name,
                            command.getClass().getName()));
                }
                commands.put(name, command);
            }
        }
    }

    Command getCommand(String name) {
        return commands.get(name);
    }
}

package ovh.not.javamusicbot.command;

import ovh.not.javamusicbot.Command;
import ovh.not.javamusicbot.Constants;
import ovh.not.javamusicbot.CommandManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelpCommand extends Command {
    private final CommandManager commandManager;
    private final Map<String, String> commandDescriptions;

    public HelpCommand(CommandManager commandManager, Constants constants) {
        super("help", "commands", "h", "music");
        this.commandManager = commandManager;
        this.commandDescriptions = constants.commandDescriptions;
    }

    @Override
    public void on(Context context) {
        StringBuilder builder = new StringBuilder("**Commands:**");
        List<String> added = new ArrayList<>(commandManager.commands.size());
        for (Command command : commandManager.commands.values()) {
            if (added.contains(command.names[0]) || command.hide) {
                continue;
            }
            added.add(command.names[0]);
            builder.append("\n`").append(command.names[0]).append('`');
            if (commandDescriptions.containsKey(command.names[0])) {
                builder.append(" ").append(commandDescriptions.get(command.names[0]));
            }
        }
        builder.append("\n\nThanks for using AceBot and all it's been through. :musical_note:");
        context.reply(builder.toString());
    }
}

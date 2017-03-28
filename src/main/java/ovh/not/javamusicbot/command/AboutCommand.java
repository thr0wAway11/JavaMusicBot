package ovh.not.javamusicbot.command;

import ovh.not.javamusicbot.Command;
import ovh.not.javamusicbot.Config;

public class AboutCommand extends Command {
    private final String about;

    public AboutCommand(Config config) {
        super("discord");
        this.about = config.about;
    }

    @Override
    public void on(Context context) {
        context.reply("Here you go! " + about);
    }
}

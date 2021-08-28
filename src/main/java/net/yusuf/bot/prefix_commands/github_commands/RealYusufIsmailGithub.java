package net.yusuf.bot.prefix_commands.github_commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.prefix_commands.CommandContext;
import net.yusuf.bot.prefix_commands.ICommand;

import java.util.List;


public class RealYusufIsmailGithub implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if(args.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("https://github.com/RealYusufIsmail");
            channel.sendMessage(builder.toString()).queue();
        }
    }

    @Override
    public String getName() {
        return "realyusufismail_github";
    }

    @Override
    public String getHelp() {
        return "Shows Github link";
    }
}
package net.yusuf.bot.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.yusuf.bot.CommandManager;
import net.yusuf.bot.Config;
import net.yusuf.bot.VeryBadDesign;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandList implements ICommand  {
    private final CommandManager manager;


    public CommandList(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        GuildMessageReceivedEvent event = ctx.getEvent();
        User sender = ctx.getAuthor();
        Message message = ctx.getMessage();

        if(args.isEmpty()) {
            EmbedBuilder builder = new EmbedBuilder();
            //StringBuilder builder = new StringBuilder();
            String prefix = VeryBadDesign.PREFIXES.get(ctx.getGuild().getIdLong());

            builder.setAuthor("Made by " + message.getMember().getEffectiveName(), null, sender.getEffectiveAvatarUrl());
            builder.setTitle("List of commands\n");
            manager.getCommands().stream().map(ICommand::getName).forEach(
                    (it) -> builder.appendDescription("`")
                            .appendDescription(prefix)
                            .appendDescription(it)
                            .appendDescription("`\n")
            );
            builder.setColor(0x34d8eb);
            channel.sendMessageEmbeds(builder.build()).queue();
            return;
        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if(command == null) {
            channel.sendMessage("Nothing found for " + search).queue();
            return;
        }

        channel.sendMessage(command.getHelp()).queue();
    }

    @Override
    public String getName() {
        return "commands";
    }



    @Override
    public String getHelp() {
        return "Shows the list with commands in the bot\n" +
                "Usage: `&commands [command]`";
    }

    @Override
    public List<String> getAliases() {
        return List.of("cmds","commandlist");
    }
}

package net.yusuf.bot.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;
import org.menudocs.paste.PasteClient;
import org.menudocs.paste.PasteClientBuilder;

import java.util.List;

public class PasteCommand implements ICommand {
    private final PasteClient client = new PasteClientBuilder()
            .setUserAgent("Yusuf Arfan Ismail Moderation Bot")
            .setDefaultExpiry("2880m")
            .build();

    @Override
    public void handle(CommandContext ctx) {
            final List<String> args = ctx.getArgs();
            final TextChannel channel = ctx.getChannel();

            if (args.size() < 2) {
                channel.sendMessage("Missing arguments").queue();
                return;
            }

            final String language = args.get(0);
            final String contentRaw = ctx.getMessage().getContentRaw();
            final int index = contentRaw.indexOf(language) + language.length();
            final String body = contentRaw.substring(index).trim();

            client.createPaste(language, body).async(
                    (id) -> client.getPaste(id).async((paste) -> {
                        EmbedBuilder builder = new EmbedBuilder()
                                .setTitle("Paste " + id, paste.getPasteUrl())
                                .setDescription("```")
                                .appendDescription(paste.getLanguage().getId())
                                .appendDescription("\n")
                                .appendDescription(paste.getBody())
                                .appendDescription("```");

                        channel.sendMessage(builder.build()).queue();
                    })
            );
    }

    @Override
    public String getName() {
        return "paste";
    }

    @Override
    public String getHelp() {
        return "Creates a paste on the bots paste\n" +
                "Usage: `&paste [language] [text]`";
    }
}

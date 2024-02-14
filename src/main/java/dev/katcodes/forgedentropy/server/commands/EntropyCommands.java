package dev.katcodes.forgedentropy.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.ChaosEventRegistry;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

public class EntropyCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("entropy").then(Commands.literal("start").then(Commands.argument("event", StringArgumentType.word())
                        .suggests(((context, builder) -> SharedSuggestionProvider.suggest(ChaosEventRegistry.EntropyEvents.keySet(),builder)))
                .executes(EntropyCommands::execute))));
    }

    private static int execute(CommandContext<CommandSourceStack> command) {
        String eventName = StringArgumentType.getString(command,"event");
        if(ForgedEntropyMod.eventHandler.runEvent(ChaosEventRegistry.get(eventName))) {
            command.getSource().sendSuccess(()->Component.translatable("forged_entropy.chat.new_event",eventName),true);
        } else {
            command.getSource().sendFailure(Component.translatable("forged_entropy.chat.fail_event",eventName));
        }
        return Command.SINGLE_SUCCESS;
    }
}

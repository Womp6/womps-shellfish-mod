package womp.shellfishmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public class PassiveShellfishCommand {

    public PassiveShellfishCommand(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(Commands.literal("passiveshellfish").then(Commands.argument("value", BoolArgumentType.bool())
                .executes(context -> setBoolean(context, BoolArgumentType.getBool(context, "value")))));
    }

    private static int setBoolean(CommandContext<CommandSourceStack> context, boolean value) {
        CommandSourceStack source = context.getSource();
        ServerLevel world = source.getLevel();

        PassiveShellfishState passiveShellfishState = world.getDataStorage().computeIfAbsent(PassiveShellfishState.TYPE, "passiveshellfish");

        passiveShellfishState.setValue(value);
        context.getSource().sendSuccess(() -> Component.translatable(value ? "shellfish.passive.true" : "shellfish.passive.false"), true);

        return 1;
    }
}

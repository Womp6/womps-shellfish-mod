package womp.shellfishmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.PersistentStateManager;

public class PassiveShellfishCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {

        dispatcher.register(CommandManager.literal("passiveshellfish").then(CommandManager.argument("value", BoolArgumentType.bool())
        .executes(context -> setBoolean(context, BoolArgumentType.getBool(context, "value")))));
    }

    private static int setBoolean(CommandContext<ServerCommandSource> context, boolean value) {
        ServerCommandSource source = context.getSource();
        ServerWorld world = source.getWorld();

        PersistentStateManager stateManager = world.getPersistentStateManager();
        PassiveShellfishState shellfishState = stateManager.getOrCreate(
            PassiveShellfishState::fromNbt, 
            () -> new PassiveShellfishState(value), 
            "passiveshellfish"
        );

        shellfishState.setValue(value);
        context.getSource().sendFeedback(() -> Text.translatable(value ? "shellfish.passive.true" : "shellfish.passive.false"), true);

        return 1;
    }
}

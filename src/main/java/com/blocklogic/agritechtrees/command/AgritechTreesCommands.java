package com.blocklogic.agritechtrees.command;

import com.blocklogic.agritechtrees.config.AgritechTreesConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class AgritechTreesCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("agritechtrees")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("reload")
                                .executes(context -> {
                                    try {
                                        AgritechTreesConfig.loadConfig();
                                        context.getSource().sendSuccess(() ->
                                                Component.literal("AgriTech Trees sapling config reloaded successfully!"), true);
                                        return 1;
                                    } catch (Exception e) {
                                        context.getSource().sendFailure(
                                                Component.literal("Failed to reload AgriTech Trees sapling config: " + e.getMessage()));
                                        return 0;
                                    }
                                })
                        )
        );
    }
}

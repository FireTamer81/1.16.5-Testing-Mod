package io.github.FireTamer81;

import com.mojang.brigadier.CommandDispatcher;

import io.github.FireTamer81.TeleportCommandStuffTest1.TeleportWithDimensionCommand;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class RegisterCommandEvent 
{
	@SubscribeEvent
	public static void onRegisterCommandEvent(RegisterCommandsEvent event) 
	{
		CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
		
		YouShallNotPassCommandTest.register(commandDispatcher);
		TeleportWithDimensionCommand.register(commandDispatcher);
		
	}
}

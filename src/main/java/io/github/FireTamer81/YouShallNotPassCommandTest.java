package io.github.FireTamer81;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class YouShallNotPassCommandTest 
{
	public static void register(CommandDispatcher<CommandSource> dispatcher) 
	{
		LiteralArgumentBuilder<CommandSource> youShallNotPassCommandTest = Commands.literal("IWantToPass")
				.requires((commandSource) -> commandSource.hasPermission(1))
				.then(Commands.literal("goHere")
						.executes(commandContext -> sendMessage(commandContext, "You... Shall Not... PASSSS!!!")))
				.executes(commandContext -> sendMessage(commandContext, "Dimensional Travel is Illegal")); //If "goHere" wasn't placed after "IWantToPass", this will be displayed.
		
		
		
		dispatcher.register(youShallNotPassCommandTest);
	}
	
	
	
	static int sendMessage(CommandContext<CommandSource> commandContext, String message) throws CommandSyntaxException 
	{
		TranslationTextComponent finalText = new TranslationTextComponent("chat.type.announcement", 
				commandContext.getSource().getDisplayName(), new StringTextComponent(message));
		
		Entity entity = commandContext.getSource().getEntity();
		
		if (entity != null) 
		{
			commandContext.getSource().getServer().getPlayerList().broadcastMessage(finalText, ChatType.CHAT, entity.getUUID());
		} else {
			commandContext.getSource().getServer().getPlayerList().broadcastMessage(finalText, ChatType.SYSTEM, Util.NIL_UUID);
		} 
		
		return 1;
	}
}

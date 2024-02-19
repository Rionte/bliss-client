package rionte.bliss.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import rionte.bliss.listeners;

public class KillauraCommand extends CommandBase {

	public static boolean active = false;
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
	
	@Override
	public String getCommandName() {
		return "killaura";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0) {
			active = !active;
			if (active) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(listeners.prefix + EnumChatFormatting.GREEN + "Killaura Activated"));
			} else {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(listeners.prefix + EnumChatFormatting.RED + "Killaura Deactivated"));
			}
		} else {
			/* if (args[0].equalsIgnoreCase("airstrafe")) {
				airstrafe = !airstrafe;
				if (airstrafe) {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(listeners.prefix + EnumChatFormatting.GREEN + "Air Strafe Activated"));
				} else {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(listeners.prefix + EnumChatFormatting.RED + "Air Strafe Deactivated"));
				}
			} */
		}
	}
	
}
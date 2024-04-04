package rionte.bliss.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import rionte.bliss.listeners;

public class SaveCommand extends CommandBase {

	public static boolean active = false;
	static Minecraft mc = Minecraft.getMinecraft();
	
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
		return "sv";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		mc.thePlayer.sendChatMessage(".cfg save " + SwapCommand.current);
		listeners.gameprint(listeners.prefix + "Saved config \"" + SwapCommand.current + "\"!");
	}
}
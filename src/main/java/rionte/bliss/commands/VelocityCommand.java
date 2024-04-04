package rionte.bliss.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import rionte.bliss.listeners;

public class VelocityCommand extends CommandBase {

	public static int horizontal;
	public static int vertical;
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
		return "v";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args[0].equalsIgnoreCase("h")) {
			horizontal = Integer.valueOf(args[1]);
			mc.thePlayer.sendChatMessage(".velocity horizontal " + horizontal);
			listeners.gameprint(listeners.prefix + "Horizontal velocity set to " + horizontal);
		} else if (args[0].equalsIgnoreCase("v")) {
			vertical = Integer.valueOf(args[1]);
			mc.thePlayer.sendChatMessage(".velocity vertical " + vertical);
			listeners.gameprint(listeners.prefix + "Vertical velocity set to " + vertical);
		}
	}
}
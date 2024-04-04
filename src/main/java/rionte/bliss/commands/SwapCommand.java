package rionte.bliss.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import rionte.bliss.listeners;

public class SwapCommand extends CommandBase {

	public static String first = "hypixel";
	public static String second = "hypixell";
	public static String current = first;
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
		return "b";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("first")) {
				first = args[1];
				listeners.gameprint(listeners.prefix + "First config set to \"" + first + "\"!");
				if (current == first) listeners.toDisplay.put("config", current);
			} else if (args[0].equalsIgnoreCase("second")) {
				second = args[1];
				if (current == second) listeners.toDisplay.put("config", current);
				listeners.gameprint(listeners.prefix + "Second config set to \"" + second + "\"!");
			}
		} else {
			if (current == first) {
				current = second;
			} else {
				current = first;
			}
			
			mc.thePlayer.sendChatMessage(".cfg load " + current);
			listeners.toDisplay.put("config", current);
			listeners.gameprint(listeners.prefix + "Loaded \"" + current + "\" config!");
		}
	}
	
}
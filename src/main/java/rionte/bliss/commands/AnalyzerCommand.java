package rionte.bliss.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import rionte.bliss.listeners;

public class AnalyzerCommand extends CommandBase {

	public static boolean active = false;
	public static int delay = 200;
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
		return "a";
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
				listeners.toDisplay.put("analyzer", String.valueOf(delay));
				listeners.gameprint(listeners.prefix + "Analyzer " + EnumChatFormatting.GREEN + "Enabled");
			} else {
				listeners.toDisplay.remove("analyzer");
				listeners.gameprint(listeners.prefix + "Analyzer " + EnumChatFormatting.RED + "Disabled");
			}
		} else {
			if (args[0].equalsIgnoreCase("delay")) {
				delay = Integer.valueOf(args[1]);
				listeners.gameprint(listeners.prefix + "Delay set to " + EnumChatFormatting.YELLOW + delay);
			}
		}
		
		if (listeners.toDisplay.containsKey("analyzer")) { 
			listeners.toDisplay.put("analyzer", String.valueOf(delay));
		}
	}
}
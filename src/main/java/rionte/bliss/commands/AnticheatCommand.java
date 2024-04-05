package rionte.bliss.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import rionte.bliss.listeners;

public class AnticheatCommand extends CommandBase {

	public static boolean active = false;
	public static boolean scaffoldCheck = true;
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
		return "anticheat";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("scaffold")) {
				scaffoldCheck = !scaffoldCheck;
				if (scaffoldCheck) {
					listeners.gameprint(listeners.prefix + "Scaffold Check " + EnumChatFormatting.GREEN + "Enabled");
				} else {
					listeners.gameprint(listeners.prefix + "Scaffold Check " + EnumChatFormatting.RED + "Disabled");
				}
			}
		} else {
			active = !active;
			if (active) {
				listeners.toDisplay.put("anticheat", "all");
				listeners.gameprint(listeners.prefix + "Anticheat " + EnumChatFormatting.GREEN + "Enabled");
			} else {
				listeners.toDisplay.remove("anticheat");
				listeners.gameprint(listeners.prefix + "Anticheat " + EnumChatFormatting.RED + "Disabled");
			}
		}
	}
}
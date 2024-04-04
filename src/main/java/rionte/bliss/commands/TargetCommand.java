package rionte.bliss.commands;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import rionte.bliss.listeners;

public class TargetCommand extends CommandBase {

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
		return "t";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("a")) {
				if (listeners.targetList.contains(args[1])) {
					listeners.gameprint(listeners.prefix + args[1] + " is already in the target list");
				} else {
					listeners.targetList.add(args[1]);
					listeners.anotifs.put(args[1], new ArrayList<Item>());
					mc.thePlayer.sendChatMessage(".target add " + args[1]);
					listeners.gameprint(listeners.prefix + "Added " + args[1] + " to the target list");
				}
			} else if (args[0].equalsIgnoreCase("r")) {
				if (listeners.targetList.contains(args[1])) {
					listeners.targetList.remove(listeners.targetList.indexOf(args[1]));
					listeners.anotifs.remove(args[1]);
					mc.thePlayer.sendChatMessage(".target remove " + args[1]);
					listeners.gameprint(listeners.prefix + "Removed " + args[1] + " from the target list");
				} else {
					listeners.gameprint(listeners.prefix + args[1] + " is not in the target list");
				}
			} else if (args[0].equalsIgnoreCase("c")) {
				for (String i : listeners.targetList) {
					mc.thePlayer.sendChatMessage(".target remove " + i);
				}
				listeners.targetList = new ArrayList<String>();
				listeners.anotifs = new HashMap<String, ArrayList<Item>>();
				listeners.gameprint(listeners.prefix + "Target list cleared");
			} else if (args[0].equalsIgnoreCase("l")) {
				if (listeners.targetList.isEmpty()) {
					listeners.gameprint(listeners.prefix + "Target list is empty");
				} else {
					String print = "Target list: ";
					for (String target : listeners.targetList) {
						if (target == listeners.targetList.get(listeners.targetList.size() - 1)) {
							print += EnumChatFormatting.RED + target;
						} else {
							print += EnumChatFormatting.RED + target + EnumChatFormatting.WHITE + ", ";
						}
					}
					listeners.gameprint(listeners.prefix + print);
				}
			}
		}
	}
}
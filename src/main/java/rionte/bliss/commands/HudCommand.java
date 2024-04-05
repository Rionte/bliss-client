package rionte.bliss.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import rionte.bliss.listeners;

public class HudCommand extends CommandBase {

	public static String shoutmsg;
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
		return "hud";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
		    if (args[0].equals("rename")) {
		        listeners.cname = "";
		        for (int i = 1; i < args.length; i++) {
		            listeners.cname += args[i] + " ";
		        }
		        listeners.gameprint(listeners.prefix + "Successfully renamed client");
		    } else if (args[0].equals("color") || args[0].equals("colour")) {
		    	String colour = args[1];
		    	int colorValue = Integer.parseInt(colour, 16);
		    	listeners.ccolour = colorValue;
		    	listeners.gameprint(listeners.prefix + "Successfully changed color");
		    } else {
		        listeners.gameprint(listeners.prefix + EnumChatFormatting.RED + "Failed to edit HUD");
		    }
		} else {
	        listeners.gameprint(listeners.prefix + EnumChatFormatting.RED + "Failed to edit HUD");
	    }
	}
}
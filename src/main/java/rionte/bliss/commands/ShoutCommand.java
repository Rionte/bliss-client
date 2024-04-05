package rionte.bliss.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import rionte.bliss.listeners;

public class ShoutCommand extends CommandBase {

	public static String shoutmsg = "";
	static Minecraft mc = Minecraft.getMinecraft();
	public static boolean shoutbind = false;
	
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
		return "s";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
		    if (args[0].equals("bind")) {
		        shoutbind = !shoutbind;
		    	listeners.gameprint(listeners.prefix + "Shout bind is now " + (shoutbind ? EnumChatFormatting.GREEN + "on" : EnumChatFormatting.RED + "off"));
		    } else {
		    	for (String s : args) {
		    		shoutmsg += s + " ";
		    	}
		    	listeners.gameprint(listeners.prefix + "Successfully changed shout message");
		    }
		} else {
		    mc.thePlayer.sendChatMessage("/shout " + shoutmsg);
		}
	}
}
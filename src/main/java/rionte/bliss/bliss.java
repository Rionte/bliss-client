package rionte.bliss;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import rionte.bliss.commands.AnalyzerCommand;
import rionte.bliss.commands.AnticheatCommand;
import rionte.bliss.commands.HudCommand;
import rionte.bliss.commands.SaveCommand;
import rionte.bliss.commands.ShoutCommand;
import rionte.bliss.commands.SwapCommand;
import rionte.bliss.commands.TargetCommand;
import rionte.bliss.commands.VelocityCommand;

@Mod(modid = reference.MODID, name = reference.NAME, version = reference.VERSION)
public class bliss {
	
	@Mod.Instance("bls")
	public static bliss instance;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new SwapCommand());
		ClientCommandHandler.instance.registerCommand(new VelocityCommand());
		ClientCommandHandler.instance.registerCommand(new SaveCommand());
		ClientCommandHandler.instance.registerCommand(new ShoutCommand());
		ClientCommandHandler.instance.registerCommand(new AnalyzerCommand());
		ClientCommandHandler.instance.registerCommand(new TargetCommand());
		ClientCommandHandler.instance.registerCommand(new AnticheatCommand());
		ClientCommandHandler.instance.registerCommand(new HudCommand());
		MinecraftForge.EVENT_BUS.register(new listeners());
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		
	}

}
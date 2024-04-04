package rionte.bliss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import rionte.bliss.commands.AnalyzerCommand;
import rionte.bliss.commands.AnticheatCommand;

public class listeners {
	
	public static String prefix = EnumChatFormatting.DARK_AQUA + "[" +  EnumChatFormatting.AQUA + "R" + EnumChatFormatting.DARK_AQUA + "] " +  EnumChatFormatting.WHITE;
	public static int totalTicks = 0;
	static Minecraft mc = Minecraft.getMinecraft();
	
	// Analyzer
	public static ArrayList<Item> armorChecks = new ArrayList<Item>(Arrays.asList(Items.iron_boots, Items.diamond_boots, Items.chainmail_boots));
	public static ArrayList<Item> itemChecks = new ArrayList<Item>(Arrays.asList(Items.iron_sword, Items.diamond_sword, Items.ender_pearl));
	public static ArrayList<String> targetList = new ArrayList<String>();
	public static HashMap<String, ArrayList<Item>> anotifs = new HashMap<String, ArrayList<Item>>();
	
	// Anticheat
	public static HashMap<String, ArrayList<BlockPos>> blocksPlaced = new HashMap<String, ArrayList<BlockPos>>();
	public static HashMap<String, HashSet<Float>> scaffoldTargets = new HashMap<String, HashSet<Float>>();
	public static HashMap<String, HashSet<String>> acnotifs = new HashMap<String, HashSet<String>>();
	
	// Arraylist
	FontRenderer fr = mc.fontRendererObj;
	public static HashMap<String, String> toDisplay = new HashMap<String, String>();
	
	public static void gameprint(String s) {
		mc.thePlayer.addChatMessage(new ChatComponentText(s));
	}
	
	public static boolean onlyOneElement(ArrayList<Double> arr) {
	    Collections.sort(arr);
	    return String.valueOf(arr.get(0)) == String.valueOf(arr.get(arr.size() - 1));
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.PlayerTickEvent event) {
		
		if (mc.theWorld == null || mc.thePlayer == null) {
			return;
		}
		
		if (totalTicks % AnalyzerCommand.delay == 0 && !targetList.isEmpty() && AnalyzerCommand.active) {
			for (String target : targetList) {
				if (mc.theWorld.getPlayerEntityByName(target) != null) {
					InventoryPlayer inv = mc.theWorld.getPlayerEntityByName(target).inventory;
					for (int i = 0; i < 9; i++) {
						if (inv.getStackInSlot(i) != null) {
							if (itemChecks.contains(inv.getStackInSlot(i).getItem()) && !anotifs.get(target).contains(inv.getStackInSlot(i).getItem())) {
								gameprint(prefix + EnumChatFormatting.RED + target + EnumChatFormatting.WHITE + " now has " + EnumChatFormatting.ITALIC + inv.getStackInSlot(i).getDisplayName() + "!");
								anotifs.get(target).add(inv.getStackInSlot(i).getItem());
							}
							
						}
					}
					
					for (Item i : itemChecks) {
						if (!inv.hasItem(i) && anotifs.get(target).contains(i)) {
							anotifs.get(target).remove(anotifs.get(target).indexOf(i));
							gameprint(prefix + EnumChatFormatting.RED + target + EnumChatFormatting.WHITE + " no longer has " + EnumChatFormatting.ITALIC + new ItemStack(i).getDisplayName() + "!");
						}
					}
					
					if (mc.theWorld.getPlayerEntityByName(target).getCurrentArmor(0) != null) {
						Item boots = mc.theWorld.getPlayerEntityByName(target).getCurrentArmor(0).getItem();
						if (armorChecks.contains(boots) && !anotifs.get(target).contains(boots)) {
							gameprint(prefix + EnumChatFormatting.RED + target + EnumChatFormatting.WHITE + " now has " + EnumChatFormatting.ITALIC + mc.theWorld.getPlayerEntityByName(target).getCurrentArmor(0).getDisplayName().split(" ")[0] + " armor!");
							anotifs.get(target).add(boots);
						}
					}
				}
			}
		}
		
		if (AnticheatCommand.active && AnticheatCommand.scaffoldCheck) {
			if (!scaffoldTargets.isEmpty()) {
				for (String target : scaffoldTargets.keySet()) {
					scaffoldTargets.get(target).add(mc.theWorld.getPlayerEntityByName(target).getEyeHeight());
					if (blocksPlaced.get(target) != null) {
						if (scaffoldTargets.get(target).size() == 1 && scaffoldTargets.get(target).contains(1.62f) && blocksPlaced.get(target).size() > 3) {
							if (acnotifs.get(target) != null) {
								if (acnotifs.get(target).contains("Scaffold") && totalTicks % 160 == 0) gameprint(prefix + EnumChatFormatting.RED + target + EnumChatFormatting.WHITE + " has failed " + EnumChatFormatting.YELLOW + "Scaffold");
							}
							if (acnotifs.containsKey(target)) {
								acnotifs.get(target).add("Scaffold");
							} else {
								acnotifs.put(target, new HashSet<String>() {{ add("Scaffold"); }});
							}
						}
					}
				}
			}
		}
		
		if (totalTicks % 160 == 0) {
			if (AnticheatCommand.active && AnticheatCommand.scaffoldCheck) {
				for (String a : blocksPlaced.keySet()) {
					if (blocksPlaced.get(a).size() > 5) {
						scaffoldTargets.put(a, new HashSet<Float>());
					} else {
						if (scaffoldTargets.containsKey(a)) {
							scaffoldTargets.remove(a);
						}
					}
				}
				blocksPlaced = new HashMap<String, ArrayList<BlockPos>>();
			}
			acnotifs = new HashMap<String, HashSet<String>>();
		}
		
		/* if (totalTicks % AnalyzerCommand.delay == 0) {
			gameprint(scaffoldTargets.toString());
			gameprint(blocksPlaced.toString());
		} */
		
		totalTicks += 1;
	}
	
	@SubscribeEvent
	public void onRenderTick(RenderGameOverlayEvent.Text e) {
		fr.drawStringWithShadow("bliss client " + EnumChatFormatting.GRAY + "v1.6", 5, 5, 0x84FCFF);
		for (int i = 0; i < toDisplay.size(); i++) {
			fr.drawStringWithShadow(toDisplay.keySet().toArray()[i].toString() + " " + EnumChatFormatting.GRAY + toDisplay.get(toDisplay.keySet().toArray()[i].toString()), 4, 16 + 11*i, 0x84FCFF);
		}
	}
	
	@SubscribeEvent
	public void onChangeWorld(PlayerEvent.PlayerLoggedInEvent e) {
		targetList = new ArrayList<String>();
		anotifs = new HashMap<String, ArrayList<Item>>();
	}
	
	// step 1: array of all blocks placed updated every second
	// step 2: have target array that tracks players shifting between block placements
	// step 3: after 2 seconds without block placements, remove user from target list
	
	@SubscribeEvent
	public void onPlacedBlock(BlockEvent.PlaceEvent e) {
		if (AnticheatCommand.active && AnticheatCommand.scaffoldCheck) {
			String playerName = e.player.getDisplayNameString();
			if (!blocksPlaced.containsKey(playerName)) {
				blocksPlaced.put(playerName, new ArrayList<BlockPos>());
			}
			blocksPlaced.get(playerName).add(e.pos);
		}
	}
}

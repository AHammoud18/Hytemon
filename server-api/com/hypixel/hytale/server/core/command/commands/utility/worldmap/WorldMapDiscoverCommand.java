/*    */ package com.hypixel.hytale.server.core.command.commands.utility.worldmap;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.StringUtil;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.BiomeData;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class WorldMapDiscoverCommand
/*    */   extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 26 */   private static final Message MESSAGE_COMMANDS_WORLD_MAP_ALL_ZONES_DISCOVERED = Message.translation("server.commands.worldmap.allZonesDiscovered");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 32 */   private final OptionalArg<String> zoneArg = withOptionalArg("zone", "server.commands.worldmap.zone.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldMapDiscoverCommand() {
/* 38 */     super("discover", "server.commands.worldmap.discover.desc");
/* 39 */     addAliases(new String[] { "disc" });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 45 */     Map<Short, BiomeData> biomeDataMap = (world.getWorldMapManager().getWorldMapSettings().getSettingsPacket()).biomeDataMap;
/* 46 */     if (biomeDataMap == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 51 */     Set<String> zoneNames = (Set<String>)biomeDataMap.values().stream().map(biomeData -> biomeData.zoneName).collect(Collectors.toSet());
/*    */     
/* 53 */     if (!this.zoneArg.provided(context)) {
/*    */       
/* 55 */       context.sendMessage(Message.translation("server.commands.worldmap.zoneNames")
/* 56 */           .param("zoneNames", zoneNames.toString()));
/*    */       
/* 58 */       Player player = (Player)store.getComponent(ref, Player.getComponentType());
/* 59 */       assert player != null;
/*    */       
/* 61 */       zoneNames.removeAll(player.getPlayerConfigData().getDiscoveredZones());
/* 62 */       context.sendMessage(Message.translation("server.commands.worldmap.zonesNotDiscovered")
/* 63 */           .param("zoneNames", zoneNames.toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 67 */     String zoneName = (String)this.zoneArg.get(context);
/*    */     
/* 69 */     if (zoneName.equalsIgnoreCase("all")) {
/* 70 */       Player player = (Player)store.getComponent(ref, Player.getComponentType());
/* 71 */       assert player != null;
/*    */       
/* 73 */       player.getWorldMapTracker().discoverZones(world, zoneNames);
/* 74 */       context.sendMessage(MESSAGE_COMMANDS_WORLD_MAP_ALL_ZONES_DISCOVERED);
/*    */       
/*    */       return;
/*    */     } 
/* 78 */     if (!zoneNames.contains(zoneName)) {
/* 79 */       context.sendMessage(Message.translation("server.commands.worldmap.zoneNotFound")
/* 80 */           .param("zoneName", zoneName));
/* 81 */       context.sendMessage(Message.translation("server.general.failed.didYouMean")
/* 82 */           .param("choices", StringUtil.sortByFuzzyDistance(zoneName, zoneNames, CommandUtil.RECOMMEND_COUNT).toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 86 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 87 */     assert playerComponent != null;
/*    */     
/* 89 */     boolean added = playerComponent.getWorldMapTracker().discoverZone(world, zoneName);
/* 90 */     if (added) {
/* 91 */       context.sendMessage(Message.translation("server.commands.worldmap.zoneDiscovered")
/* 92 */           .param("zoneName", zoneName));
/*    */     } else {
/* 94 */       context.sendMessage(Message.translation("server.commands.worldmap.zoneAlreadyDiscovered")
/* 95 */           .param("zoneName", zoneName));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\worldmap\WorldMapDiscoverCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
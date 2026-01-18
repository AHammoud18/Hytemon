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
/*    */ public class WorldMapUndiscoverCommand
/*    */   extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 26 */   private static final Message MESSAGE_COMMANDS_WORLD_MAP_ALL_ZONES_REMOVED_FROM_DISCOVERED = Message.translation("server.commands.worldmap.allZonesRemovedFromDiscovered");
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
/*    */   public WorldMapUndiscoverCommand() {
/* 38 */     super("undiscover", "server.commands.worldmap.undiscover.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 44 */     Map<Short, BiomeData> biomeDataMap = (world.getWorldMapManager().getWorldMapSettings().getSettingsPacket()).biomeDataMap;
/* 45 */     if (biomeDataMap == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 50 */     Set<String> zoneNames = (Set<String>)biomeDataMap.values().stream().map(biomeData -> biomeData.zoneName).collect(Collectors.toSet());
/*    */     
/* 52 */     if (!this.zoneArg.provided(context)) {
/*    */       
/* 54 */       context.sendMessage(Message.translation("server.commands.worldmap.zoneNames")
/* 55 */           .param("zoneNames", zoneNames.toString()));
/*    */       
/* 57 */       Player player = (Player)store.getComponent(ref, Player.getComponentType());
/* 58 */       if (player != null) {
/* 59 */         context.sendMessage(Message.translation("server.commands.worldmap.zonesDiscovered")
/* 60 */             .param("zoneNames", player.getPlayerConfigData().getDiscoveredZones().toString()));
/*    */       }
/*    */       
/*    */       return;
/*    */     } 
/* 65 */     String zoneName = (String)this.zoneArg.get(context);
/*    */     
/* 67 */     if ("all".equalsIgnoreCase(zoneName)) {
/* 68 */       Player player = (Player)store.getComponent(ref, Player.getComponentType());
/* 69 */       if (player != null) {
/* 70 */         player.getWorldMapTracker().undiscoverZones(world, zoneNames);
/* 71 */         context.sendMessage(MESSAGE_COMMANDS_WORLD_MAP_ALL_ZONES_REMOVED_FROM_DISCOVERED);
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/* 76 */     if (!zoneNames.contains(zoneName)) {
/* 77 */       context.sendMessage(Message.translation("server.commands.worldmap.zoneNotFound")
/* 78 */           .param("zoneName", zoneName));
/* 79 */       context.sendMessage(Message.translation("server.general.failed.didYouMean")
/* 80 */           .param("choices", StringUtil.sortByFuzzyDistance(zoneName, zoneNames, CommandUtil.RECOMMEND_COUNT).toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 84 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 85 */     if (playerComponent != null) {
/* 86 */       boolean removed = playerComponent.getWorldMapTracker().undiscoverZone(world, zoneName);
/* 87 */       if (removed) {
/* 88 */         context.sendMessage(Message.translation("server.commands.worldmap.zoneRemovedFromDiscovered")
/* 89 */             .param("zoneName", zoneName));
/*    */       } else {
/* 91 */         context.sendMessage(Message.translation("server.commands.worldmap.zoneNotDiscoveredYet")
/* 92 */             .param("zoneName", zoneName));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\worldmap\WorldMapUndiscoverCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
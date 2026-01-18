/*     */ package com.hypixel.hytale.server.core.command.commands.debug;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.common.util.StringUtil;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.io.PacketStatsRecorder;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketStatsCommand
/*     */   extends AbstractTargetPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  31 */   private final RequiredArg<String> packetArg = withRequiredArg("packet", "server.commands.packetStats.packet.desc", (ArgumentType)ArgTypes.STRING);
/*     */   
/*     */   public PacketStatsCommand() {
/*  34 */     super("packetstats", "server.commands.packetStats.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  44 */     String packetName = (String)this.packetArg.get(context);
/*  45 */     PacketHandler packetHandler = playerRef.getPacketHandler();
/*  46 */     PacketStatsRecorder recorder = packetHandler.getPacketStatsRecorder();
/*  47 */     if (recorder == null) {
/*  48 */       context.sendMessage(Message.translation("server.commands.packetStats.notAvailable"));
/*     */       
/*     */       return;
/*     */     } 
/*  52 */     PacketStatsRecorder.PacketStatsEntry entry = findEntry(recorder, packetName);
/*  53 */     if (entry == null) {
/*  54 */       context.sendMessage(Message.translation("server.commands.packetStats.notFound")
/*  55 */           .param("name", packetName));
/*  56 */       context.sendMessage(Message.translation("server.general.failed.didYouMean")
/*  57 */           .param("choices", StringUtil.sortByFuzzyDistance(packetName, getEntryNames(recorder), CommandUtil.RECOMMEND_COUNT).toString()));
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  66 */     Message sentTotal = Message.translation("server.commands.packetStats.row").param("count", entry.getSentCount()).param("size", FormatUtil.bytesToString(entry.getSentCompressedTotal()) + " (" + FormatUtil.bytesToString(entry.getSentCompressedTotal()) + " uncompressed)").param("avg", FormatUtil.bytesToString((long)entry.getSentCompressedAvg())).param("min", FormatUtil.bytesToString(entry.getSentCompressedMin())).param("max", FormatUtil.bytesToString(entry.getSentCompressedMax()));
/*     */     
/*  68 */     PacketStatsRecorder.RecentStats sentRecently = entry.getSentRecently();
/*  69 */     int sentRecentlyCount = sentRecently.count();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     Message sentRecent = Message.translation("server.commands.packetStats.row").param("count", sentRecentlyCount).param("size", FormatUtil.bytesToString(sentRecently.compressedTotal()) + " (" + FormatUtil.bytesToString(sentRecently.compressedTotal()) + " uncompressed)").param("avg", FormatUtil.bytesToString((sentRecentlyCount > 0) ? (sentRecently.compressedTotal() / sentRecentlyCount) : 0L)).param("min", FormatUtil.bytesToString(sentRecently.compressedMin())).param("max", FormatUtil.bytesToString(sentRecently.compressedMax()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     Message receivedTotal = Message.translation("server.commands.packetStats.row").param("count", entry.getReceivedCount()).param("size", FormatUtil.bytesToString(entry.getReceivedCompressedTotal()) + " (" + FormatUtil.bytesToString(entry.getReceivedCompressedTotal()) + " uncompressed)").param("avg", FormatUtil.bytesToString((long)entry.getReceivedCompressedAvg())).param("min", FormatUtil.bytesToString(entry.getReceivedCompressedMin())).param("max", FormatUtil.bytesToString(entry.getReceivedCompressedMax()));
/*     */     
/*  84 */     PacketStatsRecorder.RecentStats receivedRecently = entry.getReceivedRecently();
/*  85 */     int receivedRecentlyCount = receivedRecently.count();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     Message receivedRecent = Message.translation("server.commands.packetStats.row").param("count", receivedRecentlyCount).param("size", FormatUtil.bytesToString(receivedRecently.compressedTotal()) + " (" + FormatUtil.bytesToString(receivedRecently.compressedTotal()) + " uncompressed)").param("avg", FormatUtil.bytesToString((receivedRecentlyCount > 0) ? (receivedRecently.compressedTotal() / receivedRecentlyCount) : 0L)).param("min", FormatUtil.bytesToString(receivedRecently.compressedMin())).param("max", FormatUtil.bytesToString(receivedRecently.compressedMax()));
/*     */     
/*  93 */     context.sendMessage(Message.translation("server.commands.packetStats.stats")
/*  94 */         .param("name", entry.getName())
/*  95 */         .param("id", entry.getPacketId())
/*  96 */         .param("sentTotal", sentTotal)
/*  97 */         .param("sentRecent", sentRecent)
/*  98 */         .param("receivedTotal", receivedTotal)
/*  99 */         .param("receivedRecent", receivedRecent));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static PacketStatsRecorder.PacketStatsEntry findEntry(PacketStatsRecorder recorder, String name) {
/* 104 */     for (int i = 0; i < 512; i++) {
/* 105 */       PacketStatsRecorder.PacketStatsEntry entry = recorder.getEntry(i);
/* 106 */       if (entry.hasData()) {
/* 107 */         String entryName = entry.getName();
/* 108 */         if (entryName != null && name.equalsIgnoreCase(entryName)) {
/* 109 */           return entry;
/*     */         }
/*     */       } 
/*     */     } 
/* 113 */     return null;
/*     */   }
/*     */   
/*     */   private static List<String> getEntryNames(PacketStatsRecorder recorder) {
/* 117 */     ObjectArrayList<String> list = new ObjectArrayList();
/* 118 */     for (int i = 0; i < 512; i++) {
/* 119 */       PacketStatsRecorder.PacketStatsEntry entry = recorder.getEntry(i);
/* 120 */       if (entry.hasData()) {
/* 121 */         String name = entry.getName();
/* 122 */         if (name != null) {
/* 123 */           list.add(name);
/*     */         }
/*     */       } 
/*     */     } 
/* 127 */     return (List<String>)list;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\PacketStatsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
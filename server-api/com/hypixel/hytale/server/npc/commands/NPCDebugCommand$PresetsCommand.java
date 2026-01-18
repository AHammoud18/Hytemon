/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.npc.role.RoleDebugFlags;
/*     */ import java.util.EnumSet;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PresetsCommand
/*     */   extends AbstractCommand
/*     */ {
/*     */   @Nonnull
/*     */   private final OptionalArg<String> presetArg;
/*     */   
/*     */   public PresetsCommand() {
/* 198 */     super("presets", "server.commands.npc.debug.presets.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     this
/* 205 */       .presetArg = withOptionalArg("preset", "server.commands.npc.debug.presets.preset.desc", (ArgumentType)ArgTypes.STRING);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
/* 210 */     if (this.presetArg.provided(context)) {
/* 211 */       String presetName = (String)this.presetArg.get(context);
/* 212 */       if (presetName.isEmpty() || !RoleDebugFlags.havePreset(presetName)) {
/* 213 */         context.sendMessage(Message.translation("server.commands.errors.npc.unknown_debug_preset").param("preset", presetName));
/* 214 */         return null;
/*     */       } 
/* 216 */       EnumSet<RoleDebugFlags> enumSet = RoleDebugFlags.getPreset(presetName);
/* 217 */       String flagString = NPCDebugCommand.getListOfFlags(enumSet).toString();
/* 218 */       context.sendMessage(Message.translation("server.commands.npc.debug.preset.info")
/* 219 */           .param("preset", presetName)
/* 220 */           .param("flags", !flagString.isEmpty() ? flagString : "<None>"));
/* 221 */       return null;
/*     */     } 
/*     */     
/* 224 */     String flags = RoleDebugFlags.getListOfAllFlags(new StringBuilder()).toString();
/* 225 */     String presets = RoleDebugFlags.getListOfAllPresets(new StringBuilder()).toString();
/*     */ 
/*     */ 
/*     */     
/* 229 */     Message message = Message.translation("server.commands.npc.debug.presets.info").param("flags", flags).param("presets", presets);
/* 230 */     context.sendMessage(message);
/* 231 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCDebugCommand$PresetsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
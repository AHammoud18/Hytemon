/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.RoleDebugFlags;
/*     */ import java.util.EnumSet;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NPCDebugCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public NPCDebugCommand() {
/*  34 */     super("debug", "server.commands.npc.debug.desc");
/*  35 */     addSubCommand((AbstractCommand)new ShowCommand());
/*  36 */     addSubCommand((AbstractCommand)new SetCommand());
/*  37 */     addSubCommand((AbstractCommand)new ToggleCommand());
/*  38 */     addSubCommand((AbstractCommand)new DefaultsCommand());
/*  39 */     addSubCommand((AbstractCommand)new ClearCommand());
/*  40 */     addSubCommand(new PresetsCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ShowCommand
/*     */     extends NPCMultiSelectCommandBase
/*     */   {
/*     */     public ShowCommand() {
/*  52 */       super("show", "server.commands.npc.debug.show.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/*  62 */       String flags = NPCDebugCommand.getListOfFlags(npc.getRoleDebugFlags()).toString();
/*  63 */       context.sendMessage(Message.translation("server.commands.npc.debug.currentFlags")
/*  64 */           .param("role", npc.getRoleName())
/*  65 */           .param("flags", !flags.isEmpty() ? flags : "<None>"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SetCommand
/*     */     extends NPCMultiSelectCommandBase
/*     */   {
/*     */     @Nonnull
/*  78 */     private final RequiredArg<String> flagsArg = withRequiredArg("flags", "server.commands.npc.debug.flags.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SetCommand() {
/*  84 */       super("set", "server.commands.npc.debug.set.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/*  94 */       String flagsString = (String)this.flagsArg.get(context);
/*  95 */       EnumSet<RoleDebugFlags> flags = RoleDebugFlags.getFlags(flagsString.split(","));
/*  96 */       NPCDebugCommand.modifyFlags(context, npc, ref, flags, (oldFlags, argFlags) -> argFlags, store);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ToggleCommand
/*     */     extends NPCMultiSelectCommandBase
/*     */   {
/*     */     @Nonnull
/* 109 */     private final RequiredArg<String> flagsArg = withRequiredArg("flags", "server.commands.npc.debug.flags.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToggleCommand() {
/* 115 */       super("toggle", "server.commands.npc.debug.toggle.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 125 */       String flagsString = (String)this.flagsArg.get(context);
/* 126 */       EnumSet<RoleDebugFlags> flags = RoleDebugFlags.getFlags(flagsString.split(","));
/* 127 */       NPCDebugCommand.modifyFlags(context, npc, ref, flags, (oldFlags, argFlags) -> { if (argFlags.isEmpty()) return null;  EnumSet<RoleDebugFlags> newFlags = oldFlags.clone(); for (RoleDebugFlags flag : argFlags) { if (newFlags.contains(flag)) { newFlags.remove(flag); continue; }  newFlags.add(flag); }  return newFlags; }store);
/*     */     }
/*     */   }
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
/*     */   public static class DefaultsCommand
/*     */     extends NPCMultiSelectCommandBase
/*     */   {
/*     */     public DefaultsCommand() {
/* 152 */       super("defaults", "server.commands.npc.debug.defaults.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 162 */       NPCDebugCommand.safeSetRoleDebugFlags(npc, ref, RoleDebugFlags.getPreset("default"), store);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ClearCommand
/*     */     extends NPCMultiSelectCommandBase
/*     */   {
/*     */     public ClearCommand() {
/* 175 */       super("clear", "server.commands.npc.debug.clear.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 185 */       NPCDebugCommand.safeSetRoleDebugFlags(npc, ref, EnumSet.noneOf(RoleDebugFlags.class), store);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PresetsCommand
/*     */     extends AbstractCommand
/*     */   {
/*     */     @Nonnull
/*     */     private final OptionalArg<String> presetArg;
/*     */ 
/*     */     
/*     */     public PresetsCommand() {
/* 198 */       super("presets", "server.commands.npc.debug.presets.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 204 */       this
/* 205 */         .presetArg = withOptionalArg("preset", "server.commands.npc.debug.presets.preset.desc", (ArgumentType)ArgTypes.STRING);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
/* 210 */       if (this.presetArg.provided(context)) {
/* 211 */         String presetName = (String)this.presetArg.get(context);
/* 212 */         if (presetName.isEmpty() || !RoleDebugFlags.havePreset(presetName)) {
/* 213 */           context.sendMessage(Message.translation("server.commands.errors.npc.unknown_debug_preset").param("preset", presetName));
/* 214 */           return null;
/*     */         } 
/* 216 */         EnumSet<RoleDebugFlags> enumSet = RoleDebugFlags.getPreset(presetName);
/* 217 */         String flagString = NPCDebugCommand.getListOfFlags(enumSet).toString();
/* 218 */         context.sendMessage(Message.translation("server.commands.npc.debug.preset.info")
/* 219 */             .param("preset", presetName)
/* 220 */             .param("flags", !flagString.isEmpty() ? flagString : "<None>"));
/* 221 */         return null;
/*     */       } 
/*     */       
/* 224 */       String flags = RoleDebugFlags.getListOfAllFlags(new StringBuilder()).toString();
/* 225 */       String presets = RoleDebugFlags.getListOfAllPresets(new StringBuilder()).toString();
/*     */ 
/*     */ 
/*     */       
/* 229 */       Message message = Message.translation("server.commands.npc.debug.presets.info").param("flags", flags).param("presets", presets);
/* 230 */       context.sendMessage(message);
/* 231 */       return null;
/*     */     }
/*     */   }
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
/*     */   private static void modifyFlags(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull Ref<EntityStore> ref, @Nonnull EnumSet<RoleDebugFlags> flags, @Nonnull BiFunction<EnumSet<RoleDebugFlags>, EnumSet<RoleDebugFlags>, EnumSet<RoleDebugFlags>> flagsModifier, @Nonnull Store<EntityStore> store) {
/* 253 */     EnumSet<RoleDebugFlags> newFlags = flagsModifier.apply(npc.getRoleDebugFlags(), flags);
/* 254 */     if (newFlags == null) {
/*     */       return;
/*     */     }
/* 257 */     safeSetRoleDebugFlags(npc, ref, newFlags, store);
/* 258 */     printNewFlags(npc, context, newFlags);
/*     */   }
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
/*     */   private static void safeSetRoleDebugFlags(@Nonnull NPCEntity npc, @Nonnull Ref<EntityStore> ref, @Nonnull EnumSet<RoleDebugFlags> flags, @Nonnull Store<EntityStore> store) {
/* 274 */     store.tryRemoveComponent(ref, Nameplate.getComponentType());
/* 275 */     npc.setRoleDebugFlags(flags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void printNewFlags(@Nonnull NPCEntity npc, @Nonnull CommandContext context, @Nonnull EnumSet<RoleDebugFlags> newFlags) {
/* 286 */     String flags = getListOfFlags(newFlags).toString();
/* 287 */     context.sendMessage(Message.translation("server.commands.npc.debug.debugFlagsSet")
/* 288 */         .param("role", npc.getRoleName())
/* 289 */         .param("flags", !flags.isEmpty() ? flags : "<None>"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static StringBuilder getListOfFlags(@Nonnull EnumSet<RoleDebugFlags> flags) {
/* 300 */     return RoleDebugFlags.getListOfFlags(flags, new StringBuilder());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCDebugCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
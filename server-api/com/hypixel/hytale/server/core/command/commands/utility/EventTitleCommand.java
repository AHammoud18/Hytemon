/*     */ package com.hypixel.hytale.server.core.command.commands.utility;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.util.EventTitleUtil;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class EventTitleCommand
/*     */   extends CommandBase
/*     */ {
/*     */   @Nonnull
/*  24 */   private static final Message MESSAGE_COMMANDS_EVENT_TITLE_TITLE_REQUIRED = Message.translation("server.commands.eventtitle.titleRequired");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  30 */   private final FlagArg majorFlag = withFlagArg("major", "server.commands.eventtitle.major.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  36 */   private final DefaultArg<String> secondaryTitleArg = withDefaultArg("secondary", "server.commands.eventtitle.secondary.desc", (ArgumentType)ArgTypes.STRING, "Event", "server.commands.eventtitle.secondary.defaultDesc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  42 */   private final OptionalArg<String> primaryTitleArg = withOptionalArg("title", "server.commands.eventtitle.title.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventTitleCommand() {
/*  48 */     super("eventtitle", "server.commands.eventtitle.desc");
/*  49 */     setAllowsExtraArguments(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/*     */     String primaryTitleText;
/*  57 */     if (this.primaryTitleArg.provided(context)) {
/*  58 */       primaryTitleText = ((String)this.primaryTitleArg.get(context)).replace("\"", "");
/*     */     }
/*     */     else {
/*     */       
/*  62 */       String inputString = context.getInputString();
/*  63 */       String rawArgs = CommandUtil.stripCommandName(inputString);
/*  64 */       if (rawArgs.trim().isEmpty()) {
/*  65 */         context.sendMessage(MESSAGE_COMMANDS_EVENT_TITLE_TITLE_REQUIRED);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  70 */       primaryTitleText = extractTitleFromRawInput(rawArgs, context);
/*  71 */       if (primaryTitleText.trim().isEmpty()) {
/*  72 */         context.sendMessage(MESSAGE_COMMANDS_EVENT_TITLE_TITLE_REQUIRED);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  77 */     Message primaryTitle = Message.raw(primaryTitleText);
/*  78 */     Message secondaryTitle = Message.raw((String)this.secondaryTitleArg.get(context));
/*  79 */     boolean isMajor = ((Boolean)this.majorFlag.get(context)).booleanValue();
/*     */ 
/*     */     
/*  82 */     for (World world : Universe.get().getWorlds().values()) {
/*  83 */       for (PlayerRef playerRef : world.getPlayerRefs()) {
/*  84 */         EventTitleUtil.showEventTitleToPlayer(playerRef, primaryTitle, secondaryTitle, isMajor);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private String extractTitleFromRawInput(@Nonnull String rawArgs, @Nonnull CommandContext context) {
/*  94 */     String titleText = rawArgs.trim();
/*     */ 
/*     */     
/*  97 */     if (((Boolean)this.majorFlag.get(context)).booleanValue()) {
/*  98 */       titleText = titleText.replaceAll("--major\\b", "").trim();
/*     */     }
/*     */ 
/*     */     
/* 102 */     if (this.secondaryTitleArg.provided(context)) {
/* 103 */       String secondaryValue = (String)this.secondaryTitleArg.get(context);
/*     */       
/* 105 */       titleText = titleText.replaceAll("--secondary\\s*=\\s*" + Pattern.quote(secondaryValue), "");
/* 106 */       titleText = titleText.replaceAll("--secondary\\s+" + Pattern.quote(secondaryValue), "");
/*     */     } else {
/*     */       
/* 109 */       titleText = titleText.replaceAll("--secondary\\s*=\\s*[^\\s]+", "");
/* 110 */       titleText = titleText.replaceAll("--secondary\\s+[^\\s]+", "");
/*     */     } 
/*     */     
/* 113 */     return titleText.trim();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\EventTitleCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
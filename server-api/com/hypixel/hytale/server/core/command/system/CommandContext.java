/*     */ package com.hypixel.hytale.server.core.command.system;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.AbstractOptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*     */ import com.hypixel.hytale.server.core.command.system.exceptions.SenderTypeException;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Map;
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
/*     */ public final class CommandContext
/*     */ {
/*     */   @Nonnull
/*     */   private final AbstractCommand calledCommand;
/*     */   @Nonnull
/*     */   private final String inputString;
/*     */   @Nonnull
/*     */   private final CommandSender sender;
/*     */   @Nonnull
/*     */   private final Map<Argument<?, ?>, Object> argValues;
/*     */   @Nonnull
/*     */   private final Map<Argument<?, ?>, String[]> argInput;
/*     */   
/*     */   public CommandContext(@Nonnull AbstractCommand calledCommand, @Nonnull CommandSender sender, @Nonnull String inputString) {
/*  67 */     this.calledCommand = calledCommand;
/*  68 */     this.inputString = inputString;
/*  69 */     this.sender = sender;
/*  70 */     this.argValues = (Map<Argument<?, ?>, Object>)new Object2ObjectOpenHashMap();
/*  71 */     this.argInput = (Map<Argument<?, ?>, String[]>)new Object2ObjectOpenHashMap();
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
/*     */   <DataType> void appendArgumentData(@Nonnull Argument<?, DataType> argument, @Nonnull String[] data, boolean asListArgument, @Nonnull ParseResult parseResult) {
/*  86 */     if (data.length == 0 && argument instanceof DefaultArg) { DefaultArg<?> defaultArg = (DefaultArg)argument;
/*     */ 
/*     */       
/*  89 */       this.argValues.put(argument, defaultArg.getDefaultValue());
/*     */       
/*     */       return; }
/*     */     
/*  93 */     int numParameters = argument.getArgumentType().getNumberOfParameters();
/*     */ 
/*     */     
/*  96 */     if ((asListArgument && data.length % numParameters != 0) || (!asListArgument && data.length != numParameters)) {
/*  97 */       parseResult.fail(Message.translation("server.commands.parsing.error.wrongNumberOfParametersForArgument")
/*  98 */           .param("argument", argument.getName())
/*  99 */           .param("expected", argument.getArgumentType().getNumberOfParameters())
/* 100 */           .param("actual", data.length)
/* 101 */           .param("input", String.join(" ", (CharSequence[])data)));
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 108 */     DataType convertedValue = (DataType)argument.getArgumentType().parse(data, parseResult);
/* 109 */     if (parseResult.failed()) {
/*     */       return;
/*     */     }
/* 112 */     argument.validate(convertedValue, parseResult);
/* 113 */     if (parseResult.failed()) {
/*     */       return;
/*     */     }
/* 116 */     this.argValues.put(argument, convertedValue);
/* 117 */     this.argInput.put(argument, data);
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
/*     */   public <DataType> DataType get(@Nonnull Argument<?, DataType> argument) {
/* 134 */     if (!this.argValues.containsKey(argument) && argument instanceof AbstractOptionalArg.DefaultValueArgument) { AbstractOptionalArg.DefaultValueArgument<?> defaultValueArgument = (AbstractOptionalArg.DefaultValueArgument)argument;
/* 135 */       Object defaultValue = defaultValueArgument.getDefaultValue();
/* 136 */       this.argValues.put(argument, defaultValue);
/* 137 */       return (DataType)defaultValue; }
/*     */     
/* 139 */     return (DataType)this.argValues.get(argument);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getInput(@Nonnull Argument<?, ?> argument) {
/* 150 */     return this.argInput.get(argument);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean provided(@Nonnull Argument<?, ?> argument) {
/* 161 */     return this.argValues.containsKey(argument);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getInputString() {
/* 169 */     return this.inputString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(@Nonnull Message message) {
/* 178 */     this.sender.sendMessage(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayer() {
/* 185 */     return this.sender instanceof Player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <T extends CommandSender> T senderAs(@Nonnull Class<T> senderType) {
/*     */     try {
/* 198 */       return senderType.cast(this.sender);
/* 199 */     } catch (ClassCastException e) {
/* 200 */       throw new SenderTypeException(senderType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> senderAsPlayerRef() {
/* 209 */     return ((Player)senderAs(Player.class)).getReference();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CommandSender sender() {
/* 217 */     return this.sender;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AbstractCommand getCalledCommand() {
/* 225 */     return this.calledCommand;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\CommandContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
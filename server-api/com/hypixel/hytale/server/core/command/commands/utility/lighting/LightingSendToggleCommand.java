/*    */ package com.hypixel.hytale.server.core.command.commands.utility.lighting;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import java.util.Objects;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class LightingSendToggleCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/*    */   private final String statusTranslationKey;
/*    */   @Nonnull
/*    */   private final BooleanSupplier getter;
/*    */   @Nonnull
/*    */   private final Consumer<Boolean> setter;
/*    */   @Nonnull
/*    */   private final OptionalArg<Boolean> enabledArg;
/*    */   
/*    */   protected LightingSendToggleCommand(@Nonnull String name, @Nonnull String description, @Nonnull String enabledDesc, @Nonnull String statusTranslationKey, @Nonnull BooleanSupplier getter, @Nonnull Consumer<Boolean> setter) {
/* 65 */     super(name, description);
/* 66 */     this.statusTranslationKey = statusTranslationKey;
/* 67 */     this.getter = getter;
/* 68 */     this.setter = setter;
/* 69 */     this.enabledArg = withOptionalArg("enabled", enabledDesc, (ArgumentType)ArgTypes.BOOLEAN);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 74 */     Boolean enabled = this.enabledArg.provided(context) ? (Boolean)this.enabledArg.get(context) : null;
/* 75 */     Boolean newValue = Objects.<Boolean>requireNonNullElseGet(enabled, () -> Boolean.valueOf(!this.getter.getAsBoolean()));
/* 76 */     this.setter.accept(newValue);
/*    */     
/* 78 */     context.sendMessage(Message.translation(this.statusTranslationKey)
/* 79 */         .param("status", MessageFormat.enabled(newValue.booleanValue())));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\lighting\LightingSendToggleCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.hypixel.hytale.server.core.command.system;
/*    */ 
/*    */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*    */ import com.hypixel.hytale.registry.Registry;
/*    */ import com.hypixel.hytale.server.core.plugin.PluginBase;
/*    */ import java.util.List;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CommandRegistry
/*    */   extends Registry<CommandRegistration> {
/*    */   private final PluginBase plugin;
/*    */   
/*    */   public CommandRegistry(@Nonnull List<BooleanConsumer> registrations, BooleanSupplier precondition, String preconditionMessage, PluginBase plugin) {
/* 15 */     super(registrations, precondition, preconditionMessage, CommandRegistration::new);
/* 16 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CommandRegistration registerCommand(@Nonnull AbstractCommand command) {
/* 27 */     checkPrecondition();
/* 28 */     if (this.plugin != null) command.setOwner((CommandOwner)this.plugin);
/*    */     
/* 30 */     return (CommandRegistration)register(CommandManager.get().register(command));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\CommandRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
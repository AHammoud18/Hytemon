/*    */ package com.hypixel.hytale.server.core.command.system;
/*    */ 
/*    */ import com.hypixel.hytale.registry.Registration;
/*    */ import java.util.function.BooleanSupplier;
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
/*    */ public class CommandRegistration
/*    */   extends Registration
/*    */ {
/*    */   @Nonnull
/*    */   private final AbstractCommand abstractCommand;
/*    */   
/*    */   public CommandRegistration(@Nonnull AbstractCommand command, @Nonnull BooleanSupplier isEnabled, @Nonnull Runnable unregister) {
/* 27 */     super(isEnabled, unregister);
/* 28 */     this.abstractCommand = command;
/*    */   }
/*    */   
/*    */   public CommandRegistration(@Nonnull CommandRegistration registration, @Nonnull BooleanSupplier isEnabled, @Nonnull Runnable unregister) {
/* 32 */     super(isEnabled, unregister);
/* 33 */     this.abstractCommand = registration.abstractCommand;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\CommandRegistration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
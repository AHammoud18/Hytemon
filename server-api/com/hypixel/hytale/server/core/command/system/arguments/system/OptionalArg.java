/*    */ package com.hypixel.hytale.server.core.command.system.arguments.system;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
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
/*    */ public class OptionalArg<DataType>
/*    */   extends AbstractOptionalArg<OptionalArg<DataType>, DataType>
/*    */ {
/*    */   public OptionalArg(@Nonnull AbstractCommand commandRegisteredTo, @Nonnull String name, @Nonnull String description, @Nonnull ArgumentType<DataType> argumentType) {
/* 28 */     super(commandRegisteredTo, name, description, argumentType);
/* 29 */     if (argumentType.getNumberOfParameters() < 1) {
/* 30 */       throw new IllegalArgumentException("Cannot create an Optional Argument with 0 parameters. If you want to have 0 parameters, use Flag Arguments");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected OptionalArg<DataType> getThis() {
/* 37 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Message getUsageMessage() {
/* 43 */     return Message.raw("--").insert(getName()).insert(" ").insert(getArgumentType().getName()).insert(" -> \"").insert(Message.translation(getDescription())).insert("\"");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Message getUsageOneLiner() {
/* 49 */     return Message.raw("[--").insert(getName()).insert("=?]");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\system\OptionalArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
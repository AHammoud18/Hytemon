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
/*    */ 
/*    */ public class RequiredArg<DataType>
/*    */   extends Argument<RequiredArg<DataType>, DataType>
/*    */ {
/*    */   public RequiredArg(@Nonnull AbstractCommand commandRegisteredTo, @Nonnull String name, @Nonnull String description, @Nonnull ArgumentType<DataType> argumentType) {
/* 29 */     super(commandRegisteredTo, name, description, argumentType);
/*    */     
/* 31 */     if (argumentType.getNumberOfParameters() < 1) {
/* 32 */       throw new IllegalArgumentException("Cannot create a Required Argument with 0 parameters.");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Message getUsageMessageWithoutDescription() {
/* 41 */     return Message.raw("<").insert(getName()).insert(":").insert(getArgumentType().getName()).insert(">");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Message getUsageMessage() {
/* 47 */     return Message.raw(getName()).insert(" (").insert(getArgumentType().getName()).insert(") -> \"").insert(Message.translation(getDescription())).insert("\"");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Message getUsageOneLiner() {
/* 54 */     return Message.raw("<").insert(getName()).insert(">");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected RequiredArg<DataType> getThis() {
/* 60 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\system\RequiredArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
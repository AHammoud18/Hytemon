/*    */ package com.hypixel.hytale.server.core.command.system.arguments.system;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.BooleanFlagArgumentType;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FlagArg
/*    */   extends AbstractOptionalArg<FlagArg, Boolean>
/*    */   implements AbstractOptionalArg.DefaultValueArgument<Boolean>
/*    */ {
/*    */   @Nonnull
/* 19 */   private static final BooleanFlagArgumentType BOOLEAN_FLAG_ARGUMENT_TYPE = new BooleanFlagArgumentType();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FlagArg(@Nonnull AbstractCommand commandRegisteredTo, @Nonnull String name, @Nonnull String description) {
/* 29 */     super(commandRegisteredTo, name, description, (ArgumentType<Boolean>)BOOLEAN_FLAG_ARGUMENT_TYPE);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected FlagArg getThis() {
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Boolean getDefaultValue() {
/* 41 */     return Boolean.FALSE;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Message getUsageMessage() {
/* 47 */     return Message.raw("--").insert(getName()).insert(" -> \"").insert(Message.translation(getDescription())).insert("\"");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Message getUsageOneLiner() {
/* 53 */     return Message.raw("[--").insert(getName()).insert("]");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\system\FlagArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
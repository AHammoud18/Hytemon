/*    */ package com.hypixel.hytale.server.core.command.system.arguments.system;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.ParseResult;
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
/*    */ public class DefaultArg<DataType>
/*    */   extends AbstractOptionalArg<DefaultArg<DataType>, DataType>
/*    */   implements AbstractOptionalArg.DefaultValueArgument<DataType>
/*    */ {
/*    */   @Nonnull
/*    */   private final DataType defaultValue;
/*    */   @Nonnull
/*    */   private final String defaultValueDescription;
/*    */   
/*    */   public DefaultArg(@Nonnull AbstractCommand commandRegisteredTo, @Nonnull String name, @Nonnull String description, @Nonnull ArgumentType<DataType> argumentType, @Nonnull DataType defaultValue, @Nonnull String defaultValueDescription) {
/* 47 */     super(commandRegisteredTo, name, description, argumentType);
/*    */     
/* 49 */     this.defaultValue = defaultValue;
/* 50 */     this.defaultValueDescription = defaultValueDescription;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected DefaultArg<DataType> getThis() {
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public final DataType getDefaultValue() {
/* 61 */     return this.defaultValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void validateDefaultValue(@Nonnull ParseResult parseResult) {
/* 70 */     validate(getDefaultValue(), parseResult);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Message getUsageMessage() {
/* 76 */     return Message.raw("--").insert(getName()).insert(" (").insert(getArgumentType().getName()).insert(":default=")
/* 77 */       .insert(Message.translation(getDefaultValueDescription()))
/* 78 */       .insert(") -> \"")
/* 79 */       .insert(Message.translation(getDescription()))
/* 80 */       .insert("\"");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Message getUsageOneLiner() {
/* 86 */     String defaultValueStr = (this.defaultValue == null) ? "?" : this.defaultValue.toString();
/* 87 */     return Message.raw("[--").insert(getName()).insert("=").insert(defaultValueStr).insert("]");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDefaultValueDescription() {
/* 95 */     return this.defaultValueDescription;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\system\DefaultArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
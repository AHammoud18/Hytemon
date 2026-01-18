/*    */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.ParseResult;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ProcessedArgumentType<InputType, OutputType>
/*    */   extends ArgumentType<OutputType>
/*    */ {
/*    */   @Nonnull
/*    */   private final ArgumentType<InputType> inputTypeArgumentType;
/*    */   
/*    */   public ProcessedArgumentType(String name, Message argumentUsage, @Nonnull ArgumentType<InputType> inputTypeArgumentType, @Nullable String... examples) {
/* 19 */     super(name, argumentUsage, inputTypeArgumentType.numberOfParameters, examples);
/* 20 */     this.inputTypeArgumentType = inputTypeArgumentType;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ArgumentType<InputType> getInputTypeArgumentType() {
/* 25 */     return this.inputTypeArgumentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isListArgument() {
/* 30 */     return getInputTypeArgumentType().isListArgument();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public OutputType parse(@Nonnull String[] input, @Nonnull ParseResult parseResult) {
/* 36 */     InputType parsedData = this.inputTypeArgumentType.parse(input, parseResult);
/* 37 */     if (parseResult.failed()) return null;
/*    */     
/* 39 */     OutputType outputType = processInput(parsedData);
/* 40 */     if (parseResult.failed()) return null;
/*    */     
/* 42 */     return outputType;
/*    */   }
/*    */   
/*    */   public abstract OutputType processInput(InputType paramInputType);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\ProcessedArgumentType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
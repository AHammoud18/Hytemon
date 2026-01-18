/*    */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WrappedArgumentType<DataType>
/*    */   extends SingleArgumentType<DataType>
/*    */ {
/*    */   protected final ArgumentType<DataType> wrappedArgumentType;
/*    */   
/*    */   public WrappedArgumentType(Message name, ArgumentType<DataType> wrappedArgumentType, @Nonnull String argumentUsage, @Nullable String... examples) {
/* 19 */     super(name, argumentUsage, examples);
/* 20 */     this.wrappedArgumentType = wrappedArgumentType;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String[] getExamples() {
/* 26 */     if (Arrays.equals((Object[])this.examples, (Object[])EMPTY_EXAMPLES)) {
/* 27 */       return this.wrappedArgumentType.getExamples();
/*    */     }
/* 29 */     return this.examples;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public DataType get(@Nonnull MultiArgumentContext context) {
/* 41 */     return context.get(this);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\WrappedArgumentType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
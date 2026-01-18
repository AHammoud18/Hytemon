/*    */ package com.hypixel.hytale.server.core.command.system.arguments.system;
/*    */ public final class ArgWrapper<W extends WrappedArg<BasicType>, BasicType> extends Record {
/*    */   @Nonnull
/*    */   private final ArgumentType<BasicType> argumentType;
/*    */   @Nonnull
/*    */   private final Function<Argument<?, BasicType>, W> wrappedArgProviderFunction;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/command/system/arguments/system/ArgWrapper;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/command/system/arguments/system/ArgWrapper;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/command/system/arguments/system/ArgWrapper<TW;TBasicType;>;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/command/system/arguments/system/ArgWrapper;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/command/system/arguments/system/ArgWrapper;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/command/system/arguments/system/ArgWrapper<TW;TBasicType;>;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/command/system/arguments/system/ArgWrapper;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/command/system/arguments/system/ArgWrapper;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/command/system/arguments/system/ArgWrapper<TW;TBasicType;>;
/*    */   }
/*    */   
/* 20 */   public ArgWrapper(@Nonnull ArgumentType<BasicType> argumentType, @Nonnull Function<Argument<?, BasicType>, W> wrappedArgProviderFunction) { this.argumentType = argumentType; this.wrappedArgProviderFunction = wrappedArgProviderFunction; } @Nonnull public ArgumentType<BasicType> argumentType() { return this.argumentType; } @Nonnull public Function<Argument<?, BasicType>, W> wrappedArgProviderFunction() { return this.wrappedArgProviderFunction; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public W wrapArg(@Nonnull Argument<?, BasicType> argument) {
/* 30 */     return this.wrappedArgProviderFunction.apply(argument);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\system\ArgWrapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
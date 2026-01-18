/*    */ package com.hypixel.hytale.server.core.command.system.arguments.system;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
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
/*    */ public abstract class WrappedArg<BasicType>
/*    */ {
/*    */   @Nonnull
/*    */   protected final Argument<?, BasicType> arg;
/*    */   
/*    */   public WrappedArg(@Nonnull Argument<?, BasicType> arg) {
/* 32 */     this.arg = arg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean provided(@Nonnull CommandContext context) {
/* 42 */     return this.arg.provided(context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getName() {
/* 50 */     return this.arg.getName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDescription() {
/* 58 */     return this.arg.getDescription();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public <D extends WrappedArg<BasicType>> D addAliases(@Nonnull String... aliases) {
/*    */     AbstractOptionalArg abstractOptionalArg;
/* 70 */     Argument<?, BasicType> argument = this.arg; if (argument instanceof AbstractOptionalArg) { abstractOptionalArg = (AbstractOptionalArg)argument; }
/* 71 */     else { throw new UnsupportedOperationException("You are trying to add aliases to a wrapped arg that is wrapping a RequiredArgument. RequiredArguments do not accept aliases"); }
/*    */ 
/*    */     
/* 74 */     abstractOptionalArg.addAliases(aliases);
/*    */ 
/*    */     
/* 77 */     return (D)this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Argument<?, BasicType> getArg() {
/* 85 */     return this.arg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected BasicType get(@Nonnull CommandContext context) {
/* 96 */     return this.arg.get(context);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\system\WrappedArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
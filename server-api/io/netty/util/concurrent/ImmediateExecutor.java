/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.util.concurrent.Executor;
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
/*    */ public final class ImmediateExecutor
/*    */   implements Executor
/*    */ {
/* 26 */   public static final ImmediateExecutor INSTANCE = new ImmediateExecutor();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(Runnable command) {
/* 34 */     ((Runnable)ObjectUtil.checkNotNull(command, "command")).run();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\ImmediateExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
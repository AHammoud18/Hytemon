/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public final class NoOpSocketTagger
/*    */   implements ISocketTagger {
/*  7 */   private static final NoOpSocketTagger instance = new NoOpSocketTagger();
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static ISocketTagger getInstance() {
/* 12 */     return instance;
/*    */   }
/*    */   
/*    */   public void tagSockets() {}
/*    */   
/*    */   public void untagSockets() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpSocketTagger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
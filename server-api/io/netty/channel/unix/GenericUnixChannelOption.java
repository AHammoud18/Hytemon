/*    */ package io.netty.channel.unix;
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
/*    */ public abstract class GenericUnixChannelOption<T>
/*    */   extends UnixChannelOption<T>
/*    */ {
/*    */   private final int level;
/*    */   private final int optname;
/*    */   
/*    */   GenericUnixChannelOption(String name, int level, int optname) {
/* 29 */     super(name);
/* 30 */     this.level = level;
/* 31 */     this.optname = optname;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int level() {
/* 40 */     return this.level;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int optname() {
/* 49 */     return this.optname;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\GenericUnixChannelOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
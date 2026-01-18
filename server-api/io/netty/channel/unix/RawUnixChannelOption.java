/*    */ package io.netty.channel.unix;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public final class RawUnixChannelOption
/*    */   extends GenericUnixChannelOption<ByteBuffer>
/*    */ {
/*    */   private final int length;
/*    */   
/*    */   public RawUnixChannelOption(String name, int level, int optname, int length) {
/* 40 */     super(name, level, optname);
/* 41 */     this.length = ObjectUtil.checkPositive(length, "length");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int length() {
/* 50 */     return this.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ByteBuffer value) {
/* 55 */     super.validate(value);
/* 56 */     if (value.remaining() != this.length)
/* 57 */       throw new IllegalArgumentException("Length of value does not match. Expected " + this.length + ", but got " + value
/* 58 */           .remaining()); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\RawUnixChannelOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
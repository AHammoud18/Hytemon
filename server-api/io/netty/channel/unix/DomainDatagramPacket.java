/*    */ package io.netty.channel.unix;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
/*    */ import io.netty.channel.AddressedEnvelope;
/*    */ import io.netty.channel.DefaultAddressedEnvelope;
/*    */ import io.netty.util.ReferenceCounted;
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
/*    */ public final class DomainDatagramPacket
/*    */   extends DefaultAddressedEnvelope<ByteBuf, DomainSocketAddress>
/*    */   implements ByteBufHolder
/*    */ {
/*    */   public DomainDatagramPacket(ByteBuf data, DomainSocketAddress recipient) {
/* 32 */     super(data, recipient);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DomainDatagramPacket(ByteBuf data, DomainSocketAddress recipient, DomainSocketAddress sender) {
/* 40 */     super(data, recipient, sender);
/*    */   }
/*    */ 
/*    */   
/*    */   public DomainDatagramPacket copy() {
/* 45 */     return replace(((ByteBuf)content()).copy());
/*    */   }
/*    */ 
/*    */   
/*    */   public DomainDatagramPacket duplicate() {
/* 50 */     return replace(((ByteBuf)content()).duplicate());
/*    */   }
/*    */ 
/*    */   
/*    */   public DomainDatagramPacket replace(ByteBuf content) {
/* 55 */     return new DomainDatagramPacket(content, (DomainSocketAddress)recipient(), (DomainSocketAddress)sender());
/*    */   }
/*    */ 
/*    */   
/*    */   public DomainDatagramPacket retain() {
/* 60 */     super.retain();
/* 61 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public DomainDatagramPacket retain(int increment) {
/* 66 */     super.retain(increment);
/* 67 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public DomainDatagramPacket retainedDuplicate() {
/* 72 */     return replace(((ByteBuf)content()).retainedDuplicate());
/*    */   }
/*    */ 
/*    */   
/*    */   public DomainDatagramPacket touch() {
/* 77 */     super.touch();
/* 78 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public DomainDatagramPacket touch(Object hint) {
/* 83 */     super.touch(hint);
/* 84 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\DomainDatagramPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
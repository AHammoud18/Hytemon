/*    */ package io.netty.channel.socket;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
/*    */ import io.netty.channel.AddressedEnvelope;
/*    */ import io.netty.channel.DefaultAddressedEnvelope;
/*    */ import io.netty.util.ReferenceCounted;
/*    */ import java.net.InetSocketAddress;
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
/*    */ public class DatagramPacket
/*    */   extends DefaultAddressedEnvelope<ByteBuf, InetSocketAddress>
/*    */   implements ByteBufHolder
/*    */ {
/*    */   public DatagramPacket(ByteBuf data, InetSocketAddress recipient) {
/* 34 */     super(data, recipient);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DatagramPacket(ByteBuf data, InetSocketAddress recipient, InetSocketAddress sender) {
/* 42 */     super(data, recipient, sender);
/*    */   }
/*    */ 
/*    */   
/*    */   public DatagramPacket copy() {
/* 47 */     return replace(((ByteBuf)content()).copy());
/*    */   }
/*    */ 
/*    */   
/*    */   public DatagramPacket duplicate() {
/* 52 */     return replace(((ByteBuf)content()).duplicate());
/*    */   }
/*    */ 
/*    */   
/*    */   public DatagramPacket retainedDuplicate() {
/* 57 */     return replace(((ByteBuf)content()).retainedDuplicate());
/*    */   }
/*    */ 
/*    */   
/*    */   public DatagramPacket replace(ByteBuf content) {
/* 62 */     return new DatagramPacket(content, (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*    */   }
/*    */ 
/*    */   
/*    */   public DatagramPacket retain() {
/* 67 */     super.retain();
/* 68 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public DatagramPacket retain(int increment) {
/* 73 */     super.retain(increment);
/* 74 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public DatagramPacket touch() {
/* 79 */     super.touch();
/* 80 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public DatagramPacket touch(Object hint) {
/* 85 */     super.touch(hint);
/* 86 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\DatagramPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
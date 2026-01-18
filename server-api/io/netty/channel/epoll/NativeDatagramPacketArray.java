/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.channel.unix.IovArray;
/*     */ import io.netty.channel.unix.Limits;
/*     */ import io.netty.channel.unix.NativeInetAddress;
/*     */ import io.netty.channel.unix.SegmentedDatagramPacket;
/*     */ import java.net.Inet6Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.UnknownHostException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class NativeDatagramPacketArray
/*     */ {
/*  41 */   private final NativeDatagramPacket[] packets = new NativeDatagramPacket[Limits.UIO_MAX_IOV];
/*     */ 
/*     */ 
/*     */   
/*  45 */   private final IovArray iovArray = new IovArray();
/*     */ 
/*     */   
/*  48 */   private final byte[] ipv4Bytes = new byte[4];
/*  49 */   private final MyMessageProcessor processor = new MyMessageProcessor();
/*     */   
/*     */   private int count;
/*     */   
/*     */   NativeDatagramPacketArray() {
/*  54 */     for (int i = 0; i < this.packets.length; i++) {
/*  55 */       this.packets[i] = new NativeDatagramPacket();
/*     */     }
/*     */   }
/*     */   
/*     */   boolean addWritable(ByteBuf buf, int index, int len) {
/*  60 */     return add0(buf, index, len, 0, null);
/*     */   }
/*     */   
/*     */   private boolean add0(ByteBuf buf, int index, int len, int segmentLen, InetSocketAddress recipient) {
/*  64 */     if (this.count == this.packets.length)
/*     */     {
/*     */       
/*  67 */       return false;
/*     */     }
/*  69 */     if (len == 0) {
/*  70 */       return true;
/*     */     }
/*  72 */     int offset = this.iovArray.count();
/*  73 */     if (offset == Limits.IOV_MAX || !this.iovArray.add(buf, index, len))
/*     */     {
/*  75 */       return false;
/*     */     }
/*  77 */     NativeDatagramPacket p = this.packets[this.count];
/*  78 */     p.init(this.iovArray.memoryAddress(offset), this.iovArray.count() - offset, segmentLen, recipient);
/*     */     
/*  80 */     this.count++;
/*  81 */     return true;
/*     */   }
/*     */   
/*     */   void add(ChannelOutboundBuffer buffer, boolean connected, int maxMessagesPerWrite) throws Exception {
/*  85 */     this.processor.connected = connected;
/*  86 */     this.processor.maxMessagesPerWrite = maxMessagesPerWrite;
/*  87 */     buffer.forEachFlushedMessage(this.processor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int count() {
/*  94 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NativeDatagramPacket[] packets() {
/* 101 */     return this.packets;
/*     */   }
/*     */   
/*     */   void clear() {
/* 105 */     this.count = 0;
/* 106 */     this.iovArray.clear();
/*     */   }
/*     */   
/*     */   void release() {
/* 110 */     this.iovArray.release();
/*     */   }
/*     */   
/*     */   private final class MyMessageProcessor
/*     */     implements ChannelOutboundBuffer.MessageProcessor
/*     */   {
/*     */     private boolean connected;
/*     */     
/*     */     public boolean processMessage(Object msg) {
/*     */       boolean added;
/* 120 */       if (msg instanceof DatagramPacket) {
/* 121 */         DatagramPacket packet = (DatagramPacket)msg;
/* 122 */         ByteBuf buf = (ByteBuf)packet.content();
/* 123 */         int segmentSize = 0;
/* 124 */         if (packet instanceof SegmentedDatagramPacket) {
/* 125 */           int seg = ((SegmentedDatagramPacket)packet).segmentSize();
/*     */ 
/*     */           
/* 128 */           if (buf.readableBytes() > seg) {
/* 129 */             segmentSize = seg;
/*     */           }
/*     */         } 
/* 132 */         added = NativeDatagramPacketArray.this.add0(buf, buf.readerIndex(), buf.readableBytes(), segmentSize, (InetSocketAddress)packet.recipient());
/* 133 */       } else if (msg instanceof ByteBuf && this.connected) {
/* 134 */         ByteBuf buf = (ByteBuf)msg;
/* 135 */         added = NativeDatagramPacketArray.this.add0(buf, buf.readerIndex(), buf.readableBytes(), 0, null);
/*     */       } else {
/* 137 */         added = false;
/*     */       } 
/* 139 */       if (added) {
/* 140 */         this.maxMessagesPerWrite--;
/* 141 */         return (this.maxMessagesPerWrite > 0);
/*     */       } 
/* 143 */       return false;
/*     */     }
/*     */     private int maxMessagesPerWrite;
/*     */     private MyMessageProcessor() {} }
/*     */   
/*     */   private static InetSocketAddress newAddress(byte[] addr, int addrLen, int port, int scopeId, byte[] ipv4Bytes) throws UnknownHostException {
/*     */     InetAddress address;
/* 150 */     if (addrLen == ipv4Bytes.length) {
/* 151 */       System.arraycopy(addr, 0, ipv4Bytes, 0, addrLen);
/* 152 */       address = InetAddress.getByAddress(ipv4Bytes);
/*     */     } else {
/* 154 */       address = Inet6Address.getByAddress((String)null, addr, scopeId);
/*     */     } 
/* 156 */     return new InetSocketAddress(address, port);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final class NativeDatagramPacket
/*     */   {
/*     */     private long memoryAddress;
/*     */ 
/*     */ 
/*     */     
/*     */     private int count;
/*     */ 
/*     */ 
/*     */     
/* 173 */     private final byte[] senderAddr = new byte[16];
/*     */     
/*     */     private int senderAddrLen;
/*     */     private int senderScopeId;
/*     */     private int senderPort;
/* 178 */     private final byte[] recipientAddr = new byte[16];
/*     */     
/*     */     private int recipientAddrLen;
/*     */     private int recipientScopeId;
/*     */     private int recipientPort;
/*     */     private int segmentSize;
/*     */     
/*     */     private void init(long memoryAddress, int count, int segmentSize, InetSocketAddress recipient) {
/* 186 */       this.memoryAddress = memoryAddress;
/* 187 */       this.count = count;
/* 188 */       this.segmentSize = segmentSize;
/*     */       
/* 190 */       this.senderScopeId = 0;
/* 191 */       this.senderPort = -1;
/* 192 */       this.senderAddrLen = 0;
/*     */       
/* 194 */       if (recipient == null) {
/* 195 */         this.recipientScopeId = 0;
/* 196 */         this.recipientPort = 0;
/* 197 */         this.recipientAddrLen = 0;
/*     */       } else {
/* 199 */         InetAddress address = recipient.getAddress();
/* 200 */         if (address instanceof Inet6Address) {
/* 201 */           System.arraycopy(address.getAddress(), 0, this.recipientAddr, 0, this.recipientAddr.length);
/* 202 */           this.recipientScopeId = ((Inet6Address)address).getScopeId();
/*     */         } else {
/* 204 */           NativeInetAddress.copyIpv4MappedIpv6Address(address.getAddress(), this.recipientAddr);
/* 205 */           this.recipientScopeId = 0;
/*     */         } 
/* 207 */         this.recipientAddrLen = this.recipientAddr.length;
/* 208 */         this.recipientPort = recipient.getPort();
/*     */       } 
/*     */     }
/*     */     
/*     */     boolean hasSender() {
/* 213 */       return (this.senderPort >= 0);
/*     */     }
/*     */     
/*     */     DatagramPacket newDatagramPacket(ByteBuf buffer, InetSocketAddress recipient) throws UnknownHostException {
/* 217 */       InetSocketAddress sender = NativeDatagramPacketArray.newAddress(this.senderAddr, this.senderAddrLen, this.senderPort, this.senderScopeId, NativeDatagramPacketArray.this.ipv4Bytes);
/* 218 */       if (this.recipientAddrLen != 0) {
/* 219 */         recipient = NativeDatagramPacketArray.newAddress(this.recipientAddr, this.recipientAddrLen, this.recipientPort, this.recipientScopeId, NativeDatagramPacketArray.this.ipv4Bytes);
/*     */       }
/*     */ 
/*     */       
/* 223 */       ByteBuf slice = buffer.retainedSlice(buffer.readerIndex(), this.count);
/*     */ 
/*     */       
/* 226 */       if (this.segmentSize > 0) {
/* 227 */         return (DatagramPacket)new SegmentedDatagramPacket(slice, this.segmentSize, recipient, sender);
/*     */       }
/* 229 */       return new DatagramPacket(slice, recipient, sender);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\NativeDatagramPacketArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
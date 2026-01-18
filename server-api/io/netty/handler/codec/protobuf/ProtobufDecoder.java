/*     */ package io.netty.handler.codec.protobuf;
/*     */ 
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.List;
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
/*     */ @Sharable
/*     */ public class ProtobufDecoder
/*     */   extends MessageToMessageDecoder<ByteBuf>
/*     */ {
/*     */   private static final boolean HAS_PARSER;
/*     */   private final MessageLite prototype;
/*     */   private final ExtensionRegistryLite extensionRegistry;
/*     */   
/*     */   static {
/*  72 */     boolean hasParser = false;
/*     */     
/*     */     try {
/*  75 */       MessageLite.class.getDeclaredMethod("getParserForType", new Class[0]);
/*  76 */       hasParser = true;
/*  77 */     } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */     
/*  81 */     HAS_PARSER = hasParser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtobufDecoder(MessageLite prototype) {
/*  91 */     this(prototype, (ExtensionRegistry)null);
/*     */   }
/*     */   
/*     */   public ProtobufDecoder(MessageLite prototype, ExtensionRegistry extensionRegistry) {
/*  95 */     this(prototype, (ExtensionRegistryLite)extensionRegistry);
/*     */   }
/*     */   
/*     */   public ProtobufDecoder(MessageLite prototype, ExtensionRegistryLite extensionRegistry) {
/*  99 */     super(ByteBuf.class);
/* 100 */     this.prototype = ((MessageLite)ObjectUtil.checkNotNull(prototype, "prototype")).getDefaultInstanceForType();
/* 101 */     this.extensionRegistry = extensionRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
/*     */     byte[] array;
/* 109 */     int offset, length = msg.readableBytes();
/* 110 */     if (msg.hasArray()) {
/* 111 */       array = msg.array();
/* 112 */       offset = msg.arrayOffset() + msg.readerIndex();
/*     */     } else {
/* 114 */       array = ByteBufUtil.getBytes(msg, msg.readerIndex(), length, false);
/* 115 */       offset = 0;
/*     */     } 
/*     */     
/* 118 */     if (this.extensionRegistry == null) {
/* 119 */       if (HAS_PARSER) {
/* 120 */         out.add(this.prototype.getParserForType().parseFrom(array, offset, length));
/*     */       } else {
/* 122 */         out.add(this.prototype.newBuilderForType().mergeFrom(array, offset, length).build());
/*     */       }
/*     */     
/* 125 */     } else if (HAS_PARSER) {
/* 126 */       out.add(this.prototype.getParserForType().parseFrom(array, offset, length, this.extensionRegistry));
/*     */     } else {
/*     */       
/* 129 */       out.add(this.prototype.newBuilderForType().mergeFrom(array, offset, length, this.extensionRegistry)
/* 130 */           .build());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\protobuf\ProtobufDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
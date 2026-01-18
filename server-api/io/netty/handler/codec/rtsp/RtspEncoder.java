/*    */ package io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufUtil;
/*    */ import io.netty.handler.codec.UnsupportedMessageTypeException;
/*    */ import io.netty.handler.codec.http.HttpMessage;
/*    */ import io.netty.handler.codec.http.HttpObjectEncoder;
/*    */ import io.netty.handler.codec.http.HttpRequest;
/*    */ import io.netty.handler.codec.http.HttpResponse;
/*    */ import io.netty.util.CharsetUtil;
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
/*    */ public class RtspEncoder
/*    */   extends HttpObjectEncoder<HttpMessage>
/*    */ {
/*    */   private static final int CRLF_SHORT = 3338;
/*    */   
/*    */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 40 */     return (super.acceptOutboundMessage(msg) && (msg instanceof HttpRequest || msg instanceof HttpResponse));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf buf, HttpMessage message) throws Exception {
/* 46 */     if (message instanceof HttpRequest) {
/* 47 */       HttpRequest request = (HttpRequest)message;
/* 48 */       ByteBufUtil.copy(request.method().asciiName(), buf);
/* 49 */       buf.writeByte(32);
/* 50 */       buf.writeCharSequence(request.uri(), CharsetUtil.UTF_8);
/* 51 */       buf.writeByte(32);
/* 52 */       buf.writeCharSequence(request.protocolVersion().toString(), CharsetUtil.US_ASCII);
/* 53 */       ByteBufUtil.writeShortBE(buf, 3338);
/* 54 */     } else if (message instanceof HttpResponse) {
/* 55 */       HttpResponse response = (HttpResponse)message;
/* 56 */       buf.writeCharSequence(response.protocolVersion().toString(), CharsetUtil.US_ASCII);
/* 57 */       buf.writeByte(32);
/* 58 */       ByteBufUtil.copy(response.status().codeAsText(), buf);
/* 59 */       buf.writeByte(32);
/* 60 */       buf.writeCharSequence(response.status().reasonPhrase(), CharsetUtil.US_ASCII);
/* 61 */       ByteBufUtil.writeShortBE(buf, 3338);
/*    */     } else {
/* 63 */       throw new UnsupportedMessageTypeException(message, new Class[] { HttpRequest.class, HttpResponse.class });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\rtsp\RtspEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
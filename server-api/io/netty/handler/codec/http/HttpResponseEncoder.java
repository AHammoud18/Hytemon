/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufUtil;
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
/*    */ public class HttpResponseEncoder
/*    */   extends HttpObjectEncoder<HttpResponse>
/*    */ {
/*    */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 37 */     Class<?> msgClass = msg.getClass();
/* 38 */     if (msgClass == DefaultFullHttpResponse.class || msgClass == DefaultHttpResponse.class) {
/* 39 */       return true;
/*    */     }
/* 41 */     return (super.acceptOutboundMessage(msg) && !(msg instanceof HttpRequest));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf buf, HttpResponse response) throws Exception {
/* 46 */     response.protocolVersion().encode(buf);
/* 47 */     buf.writeByte(32);
/* 48 */     response.status().encode(buf);
/* 49 */     ByteBufUtil.writeShortBE(buf, 3338);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void sanitizeHeadersBeforeEncode(HttpResponse msg, boolean isAlwaysEmpty) {
/* 54 */     if (isAlwaysEmpty) {
/* 55 */       HttpResponseStatus status = msg.status();
/* 56 */       if (status.codeClass() == HttpStatusClass.INFORMATIONAL || status
/* 57 */         .code() == HttpResponseStatus.NO_CONTENT.code()) {
/*    */ 
/*    */ 
/*    */         
/* 61 */         msg.headers().remove((CharSequence)HttpHeaderNames.CONTENT_LENGTH);
/*    */ 
/*    */ 
/*    */         
/* 65 */         msg.headers().remove((CharSequence)HttpHeaderNames.TRANSFER_ENCODING);
/* 66 */       } else if (status.code() == HttpResponseStatus.RESET_CONTENT.code()) {
/*    */ 
/*    */         
/* 69 */         msg.headers().remove((CharSequence)HttpHeaderNames.TRANSFER_ENCODING);
/*    */ 
/*    */ 
/*    */         
/* 73 */         msg.headers().setInt((CharSequence)HttpHeaderNames.CONTENT_LENGTH, 0);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isContentAlwaysEmpty(HttpResponse msg) {
/* 82 */     HttpResponseStatus status = msg.status();
/*    */     
/* 84 */     if (status.codeClass() == HttpStatusClass.INFORMATIONAL) {
/*    */       
/* 86 */       if (status.code() == HttpResponseStatus.SWITCHING_PROTOCOLS.code())
/*    */       {
/*    */ 
/*    */         
/* 90 */         return msg.headers().contains((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_VERSION);
/*    */       }
/* 92 */       return true;
/*    */     } 
/* 94 */     return (status.code() == HttpResponseStatus.NO_CONTENT.code() || status
/* 95 */       .code() == HttpResponseStatus.NOT_MODIFIED.code() || status
/* 96 */       .code() == HttpResponseStatus.RESET_CONTENT.code());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpResponseEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
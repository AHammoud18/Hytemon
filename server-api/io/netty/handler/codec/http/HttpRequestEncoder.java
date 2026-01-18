/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufUtil;
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
/*    */ public class HttpRequestEncoder
/*    */   extends HttpObjectEncoder<HttpRequest>
/*    */ {
/*    */   private static final char SLASH = '/';
/*    */   private static final char QUESTION_MARK = '?';
/*    */   private static final int SLASH_AND_SPACE_SHORT = 12064;
/*    */   private static final int SPACE_SLASH_AND_SPACE_MEDIUM = 2109216;
/*    */   
/*    */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 36 */     return (super.acceptOutboundMessage(msg) && !(msg instanceof HttpResponse));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf buf, HttpRequest request) throws Exception {
/* 41 */     ByteBufUtil.copy(request.method().asciiName(), buf);
/*    */     
/* 43 */     String uri = request.uri();
/*    */     
/* 45 */     if (uri.isEmpty()) {
/*    */ 
/*    */       
/* 48 */       ByteBufUtil.writeMediumBE(buf, 2109216);
/*    */     } else {
/* 50 */       CharSequence uriCharSequence = uri;
/* 51 */       boolean needSlash = false;
/* 52 */       int start = uri.indexOf("://");
/* 53 */       if (start != -1 && uri.charAt(0) != '/') {
/* 54 */         start += 3;
/*    */ 
/*    */         
/* 57 */         int index = uri.indexOf('?', start);
/* 58 */         if (index == -1) {
/* 59 */           if (uri.lastIndexOf('/') < start) {
/* 60 */             needSlash = true;
/*    */           }
/*    */         }
/* 63 */         else if (uri.lastIndexOf('/', index) < start) {
/* 64 */           uriCharSequence = (new StringBuilder(uri)).insert(index, '/');
/*    */         } 
/*    */       } 
/*    */       
/* 68 */       buf.writeByte(32).writeCharSequence(uriCharSequence, CharsetUtil.UTF_8);
/* 69 */       if (needSlash) {
/*    */         
/* 71 */         ByteBufUtil.writeShortBE(buf, 12064);
/*    */       } else {
/* 73 */         buf.writeByte(32);
/*    */       } 
/*    */     } 
/*    */     
/* 77 */     request.protocolVersion().encode(buf);
/* 78 */     ByteBufUtil.writeShortBE(buf, 3338);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpRequestEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
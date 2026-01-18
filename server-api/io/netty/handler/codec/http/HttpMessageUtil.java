/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Map;
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
/*     */ final class HttpMessageUtil
/*     */ {
/*     */   static StringBuilder appendRequest(StringBuilder buf, HttpRequest req) {
/*  28 */     appendCommon(buf, req);
/*  29 */     appendInitialLine(buf, req);
/*  30 */     appendHeaders(buf, req.headers());
/*  31 */     removeLastNewLine(buf);
/*  32 */     return buf;
/*     */   }
/*     */   
/*     */   static StringBuilder appendResponse(StringBuilder buf, HttpResponse res) {
/*  36 */     appendCommon(buf, res);
/*  37 */     appendInitialLine(buf, res);
/*  38 */     appendHeaders(buf, res.headers());
/*  39 */     removeLastNewLine(buf);
/*  40 */     return buf;
/*     */   }
/*     */   
/*     */   private static void appendCommon(StringBuilder buf, HttpMessage msg) {
/*  44 */     buf.append(StringUtil.simpleClassName(msg));
/*  45 */     buf.append("(decodeResult: ");
/*  46 */     buf.append(msg.decoderResult());
/*  47 */     buf.append(", version: ");
/*  48 */     buf.append(msg.protocolVersion());
/*  49 */     buf.append(')');
/*  50 */     buf.append(StringUtil.NEWLINE);
/*     */   }
/*     */   
/*     */   static StringBuilder appendFullRequest(StringBuilder buf, FullHttpRequest req) {
/*  54 */     appendFullCommon(buf, req);
/*  55 */     appendInitialLine(buf, req);
/*  56 */     appendHeaders(buf, req.headers());
/*  57 */     appendHeaders(buf, req.trailingHeaders());
/*  58 */     removeLastNewLine(buf);
/*  59 */     return buf;
/*     */   }
/*     */   
/*     */   static StringBuilder appendFullResponse(StringBuilder buf, FullHttpResponse res) {
/*  63 */     appendFullCommon(buf, res);
/*  64 */     appendInitialLine(buf, res);
/*  65 */     appendHeaders(buf, res.headers());
/*  66 */     appendHeaders(buf, res.trailingHeaders());
/*  67 */     removeLastNewLine(buf);
/*  68 */     return buf;
/*     */   }
/*     */   
/*     */   private static void appendFullCommon(StringBuilder buf, FullHttpMessage msg) {
/*  72 */     buf.append(StringUtil.simpleClassName(msg));
/*  73 */     buf.append("(decodeResult: ");
/*  74 */     buf.append(msg.decoderResult());
/*  75 */     buf.append(", version: ");
/*  76 */     buf.append(msg.protocolVersion());
/*  77 */     buf.append(", content: ");
/*  78 */     buf.append(msg.content());
/*  79 */     buf.append(')');
/*  80 */     buf.append(StringUtil.NEWLINE);
/*     */   }
/*     */   
/*     */   private static void appendInitialLine(StringBuilder buf, HttpRequest req) {
/*  84 */     buf.append(req.method());
/*  85 */     buf.append(' ');
/*  86 */     buf.append(req.uri());
/*  87 */     buf.append(' ');
/*  88 */     buf.append(req.protocolVersion());
/*  89 */     buf.append(StringUtil.NEWLINE);
/*     */   }
/*     */   
/*     */   private static void appendInitialLine(StringBuilder buf, HttpResponse res) {
/*  93 */     buf.append(res.protocolVersion());
/*  94 */     buf.append(' ');
/*  95 */     buf.append(res.status());
/*  96 */     buf.append(StringUtil.NEWLINE);
/*     */   }
/*     */   
/*     */   private static void appendHeaders(StringBuilder buf, HttpHeaders headers) {
/* 100 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)headers) {
/* 101 */       buf.append(e.getKey());
/* 102 */       buf.append(": ");
/* 103 */       buf.append(e.getValue());
/* 104 */       buf.append(StringUtil.NEWLINE);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void removeLastNewLine(StringBuilder buf) {
/* 109 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpMessageUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
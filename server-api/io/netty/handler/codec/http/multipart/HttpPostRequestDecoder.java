/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.handler.codec.DecoderException;
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import io.netty.handler.codec.http.HttpContent;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaderValues;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.nio.charset.Charset;
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
/*     */ public class HttpPostRequestDecoder
/*     */   implements InterfaceHttpPostRequestDecoder
/*     */ {
/*     */   static final int DEFAULT_DISCARD_THRESHOLD = 10485760;
/*     */   static final int DEFAULT_MAX_FIELDS = 128;
/*     */   static final int DEFAULT_MAX_BUFFERED_BYTES = 1024;
/*     */   private final InterfaceHttpPostRequestDecoder decoder;
/*     */   
/*     */   public HttpPostRequestDecoder(HttpRequest request) {
/*  57 */     this(new DefaultHttpDataFactory(16384L), request, HttpConstants.DEFAULT_CHARSET);
/*     */   }
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
/*     */   public HttpPostRequestDecoder(HttpRequest request, int maxFields, int maxBufferedBytes) {
/*  75 */     this(new DefaultHttpDataFactory(16384L), request, HttpConstants.DEFAULT_CHARSET, maxFields, maxBufferedBytes);
/*     */   }
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
/*     */   public HttpPostRequestDecoder(HttpDataFactory factory, HttpRequest request) {
/*  92 */     this(factory, request, HttpConstants.DEFAULT_CHARSET);
/*     */   }
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
/*     */   public HttpPostRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset) {
/* 110 */     ObjectUtil.checkNotNull(factory, "factory");
/* 111 */     ObjectUtil.checkNotNull(request, "request");
/* 112 */     ObjectUtil.checkNotNull(charset, "charset");
/*     */ 
/*     */     
/* 115 */     if (isMultipart(request)) {
/* 116 */       this.decoder = new HttpPostMultipartRequestDecoder(factory, request, charset);
/*     */     } else {
/* 118 */       this.decoder = new HttpPostStandardRequestDecoder(factory, request, charset);
/*     */     } 
/*     */   }
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
/*     */   public HttpPostRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset, int maxFields, int maxBufferedBytes) {
/* 142 */     ObjectUtil.checkNotNull(factory, "factory");
/* 143 */     ObjectUtil.checkNotNull(request, "request");
/* 144 */     ObjectUtil.checkNotNull(charset, "charset");
/*     */ 
/*     */     
/* 147 */     if (isMultipart(request)) {
/* 148 */       this.decoder = new HttpPostMultipartRequestDecoder(factory, request, charset, maxFields, maxBufferedBytes);
/*     */     } else {
/* 150 */       this.decoder = new HttpPostStandardRequestDecoder(factory, request, charset, maxFields, maxBufferedBytes);
/*     */     } 
/*     */   }
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
/*     */   protected enum MultiPartStatus
/*     */   {
/* 185 */     NOTSTARTED, PREAMBLE, HEADERDELIMITER, DISPOSITION, FIELD, FILEUPLOAD, MIXEDPREAMBLE, MIXEDDELIMITER,
/* 186 */     MIXEDDISPOSITION, MIXEDFILEUPLOAD, MIXEDCLOSEDELIMITER, CLOSEDELIMITER, PREEPILOGUE, EPILOGUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMultipart(HttpRequest request) {
/* 194 */     String mimeType = request.headers().get((CharSequence)HttpHeaderNames.CONTENT_TYPE);
/* 195 */     if (mimeType != null && mimeType.startsWith(HttpHeaderValues.MULTIPART_FORM_DATA.toString())) {
/* 196 */       return (getMultipartDataBoundary(mimeType) != null);
/*     */     }
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String[] getMultipartDataBoundary(String contentType) {
/* 208 */     String[] headerContentType = splitHeaderContentType(contentType);
/* 209 */     String multiPartHeader = HttpHeaderValues.MULTIPART_FORM_DATA.toString();
/* 210 */     if (headerContentType[0].regionMatches(true, 0, multiPartHeader, 0, multiPartHeader.length())) {
/*     */       int mrank, crank;
/*     */       
/* 213 */       String boundaryHeader = HttpHeaderValues.BOUNDARY.toString();
/* 214 */       if (headerContentType[1].regionMatches(true, 0, boundaryHeader, 0, boundaryHeader.length())) {
/* 215 */         mrank = 1;
/* 216 */         crank = 2;
/* 217 */       } else if (headerContentType[2].regionMatches(true, 0, boundaryHeader, 0, boundaryHeader.length())) {
/* 218 */         mrank = 2;
/* 219 */         crank = 1;
/*     */       } else {
/* 221 */         return null;
/*     */       } 
/* 223 */       String boundary = StringUtil.substringAfter(headerContentType[mrank], '=');
/* 224 */       if (boundary == null) {
/* 225 */         throw new ErrorDataDecoderException("Needs a boundary value");
/*     */       }
/* 227 */       if (boundary.charAt(0) == '"') {
/* 228 */         String bound = boundary.trim();
/* 229 */         int index = bound.length() - 1;
/* 230 */         if (bound.charAt(index) == '"') {
/* 231 */           boundary = bound.substring(1, index);
/*     */         }
/*     */       } 
/* 234 */       String charsetHeader = HttpHeaderValues.CHARSET.toString();
/* 235 */       if (headerContentType[crank].regionMatches(true, 0, charsetHeader, 0, charsetHeader.length())) {
/* 236 */         String charset = StringUtil.substringAfter(headerContentType[crank], '=');
/* 237 */         if (charset != null) {
/* 238 */           return new String[] { "--" + boundary, charset };
/*     */         }
/*     */       } 
/* 241 */       return new String[] { "--" + boundary };
/*     */     } 
/* 243 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMultipart() {
/* 248 */     return this.decoder.isMultipart();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDiscardThreshold(int discardThreshold) {
/* 253 */     this.decoder.setDiscardThreshold(discardThreshold);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDiscardThreshold() {
/* 258 */     return this.decoder.getDiscardThreshold();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<InterfaceHttpData> getBodyHttpDatas() {
/* 263 */     return this.decoder.getBodyHttpDatas();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<InterfaceHttpData> getBodyHttpDatas(String name) {
/* 268 */     return this.decoder.getBodyHttpDatas(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData getBodyHttpData(String name) {
/* 273 */     return this.decoder.getBodyHttpData(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpPostRequestDecoder offer(HttpContent content) {
/* 278 */     return this.decoder.offer(content);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/* 283 */     return this.decoder.hasNext();
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData next() {
/* 288 */     return this.decoder.next();
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData currentPartialHttpData() {
/* 293 */     return this.decoder.currentPartialHttpData();
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 298 */     this.decoder.destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanFiles() {
/* 303 */     this.decoder.cleanFiles();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeHttpDataFromClean(InterfaceHttpData data) {
/* 308 */     this.decoder.removeHttpDataFromClean(data);
/*     */   }
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
/*     */   private static String[] splitHeaderContentType(String sb) {
/* 323 */     int aStart = HttpPostBodyUtil.findNonWhitespace(sb, 0);
/* 324 */     int aEnd = sb.indexOf(';');
/* 325 */     if (aEnd == -1) {
/* 326 */       return new String[] { sb, "", "" };
/*     */     }
/* 328 */     int bStart = HttpPostBodyUtil.findNonWhitespace(sb, aEnd + 1);
/* 329 */     if (sb.charAt(aEnd - 1) == ' ') {
/* 330 */       aEnd--;
/*     */     }
/* 332 */     int bEnd = sb.indexOf(';', bStart);
/* 333 */     if (bEnd == -1) {
/* 334 */       bEnd = HttpPostBodyUtil.findEndOfString(sb);
/* 335 */       return new String[] { sb.substring(aStart, aEnd), sb.substring(bStart, bEnd), "" };
/*     */     } 
/* 337 */     int cStart = HttpPostBodyUtil.findNonWhitespace(sb, bEnd + 1);
/* 338 */     if (sb.charAt(bEnd - 1) == ' ') {
/* 339 */       bEnd--;
/*     */     }
/* 341 */     int cEnd = HttpPostBodyUtil.findEndOfString(sb);
/* 342 */     return new String[] { sb.substring(aStart, aEnd), sb.substring(bStart, bEnd), sb.substring(cStart, cEnd) };
/*     */   }
/*     */ 
/*     */   
/*     */   public static class NotEnoughDataDecoderException
/*     */     extends DecoderException
/*     */   {
/*     */     private static final long serialVersionUID = -7846841864603865638L;
/*     */ 
/*     */     
/*     */     public NotEnoughDataDecoderException() {}
/*     */ 
/*     */     
/*     */     public NotEnoughDataDecoderException(String msg) {
/* 356 */       super(msg);
/*     */     }
/*     */     
/*     */     public NotEnoughDataDecoderException(Throwable cause) {
/* 360 */       super(cause);
/*     */     }
/*     */     
/*     */     public NotEnoughDataDecoderException(String msg, Throwable cause) {
/* 364 */       super(msg, cause);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class EndOfDataDecoderException
/*     */     extends DecoderException
/*     */   {
/*     */     private static final long serialVersionUID = 1336267941020800769L;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ErrorDataDecoderException
/*     */     extends DecoderException
/*     */   {
/*     */     private static final long serialVersionUID = 5020247425493164465L;
/*     */ 
/*     */     
/*     */     public ErrorDataDecoderException() {}
/*     */     
/*     */     public ErrorDataDecoderException(String msg) {
/* 385 */       super(msg);
/*     */     }
/*     */     
/*     */     public ErrorDataDecoderException(Throwable cause) {
/* 389 */       super(cause);
/*     */     }
/*     */     
/*     */     public ErrorDataDecoderException(String msg, Throwable cause) {
/* 393 */       super(msg, cause);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class TooManyFormFieldsException extends DecoderException {
/*     */     private static final long serialVersionUID = 1336267941020800769L;
/*     */   }
/*     */   
/*     */   public static final class TooLongFormFieldException extends DecoderException {
/*     */     private static final long serialVersionUID = 1336267941020800769L;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\HttpPostRequestDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
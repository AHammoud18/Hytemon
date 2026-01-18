/*    */ package io.netty.handler.codec.http.cookie;
/*    */ 
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ import java.nio.CharBuffer;
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
/*    */ public abstract class CookieDecoder
/*    */ {
/* 32 */   private final InternalLogger logger = InternalLoggerFactory.getInstance(getClass());
/*    */   
/*    */   private final boolean strict;
/*    */   
/*    */   protected CookieDecoder(boolean strict) {
/* 37 */     this.strict = strict;
/*    */   }
/*    */   
/*    */   protected DefaultCookie initCookie(String header, int nameBegin, int nameEnd, int valueBegin, int valueEnd) {
/* 41 */     if (nameBegin == -1 || nameBegin == nameEnd) {
/* 42 */       this.logger.debug("Skipping cookie with null name");
/* 43 */       return null;
/*    */     } 
/*    */     
/* 46 */     if (valueBegin == -1) {
/* 47 */       this.logger.debug("Skipping cookie with null value");
/* 48 */       return null;
/*    */     } 
/*    */     
/* 51 */     CharSequence wrappedValue = CharBuffer.wrap(header, valueBegin, valueEnd);
/* 52 */     CharSequence unwrappedValue = CookieUtil.unwrapValue(wrappedValue);
/* 53 */     if (unwrappedValue == null) {
/* 54 */       this.logger.debug("Skipping cookie because starting quotes are not properly balanced in '{}'", wrappedValue);
/*    */       
/* 56 */       return null;
/*    */     } 
/*    */     
/* 59 */     String name = header.substring(nameBegin, nameEnd);
/*    */     
/*    */     int invalidOctetPos;
/* 62 */     if (this.strict && (invalidOctetPos = CookieUtil.firstInvalidCookieNameOctet(name)) >= 0) {
/* 63 */       if (this.logger.isDebugEnabled()) {
/* 64 */         this.logger.debug("Skipping cookie because name '{}' contains invalid char '{}'", name, 
/* 65 */             Character.valueOf(name.charAt(invalidOctetPos)));
/*    */       }
/* 67 */       return null;
/*    */     } 
/*    */     
/* 70 */     boolean wrap = (unwrappedValue.length() != valueEnd - valueBegin);
/*    */     
/* 72 */     if (this.strict && (invalidOctetPos = CookieUtil.firstInvalidCookieValueOctet(unwrappedValue)) >= 0) {
/* 73 */       if (this.logger.isDebugEnabled()) {
/* 74 */         this.logger.debug("Skipping cookie because value '{}' contains invalid char '{}'", unwrappedValue, 
/* 75 */             Character.valueOf(unwrappedValue.charAt(invalidOctetPos)));
/*    */       }
/* 77 */       return null;
/*    */     } 
/*    */     
/* 80 */     DefaultCookie cookie = new DefaultCookie(name, unwrappedValue.toString());
/* 81 */     cookie.setWrap(wrap);
/* 82 */     return cookie;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\cookie\CookieDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
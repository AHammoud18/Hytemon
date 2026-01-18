/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.handler.codec.Headers;
/*    */ import io.netty.util.AsciiString;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public interface SpdyHeaders
/*    */   extends Headers<CharSequence, CharSequence, SpdyHeaders>
/*    */ {
/*    */   String getAsString(CharSequence paramCharSequence);
/*    */   
/*    */   List<String> getAllAsString(CharSequence paramCharSequence);
/*    */   
/*    */   Iterator<Map.Entry<String, String>> iteratorAsString();
/*    */   
/*    */   boolean contains(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean);
/*    */   
/*    */   public static final class HttpNames
/*    */   {
/* 38 */     public static final AsciiString HOST = AsciiString.cached(":host");
/*    */ 
/*    */ 
/*    */     
/* 42 */     public static final AsciiString METHOD = AsciiString.cached(":method");
/*    */ 
/*    */ 
/*    */     
/* 46 */     public static final AsciiString PATH = AsciiString.cached(":path");
/*    */ 
/*    */ 
/*    */     
/* 50 */     public static final AsciiString SCHEME = AsciiString.cached(":scheme");
/*    */ 
/*    */ 
/*    */     
/* 54 */     public static final AsciiString STATUS = AsciiString.cached(":status");
/*    */ 
/*    */ 
/*    */     
/* 58 */     public static final AsciiString VERSION = AsciiString.cached(":version");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyHeaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
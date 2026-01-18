/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.handler.codec.CharSequenceValueConverter;
/*    */ import io.netty.handler.codec.DefaultHeaders;
/*    */ import io.netty.handler.codec.HeadersUtils;
/*    */ import io.netty.handler.codec.ValueConverter;
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
/*    */ public class DefaultSpdyHeaders
/*    */   extends DefaultHeaders<CharSequence, CharSequence, SpdyHeaders>
/*    */   implements SpdyHeaders
/*    */ {
/* 30 */   private static final DefaultHeaders.NameValidator<CharSequence> SpdyNameValidator = new DefaultHeaders.NameValidator<CharSequence>()
/*    */     {
/*    */       public void validateName(CharSequence name) {
/* 33 */         SpdyCodecUtil.validateHeaderName(name);
/*    */       }
/*    */     };
/*    */   
/*    */   public DefaultSpdyHeaders() {
/* 38 */     this(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public DefaultSpdyHeaders(boolean validate) {
/* 43 */     super(AsciiString.CASE_INSENSITIVE_HASHER, 
/* 44 */         validate ? (ValueConverter)HeaderValueConverterAndValidator.INSTANCE : (ValueConverter)CharSequenceValueConverter.INSTANCE, 
/* 45 */         validate ? SpdyNameValidator : DefaultHeaders.NameValidator.NOT_NULL);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAsString(CharSequence name) {
/* 50 */     return HeadersUtils.getAsString(this, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getAllAsString(CharSequence name) {
/* 55 */     return HeadersUtils.getAllAsString(this, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<Map.Entry<String, String>> iteratorAsString() {
/* 60 */     return HeadersUtils.iteratorAsString((Iterable)this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(CharSequence name, CharSequence value) {
/* 65 */     return contains(name, value, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(CharSequence name, CharSequence value, boolean ignoreCase) {
/* 70 */     return contains(name, value, 
/* 71 */         ignoreCase ? AsciiString.CASE_INSENSITIVE_HASHER : AsciiString.CASE_SENSITIVE_HASHER);
/*    */   }
/*    */   
/*    */   private static final class HeaderValueConverterAndValidator extends CharSequenceValueConverter {
/* 75 */     public static final HeaderValueConverterAndValidator INSTANCE = new HeaderValueConverterAndValidator();
/*    */ 
/*    */     
/*    */     public CharSequence convertObject(Object value) {
/* 79 */       CharSequence seq = super.convertObject(value);
/* 80 */       SpdyCodecUtil.validateHeaderValue(seq);
/* 81 */       return seq;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\DefaultSpdyHeaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
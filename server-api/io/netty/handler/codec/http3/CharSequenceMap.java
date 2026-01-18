/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.handler.codec.DefaultHeaders;
/*    */ import io.netty.handler.codec.UnsupportedValueConverter;
/*    */ import io.netty.handler.codec.ValueConverter;
/*    */ import io.netty.util.AsciiString;
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
/*    */ final class CharSequenceMap<V>
/*    */   extends DefaultHeaders<CharSequence, V, CharSequenceMap<V>>
/*    */ {
/*    */   CharSequenceMap() {
/* 30 */     this(true);
/*    */   }
/*    */   
/*    */   CharSequenceMap(boolean caseSensitive) {
/* 34 */     this(caseSensitive, (ValueConverter<V>)UnsupportedValueConverter.instance());
/*    */   }
/*    */   
/*    */   CharSequenceMap(boolean caseSensitive, ValueConverter<V> valueConverter) {
/* 38 */     super(caseSensitive ? AsciiString.CASE_SENSITIVE_HASHER : AsciiString.CASE_INSENSITIVE_HASHER, valueConverter);
/*    */   }
/*    */ 
/*    */   
/*    */   CharSequenceMap(boolean caseSensitive, ValueConverter<V> valueConverter, int arraySizeHint) {
/* 43 */     super(caseSensitive ? AsciiString.CASE_SENSITIVE_HASHER : AsciiString.CASE_INSENSITIVE_HASHER, valueConverter, DefaultHeaders.NameValidator.NOT_NULL, arraySizeHint);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\CharSequenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
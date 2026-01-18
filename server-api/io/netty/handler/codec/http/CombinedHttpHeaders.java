/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.handler.codec.DefaultHeaders;
/*     */ import io.netty.handler.codec.Headers;
/*     */ import io.netty.handler.codec.ValueConverter;
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.HashingStrategy;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class CombinedHttpHeaders
/*     */   extends DefaultHttpHeaders
/*     */ {
/*     */   @Deprecated
/*     */   public CombinedHttpHeaders(boolean validate) {
/*  53 */     super(new CombinedHttpHeadersImpl(AsciiString.CASE_INSENSITIVE_HASHER, valueConverter(), nameValidator(validate), 
/*  54 */           valueValidator(validate)));
/*     */   }
/*     */   
/*     */   CombinedHttpHeaders(DefaultHeaders.NameValidator<CharSequence> nameValidator, DefaultHeaders.ValueValidator<CharSequence> valueValidator) {
/*  58 */     super(new CombinedHttpHeadersImpl(AsciiString.CASE_INSENSITIVE_HASHER, 
/*     */           
/*  60 */           valueConverter(), 
/*  61 */           (DefaultHeaders.NameValidator<CharSequence>)ObjectUtil.checkNotNull(nameValidator, "nameValidator"), 
/*  62 */           (DefaultHeaders.ValueValidator<CharSequence>)ObjectUtil.checkNotNull(valueValidator, "valueValidator")));
/*     */   }
/*     */ 
/*     */   
/*     */   CombinedHttpHeaders(DefaultHeaders.NameValidator<CharSequence> nameValidator, DefaultHeaders.ValueValidator<CharSequence> valueValidator, int sizeHint) {
/*  67 */     super(new CombinedHttpHeadersImpl(AsciiString.CASE_INSENSITIVE_HASHER, 
/*     */           
/*  69 */           valueConverter(), 
/*  70 */           (DefaultHeaders.NameValidator<CharSequence>)ObjectUtil.checkNotNull(nameValidator, "nameValidator"), 
/*  71 */           (DefaultHeaders.ValueValidator<CharSequence>)ObjectUtil.checkNotNull(valueValidator, "valueValidator"), sizeHint));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(CharSequence name, CharSequence value, boolean ignoreCase) {
/*  77 */     return super.containsValue(name, StringUtil.trimOws(value), ignoreCase);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class CombinedHttpHeadersImpl
/*     */     extends DefaultHeaders<CharSequence, CharSequence, CombinedHttpHeadersImpl>
/*     */   {
/*     */     private static final int VALUE_LENGTH_ESTIMATE = 10;
/*     */     
/*     */     private CsvValueEscaper<Object> objectEscaper;
/*     */     private CsvValueEscaper<CharSequence> charSequenceEscaper;
/*     */     
/*     */     private CsvValueEscaper<Object> objectEscaper() {
/*  90 */       if (this.objectEscaper == null) {
/*  91 */         this.objectEscaper = new CsvValueEscaper()
/*     */           {
/*     */             public CharSequence escape(CharSequence name, Object value) {
/*     */               CharSequence converted;
/*     */               try {
/*  96 */                 converted = (CharSequence)CombinedHttpHeaders.CombinedHttpHeadersImpl.this.valueConverter().convertObject(value);
/*  97 */               } catch (IllegalArgumentException e) {
/*  98 */                 throw new IllegalArgumentException("Failed to convert object value for header '" + name + '\'', e);
/*     */               } 
/*     */               
/* 101 */               return StringUtil.escapeCsv(converted, true);
/*     */             }
/*     */           };
/*     */       }
/* 105 */       return this.objectEscaper;
/*     */     }
/*     */     
/*     */     private CsvValueEscaper<CharSequence> charSequenceEscaper() {
/* 109 */       if (this.charSequenceEscaper == null) {
/* 110 */         this.charSequenceEscaper = new CsvValueEscaper<CharSequence>()
/*     */           {
/*     */             public CharSequence escape(CharSequence name, CharSequence value) {
/* 113 */               return StringUtil.escapeCsv(value, true);
/*     */             }
/*     */           };
/*     */       }
/* 117 */       return this.charSequenceEscaper;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CombinedHttpHeadersImpl(HashingStrategy<CharSequence> nameHashingStrategy, ValueConverter<CharSequence> valueConverter, DefaultHeaders.NameValidator<CharSequence> nameValidator, DefaultHeaders.ValueValidator<CharSequence> valueValidator) {
/* 124 */       this(nameHashingStrategy, valueConverter, nameValidator, valueValidator, 16);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CombinedHttpHeadersImpl(HashingStrategy<CharSequence> nameHashingStrategy, ValueConverter<CharSequence> valueConverter, DefaultHeaders.NameValidator<CharSequence> nameValidator, DefaultHeaders.ValueValidator<CharSequence> valueValidator, int sizeHint) {
/* 132 */       super(nameHashingStrategy, valueConverter, nameValidator, sizeHint, valueValidator);
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<CharSequence> valueIterator(CharSequence name) {
/* 137 */       Iterator<CharSequence> itr = super.valueIterator(name);
/* 138 */       if (!itr.hasNext() || cannotBeCombined(name)) {
/* 139 */         return itr;
/*     */       }
/* 141 */       Iterator<CharSequence> unescapedItr = StringUtil.unescapeCsvFields(itr.next()).iterator();
/* 142 */       if (itr.hasNext()) {
/* 143 */         throw new IllegalStateException("CombinedHttpHeaders should only have one value");
/*     */       }
/* 145 */       return unescapedItr;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<CharSequence> getAll(CharSequence name) {
/* 150 */       List<CharSequence> values = super.getAll(name);
/* 151 */       if (values.isEmpty() || cannotBeCombined(name)) {
/* 152 */         return values;
/*     */       }
/* 154 */       if (values.size() != 1) {
/* 155 */         throw new IllegalStateException("CombinedHttpHeaders should only have one value");
/*     */       }
/* 157 */       return StringUtil.unescapeCsvFields(values.get(0));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl add(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
/* 163 */       if (headers == this) {
/* 164 */         throw new IllegalArgumentException("can't add to itself.");
/*     */       }
/* 166 */       if (headers instanceof CombinedHttpHeadersImpl) {
/* 167 */         if (isEmpty()) {
/*     */           
/* 169 */           addImpl(headers);
/*     */         } else {
/*     */           
/* 172 */           for (Map.Entry<? extends CharSequence, ? extends CharSequence> header : headers) {
/* 173 */             addEscapedValue(header.getKey(), header.getValue());
/*     */           }
/*     */         } 
/*     */       } else {
/* 177 */         for (Map.Entry<? extends CharSequence, ? extends CharSequence> header : headers) {
/* 178 */           add(header.getKey(), header.getValue());
/*     */         }
/*     */       } 
/* 181 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl set(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
/* 186 */       if (headers == this) {
/* 187 */         return this;
/*     */       }
/* 189 */       clear();
/* 190 */       return add(headers);
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl setAll(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
/* 195 */       if (headers == this) {
/* 196 */         return this;
/*     */       }
/* 198 */       for (CharSequence key : headers.names()) {
/* 199 */         remove(key);
/*     */       }
/* 201 */       return add(headers);
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl add(CharSequence name, CharSequence value) {
/* 206 */       return addEscapedValue(name, charSequenceEscaper().escape(name, value));
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl add(CharSequence name, CharSequence... values) {
/* 211 */       return addEscapedValue(name, commaSeparate(name, charSequenceEscaper(), values));
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl add(CharSequence name, Iterable<? extends CharSequence> values) {
/* 216 */       return addEscapedValue(name, commaSeparate(name, charSequenceEscaper(), values));
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl addObject(CharSequence name, Object value) {
/* 221 */       return addEscapedValue(name, commaSeparate(name, objectEscaper(), new Object[] { value }));
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl addObject(CharSequence name, Iterable<?> values) {
/* 226 */       return addEscapedValue(name, commaSeparate(name, objectEscaper(), values));
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl addObject(CharSequence name, Object... values) {
/* 231 */       return addEscapedValue(name, commaSeparate(name, objectEscaper(), values));
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl set(CharSequence name, CharSequence... values) {
/* 236 */       set(name, commaSeparate(name, charSequenceEscaper(), values));
/* 237 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl set(CharSequence name, Iterable<? extends CharSequence> values) {
/* 242 */       set(name, commaSeparate(name, charSequenceEscaper(), values));
/* 243 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl setObject(CharSequence name, Object value) {
/* 248 */       set(name, commaSeparate(name, objectEscaper(), new Object[] { value }));
/* 249 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl setObject(CharSequence name, Object... values) {
/* 254 */       set(name, commaSeparate(name, objectEscaper(), values));
/* 255 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public CombinedHttpHeadersImpl setObject(CharSequence name, Iterable<?> values) {
/* 260 */       set(name, commaSeparate(name, objectEscaper(), values));
/* 261 */       return this;
/*     */     }
/*     */     
/*     */     private static boolean cannotBeCombined(CharSequence name) {
/* 265 */       return HttpHeaderNames.SET_COOKIE.contentEqualsIgnoreCase(name);
/*     */     }
/*     */     
/*     */     private CombinedHttpHeadersImpl addEscapedValue(CharSequence name, CharSequence escapedValue) {
/* 269 */       CharSequence currentValue = (CharSequence)get(name);
/* 270 */       if (currentValue == null || cannotBeCombined(name)) {
/* 271 */         super.add(name, escapedValue);
/*     */       } else {
/* 273 */         set(name, commaSeparateEscapedValues(currentValue, escapedValue));
/*     */       } 
/* 275 */       return this;
/*     */     }
/*     */     
/*     */     private static <T> CharSequence commaSeparate(CharSequence name, CsvValueEscaper<T> escaper, T... values) {
/* 279 */       StringBuilder sb = new StringBuilder(values.length * 10);
/* 280 */       if (values.length > 0) {
/* 281 */         int end = values.length - 1;
/* 282 */         for (int i = 0; i < end; i++) {
/* 283 */           sb.append(escaper.escape(name, values[i])).append(',');
/*     */         }
/* 285 */         sb.append(escaper.escape(name, values[end]));
/*     */       } 
/* 287 */       return sb;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static <T> CharSequence commaSeparate(CharSequence name, CsvValueEscaper<T> escaper, Iterable<? extends T> values) {
/* 294 */       StringBuilder sb = (values instanceof Collection) ? new StringBuilder(((Collection)values).size() * 10) : new StringBuilder();
/* 295 */       Iterator<? extends T> iterator = values.iterator();
/* 296 */       if (iterator.hasNext()) {
/* 297 */         T next = iterator.next();
/* 298 */         while (iterator.hasNext()) {
/* 299 */           sb.append(escaper.escape(name, next)).append(',');
/* 300 */           next = iterator.next();
/*     */         } 
/* 302 */         sb.append(escaper.escape(name, next));
/*     */       } 
/* 304 */       return sb;
/*     */     }
/*     */     
/*     */     private static CharSequence commaSeparateEscapedValues(CharSequence currentValue, CharSequence value) {
/* 308 */       return (new StringBuilder(currentValue.length() + 1 + value.length()))
/* 309 */         .append(currentValue)
/* 310 */         .append(',')
/* 311 */         .append(value);
/*     */     }
/*     */     
/*     */     private static interface CsvValueEscaper<T> {
/*     */       CharSequence escape(CharSequence param2CharSequence, T param2T);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\CombinedHttpHeaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
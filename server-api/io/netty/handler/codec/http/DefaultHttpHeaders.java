/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.handler.codec.CharSequenceValueConverter;
/*     */ import io.netty.handler.codec.DateFormatter;
/*     */ import io.netty.handler.codec.DefaultHeaders;
/*     */ import io.netty.handler.codec.DefaultHeadersImpl;
/*     */ import io.netty.handler.codec.Headers;
/*     */ import io.netty.handler.codec.HeadersUtils;
/*     */ import io.netty.handler.codec.ValueConverter;
/*     */ import io.netty.util.AsciiString;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class DefaultHttpHeaders
/*     */   extends HttpHeaders
/*     */ {
/*     */   private final DefaultHeaders<CharSequence, CharSequence, ?> headers;
/*     */   
/*     */   public DefaultHttpHeaders() {
/*  53 */     this(nameValidator(true), valueValidator(true));
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
/*     */   @Deprecated
/*     */   public DefaultHttpHeaders(boolean validate) {
/*  72 */     this(nameValidator(validate), valueValidator(validate));
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
/*     */   protected DefaultHttpHeaders(boolean validateValues, DefaultHeaders.NameValidator<CharSequence> nameValidator) {
/*  93 */     this(nameValidator, valueValidator(validateValues));
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
/*     */   protected DefaultHttpHeaders(DefaultHeaders.NameValidator<CharSequence> nameValidator, DefaultHeaders.ValueValidator<CharSequence> valueValidator) {
/* 117 */     this(nameValidator, valueValidator, 16);
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
/*     */   protected DefaultHttpHeaders(DefaultHeaders.NameValidator<CharSequence> nameValidator, DefaultHeaders.ValueValidator<CharSequence> valueValidator, int sizeHint) {
/* 143 */     this((DefaultHeaders<CharSequence, CharSequence, ?>)new DefaultHeadersImpl(AsciiString.CASE_INSENSITIVE_HASHER, (ValueConverter)HeaderValueConverter.INSTANCE, nameValidator, sizeHint, valueValidator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DefaultHttpHeaders(DefaultHeaders<CharSequence, CharSequence, ?> headers) {
/* 152 */     this.headers = headers;
/*     */   }
/*     */   
/*     */   public Headers<CharSequence, CharSequence, ?> unwrap() {
/* 156 */     return (Headers<CharSequence, CharSequence, ?>)this.headers;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders add(HttpHeaders headers) {
/* 161 */     if (headers instanceof DefaultHttpHeaders) {
/* 162 */       this.headers.add((Headers)((DefaultHttpHeaders)headers).headers);
/* 163 */       return this;
/*     */     } 
/* 165 */     return super.add(headers);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpHeaders set(HttpHeaders headers) {
/* 171 */     if (headers instanceof DefaultHttpHeaders) {
/* 172 */       this.headers.set((Headers)((DefaultHttpHeaders)headers).headers);
/* 173 */       return this;
/*     */     } 
/* 175 */     return super.set(headers);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpHeaders add(String name, Object value) {
/* 181 */     this.headers.addObject(name, value);
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders add(CharSequence name, Object value) {
/* 187 */     this.headers.addObject(name, value);
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders add(String name, Iterable<?> values) {
/* 193 */     this.headers.addObject(name, values);
/* 194 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders add(CharSequence name, Iterable<?> values) {
/* 199 */     this.headers.addObject(name, values);
/* 200 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders addInt(CharSequence name, int value) {
/* 205 */     this.headers.addInt(name, value);
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders addShort(CharSequence name, short value) {
/* 211 */     this.headers.addShort(name, value);
/* 212 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders remove(String name) {
/* 217 */     this.headers.remove(name);
/* 218 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders remove(CharSequence name) {
/* 223 */     this.headers.remove(name);
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders set(String name, Object value) {
/* 229 */     this.headers.setObject(name, value);
/* 230 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders set(CharSequence name, Object value) {
/* 235 */     this.headers.setObject(name, value);
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders set(String name, Iterable<?> values) {
/* 241 */     this.headers.setObject(name, values);
/* 242 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders set(CharSequence name, Iterable<?> values) {
/* 247 */     this.headers.setObject(name, values);
/* 248 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders setInt(CharSequence name, int value) {
/* 253 */     this.headers.setInt(name, value);
/* 254 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders setShort(CharSequence name, short value) {
/* 259 */     this.headers.setShort(name, value);
/* 260 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders clear() {
/* 265 */     this.headers.clear();
/* 266 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String get(String name) {
/* 271 */     return get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String get(CharSequence name) {
/* 276 */     return HeadersUtils.getAsString((Headers)this.headers, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getInt(CharSequence name) {
/* 281 */     return this.headers.getInt(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(CharSequence name, int defaultValue) {
/* 286 */     return this.headers.getInt(name, defaultValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public Short getShort(CharSequence name) {
/* 291 */     return this.headers.getShort(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(CharSequence name, short defaultValue) {
/* 296 */     return this.headers.getShort(name, defaultValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public Long getTimeMillis(CharSequence name) {
/* 301 */     return this.headers.getTimeMillis(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTimeMillis(CharSequence name, long defaultValue) {
/* 306 */     return this.headers.getTimeMillis(name, defaultValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAll(String name) {
/* 311 */     return getAll(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAll(CharSequence name) {
/* 316 */     return HeadersUtils.getAllAsString((Headers)this.headers, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Map.Entry<String, String>> entries() {
/* 321 */     if (isEmpty()) {
/* 322 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 325 */     List<Map.Entry<String, String>> entriesConverted = new ArrayList<>(this.headers.size());
/* 326 */     for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)this) {
/* 327 */       entriesConverted.add(entry);
/*     */     }
/* 329 */     return entriesConverted;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Iterator<Map.Entry<String, String>> iterator() {
/* 335 */     return HeadersUtils.iteratorAsString((Iterable)this.headers);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Map.Entry<CharSequence, CharSequence>> iteratorCharSequence() {
/* 340 */     return this.headers.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<String> valueStringIterator(CharSequence name) {
/* 345 */     final Iterator<CharSequence> itr = valueCharSequenceIterator(name);
/* 346 */     return new Iterator<String>()
/*     */       {
/*     */         public boolean hasNext() {
/* 349 */           return itr.hasNext();
/*     */         }
/*     */ 
/*     */         
/*     */         public String next() {
/* 354 */           return ((CharSequence)itr.next()).toString();
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 359 */           itr.remove();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<CharSequence> valueCharSequenceIterator(CharSequence name) {
/* 366 */     return this.headers.valueIterator(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String name) {
/* 371 */     return contains(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(CharSequence name) {
/* 376 */     return this.headers.contains(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 381 */     return this.headers.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 386 */     return this.headers.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String name, String value, boolean ignoreCase) {
/* 391 */     return contains(name, value, ignoreCase);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(CharSequence name, CharSequence value, boolean ignoreCase) {
/* 396 */     return this.headers.contains(name, value, ignoreCase ? AsciiString.CASE_INSENSITIVE_HASHER : AsciiString.CASE_SENSITIVE_HASHER);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> names() {
/* 401 */     return HeadersUtils.namesAsString((Headers)this.headers);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 406 */     return (o instanceof DefaultHttpHeaders && this.headers
/* 407 */       .equals((Headers)((DefaultHttpHeaders)o).headers, AsciiString.CASE_SENSITIVE_HASHER));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 412 */     return this.headers.hashCode(AsciiString.CASE_SENSITIVE_HASHER);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders copy() {
/* 417 */     return new DefaultHttpHeaders(this.headers.copy());
/*     */   }
/*     */   
/*     */   static ValueConverter<CharSequence> valueConverter() {
/* 421 */     return (ValueConverter<CharSequence>)HeaderValueConverter.INSTANCE;
/*     */   }
/*     */   
/*     */   static DefaultHeaders.ValueValidator<CharSequence> valueValidator(boolean validate) {
/* 425 */     return validate ? DefaultHttpHeadersFactory.headersFactory().getValueValidator() : 
/* 426 */       DefaultHttpHeadersFactory.headersFactory().withValidation(false).getValueValidator();
/*     */   }
/*     */   
/*     */   static DefaultHeaders.NameValidator<CharSequence> nameValidator(boolean validate) {
/* 430 */     return validate ? DefaultHttpHeadersFactory.headersFactory().getNameValidator() : 
/* 431 */       DefaultHttpHeadersFactory.headersFactory().withNameValidation(false).getNameValidator();
/*     */   }
/*     */   
/*     */   private static class HeaderValueConverter extends CharSequenceValueConverter {
/* 435 */     static final HeaderValueConverter INSTANCE = new HeaderValueConverter();
/*     */ 
/*     */     
/*     */     public CharSequence convertObject(Object value) {
/* 439 */       if (value instanceof CharSequence) {
/* 440 */         return (CharSequence)value;
/*     */       }
/* 442 */       if (value instanceof Date) {
/* 443 */         return DateFormatter.format((Date)value);
/*     */       }
/* 445 */       if (value instanceof Calendar) {
/* 446 */         return DateFormatter.format(((Calendar)value).getTime());
/*     */       }
/* 448 */       return value.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\DefaultHttpHeaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
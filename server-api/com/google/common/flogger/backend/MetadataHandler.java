/*     */ package com.google.common.flogger.backend;
/*     */ 
/*     */ import com.google.common.flogger.MetadataKey;
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ public abstract class MetadataHandler<C>
/*     */ {
/*     */   protected abstract <T> void handle(MetadataKey<T> paramMetadataKey, T paramT, C paramC);
/*     */   
/*     */   protected <T> void handleRepeated(MetadataKey<T> key, Iterator<T> values, C context) {
/*  64 */     while (values.hasNext()) {
/*  65 */       handle(key, values.next(), context);
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
/*     */   public static <C> Builder<C> builder(ValueHandler<Object, C> defaultHandler) {
/*  87 */     return new Builder<C>(defaultHandler);
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
/*     */   public static interface ValueHandler<T, C>
/*     */   {
/*     */     void handle(MetadataKey<T> param1MetadataKey, T param1T, C param1C);
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
/*     */   public static interface RepeatedValueHandler<T, C>
/*     */   {
/*     */     void handle(MetadataKey<T> param1MetadataKey, Iterator<T> param1Iterator, C param1C);
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
/*     */   public static final class Builder<C>
/*     */   {
/* 134 */     private static final MetadataHandler.ValueHandler<Object, Object> IGNORE_VALUE = new MetadataHandler.ValueHandler<Object, Object>()
/*     */       {
/*     */         public void handle(MetadataKey<Object> key, Object value, Object context) {}
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 141 */     private static final MetadataHandler.RepeatedValueHandler<Object, Object> IGNORE_REPEATED_VALUE = new MetadataHandler.RepeatedValueHandler<Object, Object>()
/*     */       {
/*     */         public void handle(MetadataKey<Object> key, Iterator<Object> value, Object context) {}
/*     */       };
/*     */ 
/*     */     
/* 147 */     private final Map<MetadataKey<?>, MetadataHandler.ValueHandler<?, ? super C>> singleValueHandlers = new HashMap<MetadataKey<?>, MetadataHandler.ValueHandler<?, ? super C>>();
/*     */     
/* 149 */     private final Map<MetadataKey<?>, MetadataHandler.RepeatedValueHandler<?, ? super C>> repeatedValueHandlers = new HashMap<MetadataKey<?>, MetadataHandler.RepeatedValueHandler<?, ? super C>>();
/*     */     
/*     */     private final MetadataHandler.ValueHandler<Object, ? super C> defaultHandler;
/* 152 */     private MetadataHandler.RepeatedValueHandler<Object, ? super C> defaultRepeatedHandler = null;
/*     */     
/*     */     private Builder(MetadataHandler.ValueHandler<Object, ? super C> defaultHandler) {
/* 155 */       this.defaultHandler = (MetadataHandler.ValueHandler<Object, ? super C>)Checks.checkNotNull(defaultHandler, "default handler");
/*     */     }
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
/*     */     public Builder<C> setDefaultRepeatedHandler(MetadataHandler.RepeatedValueHandler<Object, ? super C> defaultHandler) {
/* 173 */       this.defaultRepeatedHandler = (MetadataHandler.RepeatedValueHandler<Object, ? super C>)Checks.checkNotNull(defaultHandler, "handler");
/* 174 */       return this;
/*     */     }
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
/*     */     public <T> Builder<C> addHandler(MetadataKey<T> key, MetadataHandler.ValueHandler<? super T, ? super C> handler) {
/* 187 */       Checks.checkNotNull(key, "key");
/* 188 */       Checks.checkNotNull(handler, "handler");
/* 189 */       this.repeatedValueHandlers.remove(key);
/* 190 */       this.singleValueHandlers.put(key, handler);
/* 191 */       return this;
/*     */     }
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
/*     */     public <T> Builder<C> addRepeatedHandler(MetadataKey<? extends T> key, MetadataHandler.RepeatedValueHandler<T, ? super C> handler) {
/* 205 */       Checks.checkNotNull(key, "key");
/* 206 */       Checks.checkNotNull(handler, "handler");
/* 207 */       Checks.checkArgument(key.canRepeat(), "key must be repeating");
/* 208 */       this.singleValueHandlers.remove(key);
/* 209 */       this.repeatedValueHandlers.put(key, handler);
/* 210 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<C> ignoring(MetadataKey<?> key, MetadataKey<?>... rest) {
/* 221 */       checkAndIgnore(key);
/* 222 */       for (MetadataKey<?> k : rest) {
/* 223 */         checkAndIgnore(k);
/*     */       }
/* 225 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<C> ignoring(Iterable<MetadataKey<?>> keys) {
/* 235 */       for (MetadataKey<?> k : keys) {
/* 236 */         checkAndIgnore(k);
/*     */       }
/* 238 */       return this;
/*     */     }
/*     */     
/*     */     <T> void checkAndIgnore(MetadataKey<T> key) {
/* 242 */       Checks.checkNotNull(key, "key");
/*     */       
/* 244 */       if (key.canRepeat()) {
/* 245 */         addRepeatedHandler(key, (MetadataHandler.RepeatedValueHandler)IGNORE_REPEATED_VALUE);
/*     */       } else {
/* 247 */         addHandler(key, (MetadataHandler.ValueHandler)IGNORE_VALUE);
/*     */       } 
/*     */     }
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
/*     */     public Builder<C> removeHandlers(MetadataKey<?> key, MetadataKey<?>... rest) {
/* 261 */       checkAndRemove(key);
/* 262 */       for (MetadataKey<?> k : rest) {
/* 263 */         checkAndRemove(k);
/*     */       }
/* 265 */       return this;
/*     */     }
/*     */     
/*     */     void checkAndRemove(MetadataKey<?> key) {
/* 269 */       Checks.checkNotNull(key, "key");
/* 270 */       this.singleValueHandlers.remove(key);
/* 271 */       this.repeatedValueHandlers.remove(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public MetadataHandler<C> build() {
/* 276 */       return new MetadataHandler.MapBasedhandler<C>(this);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class MapBasedhandler<C> extends MetadataHandler<C> {
/* 281 */     private final Map<MetadataKey<?>, MetadataHandler.ValueHandler<?, ? super C>> singleValueHandlers = new HashMap<MetadataKey<?>, MetadataHandler.ValueHandler<?, ? super C>>();
/*     */     
/* 283 */     private final Map<MetadataKey<?>, MetadataHandler.RepeatedValueHandler<?, ? super C>> repeatedValueHandlers = new HashMap<MetadataKey<?>, MetadataHandler.RepeatedValueHandler<?, ? super C>>();
/*     */     
/*     */     private final MetadataHandler.ValueHandler<Object, ? super C> defaultHandler;
/*     */     private final MetadataHandler.RepeatedValueHandler<Object, ? super C> defaultRepeatedHandler;
/*     */     
/*     */     private MapBasedhandler(MetadataHandler.Builder<C> builder) {
/* 289 */       this.singleValueHandlers.putAll(builder.singleValueHandlers);
/* 290 */       this.repeatedValueHandlers.putAll(builder.repeatedValueHandlers);
/* 291 */       this.defaultHandler = builder.defaultHandler;
/* 292 */       this.defaultRepeatedHandler = builder.defaultRepeatedHandler;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected <T> void handle(MetadataKey<T> key, T value, C context) {
/* 300 */       MetadataHandler.ValueHandler<T, ? super C> handler = (MetadataHandler.ValueHandler<T, ? super C>)this.singleValueHandlers.get(key);
/* 301 */       if (handler != null) {
/* 302 */         handler.handle(key, value, context);
/*     */       } else {
/*     */         
/* 305 */         this.defaultHandler.handle(key, value, context);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected <T> void handleRepeated(MetadataKey<T> key, Iterator<T> values, C context) {
/* 314 */       MetadataHandler.RepeatedValueHandler<T, ? super C> handler = (MetadataHandler.RepeatedValueHandler<T, ? super C>)this.repeatedValueHandlers.get(key);
/* 315 */       if (handler != null) {
/* 316 */         handler.handle(key, values, context);
/* 317 */       } else if (this.defaultRepeatedHandler != null && !this.singleValueHandlers.containsKey(key)) {
/*     */ 
/*     */         
/* 320 */         this.defaultRepeatedHandler.handle(key, values, context);
/*     */       }
/*     */       else {
/*     */         
/* 324 */         super.handleRepeated(key, values, context);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\MetadataHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.jline.style;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class MemoryStyleSource
/*     */   implements StyleSource
/*     */ {
/*  47 */   private static final Logger log = Logger.getLogger(MemoryStyleSource.class.getName());
/*     */   
/*  49 */   private final Map<String, Map<String, String>> backing = new ConcurrentHashMap<>();
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String get(String group, String name) {
/*  54 */     String style = null;
/*  55 */     Map<String, String> styles = this.backing.get(group);
/*  56 */     if (styles != null) {
/*  57 */       style = styles.get(name);
/*     */     }
/*     */     
/*  60 */     if (log.isLoggable(Level.FINEST)) {
/*  61 */       log.finest(String.format("Get: [%s] %s -> %s", new Object[] { group, name, style }));
/*     */     }
/*     */     
/*  64 */     return style;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(String group, String name, String style) {
/*  69 */     Objects.requireNonNull(group);
/*  70 */     Objects.requireNonNull(name);
/*  71 */     Objects.requireNonNull(style);
/*  72 */     ((Map<String, String>)this.backing.computeIfAbsent(group, k -> new ConcurrentHashMap<>())).put(name, style);
/*     */     
/*  74 */     if (log.isLoggable(Level.FINEST)) {
/*  75 */       log.finest(String.format("Set: [%s] %s -> %s", new Object[] { group, name, style }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(String group) {
/*  81 */     Objects.requireNonNull(group);
/*  82 */     if (this.backing.remove(group) != null && 
/*  83 */       log.isLoggable(Level.FINEST)) {
/*  84 */       log.finest(String.format("Removed: [%s]", new Object[] { group }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(String group, String name) {
/*  91 */     Objects.requireNonNull(group);
/*  92 */     Objects.requireNonNull(name);
/*  93 */     Map<String, String> styles = this.backing.get(group);
/*  94 */     if (styles != null) {
/*  95 */       styles.remove(name);
/*     */       
/*  97 */       if (log.isLoggable(Level.FINEST)) {
/*  98 */         log.finest(String.format("Removed: [%s] %s", new Object[] { group, name }));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 105 */     this.backing.clear();
/* 106 */     log.finest("Cleared");
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<String> groups() {
/* 111 */     return Collections.unmodifiableSet(this.backing.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, String> styles(String group) {
/* 116 */     Objects.requireNonNull(group);
/* 117 */     Map<String, String> result = this.backing.get(group);
/* 118 */     if (result == null) {
/* 119 */       result = Collections.emptyMap();
/*     */     }
/* 121 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\style\MemoryStyleSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
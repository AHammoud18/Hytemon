/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Hint
/*     */ {
/*     */   @NotNull
/*  18 */   private static final Map<String, Class<?>> PRIMITIVE_MAPPINGS = new HashMap<>(); static {
/*  19 */     PRIMITIVE_MAPPINGS.put("boolean", Boolean.class);
/*  20 */     PRIMITIVE_MAPPINGS.put("char", Character.class);
/*  21 */     PRIMITIVE_MAPPINGS.put("byte", Byte.class);
/*  22 */     PRIMITIVE_MAPPINGS.put("short", Short.class);
/*  23 */     PRIMITIVE_MAPPINGS.put("int", Integer.class);
/*  24 */     PRIMITIVE_MAPPINGS.put("long", Long.class);
/*  25 */     PRIMITIVE_MAPPINGS.put("float", Float.class);
/*  26 */     PRIMITIVE_MAPPINGS.put("double", Double.class);
/*     */   }
/*     */   @NotNull
/*  29 */   private final Map<String, Object> internalStorage = new HashMap<>(); @NotNull
/*  30 */   private final List<Attachment> attachments = new ArrayList<>(); @NotNull
/*  31 */   private final AutoClosableReentrantLock lock = new AutoClosableReentrantLock(); @Nullable
/*  32 */   private Attachment screenshot = null; @Nullable
/*  33 */   private Attachment viewHierarchy = null; @Nullable
/*  34 */   private Attachment threadDump = null; @Nullable
/*  35 */   private ReplayRecording replayRecording = null;
/*     */   @NotNull
/*     */   public static Hint withAttachment(@Nullable Attachment attachment) {
/*  38 */     Hint hint = new Hint();
/*  39 */     hint.addAttachment(attachment);
/*  40 */     return hint;
/*     */   }
/*     */   @NotNull
/*     */   public static Hint withAttachments(@Nullable List<Attachment> attachments) {
/*  44 */     Hint hint = new Hint();
/*  45 */     hint.addAttachments(attachments);
/*  46 */     return hint;
/*     */   }
/*     */   
/*     */   public void set(@NotNull String name, @Nullable Object hint) {
/*  50 */     ISentryLifecycleToken ignored = this.lock.acquire(); 
/*  51 */     try { this.internalStorage.put(name, hint);
/*  52 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  56 */      } @Nullable public Object get(@NotNull String name) { ISentryLifecycleToken ignored = this.lock.acquire(); 
/*  57 */     try { Object object = this.internalStorage.get(name);
/*  58 */       if (ignored != null) ignored.close();  return object; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  63 */      } @Nullable public <T> T getAs(@NotNull String name, @NotNull Class<T> clazz) { ISentryLifecycleToken ignored = this.lock.acquire(); 
/*  64 */     try { Object hintValue = this.internalStorage.get(name);
/*     */       
/*  66 */       if (clazz.isInstance(hintValue))
/*  67 */       { Object object = hintValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  73 */         if (ignored != null) ignored.close();  return (T)object; }  if (isCastablePrimitive(hintValue, clazz)) { Object object = hintValue; if (ignored != null) ignored.close();  return (T)object; }  T t = null; if (ignored != null) ignored.close();  return t; } catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  77 */      } public void remove(@NotNull String name) { ISentryLifecycleToken ignored = this.lock.acquire(); 
/*  78 */     try { this.internalStorage.remove(name);
/*  79 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  83 */      } public void addAttachment(@Nullable Attachment attachment) { if (attachment != null) {
/*  84 */       this.attachments.add(attachment);
/*     */     } }
/*     */ 
/*     */   
/*     */   public void addAttachments(@Nullable List<Attachment> attachments) {
/*  89 */     if (attachments != null)
/*  90 */       this.attachments.addAll(attachments); 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<Attachment> getAttachments() {
/*  95 */     return new ArrayList<>(this.attachments);
/*     */   }
/*     */   
/*     */   public void replaceAttachments(@Nullable List<Attachment> attachments) {
/*  99 */     clearAttachments();
/* 100 */     addAttachments(attachments);
/*     */   }
/*     */   
/*     */   public void clearAttachments() {
/* 104 */     this.attachments.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void clear() {
/* 114 */     ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 115 */     try { Iterator<Map.Entry<String, Object>> iterator = this.internalStorage.entrySet().iterator();
/*     */       
/* 117 */       while (iterator.hasNext()) {
/* 118 */         Map.Entry<String, Object> entry = iterator.next();
/* 119 */         if (entry.getKey() == null || !((String)entry.getKey()).startsWith("sentry:")) {
/* 120 */           iterator.remove();
/*     */         }
/*     */       } 
/* 123 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 127 */      } public void setScreenshot(@Nullable Attachment screenshot) { this.screenshot = screenshot; }
/*     */   
/*     */   @Nullable
/*     */   public Attachment getScreenshot() {
/* 131 */     return this.screenshot;
/*     */   }
/*     */   
/*     */   public void setViewHierarchy(@Nullable Attachment viewHierarchy) {
/* 135 */     this.viewHierarchy = viewHierarchy;
/*     */   }
/*     */   @Nullable
/*     */   public Attachment getViewHierarchy() {
/* 139 */     return this.viewHierarchy;
/*     */   }
/*     */   
/*     */   public void setThreadDump(@Nullable Attachment threadDump) {
/* 143 */     this.threadDump = threadDump;
/*     */   }
/*     */   @Nullable
/*     */   public Attachment getThreadDump() {
/* 147 */     return this.threadDump;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ReplayRecording getReplayRecording() {
/* 152 */     return this.replayRecording;
/*     */   }
/*     */   
/*     */   public void setReplayRecording(@Nullable ReplayRecording replayRecording) {
/* 156 */     this.replayRecording = replayRecording;
/*     */   }
/*     */   
/*     */   private boolean isCastablePrimitive(@Nullable Object hintValue, @NotNull Class<?> clazz) {
/* 160 */     Class<?> nonPrimitiveClass = PRIMITIVE_MAPPINGS.get(clazz.getCanonicalName());
/* 161 */     return (hintValue != null && clazz
/* 162 */       .isPrimitive() && nonPrimitiveClass != null && nonPrimitiveClass
/*     */       
/* 164 */       .isInstance(hintValue));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\Hint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
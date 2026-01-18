/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceLeakDetector<T>
/*     */ {
/*     */   private static final String PROP_LEVEL_OLD = "io.netty.leakDetectionLevel";
/*     */   private static final String PROP_LEVEL = "io.netty.leakDetection.level";
/*     */   
/*     */   static {
/*     */     boolean disabled;
/*     */   }
/*     */   
/*  46 */   private static final Level DEFAULT_LEVEL = Level.SIMPLE;
/*     */   
/*     */   private static final String PROP_TARGET_RECORDS = "io.netty.leakDetection.targetRecords";
/*     */   
/*     */   private static final int DEFAULT_TARGET_RECORDS = 4;
/*     */   
/*     */   private static final String PROP_SAMPLING_INTERVAL = "io.netty.leakDetection.samplingInterval";
/*     */   
/*     */   private static final int DEFAULT_SAMPLING_INTERVAL = 128;
/*     */   
/*     */   private static final String PROP_TRACK_CLOSE = "io.netty.leakDetection.trackClose";
/*     */   
/*     */   private static final boolean DEFAULT_TRACK_CLOSE = true;
/*     */   
/*     */   private static final int TARGET_RECORDS;
/*     */   
/*     */   static final int SAMPLING_INTERVAL;
/*     */   
/*     */   private static final boolean TRACK_CLOSE;
/*     */   private static Level level;
/*     */   
/*     */   public enum Level
/*     */   {
/*  69 */     DISABLED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     SIMPLE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     ADVANCED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     PARANOID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Level parseLevel(String levelStr) {
/*  93 */       String trimmedLevelStr = levelStr.trim();
/*  94 */       for (Level l : values()) {
/*  95 */         if (trimmedLevelStr.equalsIgnoreCase(l.name()) || trimmedLevelStr.equals(String.valueOf(l.ordinal()))) {
/*  96 */           return l;
/*     */         }
/*     */       } 
/*  99 */       return ResourceLeakDetector.DEFAULT_LEVEL;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 105 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ResourceLeakDetector.class);
/*     */   
/*     */   static
/*     */   {
/* 109 */     if (SystemPropertyUtil.get("io.netty.noResourceLeakDetection") != null) {
/* 110 */       disabled = SystemPropertyUtil.getBoolean("io.netty.noResourceLeakDetection", false);
/* 111 */       logger.debug("-Dio.netty.noResourceLeakDetection: {}", Boolean.valueOf(disabled));
/* 112 */       logger.warn("-Dio.netty.noResourceLeakDetection is deprecated. Use '-D{}={}' instead.", "io.netty.leakDetection.level", Level.DISABLED
/*     */           
/* 114 */           .name().toLowerCase());
/*     */     } else {
/* 116 */       disabled = false;
/*     */     } 
/*     */     
/* 119 */     Level defaultLevel = disabled ? Level.DISABLED : DEFAULT_LEVEL;
/*     */ 
/*     */     
/* 122 */     String levelStr = SystemPropertyUtil.get("io.netty.leakDetectionLevel", defaultLevel.name());
/*     */ 
/*     */     
/* 125 */     levelStr = SystemPropertyUtil.get("io.netty.leakDetection.level", levelStr);
/* 126 */     Level level = Level.parseLevel(levelStr);
/*     */     
/* 128 */     TARGET_RECORDS = SystemPropertyUtil.getInt("io.netty.leakDetection.targetRecords", 4);
/* 129 */     SAMPLING_INTERVAL = SystemPropertyUtil.getInt("io.netty.leakDetection.samplingInterval", 128);
/* 130 */     TRACK_CLOSE = SystemPropertyUtil.getBoolean("io.netty.leakDetection.trackClose", true);
/*     */     
/* 132 */     ResourceLeakDetector.level = level;
/* 133 */     if (logger.isDebugEnabled()) {
/* 134 */       logger.debug("-D{}: {}", "io.netty.leakDetection.level", level.name().toLowerCase());
/* 135 */       logger.debug("-D{}: {}", "io.netty.leakDetection.targetRecords", Integer.valueOf(TARGET_RECORDS));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 641 */     excludedMethods = (AtomicReference)new AtomicReference<>(EmptyArrays.EMPTY_STRINGS); }
/*     */   @Deprecated public static void setEnabled(boolean enabled) { setLevel(enabled ? Level.SIMPLE : Level.DISABLED); }
/*     */   public static boolean isEnabled() { return (getLevel().ordinal() > Level.DISABLED.ordinal()); }
/*     */   public static void setLevel(Level level) { ResourceLeakDetector.level = (Level)ObjectUtil.checkNotNull(level, "level"); }
/* 645 */   public static Level getLevel() { return level; } private final Set<DefaultResourceLeak<?>> allLeaks = ConcurrentHashMap.newKeySet(); private final ReferenceQueue<Object> refQueue = new ReferenceQueue(); private final Set<String> reportedLeaks = ConcurrentHashMap.newKeySet(); private final String resourceType; private final int samplingInterval; private volatile LeakListener leakListener; private static final AtomicReference<String[]> excludedMethods; @Deprecated public ResourceLeakDetector(Class<?> resourceType) { this(StringUtil.simpleClassName(resourceType)); } @Deprecated public ResourceLeakDetector(String resourceType) { this(resourceType, 128, Long.MAX_VALUE); } @Deprecated public ResourceLeakDetector(Class<?> resourceType, int samplingInterval, long maxActive) { this(resourceType, samplingInterval); } public ResourceLeakDetector(Class<?> resourceType, int samplingInterval) { this(StringUtil.simpleClassName(resourceType), samplingInterval, Long.MAX_VALUE); } public static void addExclusions(Class clz, String... methodNames) { String[] oldMethods, newMethods; Set<String> nameSet = new HashSet<>(Arrays.asList(methodNames));
/*     */ 
/*     */     
/* 648 */     for (Method method : clz.getDeclaredMethods()) {
/* 649 */       if (nameSet.remove(method.getName()) && nameSet.isEmpty()) {
/*     */         break;
/*     */       }
/*     */     } 
/* 653 */     if (!nameSet.isEmpty()) {
/* 654 */       throw new IllegalArgumentException("Can't find '" + nameSet + "' in " + clz.getName());
/*     */     }
/*     */ 
/*     */     
/*     */     do {
/* 659 */       oldMethods = excludedMethods.get();
/* 660 */       newMethods = Arrays.<String>copyOf(oldMethods, oldMethods.length + 2 * methodNames.length);
/* 661 */       for (int i = 0; i < methodNames.length; i++) {
/* 662 */         newMethods[oldMethods.length + i * 2] = clz.getName();
/* 663 */         newMethods[oldMethods.length + i * 2 + 1] = methodNames[i];
/*     */       } 
/* 665 */     } while (!excludedMethods.compareAndSet(oldMethods, newMethods)); }
/*     */   @Deprecated public ResourceLeakDetector(String resourceType, int samplingInterval, long maxActive) { this.resourceType = (String)ObjectUtil.checkNotNull(resourceType, "resourceType"); this.samplingInterval = samplingInterval; }
/*     */   @Deprecated public final ResourceLeak open(T obj) { return track0(obj, false); }
/*     */   public ResourceLeakTracker<T> track(T obj) { return track0(obj, false); }
/*     */   public ResourceLeakTracker<T> trackForcibly(T obj) { return track0(obj, true); } public boolean isRecordEnabled() { Level level = getLevel(); return ((level == Level.ADVANCED || level == Level.PARANOID) && TARGET_RECORDS > 0); } private DefaultResourceLeak<T> track0(T obj, boolean force) { Level level = ResourceLeakDetector.level; if (!force && level != Level.PARANOID) { if (level != Level.DISABLED) if (ThreadLocalRandom.current().nextInt(this.samplingInterval) == 0) { reportLeak(); return new DefaultResourceLeak<>(obj, this.refQueue, this.allLeaks, getInitialHint(this.resourceType)); }   } else { reportLeak(); return new DefaultResourceLeak<>(obj, this.refQueue, this.allLeaks, getInitialHint(this.resourceType)); }  return null; } private void clearRefQueue() { while (true) { DefaultResourceLeak<?> ref = (DefaultResourceLeak)this.refQueue.poll(); if (ref == null) break;  ref.dispose(); }  } protected boolean needReport() { return logger.isErrorEnabled(); } private void reportLeak() { if (!needReport()) { clearRefQueue(); return; }  while (true) { DefaultResourceLeak<?> ref = (DefaultResourceLeak)this.refQueue.poll(); if (ref == null) break;  if (!ref.dispose()) continue;  String records = ref.getReportAndClearRecords(); if (this.reportedLeaks.add(records)) { if (records.isEmpty()) { reportUntracedLeak(this.resourceType); } else { reportTracedLeak(this.resourceType, records); }  LeakListener listener = this.leakListener; if (listener != null) listener.onLeak(this.resourceType, records);  }  }  } protected void reportTracedLeak(String resourceType, String records) { logger.error("LEAK: {}.release() was not called before it's garbage-collected. See https://netty.io/wiki/reference-counted-objects.html for more information.{}", resourceType, records); } protected void reportUntracedLeak(String resourceType) { logger.error("LEAK: {}.release() was not called before it's garbage-collected. Enable advanced leak reporting to find out where the leak occurred. To enable advanced leak reporting, specify the JVM option '-D{}={}' or call {}.setLevel() See https://netty.io/wiki/reference-counted-objects.html for more information.", new Object[] { resourceType, "io.netty.leakDetection.level", Level.ADVANCED.name().toLowerCase(), StringUtil.simpleClassName(this) }); } @Deprecated protected void reportInstancesLeak(String resourceType) {} protected Object getInitialHint(String resourceType) { return null; } public void setLeakListener(LeakListener leakListener) { this.leakListener = leakListener; } public static interface LeakListener {
/*     */     void onLeak(String param1String1, String param1String2);
/*     */   } private static final class DefaultResourceLeak<T> extends WeakReference<Object> implements ResourceLeakTracker<T>, ResourceLeak {
/*     */     private static final AtomicReferenceFieldUpdater<DefaultResourceLeak<?>, ResourceLeakDetector.TraceRecord> headUpdater = AtomicReferenceFieldUpdater.newUpdater((Class)DefaultResourceLeak.class, ResourceLeakDetector.TraceRecord.class, "head"); private static final AtomicIntegerFieldUpdater<DefaultResourceLeak<?>> droppedRecordsUpdater = AtomicIntegerFieldUpdater.newUpdater((Class)DefaultResourceLeak.class, "droppedRecords"); private volatile ResourceLeakDetector.TraceRecord head; private volatile int droppedRecords; private final Set<DefaultResourceLeak<?>> allLeaks; private final int trackedHash; DefaultResourceLeak(Object referent, ReferenceQueue<Object> refQueue, Set<DefaultResourceLeak<?>> allLeaks, Object initialHint) { super(referent, refQueue); assert referent != null; this.allLeaks = allLeaks; this.trackedHash = System.identityHashCode(referent); allLeaks.add(this); headUpdater.set(this, (initialHint == null) ? new ResourceLeakDetector.TraceRecord(ResourceLeakDetector.TraceRecord.BOTTOM) : new ResourceLeakDetector.TraceRecord(ResourceLeakDetector.TraceRecord.BOTTOM, initialHint)); } public void record() { record0(null); } public void record(Object hint) { record0(hint); } private void record0(Object hint) { if (ResourceLeakDetector.TARGET_RECORDS > 0) while (true) { boolean dropped; ResourceLeakDetector.TraceRecord oldHead, prevHead; if ((prevHead = oldHead = headUpdater.get(this)) == null || oldHead.pos == -2) return;  int numElements = oldHead.pos + 1; if (numElements >= ResourceLeakDetector.TARGET_RECORDS) { int backOffFactor = Math.min(numElements - ResourceLeakDetector.TARGET_RECORDS, 30); dropped = (ThreadLocalRandom.current().nextInt(1 << backOffFactor) != 0); if (dropped) prevHead = oldHead.next;  } else { dropped = false; }  ResourceLeakDetector.TraceRecord newHead = (hint != null) ? new ResourceLeakDetector.TraceRecord(prevHead, hint) : new ResourceLeakDetector.TraceRecord(prevHead); if (headUpdater.compareAndSet(this, oldHead, newHead)) { if (dropped) droppedRecordsUpdater.incrementAndGet(this);  break; }  }   } boolean dispose() { clear(); return this.allLeaks.remove(this); } public boolean close() { if (this.allLeaks.remove(this)) { clear(); headUpdater.set(this, ResourceLeakDetector.TRACK_CLOSE ? new ResourceLeakDetector.TraceRecord(true) : null); return true; }  return false; } public boolean close(T trackedObject) { assert this.trackedHash == System.identityHashCode(trackedObject); try { return close(); } finally { reachabilityFence0(trackedObject); }  } private static void reachabilityFence0(Object ref) { if (ref != null) synchronized (ref) {  }   } @Nullable public Throwable getCloseStackTraceIfAny() { ResourceLeakDetector.TraceRecord head = headUpdater.get(this); if (head != null && head.pos == -2) return head;  return null; } public String toString() { ResourceLeakDetector.TraceRecord oldHead = headUpdater.get(this); return generateReport(oldHead); } String getReportAndClearRecords() { ResourceLeakDetector.TraceRecord oldHead = headUpdater.getAndSet(this, null); return generateReport(oldHead); } private String generateReport(ResourceLeakDetector.TraceRecord oldHead) { if (oldHead == null) return "";  int dropped = droppedRecordsUpdater.get(this); int duped = 0; int present = oldHead.pos + 1; StringBuilder buf = (new StringBuilder(present * 2048)).append(StringUtil.NEWLINE); buf.append("Recent access records: ").append(StringUtil.NEWLINE); int i = 1; Set<String> seen = new HashSet<>(present); for (; oldHead != ResourceLeakDetector.TraceRecord.BOTTOM; oldHead = oldHead.next) { String s = oldHead.toString(); if (seen.add(s)) { if (oldHead.next == ResourceLeakDetector.TraceRecord.BOTTOM) { buf.append("Created at:").append(StringUtil.NEWLINE).append(s); } else { buf.append('#').append(i++).append(':').append(StringUtil.NEWLINE).append(s); }  } else { duped++; }  }  if (duped > 0) buf.append(": ").append(duped).append(" leak records were discarded because they were duplicates").append(StringUtil.NEWLINE);  if (dropped > 0) buf.append(": ").append(dropped).append(" leak records were discarded because the leak record count is targeted to ").append(ResourceLeakDetector.TARGET_RECORDS).append(". Use system property ").append("io.netty.leakDetection.targetRecords").append(" to increase the limit.").append(StringUtil.NEWLINE);  buf.setLength(buf.length() - StringUtil.NEWLINE.length()); return buf.toString(); }
/* 673 */   } private static class TraceRecord extends Throwable { private static final long serialVersionUID = 6065153674892850720L; public static final int BOTTOM_POS = -1; public static final int CLOSE_MARK_POS = -2; private static final TraceRecord BOTTOM = new TraceRecord(false)
/*     */       {
/*     */         private static final long serialVersionUID = 7396077602074694571L;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public Throwable fillInStackTrace() {
/* 681 */           return this;
/*     */         }
/*     */       };
/*     */     
/*     */     private final String hintString;
/*     */     
/*     */     private final TraceRecord next;
/*     */     private final int pos;
/*     */     
/*     */     TraceRecord(TraceRecord next, Object hint) {
/* 691 */       this.hintString = (hint instanceof ResourceLeakHint) ? ((ResourceLeakHint)hint).toHintString() : hint.toString();
/* 692 */       this.next = next;
/* 693 */       next.pos++;
/*     */     }
/*     */     
/*     */     TraceRecord(TraceRecord next) {
/* 697 */       this.hintString = null;
/* 698 */       this.next = next;
/* 699 */       next.pos++;
/*     */     }
/*     */ 
/*     */     
/*     */     private TraceRecord(boolean closeMarker) {
/* 704 */       this.hintString = null;
/* 705 */       this.next = null;
/* 706 */       this.pos = closeMarker ? -2 : -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 711 */       StringBuilder buf = new StringBuilder(2048);
/* 712 */       if (this.hintString != null) {
/* 713 */         buf.append("\tHint: ").append(this.hintString).append(StringUtil.NEWLINE);
/*     */       }
/*     */ 
/*     */       
/* 717 */       StackTraceElement[] array = getStackTrace();
/*     */       
/* 719 */       for (int i = 3; i < array.length; i++) {
/* 720 */         StackTraceElement element = array[i];
/*     */         
/* 722 */         String[] exclusions = ResourceLeakDetector.excludedMethods.get();
/* 723 */         int k = 0; while (true) { if (k < exclusions.length) {
/*     */ 
/*     */             
/* 726 */             if (exclusions[k].equals(element.getClassName()) && exclusions[k + 1]
/* 727 */               .equals(element.getMethodName()))
/*     */               break; 
/*     */             k += 2;
/*     */             continue;
/*     */           } 
/* 732 */           buf.append('\t');
/* 733 */           buf.append(element.toString());
/* 734 */           buf.append(StringUtil.NEWLINE); break; }
/*     */       
/* 736 */       }  return buf.toString();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\ResourceLeakDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
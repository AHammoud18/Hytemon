/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.MacAddressUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultChannelId
/*     */   implements ChannelId
/*     */ {
/*     */   private static final long serialVersionUID = 3884076183504074063L;
/*  43 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultChannelId.class);
/*     */   
/*     */   private static final byte[] MACHINE_ID;
/*     */   private static final int PROCESS_ID_LEN = 4;
/*     */   private static final int PROCESS_ID;
/*     */   private static final int SEQUENCE_LEN = 4;
/*     */   private static final int TIMESTAMP_LEN = 8;
/*     */   private static final int RANDOM_LEN = 4;
/*  51 */   private static final AtomicInteger nextSequence = new AtomicInteger();
/*     */   
/*     */   private final byte[] data;
/*     */   private final int hashCode;
/*     */   
/*     */   public static DefaultChannelId newInstance() {
/*  57 */     return new DefaultChannelId(MACHINE_ID, PROCESS_ID, nextSequence
/*     */         
/*  59 */         .getAndIncrement(), 
/*  60 */         Long.reverse(System.nanoTime()) ^ System.currentTimeMillis(), 
/*  61 */         ThreadLocalRandom.current().nextInt());
/*     */   }
/*     */   private transient String shortValue; private transient String longValue;
/*     */   static {
/*  65 */     int processId = -1;
/*  66 */     String customProcessId = SystemPropertyUtil.get("io.netty.processId");
/*  67 */     if (customProcessId != null) {
/*     */       try {
/*  69 */         processId = Integer.parseInt(customProcessId);
/*  70 */       } catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */ 
/*     */       
/*  74 */       if (processId < 0) {
/*  75 */         processId = -1;
/*  76 */         logger.warn("-Dio.netty.processId: {} (malformed)", customProcessId);
/*  77 */       } else if (logger.isDebugEnabled()) {
/*  78 */         logger.debug("-Dio.netty.processId: {} (user-set)", Integer.valueOf(processId));
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     if (processId < 0) {
/*  83 */       processId = defaultProcessId();
/*  84 */       if (logger.isDebugEnabled()) {
/*  85 */         logger.debug("-Dio.netty.processId: {} (auto-detected)", Integer.valueOf(processId));
/*     */       }
/*     */     } 
/*     */     
/*  89 */     PROCESS_ID = processId;
/*     */     
/*  91 */     byte[] machineId = null;
/*  92 */     String customMachineId = SystemPropertyUtil.get("io.netty.machineId");
/*  93 */     if (customMachineId != null) {
/*     */       try {
/*  95 */         machineId = MacAddressUtil.parseMAC(customMachineId);
/*  96 */       } catch (Exception e) {
/*  97 */         logger.warn("-Dio.netty.machineId: {} (malformed)", customMachineId, e);
/*     */       } 
/*  99 */       if (machineId != null) {
/* 100 */         logger.debug("-Dio.netty.machineId: {} (user-set)", customMachineId);
/*     */       }
/*     */     } 
/*     */     
/* 104 */     if (machineId == null) {
/* 105 */       machineId = MacAddressUtil.defaultMachineId();
/* 106 */       if (logger.isDebugEnabled()) {
/* 107 */         logger.debug("-Dio.netty.machineId: {} (auto-detected)", MacAddressUtil.formatAddress(machineId));
/*     */       }
/*     */     } 
/*     */     
/* 111 */     MACHINE_ID = machineId;
/*     */   }
/*     */ 
/*     */   
/*     */   static int processHandlePid(ClassLoader loader) {
/* 116 */     int nilValue = -1;
/* 117 */     if (PlatformDependent.javaVersion() >= 9) {
/*     */       Long pid;
/*     */       try {
/* 120 */         Class<?> processHandleImplType = Class.forName("java.lang.ProcessHandle", true, loader);
/* 121 */         Method processHandleCurrent = processHandleImplType.getMethod("current", new Class[0]);
/* 122 */         Object processHandleInstance = processHandleCurrent.invoke(null, new Object[0]);
/* 123 */         Method processHandlePid = processHandleImplType.getMethod("pid", new Class[0]);
/* 124 */         pid = (Long)processHandlePid.invoke(processHandleInstance, new Object[0]);
/* 125 */       } catch (Exception e) {
/* 126 */         logger.debug("Could not invoke ProcessHandle.current().pid();", e);
/* 127 */         return nilValue;
/*     */       } 
/* 129 */       if (pid.longValue() > 2147483647L || pid.longValue() < -2147483648L) {
/* 130 */         throw new IllegalStateException("Current process ID exceeds int range: " + pid);
/*     */       }
/* 132 */       return pid.intValue();
/*     */     } 
/* 134 */     return nilValue;
/*     */   }
/*     */   
/*     */   static int jmxPid(ClassLoader loader) {
/*     */     String value;
/*     */     int pid;
/*     */     try {
/* 141 */       Class<?> mgmtFactoryType = Class.forName("java.lang.management.ManagementFactory", true, loader);
/* 142 */       Class<?> runtimeMxBeanType = Class.forName("java.lang.management.RuntimeMXBean", true, loader);
/*     */       
/* 144 */       Method getRuntimeMXBean = mgmtFactoryType.getMethod("getRuntimeMXBean", EmptyArrays.EMPTY_CLASSES);
/* 145 */       Object bean = getRuntimeMXBean.invoke(null, EmptyArrays.EMPTY_OBJECTS);
/* 146 */       Method getName = runtimeMxBeanType.getMethod("getName", EmptyArrays.EMPTY_CLASSES);
/* 147 */       value = (String)getName.invoke(bean, EmptyArrays.EMPTY_OBJECTS);
/* 148 */     } catch (Throwable t) {
/* 149 */       logger.debug("Could not invoke ManagementFactory.getRuntimeMXBean().getName(); Android?", t);
/*     */       
/*     */       try {
/* 152 */         Class<?> processType = Class.forName("android.os.Process", true, loader);
/* 153 */         Method myPid = processType.getMethod("myPid", EmptyArrays.EMPTY_CLASSES);
/* 154 */         value = myPid.invoke(null, EmptyArrays.EMPTY_OBJECTS).toString();
/* 155 */       } catch (Throwable t2) {
/* 156 */         logger.debug("Could not invoke Process.myPid(); not Android?", t2);
/* 157 */         value = "";
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     int atIndex = value.indexOf('@');
/* 162 */     if (atIndex >= 0) {
/* 163 */       value = value.substring(0, atIndex);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 168 */       pid = Integer.parseInt(value);
/* 169 */     } catch (NumberFormatException e) {
/*     */       
/* 171 */       pid = -1;
/*     */     } 
/*     */     
/* 174 */     if (pid < 0) {
/* 175 */       pid = ThreadLocalRandom.current().nextInt();
/* 176 */       logger.warn("Failed to find the current process ID from '{}'; using a random value: {}", value, Integer.valueOf(pid));
/*     */     } 
/*     */     
/* 179 */     return pid;
/*     */   }
/*     */   
/*     */   static int defaultProcessId() {
/* 183 */     ClassLoader loader = PlatformDependent.getClassLoader(DefaultChannelId.class);
/* 184 */     int processId = processHandlePid(loader);
/* 185 */     if (processId != -1) {
/* 186 */       return processId;
/*     */     }
/* 188 */     return jmxPid(loader);
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
/*     */   DefaultChannelId(byte[] machineId, int processId, int sequence, long timestamp, int random) {
/* 202 */     byte[] data = new byte[machineId.length + 4 + 4 + 8 + 4];
/* 203 */     int i = 0;
/*     */ 
/*     */     
/* 206 */     System.arraycopy(machineId, 0, data, i, machineId.length);
/* 207 */     i += machineId.length;
/*     */ 
/*     */     
/* 210 */     writeInt(data, i, processId);
/* 211 */     i += 4;
/*     */ 
/*     */     
/* 214 */     writeInt(data, i, sequence);
/* 215 */     i += 4;
/*     */ 
/*     */     
/* 218 */     writeLong(data, i, timestamp);
/* 219 */     i += 8;
/*     */ 
/*     */     
/* 222 */     writeInt(data, i, random);
/* 223 */     i += 4;
/* 224 */     assert i == data.length;
/*     */     
/* 226 */     this.data = data;
/* 227 */     this.hashCode = Arrays.hashCode(data);
/*     */   }
/*     */   
/*     */   private static void writeInt(byte[] data, int i, int value) {
/* 231 */     if (PlatformDependent.isUnaligned()) {
/* 232 */       PlatformDependent.putInt(data, i, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? value : Integer.reverseBytes(value));
/*     */       return;
/*     */     } 
/* 235 */     data[i] = (byte)(value >>> 24);
/* 236 */     data[i + 1] = (byte)(value >>> 16);
/* 237 */     data[i + 2] = (byte)(value >>> 8);
/* 238 */     data[i + 3] = (byte)value;
/*     */   }
/*     */   
/*     */   private static void writeLong(byte[] data, int i, long value) {
/* 242 */     if (PlatformDependent.isUnaligned()) {
/* 243 */       PlatformDependent.putLong(data, i, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? value : Long.reverseBytes(value));
/*     */       return;
/*     */     } 
/* 246 */     data[i] = (byte)(int)(value >>> 56L);
/* 247 */     data[i + 1] = (byte)(int)(value >>> 48L);
/* 248 */     data[i + 2] = (byte)(int)(value >>> 40L);
/* 249 */     data[i + 3] = (byte)(int)(value >>> 32L);
/* 250 */     data[i + 4] = (byte)(int)(value >>> 24L);
/* 251 */     data[i + 5] = (byte)(int)(value >>> 16L);
/* 252 */     data[i + 6] = (byte)(int)(value >>> 8L);
/* 253 */     data[i + 7] = (byte)(int)value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String asShortText() {
/* 258 */     String shortValue = this.shortValue;
/* 259 */     if (shortValue == null) {
/* 260 */       this.shortValue = shortValue = ByteBufUtil.hexDump(this.data, this.data.length - 4, 4);
/*     */     }
/* 262 */     return shortValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public String asLongText() {
/* 267 */     String longValue = this.longValue;
/* 268 */     if (longValue == null) {
/* 269 */       this.longValue = longValue = newLongValue();
/*     */     }
/* 271 */     return longValue;
/*     */   }
/*     */   
/*     */   private String newLongValue() {
/* 275 */     StringBuilder buf = new StringBuilder(2 * this.data.length + 5);
/* 276 */     int machineIdLen = this.data.length - 4 - 4 - 8 - 4;
/* 277 */     int i = 0;
/* 278 */     i = appendHexDumpField(buf, i, machineIdLen);
/* 279 */     i = appendHexDumpField(buf, i, 4);
/* 280 */     i = appendHexDumpField(buf, i, 4);
/* 281 */     i = appendHexDumpField(buf, i, 8);
/* 282 */     i = appendHexDumpField(buf, i, 4);
/* 283 */     assert i == this.data.length;
/* 284 */     return buf.substring(0, buf.length() - 1);
/*     */   }
/*     */   
/*     */   private int appendHexDumpField(StringBuilder buf, int i, int length) {
/* 288 */     buf.append(ByteBufUtil.hexDump(this.data, i, length));
/* 289 */     buf.append('-');
/* 290 */     i += length;
/* 291 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 296 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ChannelId o) {
/* 301 */     if (this == o)
/*     */     {
/* 303 */       return 0;
/*     */     }
/* 305 */     if (o instanceof DefaultChannelId) {
/*     */       
/* 307 */       byte[] otherData = ((DefaultChannelId)o).data;
/* 308 */       int len1 = this.data.length;
/* 309 */       int len2 = otherData.length;
/* 310 */       int len = Math.min(len1, len2);
/*     */       
/* 312 */       for (int k = 0; k < len; k++) {
/* 313 */         byte x = this.data[k];
/* 314 */         byte y = otherData[k];
/* 315 */         if (x != y)
/*     */         {
/* 317 */           return (x & 0xFF) - (y & 0xFF);
/*     */         }
/*     */       } 
/* 320 */       return len1 - len2;
/*     */     } 
/*     */     
/* 323 */     return asLongText().compareTo(o.asLongText());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 328 */     if (this == obj) {
/* 329 */       return true;
/*     */     }
/* 331 */     if (!(obj instanceof DefaultChannelId)) {
/* 332 */       return false;
/*     */     }
/* 334 */     DefaultChannelId other = (DefaultChannelId)obj;
/* 335 */     return (this.hashCode == other.hashCode && Arrays.equals(this.data, other.data));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 340 */     return asShortText();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\DefaultChannelId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
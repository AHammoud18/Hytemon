/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.lang.reflect.Field;
/*      */ import java.nio.Buffer;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class UnsafeUtil
/*      */ {
/*   21 */   private static final Unsafe UNSAFE = getUnsafe();
/*   22 */   private static final Class<?> MEMORY_CLASS = Android.getMemoryClass();
/*   23 */   private static final boolean IS_ANDROID_64 = determineAndroidSupportByAddressSize(long.class);
/*   24 */   private static final boolean IS_ANDROID_32 = determineAndroidSupportByAddressSize(int.class);
/*   25 */   private static final MemoryAccessor MEMORY_ACCESSOR = getMemoryAccessor();
/*      */   
/*   27 */   private static final boolean HAS_UNSAFE_BYTEBUFFER_OPERATIONS = supportsUnsafeByteBufferOperations();
/*   28 */   private static final boolean HAS_UNSAFE_ARRAY_OPERATIONS = supportsUnsafeArrayOperations();
/*      */   
/*   30 */   static final long BYTE_ARRAY_BASE_OFFSET = arrayBaseOffset(byte[].class);
/*      */ 
/*      */ 
/*      */   
/*   34 */   private static final long BOOLEAN_ARRAY_BASE_OFFSET = arrayBaseOffset(boolean[].class);
/*   35 */   private static final long BOOLEAN_ARRAY_INDEX_SCALE = arrayIndexScale(boolean[].class);
/*      */   
/*   37 */   private static final long INT_ARRAY_BASE_OFFSET = arrayBaseOffset(int[].class);
/*   38 */   private static final long INT_ARRAY_INDEX_SCALE = arrayIndexScale(int[].class);
/*      */   
/*   40 */   private static final long LONG_ARRAY_BASE_OFFSET = arrayBaseOffset(long[].class);
/*   41 */   private static final long LONG_ARRAY_INDEX_SCALE = arrayIndexScale(long[].class);
/*      */   
/*   43 */   private static final long FLOAT_ARRAY_BASE_OFFSET = arrayBaseOffset(float[].class);
/*   44 */   private static final long FLOAT_ARRAY_INDEX_SCALE = arrayIndexScale(float[].class);
/*      */   
/*   46 */   private static final long DOUBLE_ARRAY_BASE_OFFSET = arrayBaseOffset(double[].class);
/*   47 */   private static final long DOUBLE_ARRAY_INDEX_SCALE = arrayIndexScale(double[].class);
/*      */   
/*   49 */   private static final long OBJECT_ARRAY_BASE_OFFSET = arrayBaseOffset(Object[].class);
/*   50 */   private static final long OBJECT_ARRAY_INDEX_SCALE = arrayIndexScale(Object[].class);
/*      */   
/*   52 */   private static final long BUFFER_ADDRESS_OFFSET = fieldOffset(bufferAddressField());
/*      */   
/*      */   private static final int STRIDE = 8;
/*      */   private static final int STRIDE_ALIGNMENT_MASK = 7;
/*   56 */   private static final int BYTE_ARRAY_ALIGNMENT = (int)(BYTE_ARRAY_BASE_OFFSET & 0x7L);
/*      */ 
/*      */   
/*   59 */   static final boolean IS_BIG_ENDIAN = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean hasUnsafeArrayOperations() {
/*   64 */     return HAS_UNSAFE_ARRAY_OPERATIONS;
/*      */   }
/*      */   
/*      */   static boolean hasUnsafeByteBufferOperations() {
/*   68 */     return HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
/*      */   }
/*      */ 
/*      */   
/*      */   static <T> T allocateInstance(Class<T> clazz) {
/*      */     try {
/*   74 */       return (T)UNSAFE.allocateInstance(clazz);
/*   75 */     } catch (InstantiationException e) {
/*   76 */       throw new IllegalStateException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static long objectFieldOffset(Field field) {
/*   81 */     return MEMORY_ACCESSOR.objectFieldOffset(field);
/*      */   }
/*      */   
/*      */   private static int arrayBaseOffset(Class<?> clazz) {
/*   85 */     return HAS_UNSAFE_ARRAY_OPERATIONS ? MEMORY_ACCESSOR.arrayBaseOffset(clazz) : -1;
/*      */   }
/*      */   
/*      */   private static int arrayIndexScale(Class<?> clazz) {
/*   89 */     return HAS_UNSAFE_ARRAY_OPERATIONS ? MEMORY_ACCESSOR.arrayIndexScale(clazz) : -1;
/*      */   }
/*      */   
/*      */   static byte getByte(Object target, long offset) {
/*   93 */     return MEMORY_ACCESSOR.getByte(target, offset);
/*      */   }
/*      */   
/*      */   static void putByte(Object target, long offset, byte value) {
/*   97 */     MEMORY_ACCESSOR.putByte(target, offset, value);
/*      */   }
/*      */   
/*      */   static int getInt(Object target, long offset) {
/*  101 */     return MEMORY_ACCESSOR.getInt(target, offset);
/*      */   }
/*      */   
/*      */   static void putInt(Object target, long offset, int value) {
/*  105 */     MEMORY_ACCESSOR.putInt(target, offset, value);
/*      */   }
/*      */   
/*      */   static long getLong(Object target, long offset) {
/*  109 */     return MEMORY_ACCESSOR.getLong(target, offset);
/*      */   }
/*      */   
/*      */   static void putLong(Object target, long offset, long value) {
/*  113 */     MEMORY_ACCESSOR.putLong(target, offset, value);
/*      */   }
/*      */   
/*      */   static boolean getBoolean(Object target, long offset) {
/*  117 */     return MEMORY_ACCESSOR.getBoolean(target, offset);
/*      */   }
/*      */   
/*      */   static void putBoolean(Object target, long offset, boolean value) {
/*  121 */     MEMORY_ACCESSOR.putBoolean(target, offset, value);
/*      */   }
/*      */   
/*      */   static float getFloat(Object target, long offset) {
/*  125 */     return MEMORY_ACCESSOR.getFloat(target, offset);
/*      */   }
/*      */   
/*      */   static void putFloat(Object target, long offset, float value) {
/*  129 */     MEMORY_ACCESSOR.putFloat(target, offset, value);
/*      */   }
/*      */   
/*      */   static double getDouble(Object target, long offset) {
/*  133 */     return MEMORY_ACCESSOR.getDouble(target, offset);
/*      */   }
/*      */   
/*      */   static void putDouble(Object target, long offset, double value) {
/*  137 */     MEMORY_ACCESSOR.putDouble(target, offset, value);
/*      */   }
/*      */   
/*      */   static Object getObject(Object target, long offset) {
/*  141 */     return MEMORY_ACCESSOR.getObject(target, offset);
/*      */   }
/*      */   
/*      */   static void putObject(Object target, long offset, Object value) {
/*  145 */     MEMORY_ACCESSOR.putObject(target, offset, value);
/*      */   }
/*      */   
/*      */   static byte getByte(byte[] target, long index) {
/*  149 */     return MEMORY_ACCESSOR.getByte(target, BYTE_ARRAY_BASE_OFFSET + index);
/*      */   }
/*      */   
/*      */   static void putByte(byte[] target, long index, byte value) {
/*  153 */     MEMORY_ACCESSOR.putByte(target, BYTE_ARRAY_BASE_OFFSET + index, value);
/*      */   }
/*      */   
/*      */   static int getInt(int[] target, long index) {
/*  157 */     return MEMORY_ACCESSOR.getInt(target, INT_ARRAY_BASE_OFFSET + index * INT_ARRAY_INDEX_SCALE);
/*      */   }
/*      */   
/*      */   static void putInt(int[] target, long index, int value) {
/*  161 */     MEMORY_ACCESSOR.putInt(target, INT_ARRAY_BASE_OFFSET + index * INT_ARRAY_INDEX_SCALE, value);
/*      */   }
/*      */   
/*      */   static long getLong(long[] target, long index) {
/*  165 */     return MEMORY_ACCESSOR.getLong(target, LONG_ARRAY_BASE_OFFSET + index * LONG_ARRAY_INDEX_SCALE);
/*      */   }
/*      */ 
/*      */   
/*      */   static void putLong(long[] target, long index, long value) {
/*  170 */     MEMORY_ACCESSOR.putLong(target, LONG_ARRAY_BASE_OFFSET + index * LONG_ARRAY_INDEX_SCALE, value);
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean getBoolean(boolean[] target, long index) {
/*  175 */     return MEMORY_ACCESSOR.getBoolean(target, BOOLEAN_ARRAY_BASE_OFFSET + index * BOOLEAN_ARRAY_INDEX_SCALE);
/*      */   }
/*      */ 
/*      */   
/*      */   static void putBoolean(boolean[] target, long index, boolean value) {
/*  180 */     MEMORY_ACCESSOR.putBoolean(target, BOOLEAN_ARRAY_BASE_OFFSET + index * BOOLEAN_ARRAY_INDEX_SCALE, value);
/*      */   }
/*      */ 
/*      */   
/*      */   static float getFloat(float[] target, long index) {
/*  185 */     return MEMORY_ACCESSOR.getFloat(target, FLOAT_ARRAY_BASE_OFFSET + index * FLOAT_ARRAY_INDEX_SCALE);
/*      */   }
/*      */ 
/*      */   
/*      */   static void putFloat(float[] target, long index, float value) {
/*  190 */     MEMORY_ACCESSOR.putFloat(target, FLOAT_ARRAY_BASE_OFFSET + index * FLOAT_ARRAY_INDEX_SCALE, value);
/*      */   }
/*      */ 
/*      */   
/*      */   static double getDouble(double[] target, long index) {
/*  195 */     return MEMORY_ACCESSOR.getDouble(target, DOUBLE_ARRAY_BASE_OFFSET + index * DOUBLE_ARRAY_INDEX_SCALE);
/*      */   }
/*      */ 
/*      */   
/*      */   static void putDouble(double[] target, long index, double value) {
/*  200 */     MEMORY_ACCESSOR.putDouble(target, DOUBLE_ARRAY_BASE_OFFSET + index * DOUBLE_ARRAY_INDEX_SCALE, value);
/*      */   }
/*      */ 
/*      */   
/*      */   static Object getObject(Object[] target, long index) {
/*  205 */     return MEMORY_ACCESSOR.getObject(target, OBJECT_ARRAY_BASE_OFFSET + index * OBJECT_ARRAY_INDEX_SCALE);
/*      */   }
/*      */ 
/*      */   
/*      */   static void putObject(Object[] target, long index, Object value) {
/*  210 */     MEMORY_ACCESSOR.putObject(target, OBJECT_ARRAY_BASE_OFFSET + index * OBJECT_ARRAY_INDEX_SCALE, value);
/*      */   }
/*      */ 
/*      */   
/*      */   static void copyMemory(byte[] src, long srcIndex, long targetOffset, long length) {
/*  215 */     MEMORY_ACCESSOR.copyMemory(src, srcIndex, targetOffset, length);
/*      */   }
/*      */   
/*      */   static void copyMemory(long srcOffset, byte[] target, long targetIndex, long length) {
/*  219 */     MEMORY_ACCESSOR.copyMemory(srcOffset, target, targetIndex, length);
/*      */   }
/*      */   
/*      */   static void copyMemory(byte[] src, long srcIndex, byte[] target, long targetIndex, long length) {
/*  223 */     System.arraycopy(src, (int)srcIndex, target, (int)targetIndex, (int)length);
/*      */   }
/*      */   
/*      */   static byte getByte(long address) {
/*  227 */     return MEMORY_ACCESSOR.getByte(address);
/*      */   }
/*      */   
/*      */   static void putByte(long address, byte value) {
/*  231 */     MEMORY_ACCESSOR.putByte(address, value);
/*      */   }
/*      */   
/*      */   static int getInt(long address) {
/*  235 */     return MEMORY_ACCESSOR.getInt(address);
/*      */   }
/*      */   
/*      */   static void putInt(long address, int value) {
/*  239 */     MEMORY_ACCESSOR.putInt(address, value);
/*      */   }
/*      */   
/*      */   static long getLong(long address) {
/*  243 */     return MEMORY_ACCESSOR.getLong(address);
/*      */   }
/*      */   
/*      */   static void putLong(long address, long value) {
/*  247 */     MEMORY_ACCESSOR.putLong(address, value);
/*      */   }
/*      */ 
/*      */   
/*      */   static long addressOffset(ByteBuffer buffer) {
/*  252 */     return MEMORY_ACCESSOR.getLong(buffer, BUFFER_ADDRESS_OFFSET);
/*      */   }
/*      */   
/*      */   static Object getStaticObject(Field field) {
/*  256 */     return MEMORY_ACCESSOR.getStaticObject(field);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Unsafe getUnsafe() {
/*  263 */     Unsafe unsafe = null;
/*      */     
/*      */     try {
/*  266 */       unsafe = AccessController.<Unsafe>doPrivileged(new PrivilegedExceptionAction<Unsafe>()
/*      */           {
/*      */             public Unsafe run() throws Exception
/*      */             {
/*  270 */               Class<Unsafe> k = Unsafe.class;
/*      */               
/*  272 */               for (Field f : k.getDeclaredFields()) {
/*  273 */                 f.setAccessible(true);
/*  274 */                 Object x = f.get(null);
/*  275 */                 if (k.isInstance(x)) {
/*  276 */                   return k.cast(x);
/*      */                 }
/*      */               } 
/*      */               
/*  280 */               return null;
/*      */             }
/*      */           });
/*  283 */     } catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */     
/*  287 */     return unsafe;
/*      */   }
/*      */ 
/*      */   
/*      */   private static MemoryAccessor getMemoryAccessor() {
/*  292 */     if (UNSAFE == null) {
/*  293 */       return null;
/*      */     }
/*  295 */     if (Android.isOnAndroidDevice()) {
/*  296 */       if (IS_ANDROID_64)
/*  297 */         return new Android64MemoryAccessor(UNSAFE); 
/*  298 */       if (IS_ANDROID_32) {
/*  299 */         return new Android32MemoryAccessor(UNSAFE);
/*      */       }
/*  301 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  305 */     return new JvmMemoryAccessor(UNSAFE);
/*      */   }
/*      */   
/*      */   private static boolean supportsUnsafeArrayOperations() {
/*  309 */     if (MEMORY_ACCESSOR == null) {
/*  310 */       return false;
/*      */     }
/*  312 */     return MEMORY_ACCESSOR.supportsUnsafeArrayOperations();
/*      */   }
/*      */   
/*      */   private static boolean supportsUnsafeByteBufferOperations() {
/*  316 */     if (MEMORY_ACCESSOR == null) {
/*  317 */       return false;
/*      */     }
/*  319 */     return MEMORY_ACCESSOR.supportsUnsafeByteBufferOperations();
/*      */   }
/*      */   
/*      */   static boolean determineAndroidSupportByAddressSize(Class<?> addressClass) {
/*  323 */     if (!Android.isOnAndroidDevice()) {
/*  324 */       return false;
/*      */     }
/*      */     try {
/*  327 */       Class<?> clazz = MEMORY_CLASS;
/*  328 */       clazz.getMethod("peekLong", new Class[] { addressClass, boolean.class });
/*  329 */       clazz.getMethod("pokeLong", new Class[] { addressClass, long.class, boolean.class });
/*  330 */       clazz.getMethod("pokeInt", new Class[] { addressClass, int.class, boolean.class });
/*  331 */       clazz.getMethod("peekInt", new Class[] { addressClass, boolean.class });
/*  332 */       clazz.getMethod("pokeByte", new Class[] { addressClass, byte.class });
/*  333 */       clazz.getMethod("peekByte", new Class[] { addressClass });
/*  334 */       clazz.getMethod("pokeByteArray", new Class[] { addressClass, byte[].class, int.class, int.class });
/*  335 */       clazz.getMethod("peekByteArray", new Class[] { addressClass, byte[].class, int.class, int.class });
/*  336 */       return true;
/*  337 */     } catch (Throwable t) {
/*  338 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Field bufferAddressField() {
/*  344 */     if (Android.isOnAndroidDevice()) {
/*      */ 
/*      */       
/*  347 */       Field field1 = field(Buffer.class, "effectiveDirectAddress");
/*  348 */       if (field1 != null) {
/*  349 */         return field1;
/*      */       }
/*      */     } 
/*  352 */     Field field = field(Buffer.class, "address");
/*  353 */     return (field != null && field.getType() == long.class) ? field : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int firstDifferingByteIndexNativeEndian(long left, long right) {
/*  367 */     int n = IS_BIG_ENDIAN ? Long.numberOfLeadingZeros(left ^ right) : Long.numberOfTrailingZeros(left ^ right);
/*  368 */     return n >> 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int mismatch(byte[] left, int leftOff, byte[] right, int rightOff, int length) {
/*  380 */     if (leftOff < 0 || rightOff < 0 || length < 0 || leftOff + length > left.length || rightOff + length > right.length)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  385 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*  388 */     int index = 0;
/*  389 */     if (HAS_UNSAFE_ARRAY_OPERATIONS) {
/*  390 */       int leftAlignment = BYTE_ARRAY_ALIGNMENT + leftOff & 0x7;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  397 */       for (; index < length && (leftAlignment & 0x7) != 0; 
/*  398 */         index++, leftAlignment++) {
/*  399 */         if (left[leftOff + index] != right[rightOff + index]) {
/*  400 */           return index;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  406 */       int strideLength = (length - index & 0xFFFFFFF8) + index;
/*      */ 
/*      */ 
/*      */       
/*  410 */       for (; index < strideLength; index += 8) {
/*  411 */         long leftLongWord = getLong(left, BYTE_ARRAY_BASE_OFFSET + leftOff + index);
/*  412 */         long rightLongWord = getLong(right, BYTE_ARRAY_BASE_OFFSET + rightOff + index);
/*  413 */         if (leftLongWord != rightLongWord)
/*      */         {
/*  415 */           return index + firstDifferingByteIndexNativeEndian(leftLongWord, rightLongWord);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  422 */     for (; index < length; index++) {
/*  423 */       if (left[leftOff + index] != right[rightOff + index]) {
/*  424 */         return index;
/*      */       }
/*      */     } 
/*  427 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long fieldOffset(Field field) {
/*  435 */     return (field == null || MEMORY_ACCESSOR == null) ? -1L : MEMORY_ACCESSOR.objectFieldOffset(field);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Field field(Class<?> clazz, String fieldName) {
/*      */     Field field;
/*      */     try {
/*  444 */       field = clazz.getDeclaredField(fieldName);
/*  445 */     } catch (Throwable t) {
/*      */       
/*  447 */       field = null;
/*      */     } 
/*  449 */     return field;
/*      */   }
/*      */   
/*      */   private static abstract class MemoryAccessor
/*      */   {
/*      */     Unsafe unsafe;
/*      */     
/*      */     MemoryAccessor(Unsafe unsafe) {
/*  457 */       this.unsafe = unsafe;
/*      */     }
/*      */     
/*      */     public final long objectFieldOffset(Field field) {
/*  461 */       return this.unsafe.objectFieldOffset(field);
/*      */     }
/*      */     
/*      */     public final int arrayBaseOffset(Class<?> clazz) {
/*  465 */       return this.unsafe.arrayBaseOffset(clazz);
/*      */     }
/*      */     
/*      */     public final int arrayIndexScale(Class<?> clazz) {
/*  469 */       return this.unsafe.arrayIndexScale(clazz);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract Object getStaticObject(Field param1Field);
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean supportsUnsafeArrayOperations() {
/*  479 */       if (this.unsafe == null) {
/*  480 */         return false;
/*      */       }
/*      */       try {
/*  483 */         Class<?> clazz = this.unsafe.getClass();
/*  484 */         clazz.getMethod("objectFieldOffset", new Class[] { Field.class });
/*  485 */         clazz.getMethod("arrayBaseOffset", new Class[] { Class.class });
/*  486 */         clazz.getMethod("arrayIndexScale", new Class[] { Class.class });
/*  487 */         clazz.getMethod("getInt", new Class[] { Object.class, long.class });
/*  488 */         clazz.getMethod("putInt", new Class[] { Object.class, long.class, int.class });
/*  489 */         clazz.getMethod("getLong", new Class[] { Object.class, long.class });
/*  490 */         clazz.getMethod("putLong", new Class[] { Object.class, long.class, long.class });
/*  491 */         clazz.getMethod("getObject", new Class[] { Object.class, long.class });
/*  492 */         clazz.getMethod("putObject", new Class[] { Object.class, long.class, Object.class });
/*      */         
/*  494 */         return true;
/*  495 */       } catch (Throwable e) {
/*  496 */         UnsafeUtil.logMissingMethod(e);
/*      */         
/*  498 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public abstract byte getByte(Object param1Object, long param1Long);
/*      */     
/*      */     public final int getInt(Object target, long offset) {
/*  506 */       return this.unsafe.getInt(target, offset);
/*      */     }
/*      */     public abstract void putByte(Object param1Object, long param1Long, byte param1Byte);
/*      */     public final void putInt(Object target, long offset, int value) {
/*  510 */       this.unsafe.putInt(target, offset, value);
/*      */     }
/*      */     
/*      */     public final long getLong(Object target, long offset) {
/*  514 */       return this.unsafe.getLong(target, offset);
/*      */     }
/*      */     
/*      */     public final void putLong(Object target, long offset, long value) {
/*  518 */       this.unsafe.putLong(target, offset, value);
/*      */     }
/*      */     
/*      */     public abstract boolean getBoolean(Object param1Object, long param1Long);
/*      */     
/*      */     public abstract void putBoolean(Object param1Object, long param1Long, boolean param1Boolean);
/*      */     
/*      */     public abstract float getFloat(Object param1Object, long param1Long);
/*      */     
/*      */     public abstract void putFloat(Object param1Object, long param1Long, float param1Float);
/*      */     
/*      */     public abstract double getDouble(Object param1Object, long param1Long);
/*      */     
/*      */     public abstract void putDouble(Object param1Object, long param1Long, double param1Double);
/*      */     
/*      */     public final Object getObject(Object target, long offset) {
/*  534 */       return this.unsafe.getObject(target, offset);
/*      */     }
/*      */     
/*      */     public final void putObject(Object target, long offset, Object value) {
/*  538 */       this.unsafe.putObject(target, offset, value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean supportsUnsafeByteBufferOperations() {
/*  546 */       if (this.unsafe == null) {
/*  547 */         return false;
/*      */       }
/*      */       try {
/*  550 */         Class<?> clazz = this.unsafe.getClass();
/*      */         
/*  552 */         clazz.getMethod("objectFieldOffset", new Class[] { Field.class });
/*  553 */         clazz.getMethod("getLong", new Class[] { Object.class, long.class });
/*      */         
/*  555 */         if (UnsafeUtil.bufferAddressField() == null) {
/*  556 */           return false;
/*      */         }
/*      */         
/*  559 */         return true;
/*  560 */       } catch (Throwable e) {
/*  561 */         UnsafeUtil.logMissingMethod(e);
/*      */         
/*  563 */         return false;
/*      */       } 
/*      */     }
/*      */     
/*      */     public abstract byte getByte(long param1Long);
/*      */     
/*      */     public abstract void putByte(long param1Long, byte param1Byte);
/*      */     
/*      */     public abstract int getInt(long param1Long);
/*      */     
/*      */     public abstract void putInt(long param1Long, int param1Int);
/*      */     
/*      */     public abstract long getLong(long param1Long);
/*      */     
/*      */     public abstract void putLong(long param1Long1, long param1Long2);
/*      */     
/*      */     public abstract void copyMemory(long param1Long1, byte[] param1ArrayOfbyte, long param1Long2, long param1Long3);
/*      */     
/*      */     public abstract void copyMemory(byte[] param1ArrayOfbyte, long param1Long1, long param1Long2, long param1Long3);
/*      */   }
/*      */   
/*      */   private static final class JvmMemoryAccessor extends MemoryAccessor {
/*      */     JvmMemoryAccessor(Unsafe unsafe) {
/*  586 */       super(unsafe);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getStaticObject(Field field) {
/*  591 */       return getObject(this.unsafe.staticFieldBase(field), this.unsafe.staticFieldOffset(field));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean supportsUnsafeArrayOperations() {
/*  596 */       if (!super.supportsUnsafeArrayOperations()) {
/*  597 */         return false;
/*      */       }
/*      */       
/*      */       try {
/*  601 */         Class<?> clazz = this.unsafe.getClass();
/*  602 */         clazz.getMethod("getByte", new Class[] { Object.class, long.class });
/*  603 */         clazz.getMethod("putByte", new Class[] { Object.class, long.class, byte.class });
/*  604 */         clazz.getMethod("getBoolean", new Class[] { Object.class, long.class });
/*  605 */         clazz.getMethod("putBoolean", new Class[] { Object.class, long.class, boolean.class });
/*  606 */         clazz.getMethod("getFloat", new Class[] { Object.class, long.class });
/*  607 */         clazz.getMethod("putFloat", new Class[] { Object.class, long.class, float.class });
/*  608 */         clazz.getMethod("getDouble", new Class[] { Object.class, long.class });
/*  609 */         clazz.getMethod("putDouble", new Class[] { Object.class, long.class, double.class });
/*      */         
/*  611 */         return true;
/*  612 */       } catch (Throwable e) {
/*  613 */         UnsafeUtil.logMissingMethod(e);
/*      */         
/*  615 */         return false;
/*      */       } 
/*      */     }
/*      */     
/*      */     public byte getByte(Object target, long offset) {
/*  620 */       return this.unsafe.getByte(target, offset);
/*      */     }
/*      */ 
/*      */     
/*      */     public void putByte(Object target, long offset, byte value) {
/*  625 */       this.unsafe.putByte(target, offset, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBoolean(Object target, long offset) {
/*  630 */       return this.unsafe.getBoolean(target, offset);
/*      */     }
/*      */ 
/*      */     
/*      */     public void putBoolean(Object target, long offset, boolean value) {
/*  635 */       this.unsafe.putBoolean(target, offset, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFloat(Object target, long offset) {
/*  640 */       return this.unsafe.getFloat(target, offset);
/*      */     }
/*      */ 
/*      */     
/*      */     public void putFloat(Object target, long offset, float value) {
/*  645 */       this.unsafe.putFloat(target, offset, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDouble(Object target, long offset) {
/*  650 */       return this.unsafe.getDouble(target, offset);
/*      */     }
/*      */ 
/*      */     
/*      */     public void putDouble(Object target, long offset, double value) {
/*  655 */       this.unsafe.putDouble(target, offset, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean supportsUnsafeByteBufferOperations() {
/*  660 */       if (!super.supportsUnsafeByteBufferOperations()) {
/*  661 */         return false;
/*      */       }
/*      */       
/*      */       try {
/*  665 */         Class<?> clazz = this.unsafe.getClass();
/*  666 */         clazz.getMethod("getByte", new Class[] { long.class });
/*  667 */         clazz.getMethod("putByte", new Class[] { long.class, byte.class });
/*  668 */         clazz.getMethod("getInt", new Class[] { long.class });
/*  669 */         clazz.getMethod("putInt", new Class[] { long.class, int.class });
/*  670 */         clazz.getMethod("getLong", new Class[] { long.class });
/*  671 */         clazz.getMethod("putLong", new Class[] { long.class, long.class });
/*  672 */         clazz.getMethod("copyMemory", new Class[] { long.class, long.class, long.class });
/*  673 */         clazz.getMethod("copyMemory", new Class[] { Object.class, long.class, Object.class, long.class, long.class });
/*      */         
/*  675 */         return true;
/*  676 */       } catch (Throwable e) {
/*  677 */         UnsafeUtil.logMissingMethod(e);
/*      */         
/*  679 */         return false;
/*      */       } 
/*      */     }
/*      */     
/*      */     public byte getByte(long address) {
/*  684 */       return this.unsafe.getByte(address);
/*      */     }
/*      */ 
/*      */     
/*      */     public void putByte(long address, byte value) {
/*  689 */       this.unsafe.putByte(address, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getInt(long address) {
/*  694 */       return this.unsafe.getInt(address);
/*      */     }
/*      */ 
/*      */     
/*      */     public void putInt(long address, int value) {
/*  699 */       this.unsafe.putInt(address, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLong(long address) {
/*  704 */       return this.unsafe.getLong(address);
/*      */     }
/*      */ 
/*      */     
/*      */     public void putLong(long address, long value) {
/*  709 */       this.unsafe.putLong(address, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void copyMemory(long srcOffset, byte[] target, long targetIndex, long length) {
/*  714 */       this.unsafe.copyMemory(null, srcOffset, target, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + targetIndex, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public void copyMemory(byte[] src, long srcIndex, long targetOffset, long length) {
/*  719 */       this.unsafe.copyMemory(src, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + srcIndex, null, targetOffset, length);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class Android64MemoryAccessor
/*      */     extends MemoryAccessor {
/*      */     Android64MemoryAccessor(Unsafe unsafe) {
/*  726 */       super(unsafe);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getStaticObject(Field field) {
/*      */       try {
/*  732 */         return field.get(null);
/*  733 */       } catch (IllegalAccessException e) {
/*  734 */         return null;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByte(Object target, long offset) {
/*  740 */       if (UnsafeUtil.IS_BIG_ENDIAN) {
/*  741 */         return UnsafeUtil.getByteBigEndian(target, offset);
/*      */       }
/*  743 */       return UnsafeUtil.getByteLittleEndian(target, offset);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void putByte(Object target, long offset, byte value) {
/*  749 */       if (UnsafeUtil.IS_BIG_ENDIAN) {
/*  750 */         UnsafeUtil.putByteBigEndian(target, offset, value);
/*      */       } else {
/*  752 */         UnsafeUtil.putByteLittleEndian(target, offset, value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBoolean(Object target, long offset) {
/*  758 */       if (UnsafeUtil.IS_BIG_ENDIAN) {
/*  759 */         return UnsafeUtil.getBooleanBigEndian(target, offset);
/*      */       }
/*  761 */       return UnsafeUtil.getBooleanLittleEndian(target, offset);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void putBoolean(Object target, long offset, boolean value) {
/*  767 */       if (UnsafeUtil.IS_BIG_ENDIAN) {
/*  768 */         UnsafeUtil.putBooleanBigEndian(target, offset, value);
/*      */       } else {
/*  770 */         UnsafeUtil.putBooleanLittleEndian(target, offset, value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFloat(Object target, long offset) {
/*  776 */       return Float.intBitsToFloat(getInt(target, offset));
/*      */     }
/*      */ 
/*      */     
/*      */     public void putFloat(Object target, long offset, float value) {
/*  781 */       putInt(target, offset, Float.floatToIntBits(value));
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDouble(Object target, long offset) {
/*  786 */       return Double.longBitsToDouble(getLong(target, offset));
/*      */     }
/*      */ 
/*      */     
/*      */     public void putDouble(Object target, long offset, double value) {
/*  791 */       putLong(target, offset, Double.doubleToLongBits(value));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean supportsUnsafeByteBufferOperations() {
/*  796 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByte(long address) {
/*  801 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void putByte(long address, byte value) {
/*  806 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getInt(long address) {
/*  811 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void putInt(long address, int value) {
/*  816 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLong(long address) {
/*  821 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void putLong(long address, long value) {
/*  826 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void copyMemory(long srcOffset, byte[] target, long targetIndex, long length) {
/*  831 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void copyMemory(byte[] src, long srcIndex, long targetOffset, long length) {
/*  836 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class Android32MemoryAccessor
/*      */     extends MemoryAccessor
/*      */   {
/*      */     private static final long SMALL_ADDRESS_MASK = -1L;
/*      */     
/*      */     private static int smallAddress(long address) {
/*  847 */       return (int)(0xFFFFFFFFFFFFFFFFL & address);
/*      */     }
/*      */     
/*      */     Android32MemoryAccessor(Unsafe unsafe) {
/*  851 */       super(unsafe);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getStaticObject(Field field) {
/*      */       try {
/*  857 */         return field.get(null);
/*  858 */       } catch (IllegalAccessException e) {
/*  859 */         return null;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByte(Object target, long offset) {
/*  865 */       if (UnsafeUtil.IS_BIG_ENDIAN) {
/*  866 */         return UnsafeUtil.getByteBigEndian(target, offset);
/*      */       }
/*  868 */       return UnsafeUtil.getByteLittleEndian(target, offset);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void putByte(Object target, long offset, byte value) {
/*  874 */       if (UnsafeUtil.IS_BIG_ENDIAN) {
/*  875 */         UnsafeUtil.putByteBigEndian(target, offset, value);
/*      */       } else {
/*  877 */         UnsafeUtil.putByteLittleEndian(target, offset, value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBoolean(Object target, long offset) {
/*  883 */       if (UnsafeUtil.IS_BIG_ENDIAN) {
/*  884 */         return UnsafeUtil.getBooleanBigEndian(target, offset);
/*      */       }
/*  886 */       return UnsafeUtil.getBooleanLittleEndian(target, offset);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void putBoolean(Object target, long offset, boolean value) {
/*  892 */       if (UnsafeUtil.IS_BIG_ENDIAN) {
/*  893 */         UnsafeUtil.putBooleanBigEndian(target, offset, value);
/*      */       } else {
/*  895 */         UnsafeUtil.putBooleanLittleEndian(target, offset, value);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFloat(Object target, long offset) {
/*  901 */       return Float.intBitsToFloat(getInt(target, offset));
/*      */     }
/*      */ 
/*      */     
/*      */     public void putFloat(Object target, long offset, float value) {
/*  906 */       putInt(target, offset, Float.floatToIntBits(value));
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDouble(Object target, long offset) {
/*  911 */       return Double.longBitsToDouble(getLong(target, offset));
/*      */     }
/*      */ 
/*      */     
/*      */     public void putDouble(Object target, long offset, double value) {
/*  916 */       putLong(target, offset, Double.doubleToLongBits(value));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean supportsUnsafeByteBufferOperations() {
/*  921 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByte(long address) {
/*  926 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void putByte(long address, byte value) {
/*  931 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getInt(long address) {
/*  936 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void putInt(long address, int value) {
/*  941 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLong(long address) {
/*  946 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void putLong(long address, long value) {
/*  951 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void copyMemory(long srcOffset, byte[] target, long targetIndex, long length) {
/*  956 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void copyMemory(byte[] src, long srcIndex, long targetOffset, long length) {
/*  961 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   private static byte getByteBigEndian(Object target, long offset) {
/*  966 */     return (byte)(getInt(target, offset & 0xFFFFFFFFFFFFFFFCL) >>> (int)(((offset ^ 0xFFFFFFFFFFFFFFFFL) & 0x3L) << 3L) & 0xFF);
/*      */   }
/*      */   
/*      */   private static byte getByteLittleEndian(Object target, long offset) {
/*  970 */     return (byte)(getInt(target, offset & 0xFFFFFFFFFFFFFFFCL) >>> (int)((offset & 0x3L) << 3L) & 0xFF);
/*      */   }
/*      */   
/*      */   private static void putByteBigEndian(Object target, long offset, byte value) {
/*  974 */     int intValue = getInt(target, offset & 0xFFFFFFFFFFFFFFFCL);
/*  975 */     int shift = (((int)offset ^ 0xFFFFFFFF) & 0x3) << 3;
/*  976 */     int output = intValue & (255 << shift ^ 0xFFFFFFFF) | (0xFF & value) << shift;
/*  977 */     putInt(target, offset & 0xFFFFFFFFFFFFFFFCL, output);
/*      */   }
/*      */   
/*      */   private static void putByteLittleEndian(Object target, long offset, byte value) {
/*  981 */     int intValue = getInt(target, offset & 0xFFFFFFFFFFFFFFFCL);
/*  982 */     int shift = ((int)offset & 0x3) << 3;
/*  983 */     int output = intValue & (255 << shift ^ 0xFFFFFFFF) | (0xFF & value) << shift;
/*  984 */     putInt(target, offset & 0xFFFFFFFFFFFFFFFCL, output);
/*      */   }
/*      */   
/*      */   private static boolean getBooleanBigEndian(Object target, long offset) {
/*  988 */     return (getByteBigEndian(target, offset) != 0);
/*      */   }
/*      */   
/*      */   private static boolean getBooleanLittleEndian(Object target, long offset) {
/*  992 */     return (getByteLittleEndian(target, offset) != 0);
/*      */   }
/*      */   
/*      */   private static void putBooleanBigEndian(Object target, long offset, boolean value) {
/*  996 */     putByteBigEndian(target, offset, (byte)(value ? 1 : 0));
/*      */   }
/*      */   
/*      */   private static void putBooleanLittleEndian(Object target, long offset, boolean value) {
/* 1000 */     putByteLittleEndian(target, offset, (byte)(value ? 1 : 0));
/*      */   }
/*      */   
/*      */   private static void logMissingMethod(Throwable e) {
/* 1004 */     Logger.getLogger(UnsafeUtil.class.getName())
/* 1005 */       .log(Level.WARNING, "platform method missing - proto runtime falling back to safer methods: " + e);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\UnsafeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
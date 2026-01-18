/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CheckReturnValue
/*     */ final class FieldInfo
/*     */   implements Comparable<FieldInfo>
/*     */ {
/*     */   private final Field field;
/*     */   private final FieldType type;
/*     */   private final Class<?> messageClass;
/*     */   private final int fieldNumber;
/*     */   private final Field presenceField;
/*     */   private final int presenceMask;
/*     */   private final boolean required;
/*     */   private final boolean enforceUtf8;
/*     */   private final OneofInfo oneof;
/*     */   private final Field cachedSizeField;
/*     */   private final Class<?> oneofStoredType;
/*     */   private final Object mapDefaultEntry;
/*     */   private final Internal.EnumVerifier enumVerifier;
/*     */   
/*     */   public static FieldInfo forField(Field field, int fieldNumber, FieldType fieldType, boolean enforceUtf8) {
/*  44 */     checkFieldNumber(fieldNumber);
/*  45 */     Internal.checkNotNull(field, "field");
/*  46 */     Internal.checkNotNull(fieldType, "fieldType");
/*  47 */     if (fieldType == FieldType.MESSAGE_LIST || fieldType == FieldType.GROUP_LIST) {
/*  48 */       throw new IllegalStateException("Shouldn't be called for repeated message fields.");
/*     */     }
/*  50 */     return new FieldInfo(field, fieldNumber, fieldType, null, null, 0, false, enforceUtf8, null, null, null, null, null);
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
/*     */   public static FieldInfo forPackedField(Field field, int fieldNumber, FieldType fieldType, Field cachedSizeField) {
/*  69 */     checkFieldNumber(fieldNumber);
/*  70 */     Internal.checkNotNull(field, "field");
/*  71 */     Internal.checkNotNull(fieldType, "fieldType");
/*  72 */     if (fieldType == FieldType.MESSAGE_LIST || fieldType == FieldType.GROUP_LIST) {
/*  73 */       throw new IllegalStateException("Shouldn't be called for repeated message fields.");
/*     */     }
/*  75 */     return new FieldInfo(field, fieldNumber, fieldType, null, null, 0, false, false, null, null, null, null, cachedSizeField);
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
/*     */   public static FieldInfo forRepeatedMessageField(Field field, int fieldNumber, FieldType fieldType, Class<?> messageClass) {
/*  94 */     checkFieldNumber(fieldNumber);
/*  95 */     Internal.checkNotNull(field, "field");
/*  96 */     Internal.checkNotNull(fieldType, "fieldType");
/*  97 */     Internal.checkNotNull(messageClass, "messageClass");
/*  98 */     return new FieldInfo(field, fieldNumber, fieldType, messageClass, null, 0, false, false, null, null, null, null, null);
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
/*     */   public static FieldInfo forFieldWithEnumVerifier(Field field, int fieldNumber, FieldType fieldType, Internal.EnumVerifier enumVerifier) {
/* 116 */     checkFieldNumber(fieldNumber);
/* 117 */     Internal.checkNotNull(field, "field");
/* 118 */     return new FieldInfo(field, fieldNumber, fieldType, null, null, 0, false, false, null, null, null, enumVerifier, null);
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
/*     */   public static FieldInfo forPackedFieldWithEnumVerifier(Field field, int fieldNumber, FieldType fieldType, Internal.EnumVerifier enumVerifier, Field cachedSizeField) {
/* 140 */     checkFieldNumber(fieldNumber);
/* 141 */     Internal.checkNotNull(field, "field");
/* 142 */     return new FieldInfo(field, fieldNumber, fieldType, null, null, 0, false, false, null, null, null, enumVerifier, cachedSizeField);
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
/*     */   public static FieldInfo forExplicitPresenceField(Field field, int fieldNumber, FieldType fieldType, Field presenceField, int presenceMask, boolean enforceUtf8, Internal.EnumVerifier enumVerifier) {
/* 167 */     checkFieldNumber(fieldNumber);
/* 168 */     Internal.checkNotNull(field, "field");
/* 169 */     Internal.checkNotNull(fieldType, "fieldType");
/* 170 */     Internal.checkNotNull(presenceField, "presenceField");
/* 171 */     if (presenceField != null && !isExactlyOneBitSet(presenceMask)) {
/* 172 */       throw new IllegalArgumentException("presenceMask must have exactly one bit set: " + presenceMask);
/*     */     }
/*     */     
/* 175 */     return new FieldInfo(field, fieldNumber, fieldType, null, presenceField, presenceMask, false, enforceUtf8, null, null, null, enumVerifier, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FieldInfo forOneofMemberField(int fieldNumber, FieldType fieldType, OneofInfo oneof, Class<?> oneofStoredType, boolean enforceUtf8, Internal.EnumVerifier enumVerifier) {
/* 210 */     checkFieldNumber(fieldNumber);
/* 211 */     Internal.checkNotNull(fieldType, "fieldType");
/* 212 */     Internal.checkNotNull(oneof, "oneof");
/* 213 */     Internal.checkNotNull(oneofStoredType, "oneofStoredType");
/* 214 */     if (!fieldType.isScalar()) {
/* 215 */       throw new IllegalArgumentException("Oneof is only supported for scalar fields. Field " + fieldNumber + " is of type " + fieldType);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     return new FieldInfo(null, fieldNumber, fieldType, null, null, 0, false, enforceUtf8, oneof, oneofStoredType, null, enumVerifier, null);
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
/*     */   private static void checkFieldNumber(int fieldNumber) {
/* 238 */     if (fieldNumber <= 0) {
/* 239 */       throw new IllegalArgumentException("fieldNumber must be positive: " + fieldNumber);
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
/*     */   public static FieldInfo forLegacyRequiredField(Field field, int fieldNumber, FieldType fieldType, Field presenceField, int presenceMask, boolean enforceUtf8, Internal.EnumVerifier enumVerifier) {
/* 252 */     checkFieldNumber(fieldNumber);
/* 253 */     Internal.checkNotNull(field, "field");
/* 254 */     Internal.checkNotNull(fieldType, "fieldType");
/* 255 */     Internal.checkNotNull(presenceField, "presenceField");
/* 256 */     if (presenceField != null && !isExactlyOneBitSet(presenceMask)) {
/* 257 */       throw new IllegalArgumentException("presenceMask must have exactly one bit set: " + presenceMask);
/*     */     }
/*     */     
/* 260 */     return new FieldInfo(field, fieldNumber, fieldType, null, presenceField, presenceMask, true, enforceUtf8, null, null, null, enumVerifier, null);
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
/*     */   public static FieldInfo forMapField(Field field, int fieldNumber, Object mapDefaultEntry, Internal.EnumVerifier enumVerifier) {
/* 278 */     Internal.checkNotNull(mapDefaultEntry, "mapDefaultEntry");
/* 279 */     checkFieldNumber(fieldNumber);
/* 280 */     Internal.checkNotNull(field, "field");
/* 281 */     return new FieldInfo(field, fieldNumber, FieldType.MAP, null, null, 0, false, true, null, null, mapDefaultEntry, enumVerifier, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FieldInfo(Field field, int fieldNumber, FieldType type, Class<?> messageClass, Field presenceField, int presenceMask, boolean required, boolean enforceUtf8, OneofInfo oneof, Class<?> oneofStoredType, Object mapDefaultEntry, Internal.EnumVerifier enumVerifier, Field cachedSizeField) {
/* 311 */     this.field = field;
/* 312 */     this.type = type;
/* 313 */     this.messageClass = messageClass;
/* 314 */     this.fieldNumber = fieldNumber;
/* 315 */     this.presenceField = presenceField;
/* 316 */     this.presenceMask = presenceMask;
/* 317 */     this.required = required;
/* 318 */     this.enforceUtf8 = enforceUtf8;
/* 319 */     this.oneof = oneof;
/* 320 */     this.oneofStoredType = oneofStoredType;
/* 321 */     this.mapDefaultEntry = mapDefaultEntry;
/* 322 */     this.enumVerifier = enumVerifier;
/* 323 */     this.cachedSizeField = cachedSizeField;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFieldNumber() {
/* 328 */     return this.fieldNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   public Field getField() {
/* 333 */     return this.field;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldType getType() {
/* 338 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public OneofInfo getOneof() {
/* 343 */     return this.oneof;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getOneofStoredType() {
/* 352 */     return this.oneofStoredType;
/*     */   }
/*     */ 
/*     */   
/*     */   public Internal.EnumVerifier getEnumVerifier() {
/* 357 */     return this.enumVerifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(FieldInfo o) {
/* 362 */     return this.fieldNumber - o.fieldNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getListElementType() {
/* 370 */     return this.messageClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public Field getPresenceField() {
/* 375 */     return this.presenceField;
/*     */   }
/*     */   
/*     */   public Object getMapDefaultEntry() {
/* 379 */     return this.mapDefaultEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPresenceMask() {
/* 387 */     return this.presenceMask;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRequired() {
/* 392 */     return this.required;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnforceUtf8() {
/* 399 */     return this.enforceUtf8;
/*     */   }
/*     */   
/*     */   public Field getCachedSizeField() {
/* 403 */     return this.cachedSizeField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getMessageFieldClass() {
/* 411 */     switch (this.type) {
/*     */       case MESSAGE:
/*     */       case GROUP:
/* 414 */         return (this.field != null) ? this.field.getType() : this.oneofStoredType;
/*     */       case MESSAGE_LIST:
/*     */       case GROUP_LIST:
/* 417 */         return this.messageClass;
/*     */     } 
/* 419 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Builder newBuilder() {
/* 424 */     return new Builder();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private Field field;
/*     */     
/*     */     private FieldType type;
/*     */     
/*     */     private int fieldNumber;
/*     */     
/*     */     private Field presenceField;
/*     */     private int presenceMask;
/*     */     private boolean required;
/*     */     private boolean enforceUtf8;
/*     */     private OneofInfo oneof;
/*     */     private Class<?> oneofStoredType;
/*     */     private Object mapDefaultEntry;
/*     */     private Internal.EnumVerifier enumVerifier;
/*     */     private Field cachedSizeField;
/*     */     
/*     */     private Builder() {}
/*     */     
/*     */     public Builder withField(Field field) {
/* 449 */       if (this.oneof != null) {
/* 450 */         throw new IllegalStateException("Cannot set field when building a oneof.");
/*     */       }
/* 452 */       this.field = field;
/* 453 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withType(FieldType type) {
/* 458 */       this.type = type;
/* 459 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withFieldNumber(int fieldNumber) {
/* 464 */       this.fieldNumber = fieldNumber;
/* 465 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withPresence(Field presenceField, int presenceMask) {
/* 470 */       this.presenceField = Internal.<Field>checkNotNull(presenceField, "presenceField");
/* 471 */       this.presenceMask = presenceMask;
/* 472 */       return this;
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
/*     */     public Builder withOneof(OneofInfo oneof, Class<?> oneofStoredType) {
/* 484 */       if (this.field != null || this.presenceField != null) {
/* 485 */         throw new IllegalStateException("Cannot set oneof when field or presenceField have been provided");
/*     */       }
/*     */       
/* 488 */       this.oneof = oneof;
/* 489 */       this.oneofStoredType = oneofStoredType;
/* 490 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withRequired(boolean required) {
/* 494 */       this.required = required;
/* 495 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withMapDefaultEntry(Object mapDefaultEntry) {
/* 499 */       this.mapDefaultEntry = mapDefaultEntry;
/* 500 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withEnforceUtf8(boolean enforceUtf8) {
/* 504 */       this.enforceUtf8 = enforceUtf8;
/* 505 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withEnumVerifier(Internal.EnumVerifier enumVerifier) {
/* 509 */       this.enumVerifier = enumVerifier;
/* 510 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withCachedSizeField(Field cachedSizeField) {
/* 514 */       this.cachedSizeField = cachedSizeField;
/* 515 */       return this;
/*     */     }
/*     */     
/*     */     public FieldInfo build() {
/* 519 */       if (this.oneof != null) {
/* 520 */         return FieldInfo.forOneofMemberField(this.fieldNumber, this.type, this.oneof, this.oneofStoredType, this.enforceUtf8, this.enumVerifier);
/*     */       }
/*     */       
/* 523 */       if (this.mapDefaultEntry != null) {
/* 524 */         return FieldInfo.forMapField(this.field, this.fieldNumber, this.mapDefaultEntry, this.enumVerifier);
/*     */       }
/* 526 */       if (this.presenceField != null) {
/* 527 */         if (this.required) {
/* 528 */           return FieldInfo.forLegacyRequiredField(this.field, this.fieldNumber, this.type, this.presenceField, this.presenceMask, this.enforceUtf8, this.enumVerifier);
/*     */         }
/*     */         
/* 531 */         return FieldInfo.forExplicitPresenceField(this.field, this.fieldNumber, this.type, this.presenceField, this.presenceMask, this.enforceUtf8, this.enumVerifier);
/*     */       } 
/*     */ 
/*     */       
/* 535 */       if (this.enumVerifier != null) {
/* 536 */         if (this.cachedSizeField == null) {
/* 537 */           return FieldInfo.forFieldWithEnumVerifier(this.field, this.fieldNumber, this.type, this.enumVerifier);
/*     */         }
/* 539 */         return FieldInfo.forPackedFieldWithEnumVerifier(this.field, this.fieldNumber, this.type, this.enumVerifier, this.cachedSizeField);
/*     */       } 
/*     */ 
/*     */       
/* 543 */       if (this.cachedSizeField == null) {
/* 544 */         return FieldInfo.forField(this.field, this.fieldNumber, this.type, this.enforceUtf8);
/*     */       }
/* 546 */       return FieldInfo.forPackedField(this.field, this.fieldNumber, this.type, this.cachedSizeField);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isExactlyOneBitSet(int value) {
/* 553 */     return (value != 0 && (value & value - 1) == 0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\FieldInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
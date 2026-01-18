/*     */ package com.google.protobuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ final class RawMessageInfo
/*     */   implements MessageInfo
/*     */ {
/*     */   private static final int IS_PROTO2_BIT = 1;
/*     */   private static final int IS_EDITION_BIT = 4;
/*     */   private final MessageLite defaultInstance;
/*     */   private final String info;
/*     */   private final Object[] objects;
/*     */   private final int flags;
/*     */   
/*     */   RawMessageInfo(MessageLite defaultInstance, String info, Object[] objects) {
/* 162 */     this.defaultInstance = defaultInstance;
/* 163 */     this.info = info;
/* 164 */     this.objects = objects;
/* 165 */     int position = 0;
/* 166 */     int value = info.charAt(position++);
/* 167 */     if (value < 55296) {
/* 168 */       this.flags = value;
/*     */     } else {
/* 170 */       int result = value & 0x1FFF;
/* 171 */       int shift = 13;
/* 172 */       while ((value = info.charAt(position++)) >= 55296) {
/* 173 */         result |= (value & 0x1FFF) << shift;
/* 174 */         shift += 13;
/*     */       } 
/* 176 */       this.flags = result | value << shift;
/*     */     } 
/*     */   }
/*     */   
/*     */   String getStringInfo() {
/* 181 */     return this.info;
/*     */   }
/*     */   
/*     */   Object[] getObjects() {
/* 185 */     return this.objects;
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageLite getDefaultInstance() {
/* 190 */     return this.defaultInstance;
/*     */   }
/*     */ 
/*     */   
/*     */   public ProtoSyntax getSyntax() {
/* 195 */     if ((this.flags & 0x1) != 0)
/* 196 */       return ProtoSyntax.PROTO2; 
/* 197 */     if ((this.flags & 0x4) == 4) {
/* 198 */       return ProtoSyntax.EDITIONS;
/*     */     }
/* 200 */     return ProtoSyntax.PROTO3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMessageSetWireFormat() {
/* 206 */     return ((this.flags & 0x2) == 2);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\RawMessageInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
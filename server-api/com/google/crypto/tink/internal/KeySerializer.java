/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import com.google.crypto.tink.Key;
/*    */ import com.google.crypto.tink.SecretKeyAccess;
/*    */ import java.security.GeneralSecurityException;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class KeySerializer<KeyT extends Key, SerializationT extends Serialization>
/*    */ {
/*    */   private final Class<KeyT> keyClass;
/*    */   private final Class<SerializationT> serializationClass;
/*    */   
/*    */   private KeySerializer(Class<KeyT> keyClass, Class<SerializationT> serializationClass) {
/* 47 */     this.keyClass = keyClass;
/* 48 */     this.serializationClass = serializationClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<KeyT> getKeyClass() {
/* 55 */     return this.keyClass;
/*    */   }
/*    */   
/*    */   public Class<SerializationT> getSerializationClass() {
/* 59 */     return this.serializationClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <KeyT extends Key, SerializationT extends Serialization> KeySerializer<KeyT, SerializationT> create(final KeySerializationFunction<KeyT, SerializationT> function, Class<KeyT> keyClass, Class<SerializationT> serializationClass) {
/* 92 */     return new KeySerializer<KeyT, SerializationT>(keyClass, serializationClass)
/*    */       {
/*    */         public SerializationT serializeKey(KeyT key, @Nullable SecretKeyAccess access) throws GeneralSecurityException
/*    */         {
/* 96 */           return function.serializeKey(key, access);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public abstract SerializationT serializeKey(KeyT paramKeyT, @Nullable SecretKeyAccess paramSecretKeyAccess) throws GeneralSecurityException;
/*    */   
/*    */   public static interface KeySerializationFunction<KeyT extends Key, SerializationT extends Serialization> {
/*    */     SerializationT serializeKey(KeyT param1KeyT, @Nullable SecretKeyAccess param1SecretKeyAccess) throws GeneralSecurityException;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\KeySerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
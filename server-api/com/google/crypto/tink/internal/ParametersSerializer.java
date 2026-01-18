/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import com.google.crypto.tink.Parameters;
/*    */ import java.security.GeneralSecurityException;
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
/*    */ 
/*    */ public abstract class ParametersSerializer<ParametersT extends Parameters, SerializationT extends Serialization>
/*    */ {
/*    */   private final Class<ParametersT> parametersClass;
/*    */   private final Class<SerializationT> serializationClass;
/*    */   
/*    */   private ParametersSerializer(Class<ParametersT> parametersClass, Class<SerializationT> serializationClass) {
/* 46 */     this.parametersClass = parametersClass;
/* 47 */     this.serializationClass = serializationClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<ParametersT> getParametersClass() {
/* 54 */     return this.parametersClass;
/*    */   }
/*    */   
/*    */   public Class<SerializationT> getSerializationClass() {
/* 58 */     return this.serializationClass;
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
/*    */   public static <ParametersT extends Parameters, SerializationT extends Serialization> ParametersSerializer<ParametersT, SerializationT> create(final ParametersSerializationFunction<ParametersT, SerializationT> function, Class<ParametersT> parametersClass, Class<SerializationT> serializationClass) {
/* 88 */     return new ParametersSerializer<ParametersT, SerializationT>(parametersClass, serializationClass)
/*    */       {
/*    */         
/*    */         public SerializationT serializeParameters(ParametersT parameters) throws GeneralSecurityException
/*    */         {
/* 93 */           return function.serializeParameters(parameters);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public abstract SerializationT serializeParameters(ParametersT paramParametersT) throws GeneralSecurityException;
/*    */   
/*    */   public static interface ParametersSerializationFunction<ParametersT extends Parameters, SerializationT extends Serialization> {
/*    */     SerializationT serializeParameters(ParametersT param1ParametersT) throws GeneralSecurityException;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\ParametersSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
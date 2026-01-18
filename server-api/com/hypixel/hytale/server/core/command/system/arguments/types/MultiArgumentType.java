/*    */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.ParseResult;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*    */ import java.util.Arrays;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public abstract class MultiArgumentType<DataType>
/*    */   extends ArgumentType<DataType>
/*    */ {
/*    */   @Nonnull
/* 23 */   private final Map<String, SingleArgumentType<?>> argumentValues = (Map<String, SingleArgumentType<?>>)new Object2ObjectLinkedOpenHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MultiArgumentType(@Nonnull String name, @Nonnull String argumentUsage, @Nullable String... examples) {
/* 34 */     super(name, argumentUsage, 0, examples);
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
/*    */   @Nonnull
/*    */   protected <D> WrappedArgumentType<D> withParameter(@Nonnull String name, @Nonnull String usage, @Nonnull SingleArgumentType<D> argumentType) {
/* 48 */     WrappedArgumentType<D> wrappedArgumentType = argumentType.withOverriddenUsage(usage);
/* 49 */     name = name.toLowerCase();
/*    */     
/* 51 */     if (this.argumentValues.containsKey(name)) {
/* 52 */       throw new IllegalArgumentException("Cannot register two MultiArgumentType parameters with the same name");
/*    */     }
/* 54 */     this.argumentValues.put(name, wrappedArgumentType);
/* 55 */     this.numberOfParameters = this.argumentValues.size();
/*    */     
/* 57 */     return wrappedArgumentType;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public DataType parse(@Nonnull String[] input, @Nonnull ParseResult parseResult) {
/* 63 */     MultiArgumentContext multiArgumentContext = new MultiArgumentContext();
/* 64 */     int endOfLastIndex = 0;
/*    */     
/* 66 */     for (SingleArgumentType<?> argumentValue : this.argumentValues.values()) {
/* 67 */       multiArgumentContext.registerArgumentValues(argumentValue, 
/*    */           
/* 69 */           Arrays.<String>copyOfRange(input, endOfLastIndex, endOfLastIndex + argumentValue.numberOfParameters), parseResult);
/*    */ 
/*    */ 
/*    */       
/* 73 */       if (parseResult.failed()) return null;
/*    */       
/* 75 */       endOfLastIndex += argumentValue.numberOfParameters;
/*    */     } 
/* 77 */     return parse(multiArgumentContext, parseResult);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public abstract DataType parse(@Nonnull MultiArgumentContext paramMultiArgumentContext, @Nonnull ParseResult paramParseResult);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\MultiArgumentType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
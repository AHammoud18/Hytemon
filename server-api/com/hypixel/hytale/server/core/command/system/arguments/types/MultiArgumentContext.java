/*    */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.ParseResult;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
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
/*    */ public class MultiArgumentContext
/*    */ {
/*    */   @Nonnull
/* 19 */   private final Map<ArgumentType<?>, Object> parsedArguments = (Map<ArgumentType<?>, Object>)new Object2ObjectOpenHashMap();
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
/*    */   public void registerArgumentValues(@Nonnull ArgumentType<?> argumentType, @Nonnull String[] values, @Nonnull ParseResult parseResult) {
/* 32 */     this.parsedArguments.put(argumentType, argumentType.parse(values, parseResult));
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
/*    */   @Nullable
/*    */   public <DataType> DataType get(@Nonnull ArgumentType<DataType> argumentType) {
/* 45 */     return (DataType)this.parsedArguments.get(argumentType);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\MultiArgumentContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
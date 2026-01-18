/*    */ package com.hypixel.hytale.codec.schema.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BooleanSchema
/*    */   extends Schema
/*    */ {
/*    */   public static final BuilderCodec<BooleanSchema> CODEC;
/*    */   private Boolean default_;
/*    */   
/*    */   static {
/* 16 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BooleanSchema.class, BooleanSchema::new, Schema.BASE_CODEC).addField(new KeyedCodec("default", (Codec)Codec.BOOLEAN, false, true), (o, i) -> o.default_ = i, o -> o.default_)).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public Boolean getDefault() {
/* 21 */     return this.default_;
/*    */   }
/*    */   
/*    */   public void setDefault(Boolean default_) {
/* 25 */     this.default_ = default_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 30 */     if (this == o) return true; 
/* 31 */     if (o == null || getClass() != o.getClass()) return false; 
/* 32 */     if (!super.equals(o)) return false;
/*    */     
/* 34 */     BooleanSchema that = (BooleanSchema)o;
/*    */     
/* 36 */     return (this.default_ != null) ? this.default_.equals(that.default_) : ((that.default_ == null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 41 */     int result = super.hashCode();
/* 42 */     result = 31 * result + ((this.default_ != null) ? this.default_.hashCode() : 0);
/* 43 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\config\BooleanSchema.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
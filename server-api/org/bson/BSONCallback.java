package org.bson;

import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

public interface BSONCallback {
  void objectStart();
  
  void objectStart(String paramString);
  
  Object objectDone();
  
  void reset();
  
  Object get();
  
  BSONCallback createBSONCallback();
  
  void arrayStart();
  
  void arrayStart(String paramString);
  
  Object arrayDone();
  
  void gotNull(String paramString);
  
  void gotUndefined(String paramString);
  
  void gotMinKey(String paramString);
  
  void gotMaxKey(String paramString);
  
  void gotBoolean(String paramString, boolean paramBoolean);
  
  void gotDouble(String paramString, double paramDouble);
  
  void gotDecimal128(String paramString, Decimal128 paramDecimal128);
  
  void gotInt(String paramString, int paramInt);
  
  void gotLong(String paramString, long paramLong);
  
  void gotDate(String paramString, long paramLong);
  
  void gotString(String paramString1, String paramString2);
  
  void gotSymbol(String paramString1, String paramString2);
  
  void gotRegex(String paramString1, String paramString2, String paramString3);
  
  void gotTimestamp(String paramString, int paramInt1, int paramInt2);
  
  void gotObjectId(String paramString, ObjectId paramObjectId);
  
  void gotDBRef(String paramString1, String paramString2, ObjectId paramObjectId);
  
  void gotBinary(String paramString, byte paramByte, byte[] paramArrayOfbyte);
  
  void gotUUID(String paramString, long paramLong1, long paramLong2);
  
  void gotCode(String paramString1, String paramString2);
  
  void gotCodeWScope(String paramString1, String paramString2, Object paramObject);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BSONCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
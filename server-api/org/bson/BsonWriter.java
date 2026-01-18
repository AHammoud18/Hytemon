package org.bson;

import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

public interface BsonWriter {
  void flush();
  
  void writeBinaryData(BsonBinary paramBsonBinary);
  
  void writeBinaryData(String paramString, BsonBinary paramBsonBinary);
  
  void writeBoolean(boolean paramBoolean);
  
  void writeBoolean(String paramString, boolean paramBoolean);
  
  void writeDateTime(long paramLong);
  
  void writeDateTime(String paramString, long paramLong);
  
  void writeDBPointer(BsonDbPointer paramBsonDbPointer);
  
  void writeDBPointer(String paramString, BsonDbPointer paramBsonDbPointer);
  
  void writeDouble(double paramDouble);
  
  void writeDouble(String paramString, double paramDouble);
  
  void writeEndArray();
  
  void writeEndDocument();
  
  void writeInt32(int paramInt);
  
  void writeInt32(String paramString, int paramInt);
  
  void writeInt64(long paramLong);
  
  void writeInt64(String paramString, long paramLong);
  
  void writeDecimal128(Decimal128 paramDecimal128);
  
  void writeDecimal128(String paramString, Decimal128 paramDecimal128);
  
  void writeJavaScript(String paramString);
  
  void writeJavaScript(String paramString1, String paramString2);
  
  void writeJavaScriptWithScope(String paramString);
  
  void writeJavaScriptWithScope(String paramString1, String paramString2);
  
  void writeMaxKey();
  
  void writeMaxKey(String paramString);
  
  void writeMinKey();
  
  void writeMinKey(String paramString);
  
  void writeName(String paramString);
  
  void writeNull();
  
  void writeNull(String paramString);
  
  void writeObjectId(ObjectId paramObjectId);
  
  void writeObjectId(String paramString, ObjectId paramObjectId);
  
  void writeRegularExpression(BsonRegularExpression paramBsonRegularExpression);
  
  void writeRegularExpression(String paramString, BsonRegularExpression paramBsonRegularExpression);
  
  void writeStartArray();
  
  void writeStartArray(String paramString);
  
  void writeStartDocument();
  
  void writeStartDocument(String paramString);
  
  void writeString(String paramString);
  
  void writeString(String paramString1, String paramString2);
  
  void writeSymbol(String paramString);
  
  void writeSymbol(String paramString1, String paramString2);
  
  void writeTimestamp(BsonTimestamp paramBsonTimestamp);
  
  void writeTimestamp(String paramString, BsonTimestamp paramBsonTimestamp);
  
  void writeUndefined();
  
  void writeUndefined(String paramString);
  
  void pipe(BsonReader paramBsonReader);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
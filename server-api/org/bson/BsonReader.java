package org.bson;

import java.io.Closeable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

public interface BsonReader extends Closeable {
  BsonType getCurrentBsonType();
  
  String getCurrentName();
  
  BsonBinary readBinaryData();
  
  byte peekBinarySubType();
  
  int peekBinarySize();
  
  BsonBinary readBinaryData(String paramString);
  
  boolean readBoolean();
  
  boolean readBoolean(String paramString);
  
  BsonType readBsonType();
  
  long readDateTime();
  
  long readDateTime(String paramString);
  
  double readDouble();
  
  double readDouble(String paramString);
  
  void readEndArray();
  
  void readEndDocument();
  
  int readInt32();
  
  int readInt32(String paramString);
  
  long readInt64();
  
  long readInt64(String paramString);
  
  Decimal128 readDecimal128();
  
  Decimal128 readDecimal128(String paramString);
  
  String readJavaScript();
  
  String readJavaScript(String paramString);
  
  String readJavaScriptWithScope();
  
  String readJavaScriptWithScope(String paramString);
  
  void readMaxKey();
  
  void readMaxKey(String paramString);
  
  void readMinKey();
  
  void readMinKey(String paramString);
  
  String readName();
  
  void readName(String paramString);
  
  void readNull();
  
  void readNull(String paramString);
  
  ObjectId readObjectId();
  
  ObjectId readObjectId(String paramString);
  
  BsonRegularExpression readRegularExpression();
  
  BsonRegularExpression readRegularExpression(String paramString);
  
  BsonDbPointer readDBPointer();
  
  BsonDbPointer readDBPointer(String paramString);
  
  void readStartArray();
  
  void readStartDocument();
  
  String readString();
  
  String readString(String paramString);
  
  String readSymbol();
  
  String readSymbol(String paramString);
  
  BsonTimestamp readTimestamp();
  
  BsonTimestamp readTimestamp(String paramString);
  
  void readUndefined();
  
  void readUndefined(String paramString);
  
  void skipName();
  
  void skipValue();
  
  BsonReaderMark getMark();
  
  void close();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
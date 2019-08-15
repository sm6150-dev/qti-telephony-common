package com.qualcomm.qcrilhook;

import com.qualcomm.qcrilhook.BaseQmiTypes.BaseQmiItemType;
import com.qualcomm.qcrilhook.BaseQmiTypes.QmiBase;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidParameterException;

public class QmiPrimitiveTypes {
    private static final String LOG_TAG = "QmiPrimitiveTypes";
    public static final int SIZE_OF_BYTE = 1;
    public static final int SIZE_OF_INT = 4;
    public static final int SIZE_OF_LONG = 8;
    public static final int SIZE_OF_SHORT = 2;

    public static class QmiArray<T extends BaseQmiItemType> extends BaseQmiItemType {
        private short mArrayLength;
        private short mNumOfElements = 0;
        private T[] mVal;
        private short vLenSize;

        public QmiArray(T[] arr, short maxArraySize, Class<T> cls) throws InvalidParameterException {
            try {
                this.mVal = arr;
                this.mArrayLength = (short) arr.length;
                if (maxArraySize > 255) {
                    this.vLenSize = 2;
                } else {
                    this.vLenSize = 1;
                }
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(e.toString());
            }
        }

        public QmiArray(T[] arr, Class<T> cls, short valueSize) throws InvalidParameterException {
            try {
                this.mVal = arr;
                this.mArrayLength = (short) arr.length;
                this.vLenSize = valueSize;
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(e.toString());
            }
        }

        public QmiArray(T[] arr, Class<T> cls, short valueSize, short numOfElements) throws InvalidParameterException {
            try {
                this.mVal = arr;
                this.mArrayLength = (short) arr.length;
                this.vLenSize = valueSize;
                this.mNumOfElements = numOfElements;
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(e.toString());
            }
        }

        public int getSize() {
            int actualArrayBytesSize = 0;
            for (int i = 0; i < this.mArrayLength; i++) {
                actualArrayBytesSize += this.mVal[i].getSize();
            }
            return this.vLenSize + actualArrayBytesSize;
        }

        public String toString() {
            StringBuffer s = new StringBuffer();
            for (int i = 0; i < this.mArrayLength; i++) {
                s.append(this.mVal[i].toString());
            }
            return s.toString();
        }

        public byte[] toByteArray() {
            short numberOfSets;
            ByteBuffer buf = createByteBuffer(getSize());
            if (this.mNumOfElements != 0) {
                numberOfSets = (short) (this.mArrayLength / this.mNumOfElements);
            } else {
                numberOfSets = this.mArrayLength;
            }
            if (this.vLenSize == 2) {
                buf.putShort(numberOfSets);
            } else {
                buf.put(PrimitiveParser.parseByte(numberOfSets));
            }
            for (int i = 0; i < this.mArrayLength; i++) {
                buf.put(this.mVal[i].toByteArray());
            }
            return buf.array();
        }
    }

    public static class QmiByte extends BaseQmiItemType {
        private byte mVal;

        public QmiByte() {
            this.mVal = 0;
        }

        public QmiByte(byte val) {
            this.mVal = val;
        }

        public QmiByte(short val) throws InvalidParameterException {
            try {
                this.mVal = PrimitiveParser.parseByte(val);
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(e.toString());
            }
        }

        public QmiByte(int val) throws InvalidParameterException {
            try {
                this.mVal = PrimitiveParser.parseByte(val);
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(e.toString());
            }
        }

        public QmiByte(char val) throws InvalidParameterException {
            try {
                this.mVal = PrimitiveParser.parseByte(val);
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(e.toString());
            }
        }

        public QmiByte(byte[] bArray) throws InvalidParameterException {
            if (bArray.length >= 1) {
                this.mVal = createByteBuffer(bArray).get();
                return;
            }
            throw new InvalidParameterException();
        }

        public short toShort() {
            return PrimitiveParser.toUnsigned(this.mVal);
        }

        public int getSize() {
            return 1;
        }

        public String toString() {
            return String.format("val=%d", new Object[]{Byte.valueOf(this.mVal)});
        }

        public byte[] toByteArray() {
            ByteBuffer buf = createByteBuffer(getSize());
            buf.put(this.mVal);
            return buf.array();
        }
    }

    public static class QmiEnum extends BaseQmiItemType {
        private short mVal;

        public QmiEnum(int val, int[] allowedValues) throws InvalidParameterException {
            try {
                this.mVal = PrimitiveParser.parseShort(val);
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(e.toString());
            }
        }

        public int getSize() {
            return 1;
        }

        public byte[] toByteArray() {
            ByteBuffer buf = createByteBuffer(getSize());
            buf.putShort(this.mVal);
            return buf.array();
        }

        public String toString() {
            return String.format("val=%d", new Object[]{Short.valueOf(this.mVal)});
        }
    }

    public static class QmiInteger extends BaseQmiItemType {
        private int mVal;

        public QmiInteger() {
            this.mVal = 0;
        }

        public QmiInteger(long val) throws InvalidParameterException {
            try {
                this.mVal = PrimitiveParser.parseInt(val);
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(e.toString());
            }
        }

        public QmiInteger(byte[] bArray) throws InvalidParameterException {
            if (bArray.length >= 4) {
                this.mVal = createByteBuffer(bArray).getInt();
                return;
            }
            throw new InvalidParameterException();
        }

        public long toLong() {
            return PrimitiveParser.toUnsigned(this.mVal);
        }

        public int getSize() {
            return 4;
        }

        public String toString() {
            return String.format("val=%d", new Object[]{Integer.valueOf(this.mVal)});
        }

        public byte[] toByteArray() {
            ByteBuffer buf = createByteBuffer(getSize());
            buf.putInt(this.mVal);
            return buf.array();
        }
    }

    public static class QmiLong extends BaseQmiItemType {
        private long mVal;

        public QmiLong() {
            this.mVal = 0;
        }

        public QmiLong(long mVal2) {
            this.mVal = mVal2;
        }

        public QmiLong(String mVal2) throws InvalidParameterException {
            try {
                this.mVal = PrimitiveParser.parseLong(mVal2);
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(e.toString());
            }
        }

        public QmiLong(byte[] bArray) throws InvalidParameterException {
            if (bArray.length >= 8) {
                this.mVal = createByteBuffer(bArray).getLong();
                return;
            }
            throw new InvalidParameterException();
        }

        public String toStringValue() {
            ByteBuffer buf = createByteBuffer(getSize());
            buf.order(ByteOrder.BIG_ENDIAN);
            buf.putLong(this.mVal);
            return new BigInteger(1, buf.array()).toString();
        }

        public int getSize() {
            return 8;
        }

        public byte[] toByteArray() {
            ByteBuffer buf = createByteBuffer(getSize());
            buf.putLong(this.mVal);
            return buf.array();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("val=");
            sb.append(this.mVal);
            return sb.toString();
        }
    }

    public static class QmiNull extends BaseQmiItemType {
        public int getSize() {
            return 0;
        }

        public byte[] toByteArray() {
            return new byte[0];
        }

        public String toString() {
            return "val=null";
        }

        public byte[] toTlv(short type) {
            return new byte[0];
        }
    }

    public static class QmiShort extends BaseQmiItemType {
        private short mVal;

        public QmiShort() {
            this.mVal = 0;
        }

        public QmiShort(int val) throws InvalidParameterException {
            try {
                this.mVal = PrimitiveParser.parseShort(val);
            } catch (NumberFormatException e) {
                throw new InvalidParameterException(e.toString());
            }
        }

        public QmiShort(byte[] bArray) throws InvalidParameterException {
            if (bArray.length >= 2) {
                this.mVal = createByteBuffer(bArray).getShort();
                return;
            }
            throw new InvalidParameterException();
        }

        public int toInt() {
            return PrimitiveParser.toUnsigned(this.mVal);
        }

        public int getSize() {
            return 2;
        }

        public String toString() {
            return String.format("val=%d", new Object[]{Short.valueOf(this.mVal)});
        }

        public byte[] toByteArray() {
            ByteBuffer buf = createByteBuffer(getSize());
            buf.putShort(this.mVal);
            return buf.array();
        }
    }

    public static class QmiString extends BaseQmiItemType {
        public static final int LENGTH_SIZE = 1;
        private String mVal;

        public QmiString() {
            this.mVal = new String();
        }

        public QmiString(String mVal2) throws InvalidParameterException {
            if (mVal2.length() <= 65536) {
                this.mVal = mVal2;
                return;
            }
            throw new InvalidParameterException();
        }

        public QmiString(byte[] bArray) throws InvalidParameterException {
            try {
                this.mVal = new String(bArray, QmiBase.QMI_CHARSET);
            } catch (UnsupportedEncodingException e) {
                throw new InvalidParameterException(e.toString());
            }
        }

        public String toStringValue() {
            return this.mVal;
        }

        public int getSize() {
            return this.mVal.length();
        }

        public byte[] toByteArray() {
            ByteBuffer buf = createByteBuffer(getSize());
            for (int i = 0; i < this.mVal.length(); i++) {
                buf.put((byte) this.mVal.charAt(i));
            }
            return buf.array();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("val=");
            sb.append(this.mVal);
            return sb.toString();
        }
    }
}

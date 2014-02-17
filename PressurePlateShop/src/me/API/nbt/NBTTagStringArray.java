package me.API.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagStringArray extends NBTBase{

	/** The array of saved integers */
	public String[] Array;

	public NBTTagStringArray(String par1Str) {
		super(par1Str);
	}

	public NBTTagStringArray(String par1Str, String[] par2ArrayOfInteger) {
		super(par1Str);
		this.Array = par2ArrayOfInteger;
	}

	/**
	 * Write the actual data contents of the tag, implemented in NBT extension
	 * classes
	 */
	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeInt(this.Array.length);

		for (int i = 0; i < this.Array.length; ++i) {
			par1DataOutput.writeUTF(this.Array[i]);
		}
	}

	/**
	 * Read the actual data contents of the tag, implemented in NBT extension
	 * classes
	 */
	void load(DataInput par1DataInput, int par2) throws IOException {
		int j = par1DataInput.readInt();
		this.Array = new String[j];

		for (int k = 0; k < j; ++k) {
			this.Array[k] = par1DataInput.readUTF();
		}
	}

	/**
	 * Gets the type byte for the tag.
	 */
	public byte getId() {
		return (byte) 11;
	}

	public String toString() {
		return "[" + this.Array.length + " bytes]";
	}

	/**
	 * Creates a clone of the tag.
	 */
	public NBTBase copy() {
		int[] aint = new int[this.Array.length];
		System.arraycopy(this.Array, 0, aint, 0, this.Array.length);
		return new NBTTagIntArray(this.getName(), aint);
	}

	public boolean equals(Object par1Obj) {
		if (!super.equals(par1Obj)) {
			return false;
		} else {
			NBTTagStringArray nbttagStringarray = (NBTTagStringArray) par1Obj;
			return this.Array == null && nbttagStringarray.Array == null
					|| this.Array != null
					&& Arrays.equals(this.Array, nbttagStringarray.Array);
		}
	}

	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(this.Array);
	}

}

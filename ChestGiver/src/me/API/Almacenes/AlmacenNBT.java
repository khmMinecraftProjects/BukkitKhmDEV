package me.API.Almacenes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import me.API.nbt.CompressedStreamTools;
import me.API.nbt.NBTBase;
import me.API.nbt.NBTTagCompound;

public class AlmacenNBT implements Almacen {

	NBTTagCompound nbt;
	String name;

	public AlmacenNBT(String nam) {
		nbt = null;
		name = nam;
		createFolders();
		iniciar();
	}
	private void createFolders() {
		int i=0;
		System.out.println("folders");
		while(true){
			String s=getSeparation(i);
			if(s.length()==0){return;}
			File f = new File(s);
			if(!(f.isDirectory()&&f.exists())){
				f.mkdir();
			}
			i++;
		}
	}

	private String getSeparation(int i){
		int word=0,o=0;
		String s="";
		while(word<=i){
			while(name.length()>o&&!name.substring(o,o+1).equals(File.separator)){
				s+=name.substring(o,o+1);
				o++;
			}
			if(name.length()>o&&name.substring(o,o+1).equals(File.separator)){
				o++;
				word++;
			}else{
				return "";
			}
		}
		return s;
	}
	public AlmacenNBT(NBTTagCompound compoundTag) {
		nbt=compoundTag;
		name=compoundTag.getName();
	}


	@Override
	public void iniciar() {
		File f = new File(name);
		if (f.isFile()) {

			FileInputStream in;
			try {
				in = new FileInputStream(name);
				nbt = CompressedStreamTools.readCompressed(in);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			nbt = new NBTTagCompound();
		}
	}

	@Override
	public void finalizar() {
		FileOutputStream out;
		try {
			out = new FileOutputStream(name);
			CompressedStreamTools.writeCompressed(nbt, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void cargar() {
		

	}

	@Override
	public void guardar() {
		
	}



	private NBTTagCompound getNBT() {
		return nbt;
	}

	@Override
	public Almacen getAlmacen(String key) {
		if (nbt.hasKey(key)) {
			return new AlmacenNBT(nbt.getCompoundTag(key));
		} else if (key == "") {
			return this;
		}
		return null;
	}

	@Override
	public void setByte(String par1Str, byte par2) {nbt.setByte(par1Str, par2);}

	@Override
	public void setShort(String par1Str, short par2) {nbt.setShort(par1Str, par2);}

	@Override
	public void setInteger(String par1Str, int par2) {nbt.setInteger(par1Str, par2);}

	@Override
	public void setLong(String par1Str, long par2) {nbt.setLong(par1Str, par2);}

	@Override
	public void setFloat(String par1Str, float par2) {nbt.setFloat(par1Str, par2);}

	@Override
	public void setDouble(String par1Str, double par2) {nbt.setDouble(par1Str, par2);}

	@Override
	public void setString(String par1Str, String par2Str) {nbt.setString(par1Str, par2Str);}

	@Override
	public void setByteArray(String par1Str, byte[] par2ArrayOfByte) {nbt.setByteArray(par1Str, par2ArrayOfByte);}

	@Override
	public void setIntArray(String par1Str, int[] par2ArrayOfInteger) {nbt.setIntArray(par1Str, par2ArrayOfInteger);}

	@Override
	public void setAlmacen(String par1Str, Almacen almacen) {
		nbt.setCompoundTag(par1Str, ((AlmacenNBT)almacen).getNBT());}

	@Override
	public void setBoolean(String par1Str, boolean par2) {nbt.setBoolean(par1Str, par2);}

	@Override
	public boolean hasKey(String par1Str) {
		return nbt.hasKey(par1Str);
	}

	@Override
	public byte getByte(String par1Str) {return nbt.getByte(par1Str);}

	@Override
	public short getShort(String par1Str) {return nbt.getShort(par1Str);}

	@Override
	public int getInteger(String par1Str) {return nbt.getInteger(par1Str);}

	@Override
	public long getLong(String par1Str) {return nbt.getLong(par1Str);}

	@Override
	public float getFloat(String par1Str) {return nbt.getFloat(par1Str);}

	@Override
	public double getDouble(String par1Str) {return nbt.getDouble(par1Str);}

	@Override
	public String getString(String par1Str) {return nbt.getString(par1Str);}

	@Override
	public byte[] getByteArray(String par1Str) {return nbt.getByteArray(par1Str);}

	@Override
	public int[] getIntArray(String par1Str) {return nbt.getIntArray(par1Str);}

	@Override
	public Almacen geAlmacen(String par1Str) {return new AlmacenNBT(nbt.getCompoundTag(par1Str));}

	@Override
	public boolean getBoolean(String par1Str) {return nbt.getBoolean(par1Str);}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Almacen> getAlmacenes() {
		
		Iterator<NBTBase> it = nbt.getTags().iterator();
		List<Almacen> al = new LinkedList<Almacen>();

		while (it.hasNext()) {
			try{
			NBTTagCompound n = (NBTTagCompound) it.next();

			al.add(new AlmacenNBT(n));}
			catch(Exception e){
				
			}
		}

		return al;
	}

	@Override
	public void leer(Datos dat, String string) {
		Almacen al=this.geAlmacen(string);
		dat.cargar(al);
	}

	@Override
	public void escribir(Datos dat, String string) {

		Almacen al=this.geAlmacen(string);
		dat.guardar(al);
		setAlmacen(string,al);
	}

	@Override
	public String[] getStringArray(String string) {
		return nbt.getStringArray(string);
	}

	@Override
	public void setStringArray(String string, String[] ar) {
		nbt.setStringArray(string, ar);
	}

}

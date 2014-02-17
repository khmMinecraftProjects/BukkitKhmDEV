package me.API.Almacenes;


public class PlayerData implements Datos{

	private int experiencia;
	private int nivel;
	
	public PlayerData() {
		experiencia=0;
		setNivel(0);
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	@Override
	public void cargar(Almacen nbt) {
		experiencia=nbt.getInteger("exp");
		nivel=nbt.getInteger("nv");
	}

	@Override
	public void guardar(Almacen nbt) {
		nbt.setInteger("exp", experiencia);
		nbt.setInteger("nv",nivel);

	}




}

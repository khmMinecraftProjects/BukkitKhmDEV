package me.khmdev.WorldLimit;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ChangeWorld {
	private World w;
	private Double minX=0d,minY=0d,minZ=0d,
			maxX=0d,maxY=0d,maxZ=0d;
	private Location LminX=null,LminY=null,LminZ=null,
			LmaxX=null,LmaxY=null,LmaxZ=null;
	public ChangeWorld(World ww){
		w=ww;
	}
	public void setMinX(Double minX2,Location l) {
		this.minX = minX2;LminX=l;
	}
	
	public void setMinZ(Double minZ,Location l) {
		this.minZ = minZ;LminZ=l;
	}
	
	public void setMaxX(Double maxX,Location l) {
		this.maxX = maxX;LmaxX=l;
	}
	
	public void setMaxZ(Double maxZ,Location l) {
		this.maxZ = maxZ;LmaxZ=l;
	}
	public void changeWorld(Player pl){
		if(w!=pl.getWorld()){return;}
		
		if(LminX!=null && pl.getLocation().getX()<=minX){
			pl.teleport(LminX);
		}
		if(LminY!=null && pl.getLocation().getY()<=minY){
			pl.teleport(LminY);
		}
		if(LminZ!=null && pl.getLocation().getZ()<=minZ){
			pl.teleport(LminZ);
		}
		
		if(LmaxX!=null && pl.getLocation().getX()>=maxX){
			pl.teleport(LmaxX);
		}
		if(LmaxY!=null && pl.getLocation().getY()>=maxY){
			pl.teleport(LmaxY);
		}
		if(LmaxZ!=null && pl.getLocation().getZ()>=maxZ){
			pl.teleport(LmaxZ);
		}
	}
	public void setMinY(Double minY, Location l) {
		this.minY= minY;LminY=l;		
	}
	public void setMaxY(Double maxY, Location l) {
		this.maxY= maxY;LmaxY=l;		
	}
}

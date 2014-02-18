package me.khmDev.ChatSeparator;

public class WorldMessage {
	private String pref,suf,alias;
	public WorldMessage(String p,String s,String a){
		pref=p;
		suf=s;
		alias=a;
	}
	public String getPref() {
		return pref;
	}

	public String getSuf() {
		return suf;
	}

	public String getAlias() {
		return alias;
	}

}

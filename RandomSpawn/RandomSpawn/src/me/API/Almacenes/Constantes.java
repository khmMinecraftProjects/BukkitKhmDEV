package me.API.Almacenes;

import java.util.ArrayList;
import java.util.List;

public class Constantes {
	@SuppressWarnings("serial")
	public static List<Integer> exceptions = new ArrayList<Integer>() {
		{
			add(146);
			add(54);
			add(95);
			add(130);
			add(323);
			add(63);
			add(68);

		}
	};;

	public enum Equipo {
		Rojo, Azul, Ninguno
	}
	public enum Estado {
		Iniciando, EnCurso, Finalizada, NoDisponible, EsperandoJugadores, SinIniciar, Regenerando
	};
}

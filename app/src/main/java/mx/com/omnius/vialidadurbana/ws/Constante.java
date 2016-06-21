package mx.com.omnius.vialidadurbana.ws;


import android.content.Context;

import java.util.ArrayList;

import mx.com.omnius.vialidadurbana.pojos.Paradas;
import mx.com.omnius.vialidadurbana.pojos.Rutas;

public class Constante {

	public static final String HOST_WS											= "omnius.com.mx:8080";
	public static final int			TIEMPO_MAXIMO_ESPERA_WS							= 20000;
	public static final String Valida_Correo									= "^[\\w\\-\\_]+(\\.[\\w\\-\\_]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$";


	public static final double latitud = 21.150923; // 20.9893276
	public static final double longitud = -86.87360700000001; //  -86.8316037

	public static ArrayList<Paradas> auxParadas = null;
	public static ArrayList<Rutas> auxRutas = null;

	public static ArrayList<Paradas> auxParadaUno = null;
	public static ArrayList<Paradas> auxParadaDos = null;
	public static ArrayList<Paradas> auxParadaTres = null;

}

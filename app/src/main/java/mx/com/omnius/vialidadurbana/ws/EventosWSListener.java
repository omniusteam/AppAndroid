package mx.com.omnius.vialidadurbana.ws;

public interface EventosWSListener<T> {
	void onResultadoObtenido(T result);

	void onTiempoExpiradoConexion();

	void onError(String message);
}

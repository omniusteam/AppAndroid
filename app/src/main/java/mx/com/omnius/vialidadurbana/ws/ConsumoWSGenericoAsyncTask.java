package mx.com.omnius.vialidadurbana.ws;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class ConsumoWSGenericoAsyncTask<T> extends AsyncTask<String, Void, T> {

	private EventosWSListener<T>			eventosWSListener;
	private T								parameterizedGeneric;
	private String serviceName;
	private LinkedHashMap<String, String> queryParameters;
	private ErroresWSEnum					errorWs		= ErroresWSEnum.NINGUNO;
	private String errorMsg	= "";

	public ConsumoWSGenericoAsyncTask(T parameterizedGeneric, LinkedHashMap<String, String> queryParameters) {
		this.parameterizedGeneric = parameterizedGeneric;
		this.queryParameters = queryParameters;
	}

	public void setEventosWSListener(EventosWSListener<T> listener) {
		this.eventosWSListener = listener;
	}

	@Override
	protected T doInBackground(String... arg0) {
		return llamaServicioRest(arg0[0]);
	}

	@Override
	protected void onPostExecute(T result) {
		if (errorWs == ErroresWSEnum.NINGUNO) {
			eventosWSListener.onResultadoObtenido(result);
		}
		else if (errorWs != ErroresWSEnum.NINGUNO) {
			switch (errorWs) {
				case TIEMPO_MAXIMO_AGOTADO:
					eventosWSListener.onTiempoExpiradoConexion();
					break;
				case ERROR:
					eventosWSListener.onError(errorMsg);
					break;
				default:
					break;
			}
		}
		super.onPostExecute(result);
	}

	private T llamaServicioRest(String string) {
		this.serviceName = string;

		Uri.Builder constructorUri = new Uri.Builder();
		constructorUri.scheme("http");
		constructorUri.authority(Constante.HOST_WS);
		constructorUri.path("WSGlassfish");
		constructorUri.appendEncodedPath("web");
		constructorUri.appendEncodedPath("");
		constructorUri.appendEncodedPath(serviceName);

		if (queryParameters != null) {
			for (Entry<String, String> jugador : queryParameters.entrySet()) {
				String clave = jugador.getKey();
				String valor = jugador.getValue();
				constructorUri.appendQueryParameter(clave, valor);
			}
		}
		Uri uri = constructorUri.build();
		Log.e("url", Uri.decode(uri.toString()));
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(Constante.TIEMPO_MAXIMO_ESPERA_WS);
		factory.setConnectTimeout(Constante.TIEMPO_MAXIMO_ESPERA_WS);
		RestTemplate restTemplate = new RestTemplate(factory);
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		T result = null;
		Integer num = -99;
		try {
			if (obtainClassName(parameterizedGeneric).equalsIgnoreCase("java.lang.Integer")) {
				try {
					ResponseEntity<String> resultNew = restTemplate.getForEntity(Uri.decode(uri.toString()), String.class);
					num = Integer.valueOf(Integer.parseInt(resultNew.getBody()));
				} catch (RestClientException e1) {
					errorWs = ErroresWSEnum.ERROR;
					errorMsg = e1.getCause().getClass().getName();
				}
			}
			else {
				result = (T) restTemplate.getForObject(Uri.decode(uri.toString()), Class.forName(obtainClassName(parameterizedGeneric)));
			}
		} catch (ResourceAccessException e) {
			if (e.getRootCause().getClass().getName().equalsIgnoreCase("org.apache.http.conn.ConnectTimeoutException")) {
				errorWs = ErroresWSEnum.TIEMPO_MAXIMO_AGOTADO;
			}
			if (e.getRootCause().getClass().getName().equalsIgnoreCase("java.net.SocketTimeoutException")) {
				errorWs = ErroresWSEnum.TIEMPO_MAXIMO_AGOTADO;
			}
			else {
				errorWs = ErroresWSEnum.ERROR;
				errorMsg = e.getCause().getClass().getName();
			}
		} catch (HttpMessageNotReadableException e) {
			errorMsg = e.getClass().getName();
			errorWs = ErroresWSEnum.ERROR;
		} catch (Exception e) {
			errorMsg = e.getClass().getName();
			errorWs = ErroresWSEnum.ERROR;
		}
		if (obtainClassName(parameterizedGeneric).equalsIgnoreCase("java.lang.Integer")) {
			return (T) num;
		}
		else {
			return result;
		}
	}

	private String obtainClassName(T parameterizedGeneric) {
		String temp = ToStringBuilder.reflectionToString(parameterizedGeneric);
		int posicionFinal = 0;

		if (temp.length() == 0) {
			posicionFinal = 0;
		}

		for (int i = 0; i < temp.length(); i++) {
			if (temp.charAt(i) == '@') {
				posicionFinal = i;
			}
		}
		String cadena = "";
		if (posicionFinal != 0) {
			cadena = temp.substring(0, posicionFinal);
		}
		return cadena;
	}
}

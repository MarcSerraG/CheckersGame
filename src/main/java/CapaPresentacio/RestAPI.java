package CapaPresentacio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import CapaAplicacio.JocDamesInterficie;

public class RestAPI implements JocDamesInterficie {

	private String address = "http://127.0.0.1:8080/JocDames/";
	private JSONObject json;

	@Override
	public String login(String nom, String passwd) {

		try {
			return getData("login?user=" + URLEncoder.encode(nom, "UTF-8") + "&password="
					+ URLEncoder.encode(passwd, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String registra(String nom, String passwd) {

		try {
			return getData("registra?user=" + URLEncoder.encode(nom, "UTF-8") + "&password="
					+ URLEncoder.encode(passwd, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String logout(String idSessio) {

		try {
			return getData("logout?idSessio=" + URLEncoder.encode(idSessio, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String reconecta(String idSessio, String passwd) {

		try {
			return getData("reconecta?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&password="
					+ URLEncoder.encode(passwd, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String getEstadistics(String idSessio) {

		try {
			return getData("getEstadistics?idSessio=" + URLEncoder.encode(idSessio, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String getCandidatsSol(String idSessio) {

		try {
			return getData("getCandidatsSol?idSessio=" + URLEncoder.encode(idSessio, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String enviaSol(String idSessio, String usuari) {

		try {
			return getData("enviaSol?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&usuari="
					+ URLEncoder.encode(usuari, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String solicituds(String idSessio) {

		try {
			return getData("solicituds?idSessio=" + URLEncoder.encode(idSessio, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String acceptaSol(String idSessio, String usuari) {

		try {
			return getData("acceptaSol?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&usuari="
					+ URLEncoder.encode(usuari, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String rebutjaSol(String idSessio, String usuari) {

		try {
			return getData("rebutjaSol?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&usuari="
					+ URLEncoder.encode(usuari, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String getPartidesTorn(String idSessio) {

		try {
			return getData("getPartidesTorn?idSessio=" + URLEncoder.encode(idSessio, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String getPartidesNoTorn(String idSessio) {

		try {
			return getData("getPartidesNoTorn?idSessio=" + URLEncoder.encode(idSessio, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String getPartidesAcabades(String idSessio) {

		try {
			return getData("getPartidesAcabades?idSessio=" + URLEncoder.encode(idSessio, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String triaPartida(String idSessio, String usuari) {

		try {
			return getData("triaPartida?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&usuari="
					+ URLEncoder.encode(usuari, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String obtenirColor(String idSessio, String idPartida) {

		try {
			return getData("obtenirColor?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String obtenirTaulerAnt(String idSessio, String idPartida) {

		try {
			return getData("obtenirTaulerAnt?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String obtenirTaulerAct(String idSessio, String idPartida) {

		try {
			return getData("obtenirTaulerAct?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String obtenirTaulerRes(String idSessio, String idPartida) {

		try {
			return getData("obtenirTaulerRes?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String obtenirMovsAnt(String idSessio, String idPartida) {

		try {
			return getData("obtenirMovsAnt?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String grabarTirada(String idSessio, String idPartida) {

		try {
			return getData("grabarTirada?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String obtenirMovimentsPossibles(String idSessio, String idPartida) {

		try {
			return getData("obtenirMovimentsPossibles?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String ferMoviment(String idSessio, String idPartida, String posIni, String posFi) {

		try {
			return getData("ferMoviment?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8") + "&posIni=" + URLEncoder.encode(posIni, "UTF-8")
					+ "&posFi=" + URLEncoder.encode(posFi, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String ferDama(String idSessio, String idPartida, String pos) {

		try {
			return getData("ferDama?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8") + "&pos=" + URLEncoder.encode(pos, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String ferBufa(String idSessio, String idPartida, String pos) {

		try {
			return getData("ferBufa?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8") + "&pos=" + URLEncoder.encode(pos, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String acceptaTaules(String idSessio, String idPartida) {

		try {
			return getData("acceptaTaules?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String proposaTaules(String idSessio, String idPartida) {

		try {
			return getData("proposaTaules?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	@Override
	public String movsPessa(String idSessio, String idPartida, String pos) {

		try {
			return getData("movsPessa?idSessio=" + URLEncoder.encode(idSessio, "UTF-8") + "&idPartida="
					+ URLEncoder.encode(idPartida, "UTF-8") + "&pos=" + URLEncoder.encode(pos, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	private String getData(String method) {

		try {

			URL url = new URL(address + method);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			System.out.println(address + method);

			if (conn.getResponseCode() != 200) {

				String s = this.crearJSON(
						"Failed : HTTP error code : " + conn.getResponseCode() + "on call " + address + method);
				return s;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String line = "";
			String output = "";

			while ((line = br.readLine()) != null) {
				output += line;
			}
			conn.disconnect();
			return output;
		}

		catch (Exception e) {
			String s = this.crearJSON(e.getMessage());
			return s;
		}
	}

	private String crearJSON(String err) {
		try {
			if (this.json == null)
				this.json = new JSONObject();
			json.put("err", err);
			json.put("sErr", "");
			json.put("res", "");
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}

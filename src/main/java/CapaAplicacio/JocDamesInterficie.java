package CapaAplicacio;
/**
Tots els paràmetres son de tipus String, i retornen un String Json amb el següent format: { “res”: String, resultat de la execució
         
    
“err”: String, possible excepció produïda durant l’execució

"sErr": String, error de sessió caducada
   
}
El «res» depèn de cada funció de la API, però codifica el que es vol transmetre (podria ser un json complexe però en el nostre cas és un String...)
Pot contenir, el nom de l’usuari, o «true»/«false» codificant un booleà...
*/
public interface JocDamesInterficie {
public String login(String nom, String passwd) ; public String registra(String nom, String passwd) ; public String logout(String idSessio) ;
public String reconecta(String idSessio, String passwd) ;
public String getEstadistics(String idSessio);
public String getCandidatsSol(String idSessio) ;
public String enviaSol(String idSessio,String usuari);
public String solicituds(String idSessio);
public String acceptaSol(String idSessio,String usuari);
public String rebutjaSol(String idSessio,String usuari);
public String getPartidesTorn(String idSessio) ;
public String getPartidesNoTorn(String idSessio) ;
public String getPartidesAcabades(String idSessio) ;
public String triaPartida(String idSessio,String usuari) ;
public String obtenirColor(String idSessio,String idPartida ) ;
public String obtenirTaulerAnt(String idSessio,String idPartida) ;
public String obtenirTaulerAct(String idSessio,String idPartida);
public String obtenirTaulerRes(String idSessio,String idPartida);
public String obtenirMovsAnt(String idSessio,String idPartida) ;
public String grabarTirada(String idSessio,String idPartida);
public String obtenirMovimentsPossibles(String idSessio,String idPartida);
public String ferMoviment(String idSessio,String idPartida, String posIni, String posFi) ;
public String ferDama(String idSessio,String idPartida,String pos) ; public String ferBufa(String idSessio,String idPartida,String pos) ; public String acceptaTaules(String idSessio,String idPartida) ; public String proposaTaules(String idSessio,String idPartida);
public String movsPessa(String idSessio,String idPartida,String pos) ;
}

package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	private List<Esame> esami;
	private double bestMedia=0.0;
	private Set<Esame> bestSoluzione=null;
	
	public Model() {
		EsameDAO dao= new EsameDAO();
		this.esami=dao.getTuttiEsami();
	}
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		Set<Esame> parziale= new HashSet<>();
		cerca(parziale, 0,numeroCrediti);
		
		return bestSoluzione;
	}
	/*APPROCCIO 1
	 * COMPLESSITA' : 2^N}*/
	private void cerca(Set<Esame> parziale, int L, int m) {
		
		//casi terminali
		int crediti=sommaCrediti(parziale);
		if(crediti>m)
			return;
		if(crediti==m) {
			double media=calcolaMedia(parziale);
			if(media>bestMedia) {
				bestMedia=media;
				bestSoluzione=new HashSet<>(parziale);
			}
		}
		//sicuramente qui crediti<m
		if(L==esami.size()) {
			return;
		}
		
		//genero i sottoproblemi, esami[L] è da aggiungere o no?
		//Povo ad aggiungerlo 
		parziale.add(esami.get(L));
		cerca(parziale, L+1, m);
		parziale.remove(esami.get(L));
		
		//provo a non aggiungerlo
		cerca(parziale,L+1,m);
		
	}
	/*APPROCCIO 2
	 * COMPLESSITA': N!*/
	private void cerca2(Set<Esame> parziale, int L, int m) {
		
		//casi terminali
		int crediti=sommaCrediti(parziale);
		if(crediti>m)
			return;
		if(crediti==m) {
			double media=calcolaMedia(parziale);
			if(media>bestMedia) {
				bestMedia=media;
				bestSoluzione=new HashSet<>(parziale);
			}
		}
		//sicuramente qui crediti<m
		if(L==esami.size()) {
			return;
		}
		for(Esame e: esami){
			if(!parziale.contains(e)) {
				parziale.add(e);
				cerca2(parziale,L+1,m);
				parziale.remove(e);
			}
		}
		
	}
	public double calcolaMedia(Set<Esame> parziale) {
		int crediti=0, somma=0;
		for(Esame e:parziale) {
			crediti+=e.getCrediti();
			somma+=e.getVoto()*e.getCrediti();
		}
		return somma/crediti;
	}
	private int sommaCrediti(Set<Esame> parziale) {
		int somma=0;
		for(Esame e:parziale) {
			somma+=e.getCrediti();
		}
		return somma;
	}

}

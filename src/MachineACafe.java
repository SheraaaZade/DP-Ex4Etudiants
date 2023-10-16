public class MachineACafe {
	public final int idle = 0;
	public final int collecte = 1;
	public final int pasAssez = 2;
	
	private int montantEnCours = 0;
	private int etatCourant = idle;
	private ToucheBoisson boisson = null;
	
	public void afficherMontant() {
		System.out.println(montantEnCours + " cents disponibles");
	}
	
	public void afficherRetour() {
		System.out.println(montantEnCours + " cents rendus");
	}
	
	public void afficherPasAssez(ToucheBoisson toucheBoisson) {
		System.out.println("Vous n'avez pas introduit un montant suffisant pour un " + toucheBoisson);
		System.out.println("Il manque encore " + (toucheBoisson.getPrix() - montantEnCours) + " cents");
	}

	public void afficherBoisson(ToucheBoisson toucheBoisson) {
		System.out.println("Voici un " + toucheBoisson);
		
	}

	public void entrerMonnaie(Piece piece) {
		montantEnCours += piece.getValeur();
		afficherMontant();
		if ( etatCourant != pasAssez)
			etatCourant = collecte;
		else if (boisson.getPrix() > montantEnCours) {
				afficherPasAssez(boisson);
		} else {
			montantEnCours -= boisson.getPrix();
			afficherBoisson(boisson);
			boisson = null;
			afficherMontant();
			if (montantEnCours == 0)
				etatCourant =  idle;
			else
				etatCourant =  collecte;
		}
	}
	
	public void selectionnerBoisson(ToucheBoisson toucheBoisson) {
		if (etatCourant == pasAssez)
			throw new IllegalStateException();
		if (etatCourant == idle) {
			afficherPasAssez(toucheBoisson);
			return;			
		}
		if (toucheBoisson.getPrix() > montantEnCours) {
			boisson = toucheBoisson;
			afficherPasAssez(boisson);
			boisson = toucheBoisson;
			etatCourant =  pasAssez;
			return;
		}
		montantEnCours -= toucheBoisson.getPrix();
		afficherBoisson(toucheBoisson);
		afficherMontant();
		if (montantEnCours == 0)
			etatCourant = idle;
		else
			etatCourant = collecte;
	}
	
	public void rendreMonnaie() {
		if (etatCourant != idle) {
			afficherRetour();
			montantEnCours = 0;
			boisson = null;
		}
		etatCourant = idle;
	}
}

public class MachineACafe {
    public enum State {
        IDLE {
            public void selectionnerBoisson(MachineACafe m, ToucheBoisson toucheBoisson) {
                m.afficherPasAssez(toucheBoisson);
            }

            @Override
            public void rendreMonnaie(MachineACafe m) {

            }
        },
        COLLECTE {
            @Override
            public void selectionnerBoisson(MachineACafe m, ToucheBoisson toucheBoisson) {
                if (toucheBoisson.getPrix() > m.montantEnCours) {
                    m.boisson = toucheBoisson;
                    m.afficherPasAssez(m.boisson);
                    m.boisson = toucheBoisson;
                    m.etatCourant = State.PASASSEZ;
                    return;
                }
                m.montantEnCours -= toucheBoisson.getPrix();
                m.afficherBoisson(toucheBoisson);
                m.afficherMontant();
                if (m.montantEnCours == 0)
                    m.etatCourant = State.IDLE;
                else
                    m.etatCourant = State.COLLECTE;
            }
        },
        PASASSEZ {
            @Override
            public void entrerMonnaie(MachineACafe m, Piece piece) {
                if (m.boisson.getPrix() > m.montantEnCours) {
                    m.afficherPasAssez(m.boisson);
                } else {
                    m.afficherBoisson(m.boisson);
                    m.afficherMontant();
                    m.montantEnCours -= m.boisson.getPrix();
                    if (m.montantEnCours == 0)
                        m.etatCourant = State.IDLE;
                    else
                        m.etatCourant = State.COLLECTE;
                }

            }

            @Override
            public void selectionnerBoisson(MachineACafe m, ToucheBoisson toucheBoisson) {
                throw new IllegalStateException();
            }
        };

        public void entrerMonnaie(MachineACafe m, Piece piece) {
            m.etatCourant = State.COLLECTE;
        }

        public abstract void selectionnerBoisson(MachineACafe m, ToucheBoisson toucheBoisson);

        public void rendreMonnaie(MachineACafe m) {
            m.afficherRetour();
            m.montantEnCours = 0;
            m.boisson = null;
            m.etatCourant = State.IDLE;
        }
    }

    private int montantEnCours = 0;
    private State etatCourant = State.IDLE;
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
        etatCourant.entrerMonnaie(this, piece);
    }

    public void selectionnerBoisson(ToucheBoisson toucheBoisson) {
        etatCourant.selectionnerBoisson(this, toucheBoisson);
    }

    public void rendreMonnaie() {
        etatCourant.rendreMonnaie(this);
    }
}

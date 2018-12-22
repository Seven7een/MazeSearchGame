package mazegame;

import javax.swing.*;

class MPanel extends JPanel {
    
    private boolean inConsideration;
    
    MPanel(){
        
        super();
        inConsideration = true;
        
    }

    void setInConsideration(boolean inConsideration) {
        this.inConsideration = inConsideration;
    }

    boolean inConsideration() {
        return inConsideration;
    }
}

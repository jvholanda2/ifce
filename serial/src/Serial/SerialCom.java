package Serial;

import gnu.io.CommPortIdentifier;
import java.util.Enumeration;

public class SerialCom {
    protected String portas[];
    protected Enumeration listaDePortas;
    
    public SerialCom() {
        this.listaDePortas = CommPortIdentifier.getPortIdentifiers();
    }
    
    public String[] obterPortas() {
        return this.portas;
    }
    
    protected void ListarPortas() {
        
        int i = 0;
        this.portas = new String[10];
    
        while(this.listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips = (CommPortIdentifier)this.listaDePortas.nextElement();
            this.portas[i] = ips.getName();
            i++;
        }
    }

    public boolean portaExiste(String comP) {
        String temp;
        boolean e = false;
        
        while (listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips = (CommPortIdentifier)listaDePortas.nextElement();
            temp = ips.getName();

            if (temp.equals(comP)== true) {
                e = true;
            }
        }

       
        return e;
       
    }
    
}





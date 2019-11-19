/*

O resultado final do programa podería amosarse de modo  similar ao seguinte :

CODIGO DO PLATO : p1
nome do plato : platocarnico1
codigo do componente : c1-> graxa por cada 100 gr= 5
peso : 400
total de graxa do componente = 20

codigo do componente : c3-> graxa por cada 100 gr= 10
peso : 600
total de graxa do componente = 60

TOTAL EN GRAXAS DO PLATO: 80

CODIGO DO PLATO : p2
nome do plato : platocarnico2
codigo do componente : c2-> graxa por cada 100 gr= 20
peso : 600
total de graxa do componente = 120

codigo do componente : c3-> graxa por cada 100 gr= 10
peso : 300
total de graxa do componente = 30

codigo do componente : c4-> graxa por cada 100 gr= 5
peso : 200
total de graxa do componente = 10

TOTAL EN GRAXAS DO PLATO: 160


notas para os calculos :  
para calcular a graxa dun compoñente dun plato habera que multiplicar a graxa do compoñente por o seu peso no prato e dividilo por 100 .

por exemplo . 
O contido en graxa do compoñente 'c1' correspondente ao prato de codigo 'p1' sera:
5(graxa)*400(peso)/100= 20
O contido en graxa do compoñente 'c3' correspondente ao prato de codigo 'p1' sera :
10(graxa)*600(peso)/100= 60
Por isto o contido en graxas totais do prato p1 e de 20+60  = 80 unidades.

*/

package exa15;

import static exa15.Plato.calcularContenido;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class Exa15 {

    public static Connection conexion = null;
    static File xml = new File("/home/oracle/Desktop/compartido/Exa15_2/ExExamenAD2/platos.xml");
    static File txt = new File("/home/oracle/Desktop/compartido/Exa15_2/ExExamenAD2/composicion.txt");

    public static Connection getConexion() throws SQLException {
        String usuario = "hr";
        String password = "hr";
        String host = "localhost";
        String puerto = "1521";
        String sid = "orcl";
        String ulrjdbc = "jdbc:oracle:thin:" + usuario + "/" + password + "@" + host + ":" + puerto + ":" + sid;

        conexion = DriverManager.getConnection(ulrjdbc);
        return conexion;
    }

    public static void closeConexion() throws SQLException {
        conexion.close();
    }

    public static ArrayList<Plato> leerPlatos(File xml) {
        ArrayList<Plato> platosLeidos = new ArrayList<>();
        ObjectInputStream read;
        XMLStreamReader xmlSR = null;
        try {
            xmlSR = XMLInputFactory.newInstance().createXMLStreamReader(new FileReader(xml));
            Plato plato = null;
            while (xmlSR.hasNext()) {
                if (xmlSR.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    if (xmlSR.getLocalName() == "Plato") {
                        plato = new Plato();
                        plato.setCodigop(xmlSR.getAttributeValue(0));
                        platosLeidos.add(plato);

                    } else if (xmlSR.getLocalName() == "nomep") {
                        plato.setNomep(xmlSR.getElementText());
                    }
                }
                xmlSR.next();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();

        } catch (XMLStreamException ex) {
            ex.printStackTrace();
        } finally {
            try {
                xmlSR.close();
            } catch (XMLStreamException ex) {
                ex.printStackTrace();
            }
        }
        return platosLeidos;
    }

    public static void leerComposicion(Plato plato) {

        HashMap<String, Integer> composicion = new HashMap<>();
        BufferedReader read = null;

        try {
            read = new BufferedReader(new FileReader(txt));
            String[] puente = null;

            while (true) {
                try {
                    String str = read.readLine();
                    if(str==null)
                        break;
//                    System.out.println(str);
                    puente = str.split("#");
                    if (plato.getCodigop().equals(puente[0].toString())) {
                        Componente comp = new Componente();
                        comp.setCodc(puente[1].toString());
                        plato.getComponentes().put(comp, Integer.parseInt(puente[2]));
                    }
                } catch (IOException ex) {
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                read.close();
            } catch (IOException ex) {
                Logger.getLogger(Exa15.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void selectGrasa(Componente comp) throws SQLException {
        String sql = "Select graxa from componentes where codc='" + comp.getCodc() + "'";
        //conexion
        Connection conn = getConexion();
        //intermediario
        Statement statement = conn.createStatement();
        //resultados
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            comp.setGraxa(Integer.parseInt(rs.getNString("graxa")));
        }
    }

    public static void escribirSerializado(ArrayList<Plato> platos) {

        File txt = new File("/home/oracle/Desktop/compartido/Exa15_2/ExExamenAD2/platosSerializados.txt");
        ObjectOutputStream write = null;

        try {
            write = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(txt)));
            for (Plato plato : platos) {
                write.writeObject(plato);
            }
            write.writeObject(null);
            write.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, XMLStreamException {

        ArrayList<Plato> platos = leerPlatos(xml);

        for (Plato plato : platos) {
            leerComposicion(plato);
            for (Entry<Componente, Integer> entry : plato.getComponentes().entrySet()) {
                selectGrasa(entry.getKey());
            }
            System.out.println(plato.toString());
        }
        escribirSerializado(platos);
    }

}

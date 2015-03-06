import java.io.IOException;

import javax.xml.parsers.*;

import java.sql.*;
import java.io.StringReader;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import sun.tools.java.ClassNotFound;

import java.sql.Connection;
import java.sql.DriverManager;

public class xmlparse {
    public static void main() {
        try {

            Connection con = null;
            Statement st = null;
            ResultSet rs = null;

            String url = "jdbc:mysql://localhost:3306/healthmessagesexchange";
            String user = "root";
            String password = "";

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM healthmessagesexchange.messagequeue ORDER BY control_id;");
            //rs = st.executeQuery("SELECT * FROM healthmessagesexchange.messagequeue ORDER BY control_id LIMIT 1;");

            //the result set is initially before the first row of data. in order to get the first row, call next
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            while (rs.next()) {
                printAllColumnNames(rs);
                System.out.println("parsing item");
                String test = rs.getString("xmlmessage");
                System.out.println(test);
            }


        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Caught SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Caught ClassNotFound Exception: " + e.getMessage());
        }

    }

    public static void printAllColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = null;
        try {
            rsmd = rs.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int columnCount = 0;
        try {
            columnCount = rsmd.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // The column count starts from 1
        for (int i = 1; i < columnCount + 1; i++ ) {
            String name = rsmd.getColumnName(i);
            System.out.println("column " + i + " is " + name);
            // Do stuff with name
        }
    }
}
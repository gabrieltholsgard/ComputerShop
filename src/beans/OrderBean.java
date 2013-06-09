package beans;

import java.sql.*;
import java.util.*;

// save an order in the database
public class OrderBean {

    private Connection con;
    private PreparedStatement orderPstmt;
    private PreparedStatement orderItemPstmt;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private String url;
    private ShoppingBean sb;
    private String buyerName;
    private String shippingAddress;
    private String shippingZipcode;
    private String shippingCity;
    private static String orderSQL;
    private static String orderItemSQL;

    public OrderBean(String _url, ShoppingBean _sb, String _buyerName,
            String _shippingAddress, String _shippingZipcode,
            String _shippingCity) {
        url = _url;
        sb = _sb;
        buyerName = _buyerName;
        shippingAddress = _shippingAddress;
        shippingZipcode = _shippingZipcode;
        shippingCity = _shippingCity;
    }

    /**
     * Saves an order in the database
     */
    public String saveOrder() throws Exception {
        String ret = "";
        orderSQL = "INSERT INTO ORDERS(BUYER_NAME,";
        orderSQL += " SHIPPING_ADRESS, SHIPPING_ZIPCODE, SHIPPING_CITY)";
        orderSQL += " VALUES(?,?,?,?)";
        try {

            // load the driver and get a connection

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url);

            // turn off autocommit to handle transactions yourself

            con.setAutoCommit(false);
            orderPstmt = con.prepareStatement(orderSQL);
            orderPstmt.setString(1, buyerName);
            orderPstmt.setString(2, shippingAddress);
            orderPstmt.setString(3, shippingZipcode);
            orderPstmt.setString(4, shippingCity);
            orderPstmt.execute();

            // now handle all items in the cart

            ret = saveOrderItems();

            sb.clear();
            if (ret.equals("")) {
                con.commit();
            } else {
                con.rollback();  // end the transaction
            }
        } catch (Exception e) {
            try {
                con.rollback();    // failed, rollback the database
            } catch (Exception ee) {
            }
            throw new Exception("Error saving Order", e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                orderPstmt.close();
            } catch (Exception e) {
            }
            try {
                orderItemPstmt.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        return ret;
    }

    /**
     * Saves the different items in the order
     */
    private String saveOrderItems() throws Exception {

        // get the value of the last stored AUTO_INCREMENT variable
        // i. e. ORDER_ID
        Statement stmt2;
        stmt2 = con.createStatement();
        String sql2 = "SELECT AVAILABILITY FROM AVAILABILITY WHERE BOOK_ID = ";
        ResultSet rs2 = null;

        Statement stmt3;
        Statement stmt4;
        PreparedStatement stmt5;
        stmt3 = con.createStatement();
        String sql3 = "SELECT * FROM COMPOSITION WHERE EL_ID = ";
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        String sql4;
        stmt5 = con.prepareStatement("UPDATE AUTHORS SET QTY = ? WHERE AUTHOR_ID = ?"); 

        orderItemSQL = "INSERT INTO ORDER_ITEMS(ORDER_ID, ";
        orderItemSQL += "BOOK_ID, QUANTITY) VALUES (?,?,?)";
        stmt = con.prepareStatement("SELECT LAST_INSERT_ID()");
        rs = stmt.executeQuery();
        rs.next();
        int orderId = rs.getInt(1);

        Iterator<Object[]> iter = sb.getCart().iterator();
        BookBean bb = null;
        Object tmpArr[];

        //Loop over the entire collection, i.e the entire shopping cart

        orderItemPstmt = con.prepareStatement(orderItemSQL);
        while (iter.hasNext()) {

            tmpArr = iter.next();
            bb = (BookBean) tmpArr[0];
            rs2 = stmt2.executeQuery(sql2 + bb.getId());
            rs3 = stmt3.executeQuery(sql3 + bb.getId());
            rs2.next();

            orderItemPstmt.setInt(1, orderId);
            orderItemPstmt.setInt(2, bb.getId());
            orderItemPstmt.setInt(3, ((Integer) tmpArr[1]).intValue());
            if (((Integer) tmpArr[1]).intValue() <= rs2.getInt(1)) {
                orderItemPstmt.execute();
                while (rs3.next()) {
                    int qty = rs3.getInt("QTY");
                    int com_id = rs3.getInt("COM_ID");
                    stmt4 = con.createStatement();
                    sql4 = "SELECT * FROM AUTHORS WHERE AUTHOR_ID = " + com_id;
                    rs4 = stmt4.executeQuery(sql4);
                    rs4.next();
                    int in_stock = rs4.getInt("QTY");
                    int minska = in_stock - ((Integer) tmpArr[1]).intValue() * qty;
                    stmt5.setInt(1, minska);
                    stmt5.setInt(2, com_id);
                    stmt5.execute();
                }
            } else {
                return bb.getProduct() + " is not available in "
                        + ((Integer) tmpArr[1]).intValue() + " exemplares "
                        + "but just in " + rs2.getInt(1) + " at the moment.";
            }

        }
        return "";
    }
}

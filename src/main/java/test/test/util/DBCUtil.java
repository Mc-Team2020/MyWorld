package test.test.util;


import java.sql.*;
import java.util.ArrayList;
import test.test.Main;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBCUtil {
    private String USERNAME;
    private String PASSWORD;
    private String DRIVER;
    private String URL;
    private List<Connection> connections = new ArrayList<>();
    private int initalConnection = 10;

    public DBCUtil(Main main) {
        setDRIVER("com.mysql.jdbc.Driver");
        setPASSWORD(main.getConfig().getString("MYSQL.password"));
        setURL("jdbc:mysql://" + main.getConfig().getString("MYSQL.ip") + ":" + main.getConfig().getString("MYSQL.root") + "/" + main.getConfig().getString("MYSQL.table") + "?autoReconnect=true&useUnicode=true&characterEncoding=utf-8&useSSL=false");
        setUSERNAME(main.getConfig().getString("MYSQL.username"));
    }

    public synchronized void createPool() throws Exception {
        Driver driver = (Driver) (Class.forName(this.DRIVER).newInstance());
        DriverManager.registerDriver(driver); // 注册 JDBC 驱动程序
        for (int i = 0; i < initalConnection; i++) {
            getConnections().add(createConnection());
        }
    }

    public Connection getConnection() {
        Connection con = null;
        if(getConnections().isEmpty()){
            con = createConnection();
        }else {
            con = getConnections().get(getConnections().size());

        }
        return con;
    }

    public synchronized void closeResultSet(List<ResultSet> resultSets) {
        resultSets.forEach((v) -> {
            try {
                if (v != null) v.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }

    public synchronized void close(Statement statement, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (preparedStatement != null) preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public synchronized void returnConnection(Connection connention){
    }

    public Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getDRIVER() {
        return DRIVER;
    }

    public void setDRIVER(String DRIVER) {
        this.DRIVER = DRIVER;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}

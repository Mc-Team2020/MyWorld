package test.test.util;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import test.test.Main;

public class DataSource {
    private Main main;
    private HikariConfig hikariConfig;
    private HikariDataSource hikariDataSource;
    public DataSource(Main main){
        setMain(main);
        setHikariConfig(initHikariConfig());
        setHikariDataSource(new HikariDataSource(getHikariConfig()));
    }
    private HikariConfig initHikariConfig(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + main.getConfig().getString("MYSQL.ip") + ":" + main.getConfig().getString("MYSQL.root") + "/" + main.getConfig().getString("MYSQL.table"));
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        hikariConfig.setUsername(main.getConfig().getString("MYSQL.username"));
        hikariConfig.setPassword(main.getConfig().getString("MYSQL.password"));
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.setConnectionTimeout(1000);
        return hikariConfig;
    }
    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public HikariConfig getHikariConfig() {
        return hikariConfig;
    }

    public void setHikariConfig(HikariConfig hikariConfig) {
        this.hikariConfig = hikariConfig;
    }

    public HikariDataSource getHikariDataSource() {
        return hikariDataSource;
    }

    public void setHikariDataSource(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }
}
